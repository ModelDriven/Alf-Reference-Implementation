
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.expressions;

import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.fuml.mapping.common.ElementReferenceMapping;
import org.modeldriven.alf.fuml.mapping.expressions.UnaryExpressionMapping;
import org.modeldriven.alf.fuml.mapping.units.ClassifierDefinitionMapping;
import org.modeldriven.alf.mapping.MappingError;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.ClassificationExpression;

import org.modeldriven.alf.uml.Classifier;
import org.modeldriven.alf.uml.ReadIsClassifiedObjectAction;

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
            
            ElementReference referent = expression.getReferent();
            Classifier classifier = (Classifier)referent.getImpl().getUml();
            if (classifier == null) {
                mapping = this.fumlMap(referent);
                if (mapping instanceof ElementReferenceMapping) {
                    mapping = ((ElementReferenceMapping)mapping).getMapping();
                }
                if (!(mapping instanceof ClassifierDefinitionMapping)) {
                    this.throwError("Error mapping referent for " + 
                            expression.getTypeName().getPathName() + 
                            ": " + mapping.getErrorMessage());
                } else {
                    ClassifierDefinitionMapping referentMapping = 
                            (ClassifierDefinitionMapping)mapping;
                    classifier = referentMapping.getClassifier();                
                }
            }
            ReadIsClassifiedObjectAction action = 
                    this.graph.addReadIsClassifiedObjectAction(
                            classifier, 
                            expression.getIsDirect());
            this.graph.addObjectFlow(
                    operandMapping.getResultSource(), 
                    action.getObject());

            this.action = action;
            this.resultSource = action.getResult();
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
	                ((ReadIsClassifiedObjectAction)action).getClassifier());
	        System.out.println(prefix + " isDirect: " +
                    ((ReadIsClassifiedObjectAction)action).getIsDirect());
	    }
	}

} // ClassificationExpressionMapping
