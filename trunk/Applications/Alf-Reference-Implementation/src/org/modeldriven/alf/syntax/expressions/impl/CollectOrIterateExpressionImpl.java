
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;

/**
 * A sequence expansion expression with a collect or iterate operation.
 **/

public class CollectOrIterateExpressionImpl
		extends SequenceExpansionExpressionImpl {

	public CollectOrIterateExpressionImpl(CollectOrIterateExpression self) {
		super(self);
	}

	@Override
	public CollectOrIterateExpression getSelf() {
		return (CollectOrIterateExpression) this.self;
	}

	/**
	 * A collect or iterate expression has the same type as its argument
	 * expression.
	 **/
	@Override
	protected ElementReference deriveType() {
	    Expression argument = this.getSelf().getArgument();
	    return argument == null? null: argument.getType();
	}
	
	/**
	 * A collect or iterate expression has a multiplicity lower bound that is
	 * the product of the bounds of its primary and argument expressions.
	 **/
	@Override
	protected Integer deriveLower() {
	    CollectOrIterateExpression self = this.getSelf();
        Expression argument = self.getArgument();
        ExtentOrExpression primary = self.getPrimary();
        if (argument == null || primary == null) {
            return 0;
        } else {
            Expression expression = primary.getExpression();
            return expression == null? 0: argument.getLower() * expression.getLower();
        }
	}
	
	/**
	 * A collect or iterate expression has a multiplicity upper bound that is
	 * the product of the bounds of its primary and argument expressions.
	 **/
    @Override
    protected Integer deriveUpper() {
        CollectOrIterateExpression self = this.getSelf();
        Expression argument = self.getArgument();
        ExtentOrExpression primary = self.getPrimary();
        if (argument == null || primary == null) {
            return 0;
        } else {
            Expression expression = primary.getExpression();
            if (expression == null) {
                return 0;
            } else {
                int argumentUpper = argument.getUpper();
                int expressionUpper = expression.getUpper();
                return argumentUpper == -1 || expressionUpper == -1? -1:
                            argumentUpper * expressionUpper;
            }
        }
    }
	
	/*
	 * Derivations
	 */
	
	public boolean collectOrIterateExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	public boolean collectOrIterateExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	public boolean collectOrIterateExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

} // CollectOrIterateExpressionImpl
