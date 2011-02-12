
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

import org.modeldriven.alf.syntax.expressions.impl.CollectOrIterateExpressionImpl;

/**
 * A sequence expansion expression with a collect or iterate operation.
 **/

public class CollectOrIterateExpression extends SequenceExpansionExpression {

	public CollectOrIterateExpression() {
		this.impl = new CollectOrIterateExpressionImpl(this);
	}

	public CollectOrIterateExpressionImpl getImpl() {
		return (CollectOrIterateExpressionImpl) this.impl;
	}

	/**
	 * A collect or iterate expression has the same type as its argument
	 * expression.
	 **/
	public boolean collectOrIterateExpressionTypeDerivation() {
		return this.getImpl().collectOrIterateExpressionTypeDerivation();
	}

	/**
	 * A collect or iterate expression has a multiplicity lower bound that is
	 * the product of the bounds of its primary and argument expressions.
	 **/
	public boolean collectOrIterateExpressionLowerDerivation() {
		return this.getImpl().collectOrIterateExpressionLowerDerivation();
	}

	/**
	 * A collect or iterate expression has a multiplicity upper bound that is
	 * the product of the bounds of its primary and argument expressions.
	 **/
	public boolean collectOrIterateExpressionUpperDerivation() {
		return this.getImpl().collectOrIterateExpressionUpperDerivation();
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
	}
} // CollectOrIterateExpression
