
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.ActivityGraph;
import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.mapping.fuml.expressions.UnaryExpressionMapping;

import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.expressions.IsolationExpression;

import fUML.Syntax.Actions.BasicActions.OutputPin;
import fUML.Syntax.Activities.CompleteStructuredActivities.StructuredActivityNode;

public class IsolationExpressionMapping extends UnaryExpressionMapping {

    /**
    * An isolation expression is mapped as a structured activity node with
    * mustIsolate=true and the operand expression mapped inside it. The
    * structured activity node has a single output pin with the type and
    * multiplicity of the operand expression. The result source element from
    * the mapping of the operand expression is connected inside the structured
    * activity node by an object flow to the output pin. The result source
    * element for the isolation expression is the output pin of the structure
    * activity node.
    **/

    @Override
    protected void map() throws MappingError {
        IsolationExpression expression = this.getIsolationExpression();
        Expression operand = expression.getOperand();
        FumlMapping mapping = this.fumlMap(operand);
        if (!(mapping instanceof ExpressionMapping)) {
            this.throwError("Error mapping operand expression: " + 
                    mapping.getErrorMessage());
        } else {
            ExpressionMapping operandMapping = (ExpressionMapping)mapping;
            StructuredActivityNode node = this.graph.addStructuredActivityNode(
                    "IsolationExpression@" + expression.getId(), 
                    operandMapping.getModelElements());
            node.setMustIsolate(true);
            OutputPin outputPin = ActivityGraph.createOutputPin(
                    node.name + ".output", operandMapping.getType(), 
                    operand.getLower(), operand.getUpper());
            node.addStructuredNodeOutput(outputPin);
            node.addEdge(ActivityGraph.createObjectFlow(
                    operandMapping.getResultSource(), outputPin));
            
            this.action = node;
            this.resultSource = outputPin;
        }
    }

    public IsolationExpression getIsolationExpression() {
		return (IsolationExpression) this.getSource();
	}

} // IsolationExpressionMapping
