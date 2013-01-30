
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
import org.modeldriven.alf.fuml.mapping.units.OperationDefinitionMapping;
import org.modeldriven.alf.mapping.MappingError;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.InvocationExpression;
import org.modeldriven.alf.syntax.expressions.SuperInvocationExpression;

import org.modeldriven.alf.uml.Action;
import org.modeldriven.alf.uml.CallBehaviorAction;
import org.modeldriven.alf.uml.Operation;

public class SuperInvocationExpressionMapping extends
		InvocationExpressionMapping {

    /**
     * Once the target operation in a super invocation expression is determined,
     * the expression is mapped as a behavior invocation to the method of that
     * operation.
     */
    
    @Override
    public Action mapTarget() throws MappingError {
        CallBehaviorAction action = null;
        InvocationExpression expression = this.getInvocationExpression();
        ElementReference referent = expression.getReferent();
        FumlMapping mapping = this.fumlMap(referent);
        if (mapping instanceof ElementReferenceMapping) {
            mapping = ((ElementReferenceMapping) mapping).getMapping();
        }
        if (mapping instanceof OperationDefinitionMapping) {
            Operation operation =
                ((OperationDefinitionMapping)mapping).getOperation();
            if (operation.getMethod().isEmpty()) {
                this.throwError("No method for: " + operation);
            } else {
                action = this.graph.addCallBehaviorAction(operation.getMethod().get(0));
                this.resultSource = ActivityGraph.getReturnPin(action);
            }
        } else {
            this.throwError("Unknown referent mapping: " + mapping);
        }
        return action;
    }

	public SuperInvocationExpression getSuperInvocationExpression() {
		return (SuperInvocationExpression) this.getSource();
	}

} // SuperInvocationExpressionMapping
