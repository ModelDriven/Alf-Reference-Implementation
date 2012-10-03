
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
import org.modeldriven.alf.fuml.mapping.expressions.ExpressionMapping;
import org.modeldriven.alf.fuml.mapping.statements.StatementMapping;
import org.modeldriven.alf.fuml.mapping.units.ActivityDefinitionMapping;
import org.modeldriven.alf.fuml.mapping.units.FormalParameterMapping;
import org.modeldriven.alf.fuml.mapping.units.OperationDefinitionMapping;
import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;

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
        FumlMapping mapping = this.fumlMap(returnStatement.getExpression());
        if (!(mapping instanceof ExpressionMapping)) {
            this.throwError("Error mapping expression: " + mapping);
        } else {
            this.addToNode(mapping.getModelElements());
            
            ExpressionMapping expressionMapping = (ExpressionMapping)mapping;
            ActivityNode resultSource = expressionMapping.getResultSource();
            
            if (resultSource == null) {
                this.setErrorMessage("No result source: " + expressionMapping);
            } else {
                Expression expression = expressionMapping.getExpression();
                StructuredActivityNode node = 
                    (StructuredActivityNode)this.getElement();
                
                OutputPin pin = this.graph.createOutputPin(
                        node.getName() + ".output", expressionMapping.getType(), 
                        expression.getLower(), expression.getUpper());
                node.addStructuredNodeOutput(pin);
                
                if (!ActivityGraph.isContainedIn(resultSource, node)) {
                    StructuredActivityNode passThruNode =
                            this.graph.createPassthruNode(
                                resultSource.getName(), 
                                pin.getType(), 
                                pin.getLower(), 
                                pin.getUpper());
                    node.addNode(passThruNode);
                    this.graph.addObjectFlow(
                            resultSource, 
                            passThruNode.getStructuredNodeInput().get(0));
                    resultSource = passThruNode.getStructuredNodeOutput().get(0);
                }
                
                node.addEdge(this.graph.createObjectFlow(
                        resultSource, pin));
                
                ElementReference behavior = this.getReturnStatement().getBehavior();
                FormalParameter returnParameter = behavior == null? null:
                    behavior.getImpl().getReturnParameter();
                FumlMapping parameterMapping = this.fumlMap(returnParameter);
                if (!(parameterMapping instanceof FormalParameterMapping)) {
                    this.throwError("Error mapping return parameter: " + 
                            parameterMapping);
                } else {
                    mapping = this.fumlMap(behavior);
                    if (mapping instanceof ElementReferenceMapping) {
                        mapping = ((ElementReferenceMapping)mapping).getMapping();
                    }
                    Activity activity = null;
                    Parameter parameter = 
                        ((FormalParameterMapping)parameterMapping).getParameter();
                    
                    if (mapping instanceof ActivityDefinitionMapping) {
                        activity = (Activity)((ActivityDefinitionMapping)mapping).getBehavior();
                    } else if (mapping instanceof OperationDefinitionMapping) {
                        Operation operation = 
                            ((OperationDefinitionMapping)mapping).getOperation();
                        List<Behavior> methods = operation.getMethod();
                        if (methods.size() > 0) {
                            activity = (Activity)methods.get(0);
                            parameter = activity.getOwnedParameter().
                                get(operation.getOwnedParameter().indexOf(parameter));
                        } else {
                            this.throwError("Operation has no method: " + operation);
                        }
                    } else {
                        this.throwError("Error mapping behavior: " + 
                                mapping.getErrorMessage());
                    }
                    
                    ActivityParameterNode parameterNode = 
                        ActivityDefinitionMapping.getOutputParameterNode(
                                activity, parameter);
                    
                    if (parameterNode == null) {
                        this.throwError("Activity does not contain parameter: " + 
                                parameter);
                    } else {
                        this.add(this.graph.createObjectFlow(pin, parameterNode));
                        this.add(this.graph.createControlFlow(
                                node, 
                                ActivityDefinitionMapping.getFinalNode(activity, this)));
                    }
                }
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
