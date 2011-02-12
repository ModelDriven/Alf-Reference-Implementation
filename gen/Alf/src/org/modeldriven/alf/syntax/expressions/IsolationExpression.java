
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

import org.modeldriven.alf.syntax.expressions.impl.IsolationExpressionImpl;

/**
 * An expression used to evaluate its operand expression in isolation.
 **/

public class IsolationExpression extends UnaryExpression {

	public IsolationExpression() {
		this.impl = new IsolationExpressionImpl(this);
	}

	public IsolationExpressionImpl getImpl() {
		return (IsolationExpressionImpl) this.impl;
	}

	/**
	 * An isolation expression has the type of its operand expression.
	 **/
	public boolean isolationExpressionTypeDerivation() {
		return this.getImpl().isolationExpressionTypeDerivation();
	}

	/**
	 * An isolation expression has the multiplicity lower bound of its operand
	 * expression.
	 **/
	public boolean isolationExpressionLowerDerivation() {
		return this.getImpl().isolationExpressionLowerDerivation();
	}

	/**
	 * An isolation expression has the multiplicity upper bound of its operand
	 * expression.
	 **/
	public boolean isolationExpressionUpperDerivation() {
		return this.getImpl().isolationExpressionUpperDerivation();
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
	}
} // IsolationExpression
