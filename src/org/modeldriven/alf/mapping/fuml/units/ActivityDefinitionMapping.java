
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.mapping.fuml.units;

import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.mapping.fuml.FumlMappingFactory;
import org.modeldriven.alf.mapping.fuml.common.SyntaxElementMapping;
import org.modeldriven.alf.mapping.fuml.units.ClassifierDefinitionMapping;

import org.modeldriven.alf.syntax.common.AssignedSource;
import org.modeldriven.alf.syntax.statements.Block;
import org.modeldriven.alf.syntax.units.ActivityDefinition;

import org.modeldriven.alf.uml.*;

import fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution;

import java.util.Collection;

public class ActivityDefinitionMapping extends ClassifierDefinitionMapping {
    
    OpaqueBehaviorExecution execution = null;
    
    /**
     * 1. An activity definition that is not primitive maps to an activity. If
     * the activity is a classifier behavior, it is mapped as active
     * (isActive=true). Otherwise, it is mapped as passive (isActive=false). The
     * body of an activity maps as a block.
     * 
     * 2. An activity definition that is primitive maps to an opaque behavior.
     * An execution prototype for the opaque behavior is registered as a
     * primitive behavior with the execution factory at the execution locus for
     * the unit.
     * 
     * Activity Members (Formal Parameters)
     * 
     * 3. A formal parameter maps to an owned parameter of the activity.
     * 
     * 4. Each in and inout parameter of the activity maps to an input activity
     * parameter node for the parameter. Each such node is connected by an
     * outgoing object flow to a fork node. This fork node acts as the (initial)
     * assigned source for the values of the parameter within the activity.
     * 
     * 5. Each inout, out and return parameter of the activity maps to an output
     * activity parameter node for the parameter. For each inout and out
     * parameter, the activity includes an object flow from the assigned source,
     * if any, for the parameter name after the activity block.
     */
    
    // For the mapping of formal parameters, see FormalParameterMapping.
    // For the mappping of return parameters, see also ReturnStatementMapping.

    @Override
    public Classifier mapClassifier() {
        if (this.getActivityDefinition().getIsPrimitive()) {
            return this.create(OpaqueBehavior.class);
        } else {
            return this.create(Activity.class);
        }
    }
    
    @Override
    public void mapTo(Classifier classifier) throws MappingError {
        super.mapTo(classifier);

        ActivityDefinition definition = this.getActivityDefinition();
        if (classifier instanceof OpaqueBehavior) {
            this.execution = 
                ((FumlMappingFactory)this.getFactory()).
                    instantiatePrimitiveBehaviorPrototype
                        (definition, (OpaqueBehavior)classifier);
            if (this.execution == null) {
                this.throwError("Primitive behavior not implemented: " + classifier.getName());
            } else {
                getExecutionFactory().addPrimitiveBehaviorPrototype(this.execution);
            }
        } else {
            Activity activity = (Activity)classifier;
            activity.setIsActive(definition.getImpl().isClassifierBehavior());
        }
    }
    
    @Override
    public void mapBody() throws MappingError {
        Classifier classifier = this.getClassifier();
        if (!(classifier instanceof OpaqueBehavior)) {
            Block body = 
                this.getActivityDefinition().getImpl().getEffectiveBody();
            if (body != null) {
                FumlMapping bodyMapping = this.fumlMap(body);
                Collection<Element> elements = bodyMapping.getModelElements();
                addElements((Activity)classifier, elements, body, this);
            }
        }
    }

    @Override
    public void addMemberTo(Element element, NamedElement namespace) throws MappingError {
        if (!(element instanceof Parameter)) {
            this.throwError("Member that is not a parameter: " + element);
        } else {
            Parameter parameter = (Parameter)element;
            ((Behavior)namespace).addOwnedParameter(parameter);

            if (namespace instanceof Activity) {
                addParameterNodes((Activity)namespace, parameter, this);
            }
        }
    }
    
