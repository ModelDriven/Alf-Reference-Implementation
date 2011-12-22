
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
import org.modeldriven.alf.mapping.fuml.units.OperationDefinitionMapping;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.InvocationExpression;
import org.modeldriven.alf.syntax.expressions.SuperInvocationExpression;

import fUML.Syntax.Actions.BasicActions.Action;
import fUML.Syntax.Actions.BasicActions.CallBehaviorAction;
import fUML.Syntax.Classes.Kernel.Operation;

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
            if (operation.method.isEmpty()) {
                this.throwError("No method for: " + operation);
            } else {
                action = this.graph.addCallBehaviorAction(operation.method.get(0));
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
