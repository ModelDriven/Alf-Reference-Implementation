
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

import org.modeldriven.alf.syntax.expressions.impl.SelectOrRejectExpressionImpl;

/**
 * A sequence expansion expression with a select or reject operation.
 **/

public class SelectOrRejectExpression extends SequenceExpansionExpression {

	public SelectOrRejectExpression() {
		this.impl = new SelectOrRejectExpressionImpl(this);
	}

	public SelectOrRejectExpressionImpl getImpl() {
		return (SelectOrRejectExpressionImpl) this.impl;
	}

	/**
	 * A select or reject expression has the same type as its primary
	 * expression.
	 **/
	public boolean selectOrRejectExpressionTypeDerivation() {
		return this.getImpl().selectOrRejectExpressionTypeDerivation();
	}

	/**
	 * A select or reject expression has a multiplicity lower bound of 0.
	 **/
	public boolean selectOrRejectExpressionLowerDerivation() {
		return this.getImpl().selectOrRejectExpressionLowerDerivation();
	}

	/**
	 * A select or reject expression has a multiplicity upper bound of *.
	 **/
	public boolean selectOrRejectExpressionUpperDerivation() {
		return this.getImpl().selectOrRejectExpressionUpperDerivation();
	}

	/**
	 * The argument of a select or reject expression must have type Boolean and
	 * a multiplicity upper bound of 1.
	 **/
	public boolean selectOrRejectExpressionArgument() {
		return this.getImpl().selectOrRejectExpressionArgument();
	}

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.selectOrRejectExpressionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"selectOrRejectExpressionTypeDerivation", this));
		}
		if (!this.selectOrRejectExpressionLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"selectOrRejectExpressionLowerDerivation", this));
		}
		if (!this.selectOrRejectExpressionUpperDerivation()) {
			violations.add(new ConstraintViolation(
					"selectOrRejectExpressionUpperDerivation", this));
		}
		if (!this.selectOrRejectExpressionArgument()) {
			violations.add(new ConstraintViolation(
					"selectOrRejectExpressionArgument", this));
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
} // SelectOrRejectExpression
