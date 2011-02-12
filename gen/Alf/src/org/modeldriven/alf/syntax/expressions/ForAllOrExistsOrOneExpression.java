
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

import org.modeldriven.alf.syntax.expressions.impl.ForAllOrExistsOrOneExpressionImpl;

/**
 * A sequence expansion expression with a forAll, exists or one operation.
 **/

public class ForAllOrExistsOrOneExpression extends SequenceExpansionExpression {

	public ForAllOrExistsOrOneExpression() {
		this.impl = new ForAllOrExistsOrOneExpressionImpl(this);
	}

	public ForAllOrExistsOrOneExpressionImpl getImpl() {
		return (ForAllOrExistsOrOneExpressionImpl) this.impl;
	}

	/**
	 * A forAll, exists or one expression has the type Boolean.
	 **/
	public boolean forAllOrExistsOrOneExpressionTypeDerivation() {
		return this.getImpl().forAllOrExistsOrOneExpressionTypeDerivation();
	}

	/**
	 * A forAll, exists or one expression has a multiplicity lower bound of 1.
	 **/
	public boolean forAllOrExistsOrOneExpressionLowerDerivation() {
		return this.getImpl().forAllOrExistsOrOneExpressionLowerDerivation();
	}

	/**
	 * A forAll, exists or one expression has a multiplicity upper bound of 1.
	 **/
	public boolean forAllOrExistsOrOneExpressionUpperDerivation() {
		return this.getImpl().forAllOrExistsOrOneExpressionUpperDerivation();
	}

	/**
	 * The argument of a forAll, exists or one expression must have type Boolean
	 * and a multiplicity upper bound of 1.
	 **/
	public boolean forAllOrExistsOrOneExpressionArgument() {
		return this.getImpl().forAllOrExistsOrOneExpressionArgument();
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
	}
} // ForAllOrExistsOrOneExpression
