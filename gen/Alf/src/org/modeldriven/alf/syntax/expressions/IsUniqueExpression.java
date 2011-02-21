
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

import org.modeldriven.alf.syntax.expressions.impl.IsUniqueExpressionImpl;

/**
 * A sequence expansion expression with a isUnique.
 **/

public class IsUniqueExpression extends SequenceExpansionExpression {

	public IsUniqueExpression() {
		this.impl = new IsUniqueExpressionImpl(this);
	}

	public IsUniqueExpressionImpl getImpl() {
		return (IsUniqueExpressionImpl) this.impl;
	}

	/**
	 * An isUnique expression has the type Boolean.
	 **/
	public boolean isUniqueExpressionTypeDerivation() {
		return this.getImpl().isUniqueExpressionTypeDerivation();
	}

	/**
	 * An isUnique expression has a multiplicity lower bound of 1.
	 **/
	public boolean isUniqueExpressionLowerDerivation() {
		return this.getImpl().isUniqueExpressionLowerDerivation();
	}

	/**
	 * An isUnique expression has a multiplicity upper bound of 1.
	 **/
	public boolean isUniqueExpressionUpperDerivation() {
		return this.getImpl().isUniqueExpressionUpperDerivation();
	}

	/**
	 * The argument of an isUnique expression must have a multiplicity upper
	 * bound of 1.
	 **/
	public boolean isUniqueExpressionExpressionArgument() {
		return this.getImpl().isUniqueExpressionExpressionArgument();
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
	}
} // IsUniqueExpression
