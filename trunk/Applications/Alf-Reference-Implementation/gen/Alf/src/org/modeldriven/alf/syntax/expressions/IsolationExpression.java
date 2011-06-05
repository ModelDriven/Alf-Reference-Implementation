
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

import org.omg.uml.Element;
import org.omg.uml.Profile;
import org.omg.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.isolationExpressionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"isolationExpressionTypeDerivation", this));
		}
		if (!this.isolationExpressionLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"isolationExpressionLowerDerivation", this));
		}
		if (!this.isolationExpressionUpperDerivation()) {
			violations.add(new ConstraintViolation(
					"isolationExpressionUpperDerivation", this));
		}
	}

	public String toString() {
		return this.getImpl().toString();
	}

	public String _toString() {
		StringBuffer s = new StringBuffer(super._toString());
		return s.toString();
	}

	public void print() {
		this.print("");
	}

	public void print(String prefix) {
		super.print(prefix);
	}
} // IsolationExpression
