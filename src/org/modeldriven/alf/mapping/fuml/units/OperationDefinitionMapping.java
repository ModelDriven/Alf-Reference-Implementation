
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
import org.modeldriven.alf.mapping.fuml.ActivityGraph;
import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.mapping.fuml.common.ElementReferenceMapping;
import org.modeldriven.alf.mapping.fuml.units.NamespaceDefinitionMapping;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.statements.Block;
import org.modeldriven.alf.syntax.units.FormalParameter;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.OperationDefinition;

import fUML.Syntax.Actions.IntermediateActions.ReadSelfAction;
import fUML.Syntax.Activities.CompleteStructuredActivities.StructuredActivityNode;
import fUML.Syntax.Activities.IntermediateActivities.Activity;
import fUML.Syntax.Classes.Kernel.Class_;
import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.Classes.Kernel.NamedElement;
import fUML.Syntax.Classes.Kernel.Operation;
import fUML.Syntax.Classes.Kernel.OperationList;
import fUML.Syntax.Classes.Kernel.Parameter;
import fUML.Syntax.Classes.Kernel.ParameterDirectionKind;

import java.util.Collection;

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
    // Stubs are handled by MemberMapping.
    
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
            operation.class_ = 
                (Class_)((ClassDefinitionMapping)mapping).getClassifierOnly();

            if (definition.getIsAbstract()) {
                operation.setIsAbstract(true);
            } else {
                Activity activity = new Activity();
                // Give the method activity a name to aid in execution tracing.
                activity.setName(makeDistinguishableActivityName(
                        namespace, operation.name + "$method"));
                operation.class_.addOwnedBehavior(activity);
                operation.addMethod(activity);
                
                for (Parameter parameter: operation.ownedParameter) {
                    Parameter copy = new Parameter();
                    copy.setName(parameter.name);
                    copy.setDirection(parameter.direction);
                    copy.setLower(parameter.multiplicityElement.lower);
                    copy.setUpper(parameter.multiplicityElement.upper.naturalValue);
                    copy.setType(parameter.type);
                    copy.setIsOrdered(parameter.multiplicityElement.isOrdered);
                    copy.setIsUnique(parameter.multiplicityElement.isUnique);
                    activity.addOwnedParameter(copy);
                    ActivityDefinitionMapping.addParameterNodes(activity, copy);
                }
            }
        }
        
        for (ElementReference redefinedOperation: 
            definition.getRedefinedOperations()) {
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
            Activity activity = (Activity)operation.method.get(0);

            Block body = definition.getImpl().getEffectiveBody();
            FumlMapping bodyMapping = this.fumlMap(body);
            Collection<Element> elements = bodyMapping.getModelElements();

            if (definition.getIsConstructor()) {
                Parameter returnParameter = null;
                for (Parameter parameter: activity.ownedParameter) {
                    if (parameter.direction == ParameterDirectionKind.return_) {
                        returnParameter = parameter;
                        break;
                    }
                }
                
                ActivityGraph subgraph = new ActivityGraph();
                StructuredActivityNode node = 
                    subgraph.addStructuredActivityNode("Body", elements);

                // TODO: Add default constructor behavior.

                // Return context object as the constructor result.
                ReadSelfAction readSelfAction = 
                    subgraph.addReadSelfAction(returnParameter.type);
                subgraph.addControlFlow(node, readSelfAction);
                subgraph.addObjectFlow(readSelfAction.result,
                        ActivityDefinitionMapping.getOutputParameterNode(
                                activity, returnParameter));

                elements = subgraph.getModelElements();
            }

            ActivityDefinitionMapping.addElements(activity, elements, body, this);
        }
    }
    
    @Override
    public void addMemberTo(Element element, NamedElement namespace) throws MappingError {
        if (!(element instanceof Parameter)) {
            this.throwError("Member not a parameter: " + element);
        } else if (((Parameter)element).direction != ParameterDirectionKind.return_) {
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
            this.operation = new Operation();
            this.mapTo(this.operation);
        }
        return this.operation;
    }

	public OperationDefinition getOperationDefinition() {
		return (OperationDefinition) this.getSource();
	}
	
	@Override
	public String toString() {
	    return super.toString() + " isAbstract:" + this.operation.isAbstract;
	}
	
	@Override
	public void print(String prefix) {
	    super.print(prefix);
	    
	    if (this.operation != null) {
	        System.out.println(prefix + " operation: " + operation);
	        OperationList redefinedOperations = 
	            this.operation.redefinedOperation;
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
