
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.statements;

import java.util.List;

import org.modeldriven.alf.fuml.mapping.ActivityGraph;
import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.fuml.mapping.common.ElementReferenceMapping;
import org.modeldriven.alf.fuml.mapping.common.SyntaxElementMapping;
import org.modeldriven.alf.fuml.mapping.expressions.ExpressionMapping;
import org.modeldriven.alf.fuml.mapping.statements.StatementMapping;
import org.modeldriven.alf.fuml.mapping.units.ActivityDefinitionMapping;
import org.modeldriven.alf.fuml.mapping.units.ClassifierDefinitionMapping;
import org.modeldriven.alf.fuml.mapping.units.FormalParameterMapping;
import org.modeldriven.alf.fuml.mapping.units.OperationDefinitionMapping;
import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;

import org.modeldriven.alf.syntax.common.AssignedSource;
import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.statements.ReturnStatement;
import org.modeldriven.alf.syntax.units.FormalParameter;

import org.modeldriven.alf.uml.*;

public class ReturnStatementMapping extends StatementMapping {
    
    @Override
    public void map() throws MappingError {
        super.map();
        
        ReturnStatement returnStatement = this.getReturnStatement();
        ElementReference behavior = returnStatement.getBehavior();
        FumlMapping mapping = this.fumlMap(behavior);
        if (mapping instanceof ElementReferenceMapping) {
            mapping = ((ElementReferenceMapping)mapping).getMapping();
        }
        Activity activity = null;
        Operation operation = null;
        if (mapping instanceof ActivityDefinitionMapping) {
            activity = (Activity)((ActivityDefinitionMapping)mapping).getBehavior();
        } else if (mapping instanceof OperationDefinitionMapping) {
            operation = 
                ((OperationDefinitionMapping)mapping).getOperation();
            List<Behavior> methods = operation.getMethod();
            if (methods.size() > 0) {
                activity = (Activity)methods.get(0);
            } else {
                this.throwError("Operation has no method: " + operation);
            }
        } else {
            this.throwError("Error mapping behavior: " + 
                    mapping.getErrorMessage());
        }
        
        this.add(this.graph.createControlFlow(                
                this.node, 
                ActivityDefinitionMapping.getFinalNode(activity, this)));
        
        Expression expression = returnStatement.getExpression();
        if (expression != null) {
            mapping = this.fumlMap(expression);
            if (!(mapping instanceof ExpressionMapping)) {
                this.throwError("Error mapping expression: " + 
                        mapping.getErrorMessage());
            } else {
                this.addToNode(mapping.getModelElements());

                ExpressionMapping expressionMapping = (ExpressionMapping)mapping;
                ActivityNode resultSource = expressionMapping.getResultSource();

                if (resultSource == null) {
                    this.setErrorMessage("No result source: " + expressionMapping);
                } else {
                    this.mapOutput(
                            resultSource, 
                            expressionMapping.getType(), 
                            expression.getLower(), 
                            expression.getUpper(), 
                            behavior.getImpl().getReturnParameter(), 
                            activity, operation);
                }                
            }
        }
        
        for (FormalParameter parameter: 
            behavior.getImpl().getParameters()) {
            if (parameter.getDirection().equals("out") ||
                    parameter.getDirection().equals("inout")) {
                String name = parameter.getName();
                AssignedSource assignment = 
                    returnStatement.getImpl().getAssignmentAfter(name);
                if (assignment != null) {
                    FumlMapping sourceMapping = 
                        mapping.fumlMap(assignment.getSource());
                    if (!(sourceMapping instanceof SyntaxElementMapping)) {
                        mapping.throwError("Error mapping parameter " + 
                                name + ": " + mapping.getErrorMessage());
                    } else {
                        ActivityNode source = ((SyntaxElementMapping)sourceMapping).
                                getAssignedValueSource(name);
                        Classifier type = null;
                        ElementReference typeReference = assignment.getType();
                        if (typeReference != null) {
                            mapping = this.fumlMap(assignment.getType());
                            if (mapping instanceof ElementReferenceMapping) {
                                mapping = ((ElementReferenceMapping)mapping).
                                        getMapping();
                            }
                            if (!(mapping instanceof ClassifierDefinitionMapping)) {
                                this.throwError("Error mapping type of parameter " +
                                        parameter.getName() + ": " + 
                                        mapping.getErrorMessage());
                            } else {
                                type = 
                                        ((ClassifierDefinitionMapping)mapping).
                                        getClassifierOnly();
                            }
                        }
                        this.mapOutput(
                                source, type, 
                                assignment.getLower(), assignment.getUpper(), 
                                parameter, activity, operation);
                    }
                }
            }
        }
    }
    
    private void mapOutput(
            ActivityNode source, Type type, int lower, int upper,
            FormalParameter outputParameter, 
            Activity activity, Operation operation) throws MappingError {
        OutputPin pin = this.graph.createOutputPin(
                this.node.getName() + ".output", 
                type, lower, upper);
        this.node.addStructuredNodeOutput(pin);

        if (!ActivityGraph.isContainedIn(source, this.node)) {
            StructuredActivityNode passThruNode =
                    this.graph.createPassthruNode(
                            source.getName(), 
                            pin.getType(), 
                            pin.getLower(), 
                            pin.getUpper());
            this.node.addNode(passThruNode);
            this.graph.addObjectFlow(
                    source, 
                    passThruNode.getStructuredNodeInput().get(0));
            source = passThruNode.getStructuredNodeOutput().get(0);
        }

        node.addEdge(this.graph.createObjectFlow(
                source, pin));

        FumlMapping parameterMapping = this.fumlMap(outputParameter);
        if (!(parameterMapping instanceof FormalParameterMapping)) {
            this.throwError("Error mapping parameter: " + 
                    parameterMapping.getErrorMessage());
        } else {
            Parameter parameter = 
                    ((FormalParameterMapping)parameterMapping).getParameter();

            if (operation != null) {
                parameter = activity.getOwnedParameter().
                        get(operation.getOwnedParameter().indexOf(parameter));
            }

            ActivityParameterNode parameterNode = 
                    ActivityDefinitionMapping.getOutputParameterNode(
                            activity, parameter);

            if (parameterNode == null) {
                this.throwError("Activity does not contain parameter: " + 
                        parameter);
            } else {
                this.add(this.graph.createObjectFlow(pin, parameterNode));
            }
        }
        
    }
    
	public ReturnStatement getReturnStatement() {
		return (ReturnStatement) this.getSource();
	}
	
	@Override
	public void print(String prefix) {
	    super.print(prefix);
	    
	    ReturnStatement source = this.getReturnStatement();
	    Expression expression = source.getExpression();
	    if (expression != null) {
	        Mapping mapping = expression.getImpl().getMapping();
	        if (mapping != null) {
	            System.out.println(prefix + " expression:");
	            mapping.printChild(prefix);
	        }
	    }
	}

} // ReturnStatementMapping
