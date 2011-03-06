
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

import org.modeldriven.alf.syntax.expressions.impl.ArithmeticExpressionImpl;

/**
 * A binary expression with an arithmetic operator.
 **/

public class ArithmeticExpression extends BinaryExpression {

	public ArithmeticExpression() {
		this.impl = new ArithmeticExpressionImpl(this);
	}

	public ArithmeticExpressionImpl getImpl() {
		return (ArithmeticExpressionImpl) this.impl;
	}

	public Boolean getIsConcatenation() {
		return this.getImpl().getIsConcatenation();
	}

	public void setIsConcatenation(Boolean isConcatenation) {
		this.getImpl().setIsConcatenation(isConcatenation);
	}

	/**
	 * An arithmetic expression is a string concatenation expression if its type
	 * is String.
	 **/
	public boolean arithmeticExpressionIsConcatenationDerivation() {
		return this.getImpl().arithmeticExpressionIsConcatenationDerivation();
	}

	/**
	 * The type of an arithmetic expression is the same as the type of its
	 * operands.
	 **/
	public boolean arithmeticExpressionTypeDerivation() {
		return this.getImpl().arithmeticExpressionTypeDerivation();
	}

	/**
	 * An arithmetic expression has a multiplicity lower bound of 0 if the lower
	 * bound if either operand expression is 0 and 1 otherwise.
	 **/
	public boolean arithmeticExpressionLowerDerivation() {
		return this.getImpl().arithmeticExpressionLowerDerivation();
	}

	/**
	 * An arithmetic expression has a multiplicity upper bound of 1.
	 **/
	public boolean arithmeticExpressionUpperDerivation() {
		return this.getImpl().arithmeticExpressionUpperDerivation();
	}

	/**
	 * The operands of an arithmetic expression must both have type Integer,
	 * unless the operator is +, in which case they may also both have type
	 * String.
	 **/
	public boolean arithmeticExpressionOperandTypes() {
		return this.getImpl().arithmeticExpressionOperandTypes();
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		Boolean isConcatenation = this.getIsConcatenation();
		if (isConcatenation != null) {
			s.append(" /isConcatenation:");
			s.append(isConcatenation);
		}
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
	}
} // ArithmeticExpression
