
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.MappingError;
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
import fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior;

public class SuperInvocationExpressionMapping extends
		InvocationExpressionMapping {

    /**
     * Once the target operation a super invocation expression is determined,
     * the expression is mapped as a behavior invocation to the method of that
     * operation.
     */
    
    public Action mapAction() throws MappingError {
        return new CallBehaviorAction();
    }

    @Override
    public void mapTargetTo(Action action) throws MappingError {
        InvocationExpression expression = this.getInvocationExpression();
        ElementReference referent = expression.getReferent();
        FumlMapping mapping = this.fumlMap(referent);
        if (mapping instanceof ElementReferenceMapping) {
            mapping = ((ElementReferenceMapping) mapping).getMapping();
        }
        if (mapping instanceof OperationDefinitionMapping) {
            Operation operation =
                ((OperationDefinitionMapping)mapping).getOperation();
            Behavior method = operation.method.get(0);
            if (method == null) {
                this.throwError("No method for: " + operation);
            } else {
                ((CallBehaviorAction)action).setBehavior(method);
            }
        } else {
            this.throwError("Unknown referent mapping: " + mapping);
        }
    }

	public SuperInvocationExpression getSuperInvocationExpression() {
		return (SuperInvocationExpression) this.getSource();
	}

} // SuperInvocationExpressionMapping
