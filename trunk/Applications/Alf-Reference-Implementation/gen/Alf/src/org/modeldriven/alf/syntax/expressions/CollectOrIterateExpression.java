
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

import org.omg.uml.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.collectOrIterateExpressionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"collectOrIterateExpressionTypeDerivation", this));
		}
		if (!this.collectOrIterateExpressionLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"collectOrIterateExpressionLowerDerivation", this));
		}
		if (!this.collectOrIterateExpressionUpperDerivation()) {
			violations.add(new ConstraintViolation(
					"collectOrIterateExpressionUpperDerivation", this));
		}
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print() {
		this.print("");
	}

	public void print(String prefix) {
		super.print(prefix);
	}
} // CollectOrIterateExpression