    public static void addParameterNodes(
            Activity activity, 
            Parameter parameter,
            FumlMapping mapping) {
        ActivityParameterNode node = mapping.create(ActivityParameterNode.class);
        node.setParameter(parameter);
        activity.addNode(node);

        if (parameter.getDirection().equals("in") || 
                parameter.getDirection().equals("inout")) {
            node.setName("Input(" + parameter.getName() + ")");

            ForkNode fork = mapping.create(ForkNode.class);
            fork.setName("Fork(" + parameter.getName() + ")");
            activity.addNode(fork);

            activity.addEdge(mapping.createActivityGraph().createObjectFlow(node, fork));

            if (parameter.getDirection().equals("inout")) {
                node = mapping.create(ActivityParameterNode.class);
                node.setParameter(parameter);
                activity.addNode(node);
            }
        }

        if (parameter.getDirection().equals("inout") || 
                parameter.getDirection().equals("out")) {
            node.setName("Output(" + parameter.getName() + ")");
        } else if (parameter.getDirection().equals("return_")) {
            node.setName("Return");
        }
    }
    
    public Behavior getBehavior() throws MappingError {
        return (Behavior)this.getClassifier();
    }
    
	public ActivityDefinition getActivityDefinition() {
		return (ActivityDefinition)this.getSource();
	}
	
	public static void addElements(
	        Activity activity, 
	        Collection<Element> elements,
	        Block body,
	        FumlMapping mapping) throws MappingError {
	    
        for (Element element: elements) {
            if (element instanceof ActivityNode) {
                activity.addNode((ActivityNode)element);
            } else if (element instanceof ActivityEdge) {
                activity.addEdge((ActivityEdge)element);
            } else {
                mapping.throwError("Element not an activity node or edge: " + element);
            }
        }
        
        // Connect final output parameter assigned sources to corresponding 
        // output parameter nodes.
        for (Parameter parameter: activity.getOwnedParameter()) {
            if (parameter.getDirection().equals("out") ||
                    parameter.getDirection().equals("inout")) {
                String name = parameter.getName();
                AssignedSource assignment = 
                    body.getImpl().getAssignmentAfter(name);
                if (assignment != null) {
                    FumlMapping sourceMapping = 
                        mapping.fumlMap(assignment.getSource());
                    if (!(sourceMapping instanceof SyntaxElementMapping)) {
                        mapping.throwError("Error mapping parameter " + 
                                name + ": " + mapping.getErrorMessage());
                    } else {
                        ActivityNode source = ((SyntaxElementMapping)sourceMapping).
                                getAssignedValueSource(name);
                        activity.addEdge(mapping.createActivityGraph().createObjectFlow(
                                source, 
                                getOutputParameterNode(activity, parameter)));
                    }
                }
            }
        }
    }

    public static ForkNode getInputParameterFork(
            Activity activity, Parameter parameter) {
        if (activity != null) {
            for (ActivityNode node: activity.getNode()) {
                if (node instanceof ActivityParameterNode && 
                        ((ActivityParameterNode)node).getParameter() == parameter &&
                        node.getOutgoing().size() > 0) {
                    return (ForkNode)node.getOutgoing().get(0).getTarget();
                }
            }
        }
        return null;
    }
    
    public static ActivityParameterNode getOutputParameterNode(
            Activity activity, Parameter parameter) {
        if (activity != null) {
            for (ActivityNode node: activity.getNode()) {
                if (node instanceof ActivityParameterNode && 
                        ((ActivityParameterNode)node).getParameter() == parameter &&
                        node.getOutgoing().size() == 0) {
                    return (ActivityParameterNode)node;
                }
            }
        }
        return null;
    }
    
    public static ActivityFinalNode getFinalNode(
            Activity activity,
            FumlMapping mapping) {
        if (activity != null) {
            for (ActivityNode node: activity.getNode()) {
                if (node instanceof ActivityFinalNode) {
                    return (ActivityFinalNode)node;
                }
            }
        }
        ActivityFinalNode node = mapping.create(ActivityFinalNode.class);
        node.setName("Final");
        activity.addNode(node);
        return node;
    }

    @Override
    public String toString() {
        Behavior behavior = (Behavior)this.getElement();
        return super.toString() + 
            (behavior == null? "": " isActive:" + behavior.getIsActive());
    }
    
    @Override
    public void print(String prefix) {
        super.print(prefix);
        
        ActivityDefinition source = this.getActivityDefinition();
        Block body = source.getImpl().getEffectiveBody();
        if (body != null) {
            System.out.println(prefix + " body:");
            Mapping mapping = body.getImpl().getMapping();
            if (mapping != null) {
                mapping.printChild(prefix);
            }
        }
        
        if (this.execution != null) {
            System.out.println(prefix + " execution: " + execution);
        }
    }

} // ActivityDefinitionMapping
