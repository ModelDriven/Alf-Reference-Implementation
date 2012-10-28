
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.units;

import org.modeldriven.alf.fuml.mapping.ActivityGraph;
import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.fuml.mapping.common.ElementReferenceMapping;
import org.modeldriven.alf.fuml.mapping.units.NamespaceDefinitionMapping;
import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.statements.Block;
import org.modeldriven.alf.syntax.units.FormalParameter;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.OperationDefinition;
import org.modeldriven.alf.syntax.units.RootNamespace;

import org.modeldriven.alf.uml.*;

import java.util.Collection;
import java.util.List;

public class OperationDefinitionMapping extends NamespaceDefinitionMapping {

    private Operation operation = null;
    
    /**
     * 1. An operation definition maps to an operation with the given name and
     * isAbstract value that is an owned operation of the class mapped from the
     * class definition that is the namespace of the operation definition.
     * 
     * 2. A formal parameter that is a member of an operation definition maps to
     * an owned parameter of the operation.
     * 
     * 3. If an operation declaration has redefined operations, then the
     * operation has the these redefined operations.
     * 
     * 4. If an operation definition has a body, then the operation has an
     * associated activity (owned by the class of the operation) as its method,
     * with the body mapped as if it was the body of an activity definition for
     * this activity. The activity has an owned parameter corresponding, in
     * order, to each owned parameter of the operation, with the same name,
     * type, multiplicity and direction as the operation parameter.
     * 
     * 5. If an operation definition is a stub, then its associated subunit maps
     * to an activity. This activity becomes the method of the operation mapped
     * from the operation definition.
     * 
     * Constructors
     * 
     * 6. If the operation definition is a constructor, then it has an implicit
     * return parameter with the owning class as its type. Further, the default
     * constructor behavior is included in the mapping of the operation body,
     * sequentially before the explicit behavior defined for the constructor.
     */
    
    // For the mapping of formal parameters, see FormalParameterMapping.
    // Subunits are handled by NamespaceDefinitionMapping.
    
    public void mapTo(Operation operation) throws MappingError {
        super.mapTo(operation);

        OperationDefinition definition = this.getOperationDefinition();

        // NOTE: Using getReturnParameter ensures that a constructor gets
        // a return parameter even if one is not given explicitly in the
        // operation definition.
        FormalParameter returnFormalParameter = definition.getImpl().getReturnParameter();
        if (returnFormalParameter != null) {
            FumlMapping returnParameterMapping = this.fumlMap(returnFormalParameter);
            if (!(returnParameterMapping instanceof FormalParameterMapping)) {
                this.throwError("Error mapping return parameter: " + 
                        returnParameterMapping.getErrorMessage());
            } else  {
                operation.addOwnedParameter(((FormalParameterMapping)
                        returnParameterMapping).getParameter());
            }
        }
        
        NamespaceDefinition namespace = definition.getNamespace();
        FumlMapping mapping = this.fumlMap(namespace);
        if (!(mapping instanceof ClassDefinitionMapping)) {
            this.throwError("Error mapping class for operation: " + 
                    mapping.getErrorMessage());
        } else {
            // NOTE: The following ensures that the class property is set for an
            // operation, even if it has not been added as a member to its class
            // yet.
            operation.setClass_( 
                (Class_)((ClassDefinitionMapping)mapping).getClassifierOnly());

            if (definition.getIsAbstract()) {
                operation.setIsAbstract(true);
            } else {
                Activity activity = this.create(Activity.class);
                // Give the method activity a name to aid in execution tracing.
                activity.setName(makeDistinguishableName(
                        namespace, operation.getName() + "$method"));
                operation.getClass_().addOwnedBehavior(activity);
                operation.addMethod(activity);
                
                for (Parameter parameter: operation.getOwnedParameter()) {
                    Parameter copy = this.create(Parameter.class);
                    copy.setName(parameter.getName());
                    copy.setDirection(parameter.getDirection());
                    copy.setLower(parameter.getLower());
                    copy.setUpper(parameter.getUpper());
                    copy.setType(parameter.getType());
                    copy.setIsOrdered(parameter.getIsOrdered());
                    copy.setIsUnique(parameter.getIsUnique());
                    activity.addOwnedParameter(copy);
                    ActivityDefinitionMapping.addParameterNodes(activity, copy, this);
                }
            }
        }
        
        for (ElementReference redefinedOperation: 
            definition.getRedefinedOperation()) {
            mapping = this.fumlMap(redefinedOperation);
            if (mapping instanceof ElementReferenceMapping) {
                mapping = ((ElementReferenceMapping)mapping).getMapping();
            }
            if (!(mapping instanceof OperationDefinitionMapping)) {
                this.throwError("Error mapping redefined operation " + 
                        redefinedOperation.getImpl().getQualifiedName() + ": " +
                        mapping.getErrorMessage());
            } else {
                operation.addRedefinedOperation(
                        ((OperationDefinitionMapping)mapping).getOperation());
            }
        }
    }
    
