
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.mapping.fuml.common.ElementReferenceMapping;
import org.modeldriven.alf.mapping.fuml.expressions.UnaryExpressionMapping;
import org.modeldriven.alf.mapping.fuml.units.ClassifierDefinitionMapping;

import org.modeldriven.alf.syntax.expressions.ClassificationExpression;

import fUML.Syntax.Actions.CompleteActions.ReadIsClassifiedObjectAction;

public class ClassificationExpressionMapping extends UnaryExpressionMapping {

    /**
     * A classification expression maps to a read is classified object action
     * for the named classifier. If the classification operator is
     * instanceof, then isDirect=false. If the operator is hasType, then
     * isDirect=true. The object input pin of the action is the target of an
     * object flow from the result source element for the mapping of the operand
     * expression. The result output pin of the action is the result source
     * element for the classification expression.
     */
    
    @Override
    protected void map() throws MappingError {
        ClassificationExpression expression = this.getClassificationExpression();
        FumlMapping mapping = this.fumlMap(expression.getOperand());
        if (!(mapping instanceof ExpressionMapping)) {
            this.throwError("Error mapping operand expression: " + 
                    mapping.getErrorMessage());
        } else {
            ExpressionMapping operandMapping = (ExpressionMapping)mapping;
            this.graph.addAll(operandMapping.getGraph());
            
            mapping = this.fumlMap(expression.getReferent());
            if (mapping instanceof ElementReferenceMapping) {
                mapping = ((ElementReferenceMapping)mapping).getMapping();
            }
            if (!(mapping instanceof ClassifierDefinitionMapping)) {
                this.throwError("Error mapping referent for " + 
                        expression.getTypeName() + 
                        ": " + mapping.getErrorMessage());
            } else {
                ClassifierDefinitionMapping referentMapping = 
                    (ClassifierDefinitionMapping)mapping;
                
                ReadIsClassifiedObjectAction action = 
                    this.graph.addReadIsClassifiedObjectAction(
                            referentMapping.getClassifier(), 
                            expression.getIsDirect());
                this.graph.addObjectFlow(
                        operandMapping.getResultSource(), 
                        action.object);
                
                this.action = action;
                this.resultSource = action.result;
            }
        }
    }

	public ClassificationExpression getClassificationExpression() {
		return (ClassificationExpression) this.getSource();
	}
	
	@Override
	public void print(String prefix) {
	    super.print(prefix);
	    
	    if (this.action instanceof ReadIsClassifiedObjectAction) {
	        System.out.println(prefix + " classifier: " + 
	                ((ReadIsClassifiedObjectAction)action).classifier);
	        System.out.println(prefix + " isDirect: " +
                    ((ReadIsClassifiedObjectAction)action).isDirect);
	    }
	}

} // ClassificationExpressionMapping
