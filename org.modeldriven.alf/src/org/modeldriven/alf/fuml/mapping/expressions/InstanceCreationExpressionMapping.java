/*******************************************************************************
 * Copyright 2011-2017 Data Access Technologies, Inc. (Model Driven Solutions)
 * 
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.expressions;

import org.modeldriven.alf.fuml.mapping.ActivityGraph;
import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.fuml.mapping.common.ElementReferenceMapping;
import org.modeldriven.alf.fuml.mapping.expressions.InvocationExpressionMapping;
import org.modeldriven.alf.fuml.mapping.units.ClassDefinitionMapping;
import org.modeldriven.alf.fuml.mapping.units.DataTypeDefinitionMapping;
import org.modeldriven.alf.mapping.MappingError;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.common.impl.ElementReferenceImpl;
import org.modeldriven.alf.syntax.expressions.InstanceCreationExpression;

import org.modeldriven.alf.uml.Action;
import org.modeldriven.alf.uml.ActivityEdge;
import org.modeldriven.alf.uml.CallOperationAction;
import org.modeldriven.alf.uml.InputPin;
import org.modeldriven.alf.uml.OutputPin;
import org.modeldriven.alf.uml.StartObjectBehaviorAction;
import org.modeldriven.alf.uml.CreateObjectAction;
import org.modeldriven.alf.uml.ValueSpecificationAction;
import org.modeldriven.alf.uml.StructuredActivityNode;
import org.modeldriven.alf.uml.ActivityNode;
import org.modeldriven.alf.uml.ForkNode;
import org.modeldriven.alf.uml.Class_;
import org.modeldriven.alf.uml.DataType;
import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.InstanceValue;
import org.modeldriven.alf.uml.Property;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InstanceCreationExpressionMapping extends
		InvocationExpressionMapping {

    /**
     * 1. An instance creation expression maps as an object creation expression
     * or a data value creation expression.
     * 
     * Object Creation Expression
     * 
     * 2. An object creation expression maps as either a constructed object
     * creation or a constructorless object creation. If the class of the object
     * being created is an active class, then the mapping also includes the
     * starting of the behavior of that object.
     * 
     * 3. If the object creation expression is not constructorless, then the
     * expression maps to a create object action for the class of the
     * constructor operation. The result of the create object action is used as
     * the target instance for an invocation of the constructor, mapped as for a
     * feature invocation expression. The result source element of the object
     * creation expression is the result output pin of the call operation action
     * for the constructor operation.
     * 
     * 4. If the object creation expression is constructorless, then the
     * expression maps to a create object action for the identified class. If
     * none of the attributes owned or inherited by the class have default
     * values, then the result source element of the expression is the result
     * output pin of the create object action. Otherwise, the result output pin
     * of the create object action is connected by an object flow to a
     * control-flow sequenced set of structured activity nodes containing write
     * structural feature actions for setting the default values of any
     * attributes of the newly create object that have them.
     * 
     * 5. If the class of the object being created is an active class, then a
     * fork node is added to the mapping with an object flow from the original
     * result source element, and that fork node becomes the new result source
     * element. The fork node is connected by object flows to the object input
     * pins of start object behavior actions for the class of the object being
     * created and for each direct or indirect parent class that has a
     * classifier behavior, unless that classifier behavior is redefined in
     * another parent class or in the class of the object being created. In this
     * case, the entire mapping is always placed within a structured activity
     * node.
     * 
     * Data Value Creation Expression
     * 
     * 6. A data value creation expression maps to a value specification action
     * with an instance value for the named data type. If the tuple for the
     * expression is non-empty, then the value specification action is the
     * target of a control flow whose source is the structured activity node
     * mapped from the tuple. Further, the result of the value specification
     * action is fed through a sequence of write structural feature actions with
     * values coming from the result source elements for the argument
     * expressions. If the data value creation expression has an empty tuple,
     * then the result source element is the result pin of the value
     * specification action. Otherwise, the result source element is the result
     * of the sequence of write structural feature actions.
     */
    
    /**
     * Exclude classifiers whose classifier behaviors are redefined by the 
     * classifier behavior of the given classifier.
     */
    private static void excludeRedefinitions(
            Collection<ElementReferenceImpl> excluded, ElementReference classifier) {
        ElementReference behavior = classifier.getImpl().getClassifierBehavior();
        if (behavior != null) {
            for (ElementReference redefinedBehavior: 
                behavior.getImpl().getRedefinedElements()) {
                ElementReference context = redefinedBehavior.getImpl().getContext();
                if (context != null) {
                    excluded.add(context.getImpl());
                }
            }
        }
    }
    
    @Override
    public Action mapAction() throws MappingError {
        Action action = super.mapAction();
        ActivityNode callAction = !(action instanceof StructuredActivityNode)? action:
            ((StructuredActivityNode)action).getNode().get(0);
        
        // Add a start behavior action if creating an instance of an active
        // class.
        if (callAction instanceof CallOperationAction || callAction instanceof CreateObjectAction) {
            Class_ class_ = callAction instanceof CallOperationAction?
                    ((CallOperationAction)callAction).getOperation().getClass_():
                    (Class_)(((CreateObjectAction)callAction).getClassifier());
            
            if (class_.getIsActive()) {
                ForkNode fork = 
                    this.graph.addForkNode("Fork(" + this.resultSource.getName() + ")");                
                this.graph.addObjectFlow(this.resultSource, fork);
                
                StartObjectBehaviorAction startAction = 
                    this.graph.addStartObjectBehaviorAction(class_);         
                this.graph.addObjectFlow(fork, startAction.getObject());
                
                // NOTE: The generalizations of class_ may not have been mapped
                // yet, so we need to go back to the abstract syntax to
                // get parent class definitions.
                ElementReference referent = 
                        this.getInstanceCreationExpression().getBoundReferent();
                if (referent.getImpl().isOperation()) {
                    referent = referent.getImpl().getNamespace();
                }
                
                Collection<ElementReference> allParents = referent.getImpl().allParents();

                // Exclude parents whose classifier behaviors are redefined.
                // NOTE: ElementReferenceImpl is used in the set because equals and hashCode
                // are redefined to be in terms of the referenced element.
                Set<ElementReferenceImpl> excludedParents = new HashSet<ElementReferenceImpl>();
                excludeRedefinitions(excludedParents, referent);
                for (ElementReference parent: allParents) {
                    excludeRedefinitions(excludedParents, parent);
                }
                
                for (ElementReference parent: allParents) {
                    if (parent.getImpl().isClass() &&
                        parent.getImpl().getClassifierBehavior() != null &&
                        !excludedParents.contains(parent.getImpl())) {
                        Class_ superclass = (Class_)parent.getImpl().getUml();
                        if (superclass == null) {
                            FumlMapping mapping = this.fumlMap(parent);
                            if (mapping instanceof ElementReferenceMapping) {
                                mapping = ((ElementReferenceMapping) mapping).
                                        getMapping();
                            }
                            if (!(mapping instanceof ClassDefinitionMapping)) {
                                this.throwError("Error mapping parent class", mapping);
                            }
                            superclass = (Class_)((ClassDefinitionMapping)mapping).
                                    getClassifierOnly();
                        }
                        startAction = this.graph.addStartObjectBehaviorAction(
                                superclass);         
                        this.graph.addObjectFlow(fork, startAction.getObject());
                    }
                }
                
                Collection<Element> elements = this.graph.getModelElements();
                this.graph = this.createActivityGraph();
                action = 
                    this.graph.addStructuredActivityNode(
                        "InstanceCreationExpression@" +
                                this.getInstanceCreationExpression().getId(),
                        elements);
                
                OutputPin pin = this.graph.createOutputPin(
                        "Output(" + this.resultSource.getName() + ")", 
                        class_, 1, 1);
                ((StructuredActivityNode)action).addStructuredNodeOutput(pin);
                ActivityGraph subgraph = this.createActivityGraph();
                subgraph.addObjectFlow(fork, pin);
                this.graph.addToStructuredNode(
                        (StructuredActivityNode)action, 
                        subgraph.getModelElements());                
                this.resultSource = pin;
            }
        }
        
        return action;
    }
    
    @Override
    public Action mapTarget() throws MappingError {
        InstanceCreationExpression instanceCreationExpression =
            this.getInstanceCreationExpression();
        Action action = null;
        if (!instanceCreationExpression.getIsObjectCreation()) {
            ElementReference referent = instanceCreationExpression.getBoundReferent();
            DataType dataType = (DataType)referent.getImpl().getUml();
            if (dataType == null) {
                FumlMapping mapping = this.fumlMap(referent);
                if (mapping instanceof ElementReferenceMapping) {
                    mapping = ((ElementReferenceMapping)mapping).getMapping();
                }
                if (!(mapping instanceof DataTypeDefinitionMapping)) {
                    this.throwError("Error mapping data type referent", mapping);
                } else {
                    dataType = (DataType)
                        ((DataTypeDefinitionMapping)mapping).getClassifier();
                }
            }
            ActivityGraph subgraph = this.createActivityGraph();

            // Create a value specification action to create an instance
            // of the data type.
            ValueSpecificationAction valueAction = 
                    subgraph.addDataValueSpecificationAction(dataType);
            this.resultSource = valueAction.getResult();

            // Place the value specification action inside the structured
            // activity node. Create input pins on the structured activity 
            // node connected to property assignments for each attribute of 
            // the data type.
            //
            // Note: The mapping for a data value creation is placed inside
            // a structured activity node so that it can act as the action
            // with input pins to which the results of mapping the tuple are
            // connected.

            StructuredActivityNode structuredNode =
                    this.graph.addStructuredActivityNode(
                            "Create(" + dataType.getQualifiedName() +")", 
                            new ArrayList<Element>());

            for (Property attribute: ActivityGraph.getAllAttributes(dataType)) {
                InputPin valuePin = this.graph.createInputPin(
                        structuredNode.getName() + 
                        ".input(" + attribute.getQualifiedName() + ")", 
                        attribute.getType(), 
                        attribute.getLower(), 
                        attribute.getUpper());
                structuredNode.addStructuredNodeInput(valuePin);
                this.resultSource = 
                        AssignmentExpressionMapping.mapPropertyAssignment(
                                attribute, subgraph, 
                                this.resultSource, valuePin, this);
            }

            this.graph.addToStructuredNode(
                    structuredNode, 
                    subgraph.getModelElements());
            action = structuredNode;

        } else if (!instanceCreationExpression.getImpl().getIsConstructorless()) {
            // Map the constructor call as a normal call operation action.
            action = super.mapTarget();                
            CallOperationAction callAction = (CallOperationAction)action;

//            if (this.resultSource == null) {
//                this.throwError("Constructor has no return result: " + 
//                        callAction.getOperation().getQualifiedName());
//            }

            // Add a create object action to provide the target input to the
            // constructor call and the result of the expression.
            CreateObjectAction createAction = 
                this.graph.addCreateObjectAction(callAction.getOperation().getClass_());
            ForkNode fork = this.graph.addForkNode("Fork(" + createAction.getResult().getName() + ")");
            this.graph.addObjectFlow(createAction.getResult(), fork);
            this.graph.addObjectFlow(fork, callAction.getTarget());
            
            // NOTE: By making the result source the result of the create object action,
            // this mapping will work even if the constructor operation does not return
            // the constructed object, as it should according to UML rules.
            this.resultSource = fork;

        } else {
            // TODO: Handle attribute initialization for constructorless instance
            // creation.
            // NOTE: Instance creation expressions for classes defined in
            // Alf notation should never be constructorless.
            
            ElementReference referent = instanceCreationExpression.getBoundReferent();
            Class_ class_ = (Class_)referent.getImpl().getUml();
            if (class_ == null) {
                FumlMapping mapping = this.fumlMap(referent);
                if (mapping instanceof ElementReferenceMapping) {
                    mapping = ((ElementReferenceMapping)mapping).getMapping();
                }
                if (!(mapping instanceof ClassDefinitionMapping)) {
                    this.throwError("Error mapping class referent", mapping);
                } else {
                    class_ = (Class_)
                        ((ClassDefinitionMapping)mapping).getClassifier();
                }
            }
            CreateObjectAction createAction = this.graph.addCreateObjectAction(class_);
            this.resultSource = createAction.getResult();
            action = createAction;
        }
        return action;
    }
    
    public Action mapFeature(Action action) throws MappingError {
        // NOTE: An instance creation expression has no feature, but the mapping of a constructor
        // call needs to be wrapped at this point to ensure that the (forked) result of the object
        // create action does not flow before the constructor call has completed.
        if (!(this.resultSource instanceof OutputPin)) {
            this.graph.remove(action);
            this.graph.remove(this.resultSource);
            Collection<Element> elements = new ArrayList<Element>();
            elements.add(action);
            elements.add(this.resultSource);
            
            // Note: This will always be the create object action.
            ActivityNode source = (ActivityNode)this.resultSource.getIncoming().get(0).getSource().getOwner();
            
            List<ActivityEdge> incoming = action.getIncoming();
            
            StructuredActivityNode node = wrapAction(action, this.graph, elements, this.resultSource, null, 1, 1);
            this.resultSource = node.getStructuredNodeOutput().get(0);
            
            this.graph.addControlFlow(source, node);
            for (Object object: incoming.toArray()) {
                ActivityEdge edge = (ActivityEdge)object;
                action.removeIncoming(edge);
                edge.setTarget(node);
            }
            
            action = node;            
        }
        return action;
    }
    
	public InstanceCreationExpression getInstanceCreationExpression() {
		return (InstanceCreationExpression) this.getSource();
	}
	
	@Override
	public void print(String prefix) {
	    super.print(prefix);
	    
	    InstanceCreationExpression expression = this.getInstanceCreationExpression();
	    if (!expression.getIsObjectCreation() &&
	            this.action instanceof StructuredActivityNode) {
	        for (ActivityNode node: ((StructuredActivityNode)this.action).getNode()) {
	            if (node instanceof ValueSpecificationAction) {
	                System.out.println(prefix + " data type: " + 
	                        ((InstanceValue)((ValueSpecificationAction)node).getValue()).
	                            getInstance().getClassifier().get(0));
	            }
	        }
	    }
	}

} // InstanceCreationExpressionMapping
