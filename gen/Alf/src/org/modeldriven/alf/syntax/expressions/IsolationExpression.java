
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.parser.AlfParser;
import org.modeldriven.alf.parser.Token;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.expressions.impl.IsolationExpressionImpl;
import org.modeldriven.uml.Element;
import org.modeldriven.uml.Profile;
import org.modeldriven.uml.Stereotype;

/**
 * An expression used to evaluate its operand expression in isolation.
 **/

public class IsolationExpression extends UnaryExpression {

	public IsolationExpression() {
		this.impl = new IsolationExpressionImpl(this);
	}

	public IsolationExpression(AlfParser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public IsolationExpression(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
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

	public void _deriveAll() {
		super._deriveAll();
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

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		return s.toString();
	}

	public void print() {
		this.print("", false);
	}

	public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	public void print(String prefix, boolean includeDerived) {
		super.print(prefix, includeDerived);
	}
} // IsolationExpression