    @Override
    public void mapBody() throws MappingError {
        OperationDefinition definition = this.getOperationDefinition();
        
        if (!definition.getIsAbstract()) {
            Operation operation = this.getOperation();
            Activity activity = (Activity)operation.getMethod().get(0);

            Collection<Element> elements;

            Block body = definition.getImpl().getEffectiveBody();
            if (!definition.getIsConstructor()) {
                FumlMapping bodyMapping = this.fumlMap(body);
                elements = bodyMapping.getModelElements();
            } else {
                ActivityGraph graph = this.createActivityGraph();
                
                // Add constructor body.
                Block bodySegment =
                        definition.getImpl().getBodySegement();
                FumlMapping bodyMapping = this.fumlMap(bodySegment);
                StructuredActivityNode bodyNode = 
                    graph.addStructuredActivityNode(
                            "Body", bodyMapping.getModelElements());

                // Return context object as the constructor result.
                ReadSelfAction readSelfAction = 
                        graph.addReadSelfAction(operation.getClass_());
                ActivityNode selfFork = graph.addForkNode(
                        "Fork(" + readSelfAction.getResult().getName() + ")");
                graph.addObjectFlow(readSelfAction.getResult(), selfFork);
                
                Parameter returnParameter = null;
                for (Parameter parameter: activity.getOwnedParameter()) {
                    if (parameter.getDirection().equals("return")) {
                        returnParameter = parameter;
                        break;
                    }
                }                
                graph.addObjectFlow(selfFork,
                        ActivityDefinitionMapping.getOutputParameterNode(
                                activity, returnParameter));

                // Add default constructor behavior.
                if (!definition.getImpl().hasAlternativeConstructorCall()) {
                    
                    // Add super constructor invocations.
                    Block superInvocationSegment = 
                            definition.getImpl().getSuperInvocationSegment();
                    ActivityNode node = null;
                    if (!superInvocationSegment.getStatement().isEmpty()) {
                        FumlMapping mapping = this.fumlMap(superInvocationSegment);
                        node = graph.addStructuredActivityNode(
                                "Super", mapping.getModelElements());
                    }

                    // Add call to local initialization operation.
                    FumlMapping mapping = this.fumlMap(definition.getNamespace());
                    if (!(mapping instanceof ClassDefinitionMapping)) {
                        this.throwError("Error mapping class: " + 
                                mapping.getErrorMessage());
                    } else {
                        ClassDefinitionMapping classMapping = 
                                (ClassDefinitionMapping)mapping;
                        CallOperationAction callAction = 
                                graph.addCallOperationAction(
                                        classMapping.getInitializationOperation());
                        graph.addObjectFlow(selfFork, callAction.getTarget());
                        graph.addControlFlow(callAction, bodyNode);
                        if (node == null) {
                            node = callAction;
                        } else {
                            graph.addControlFlow(node, callAction);                        
                        }
                        
                        // Use decision node to skip constructor if this object is
                        // already initialized.
                        Property initializationFlag = classMapping.getInitializationFlag();
                        ActivityNode initialNode = graph.addInitialNode("InitialNode");
                        ReadStructuralFeatureAction readAction = 
                                graph.addReadStructuralFeatureAction(initializationFlag);
                        CallBehaviorAction testAction = graph.addCallBehaviorAction(
                                getBehavior(RootNamespace.getSequenceFunctionIsEmpty()));
                        graph.addObjectFlow(selfFork, readAction.getObject());
                        graph.addObjectFlow(readAction.getResult(), testAction.getArgument().get(0));
                        graph.addControlDecisionNode(
                                "Test(" + initializationFlag.getName() + ")", 
                                initialNode, testAction.getResult().get(0), 
                                node, null);
                    }
                    
                }
                
                elements = graph.getModelElements();
            }

            ActivityDefinitionMapping.addElements(activity, elements, body, this);
        }
    }
    
    @Override
    public void addMemberTo(Element element, NamedElement namespace) throws MappingError {
        if (!(element instanceof Parameter)) {
            this.throwError("Member not a parameter: " + element);
        } else if (!((Parameter)element).getDirection().equals("return")) {
            // Note: An operation is a namespace in full UML, but not in fUML,
            // so the "namespace" parameter actually has the type "NamedElement".
            ((Operation)namespace).addOwnedParameter((Parameter)element);
        }
    }

    @Override
    public Element getElement() {
        return this.operation;
    }
    
    @Override
	public NamedElement getNamedElement() throws MappingError {
        return this.getOperation();
	}

    public Operation getOperation() throws MappingError {
        if (this.operation == null) {
            this.operation = this.create(Operation.class);
            this.mapTo(this.operation);
        }
        return this.operation;
    }

	public OperationDefinition getOperationDefinition() {
		return (OperationDefinition) this.getSource();
	}
	
	@Override
	public String toString() {
	    return super.toString() + " isAbstract:" + this.operation.getIsAbstract();
	}
	
	@Override
	public void print(String prefix) {
	    super.print(prefix);
	    
	    if (this.operation != null) {
	        System.out.println(prefix + " operation: " + operation);
	        List<Operation> redefinedOperations = 
	            this.operation.getRedefinedOperation();
	        if (!redefinedOperations.isEmpty()) {
	            System.out.println(prefix + " redefinedOperation:");
	            for (Operation redefinedOperation: redefinedOperations) {
	                System.out.println(prefix + "  " + redefinedOperation);
	            }
	        }
	    }
	    
        OperationDefinition definition = this.getOperationDefinition();
        if (!definition.getIsAbstract()) {
            Block body = definition.getImpl().getEffectiveBody();
            if (body != null) {
                Mapping bodyMapping = 
                    body.getImpl().getMapping();
                if (bodyMapping != null) {
                    System.out.println(prefix + " method:");
                    bodyMapping.printChild(prefix);
                }
            }
        }
	}

} // OperationDefinitionMapping
