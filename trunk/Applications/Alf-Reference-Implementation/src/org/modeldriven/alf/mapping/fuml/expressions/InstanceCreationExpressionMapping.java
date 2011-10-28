
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.ActivityGraph;
import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.mapping.fuml.common.ElementReferenceMapping;
import org.modeldriven.alf.mapping.fuml.expressions.InvocationExpressionMapping;
import org.modeldriven.alf.mapping.fuml.units.DataTypeDefinitionMapping;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.InstanceCreationExpression;

import fUML.Syntax.Actions.BasicActions.Action;
import fUML.Syntax.Actions.BasicActions.CallOperationAction;
import fUML.Syntax.Actions.BasicActions.InputPin;
import fUML.Syntax.Actions.CompleteActions.StartObjectBehaviorAction;
import fUML.Syntax.Actions.IntermediateActions.CreateObjectAction;
import fUML.Syntax.Actions.IntermediateActions.ValueSpecificationAction;
import fUML.Syntax.Activities.CompleteStructuredActivities.StructuredActivityNode;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;
import fUML.Syntax.Activities.IntermediateActivities.ForkNode;
import fUML.Syntax.Classes.Kernel.Class_;
import fUML.Syntax.Classes.Kernel.DataType;
import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.Classes.Kernel.InstanceValue;
import fUML.Syntax.Classes.Kernel.Property;

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
    public void mapTo(Action action) throws MappingError {
        super.mapTo(action);
        
        // Add a start behavior action if creating an instance of an active
        // class.
        if (action instanceof CreateObjectAction) {
            Class_ class_ = (Class_)((CreateObjectAction)action).classifier;
            
            if (class_.isActive) {            
                ForkNode fork = 
                    this.graph.addForkNode("Fork(" + this.resultSource.name + ")");
                
                this.graph.addObjectFlow(this.resultSource, fork);
                this.resultSource = fork;
                
                StartObjectBehaviorAction startAction = 
                    this.graph.addStartObjectBehaviorAction(class_);
                
                this.graph.addObjectFlow(fork, startAction.object);
                
                Collection<Element> elements = this.graph.getModelElements();
                this.graph = new ActivityGraph();
                this.action = this.graph.addStructuredActivityNode(
                        "InstanceCreationExpression@" + Integer.toHexString(
                                this.getInstanceCreationExpression().hashCode()),
                        elements);
            }
        }
    }
    
    @Override
    public Action mapAction() {
        InstanceCreationExpression instanceCreationExpression =
            this.getInstanceCreationExpression();
        
        if (instanceCreationExpression.getIsObjectCreation()) {
            return new CallOperationAction();
        } else {
            // Note: The mapping for a data value creation is placed inside
            // a structured activity node so that can act as the action with
            // input pins to which the results of mapping the tuple are
            // connected.
            return new StructuredActivityNode();
        }
    }
    
    @Override
    public void mapTargetTo(Action action) throws MappingError {
        InstanceCreationExpression instanceCreationExpression =
            this.getInstanceCreationExpression();
        
        if (!instanceCreationExpression.getIsObjectCreation()) {
            ElementReference referent = instanceCreationExpression.getReferent();
            FumlMapping mapping = this.fumlMap(referent);
            if (mapping instanceof ElementReferenceMapping) {
                mapping = ((ElementReferenceMapping)mapping).getMapping();
            }
            if (!(mapping instanceof DataTypeDefinitionMapping)) {
                this.throwError("Error mapping data type referent: " + 
                        mapping.getErrorMessage());
            } else {
                DataType dataType = (DataType)
                    ((DataTypeDefinitionMapping)mapping).getClassifier();
                ActivityGraph subgraph = new ActivityGraph();
                
                // Create a value specification action to create an instance
                // of the data type.
                ValueSpecificationAction valueAction = 
                    subgraph.addDataValueSpecificationAction(dataType);
                this.resultSource = valueAction.result;
                
                // Place the value specification action inside the structured
                // activity node. Create input pins on the structured activity 
                // node connected to property assignments for each attribute of 
                // the data type.
                Collection<InputPin> inputPins = new ArrayList<InputPin>();
                for (Property attribute: dataType.attribute) {
                    InputPin valuePin = new InputPin();
                    inputPins.add(valuePin);
                    this.resultSource = 
                        AssignmentExpressionMapping.mapPropertyAssignment(
                                attribute, subgraph, 
                                this.resultSource, valuePin);
                }
                StructuredActivityNode structuredNode =
                    this.graph.addStructuredActivityNode(
                            "Create(" + dataType.name +")", 
                            subgraph.getModelElements());
                for (InputPin inputPin: inputPins) {
                    structuredNode.addStructuredNodeInput(inputPin);
                }
            }
            
        } else if (!instanceCreationExpression.getImpl().getIsConstructorless()) {
            // Map the constructor call as a normal call operation action.
            super.mapTargetTo(action);                
            CallOperationAction callAction = (CallOperationAction)action;

            if (this.resultSource == null) {
                this.throwError("Constructor has no return result: " + 
                        callAction.operation.qualifiedName);
            }

            // Add a create object action to provide the target input to the
            // constructor call.
            
            Class_ class_ = callAction.operation.class_;

            InputPin targetPin = ActivityGraph.createInputPin(
                    callAction.name + ".target", class_, 1, 1);
            callAction.setTarget(targetPin);
            
            CreateObjectAction createAction = 
                this.graph.addCreateObjectAction(class_);
            this.graph.addObjectFlow(createAction.result, targetPin);
            
        } else {
            // NOTE: Instance creation expressions for classes defined in
            // Alf notation should never be constructorless.
            this.throwError("Constructorless instance creation expression mapping not implemented.");
        }
    }

	public InstanceCreationExpression getInstanceCreationExpression() {
		return (InstanceCreationExpression) this.getSource();
	}
	
	@Override
	public void print(String prefix) {
	    super.print(prefix);
	    
	    if (this.action instanceof StructuredActivityNode) {
	        for (ActivityNode node: ((StructuredActivityNode)this.action).node) {
	            if (node instanceof ValueSpecificationAction) {
	                System.out.println(prefix + " data type: " + 
	                        ((InstanceValue)((ValueSpecificationAction)node).value).
	                            instance.classifier.get(0));
	            }
	        }
	    }
	}

} // InstanceCreationExpressionMapping
