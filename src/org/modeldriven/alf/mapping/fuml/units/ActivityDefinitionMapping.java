
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

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

import fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution;
import fUML.Syntax.Activities.IntermediateActivities.Activity;
import fUML.Syntax.Activities.IntermediateActivities.ActivityEdge;
import fUML.Syntax.Activities.IntermediateActivities.ActivityFinalNode;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;
import fUML.Syntax.Activities.IntermediateActivities.ActivityParameterNode;
import fUML.Syntax.Activities.IntermediateActivities.ForkNode;
import fUML.Syntax.Activities.IntermediateActivities.ObjectFlow;
import fUML.Syntax.Classes.Kernel.Classifier;
import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.Classes.Kernel.NamedElement;
import fUML.Syntax.Classes.Kernel.Parameter;
import fUML.Syntax.Classes.Kernel.ParameterDirectionKind;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.OpaqueBehavior;

import java.util.Collection;

public class ActivityDefinitionMapping extends ClassifierDefinitionMapping {
    
    OpaqueBehaviorExecution execution = null;

    @Override
    public Classifier mapClassifier() {
        if (this.getActivityDefinition().getIsPrimitive()) {
            return new OpaqueBehavior();
        } else {
            return new Activity();
        }
    }
    
    @Override
    public void mapTo(Classifier classifier) throws MappingError {
        super.mapTo(classifier);
        // System.out.println("[mapTo] activity=" + classifier.name);

        ActivityDefinition definition = this.getActivityDefinition();
        if (classifier instanceof OpaqueBehavior) {
            this.execution = 
                ((FumlMappingFactory)this.getFactory()).
                    instantiatePrimitiveBehaviorPrototype
                        (definition, (OpaqueBehavior)classifier);
            if (this.execution == null) {
                this.throwError("Primitive behavior not implemented: " + classifier.name);
            } else {
                getExecutionFactory().addPrimitiveBehaviorPrototype(this.execution);
            }
        } else {
            Activity activity = (Activity)classifier;
            /*
            Block body = definition.getImpl().getEffectiveBody();
            if (body != null) {
                FumlMapping bodyMapping = this.fumlMap(body);
                Collection<Element> elements = bodyMapping.getModelElements();
                addElements(activity, elements, body, this);
            }
            */
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
            // System.out.println("[addMemberTo] activity=" + namespace.name + " parameter=" + parameter.name);
            ((Behavior)namespace).addOwnedParameter(parameter);

            if (namespace instanceof Activity) {
                addParameterNodes((Activity)namespace, parameter);
            }
        }
    }
    
    public static void addParameterNodes(Activity activity, Parameter parameter) {
        ActivityParameterNode node = new ActivityParameterNode();
        node.setParameter(parameter);
        activity.addNode(node);

        if (parameter.direction == ParameterDirectionKind.in || 
                parameter.direction == ParameterDirectionKind.inout) {
            node.setName("Input(" + parameter.name + ")");

            ForkNode fork = new ForkNode();
            fork.setName("Fork(" + parameter.name + ")");
            activity.addNode(fork);

            ObjectFlow flow = new ObjectFlow();
            flow.setSource(node);
            flow.setTarget(fork);
            activity.addEdge(flow);

            if (parameter.direction == ParameterDirectionKind.inout) {
                node = new ActivityParameterNode();
                node.setParameter(parameter);
                activity.addNode(node);
            }
        }

        if (parameter.direction == ParameterDirectionKind.inout || parameter.direction == ParameterDirectionKind.out) {
            node.setName("Output(" + parameter.name + ")");
        } else if (parameter.direction == ParameterDirectionKind.return_) {
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
        for (Parameter parameter: activity.ownedParameter) {
            if (parameter.direction == ParameterDirectionKind.out ||
                    parameter.direction == ParameterDirectionKind.inout) {
                String name = parameter.name;
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
                        ObjectFlow flow = new ObjectFlow();
                        flow.setSource(source);
                        flow.setTarget(getOutputParameterNode(activity, parameter));
                        activity.addEdge(flow);
                    }
                }
            }
        }
    }

    public static ForkNode getInputParameterFork(
            Activity activity, Parameter parameter) {
        if (activity != null) {
            for (ActivityNode node: activity.node) {
                if (node instanceof ActivityParameterNode && 
                        ((ActivityParameterNode)node).parameter == parameter &&
                        node.outgoing.size() > 0) {
                    return (ForkNode)node.outgoing.get(0).target;
                }
            }
        }
        return null;
    }
    
    public static ActivityParameterNode getOutputParameterNode(
            Activity activity, Parameter parameter) {
        if (activity != null) {
            for (ActivityNode node: activity.node) {
                if (node instanceof ActivityParameterNode && 
                        ((ActivityParameterNode)node).parameter == parameter &&
                        node.outgoing.size() == 0) {
                    return (ActivityParameterNode)node;
                }
            }
        }
        return null;
    }
    
    public static ActivityFinalNode getFinalNode(Activity activity) {
        if (activity != null) {
            for (ActivityNode node: activity.node) {
                if (node instanceof ActivityFinalNode) {
                    return (ActivityFinalNode)node;
                }
            }
        }
        ActivityFinalNode node = new ActivityFinalNode();
        node.setName("Final");
        activity.addNode(node);
        return node;
    }

    @Override
    public String toString() {
        Behavior behavior = (Behavior)this.getElement();
        return super.toString() + 
            (behavior == null? "": " isActive:" + behavior.isActive);
    }
    
    @Override
    public void print(String prefix) {
        super.print(prefix);
        
        ActivityDefinition source = this.getActivityDefinition();
        Block body = source.getBody();
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
