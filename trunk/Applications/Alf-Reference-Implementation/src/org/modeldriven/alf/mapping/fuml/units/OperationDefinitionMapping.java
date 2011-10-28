
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
import org.modeldriven.alf.mapping.fuml.units.NamespaceDefinitionMapping;

import org.modeldriven.alf.syntax.statements.Block;
import org.modeldriven.alf.syntax.units.FormalParameter;
import org.modeldriven.alf.syntax.units.OperationDefinition;

import fUML.Syntax.Actions.IntermediateActions.ReadSelfAction;
import fUML.Syntax.Activities.CompleteStructuredActivities.StructuredActivityNode;
import fUML.Syntax.Activities.IntermediateActivities.Activity;
import fUML.Syntax.Activities.IntermediateActivities.ActivityEdge;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;
import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.Classes.Kernel.NamedElement;
import fUML.Syntax.Classes.Kernel.Operation;
import fUML.Syntax.Classes.Kernel.Parameter;
import fUML.Syntax.Classes.Kernel.ParameterDirectionKind;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OperationDefinitionMapping extends NamespaceDefinitionMapping {

    private Operation operation = null;
    
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

        if (definition.getIsAbstract()) {
            operation.setIsAbstract(true);
        } else {
            Activity activity = new Activity();
            operation.addMethod(activity);
            
            Parameter returnParameter = null;
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
                if (parameter.direction == ParameterDirectionKind.return_) {
                    returnParameter = copy;
                }
            }

            Block body = definition.getImpl().getEffectiveBody();
            
            FumlMapping bodyMapping = this.fumlMap(body);
            Collection<Element> elements = bodyMapping.getModelElements();
            
            if (definition.getIsConstructor()) {
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
                        
            for (Element element: elements) {
                if (element instanceof ActivityNode) {
                    activity.addNode((ActivityNode)element);
                } else if (element instanceof ActivityEdge) {
                    activity.addEdge((ActivityEdge)element);
                } else {
                    this.throwError("Element not an activity node or edge: " + element);
                }
            }
        }
    }
    
    @Override
    public void addMemberTo(Element element, NamedElement namespace) throws MappingError {
        if (!(element instanceof Parameter)) {
            this.throwError("Member not a parameter: " + element);
        } else if (((Parameter)element).direction != ParameterDirectionKind.return_) {
            // Note: An operation is a namespace in full UML, but not in fUML, so the "namespace"
            // parameter actually has the type "NamedElement".
            ((Operation)namespace).addOwnedParameter((Parameter)element);
        }
    }

    @Override
    public Element getElement() {
        return this.operation;
    }
    
    @Override
	public List<Element> getModelElements() throws MappingError {
	    ArrayList<Element> elements = new ArrayList<Element>();
	    elements.add(this.getOperation());
	    return elements;
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
