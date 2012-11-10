
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.parser.Token;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.Profile;
import org.modeldriven.alf.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

import org.modeldriven.alf.syntax.expressions.impl.RelationalExpressionImpl;

/**
 * A binary expression with a relational operator.
 **/

public class RelationalExpression extends BinaryExpression {

	public RelationalExpression() {
		this.impl = new RelationalExpressionImpl(this);
	}

	public RelationalExpression(Parser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public RelationalExpression(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public RelationalExpressionImpl getImpl() {
		return (RelationalExpressionImpl) this.impl;
	}

	public Boolean getIsUnlimitedNatural() {
		return this.getImpl().getIsUnlimitedNatural();
	}

	public void setIsUnlimitedNatural(Boolean isUnlimitedNatural) {
		this.getImpl().setIsUnlimitedNatural(isUnlimitedNatural);
	}

	/**
	 * A relational expression is an UnlimitedNatural comparison if either one
	 * of its operands has type UnlimitedNatural.
	 **/
	public boolean relationalExpressionIsUnlimitedNaturalDerivation() {
		return this.getImpl()
				.relationalExpressionIsUnlimitedNaturalDerivation();
	}

	/**
	 * The type of a relational expression is Boolean.
	 **/
	public boolean relationalExpressionTypeDerivation() {
		return this.getImpl().relationalExpressionTypeDerivation();
	}

	/**
	 * A relational expression has a multiplicity lower bound of 0 if the lower
	 * bound if either operand expression is 0 and 1 otherwise.
	 **/
	public boolean relationalExpressionLowerDerivation() {
		return this.getImpl().relationalExpressionLowerDerivation();
	}

	/**
	 * A relational expression has a multiplicity upper bound of 1.
	 **/
	public boolean relationalExpressionUpperDerivation() {
		return this.getImpl().relationalExpressionUpperDerivation();
	}

	/**
	 * The operand expressions for a comparison operator must have type Integer,
	 * UnlimitedNatural or Natural. However, it is not allowed to have one
	 * operand expression be Integer and the other be UnlimitedNatural.
	 **/
	public boolean relationalExpressionOperandTypes() {
		return this.getImpl().relationalExpressionOperandTypes();
	}

	public void _deriveAll() {
		this.getIsUnlimitedNatural();
		super._deriveAll();
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.relationalExpressionIsUnlimitedNaturalDerivation()) {
			violations.add(new ConstraintViolation(
					"relationalExpressionIsUnlimitedNaturalDerivation", this));
		}
		if (!this.relationalExpressionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"relationalExpressionTypeDerivation", this));
		}
		if (!this.relationalExpressionLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"relationalExpressionLowerDerivation", this));
		}
		if (!this.relationalExpressionUpperDerivation()) {
			violations.add(new ConstraintViolation(
					"relationalExpressionUpperDerivation", this));
		}
		if (!this.relationalExpressionOperandTypes()) {
			violations.add(new ConstraintViolation(
					"relationalExpressionOperandTypes", this));
		}
	}

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		if (includeDerived) {
			s.append(" /isUnlimitedNatural:");
			s.append(this.getIsUnlimitedNatural());
		}
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
} // RelationalExpression
