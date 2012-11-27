
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.expressions;

import org.modeldriven.alf.fuml.mapping.ActivityGraph;
import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.fuml.mapping.common.ElementReferenceMapping;
import org.modeldriven.alf.fuml.mapping.expressions.InvocationExpressionMapping;
import org.modeldriven.alf.fuml.mapping.units.DataTypeDefinitionMapping;
import org.modeldriven.alf.mapping.MappingError;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.InstanceCreationExpression;

import org.modeldriven.alf.uml.Action;
import org.modeldriven.alf.uml.CallOperationAction;
import org.modeldriven.alf.uml.Classifier;
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
     * element. The fork node is connected by an object node to the object input
     * pin of a start object behavior action. In this case, the entire mapping
     * is always placed within a structured activity node.
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
    
    @Override
    public Action mapAction() throws MappingError {
        Action action = super.mapAction();
        
        // Add a start behavior action if creating an instance of an active
        // class.
        if (action instanceof CallOperationAction) {
            Class_ class_ = ((CallOperationAction)action).getOperation().getClass_();
            
            if (class_.getIsActive()) {
                ForkNode fork = 
                    this.graph.addForkNode("Fork(" + this.resultSource.getName() + ")");                
                this.graph.addObjectFlow(this.resultSource, fork);
                
                StartObjectBehaviorAction startAction = 
                    this.graph.addStartObjectBehaviorAction(class_);         
                this.graph.addObjectFlow(fork, startAction.getObject());
                
                for (Classifier parent: class_.allParents()) {
                    if (parent instanceof Class_ && 
                            ((Class_) parent).getClassifierBehavior() != null) {
                        startAction = this.graph.addStartObjectBehaviorAction(
                                (Class_)parent);         
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
        ElementReference referent = instanceCreationExpression.getReferent();
        FumlMapping mapping = this.fumlMap(referent);
        if (mapping instanceof ElementReferenceMapping) {
            mapping = ((ElementReferenceMapping)mapping).getMapping();
        }
        if (!instanceCreationExpression.getIsObjectCreation()) {
            if (!(mapping instanceof DataTypeDefinitionMapping)) {
                this.throwError("Error mapping data type referent: " + 
                        mapping.getErrorMessage());
            } else {
                DataType dataType = (DataType)
                    ((DataTypeDefinitionMapping)mapping).getClassifier();
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
                
                for (Property attribute: dataType.getAttribute()) {
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
            }
            
        } else if (!instanceCreationExpression.getImpl().getIsConstructorless()) {
            // Map the constructor call as a normal call operation action.
            action = super.mapTarget();                
            CallOperationAction callAction = (CallOperationAction)action;

            if (this.resultSource == null) {
                this.throwError("Constructor has no return result: " + 
                        callAction.getOperation().getQualifiedName());
            }

            // Add a create object action to provide the target input to the
            // constructor call.
            CreateObjectAction createAction = 
                this.graph.addCreateObjectAction(callAction.getOperation().getClass_());
            this.graph.addObjectFlow(createAction.getResult(), callAction.getTarget());

        } else {
            // NOTE: Instance creation expressions for classes defined in
            // Alf notation should never be constructorless.
            this.throwError("Constructorless instance creation expression mapping not implemented.");
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
