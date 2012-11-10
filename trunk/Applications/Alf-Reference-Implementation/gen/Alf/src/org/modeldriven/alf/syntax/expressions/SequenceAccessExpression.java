
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

import org.modeldriven.alf.syntax.expressions.impl.SequenceAccessExpressionImpl;

/**
 * An expression used to access a specific element of a sequence.
 **/

public class SequenceAccessExpression extends Expression {

	public SequenceAccessExpression() {
		this.impl = new SequenceAccessExpressionImpl(this);
	}

	public SequenceAccessExpression(Parser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public SequenceAccessExpression(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public SequenceAccessExpressionImpl getImpl() {
		return (SequenceAccessExpressionImpl) this.impl;
	}

	public Expression getPrimary() {
		return this.getImpl().getPrimary();
	}

	public void setPrimary(Expression primary) {
		this.getImpl().setPrimary(primary);
	}

	public Expression getIndex() {
		return this.getImpl().getIndex();
	}

	public void setIndex(Expression index) {
		this.getImpl().setIndex(index);
	}

	/**
	 * The type of a sequence access expression is the same as the type of its
	 * primary expression.
	 **/
	public boolean sequenceAccessExpressionTypeDerivation() {
		return this.getImpl().sequenceAccessExpressionTypeDerivation();
	}

	/**
	 * The multiplicity lower bound of a sequence access expression is 0.
	 **/
	public boolean sequenceAccessExpressionLowerDerivation() {
		return this.getImpl().sequenceAccessExpressionLowerDerivation();
	}

	/**
	 * The multiplicity upper bound of a sequence access expression is 1.
	 **/
	public boolean sequenceAccessExpressionUpperDerivation() {
		return this.getImpl().sequenceAccessExpressionUpperDerivation();
	}

	/**
	 * The type of the index of a sequence access expression must be Integer.
	 **/
	public boolean sequenceAccessExpressionIndexType() {
		return this.getImpl().sequenceAccessExpressionIndexType();
	}

	/**
	 * The multiplicity upper bound of the index of a sequence access expression
	 * must be 1.
	 **/
	public boolean sequenceAccessExpressionIndexMultiplicity() {
		return this.getImpl().sequenceAccessExpressionIndexMultiplicity();
	}

	public void _deriveAll() {
		super._deriveAll();
		Expression primary = this.getPrimary();
		if (primary != null) {
			primary.deriveAll();
		}
		Expression index = this.getIndex();
		if (index != null) {
			index.deriveAll();
		}
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.sequenceAccessExpressionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"sequenceAccessExpressionTypeDerivation", this));
		}
		if (!this.sequenceAccessExpressionLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"sequenceAccessExpressionLowerDerivation", this));
		}
		if (!this.sequenceAccessExpressionUpperDerivation()) {
			violations.add(new ConstraintViolation(
					"sequenceAccessExpressionUpperDerivation", this));
		}
		if (!this.sequenceAccessExpressionIndexType()) {
			violations.add(new ConstraintViolation(
					"sequenceAccessExpressionIndexType", this));
		}
		if (!this.sequenceAccessExpressionIndexMultiplicity()) {
			violations.add(new ConstraintViolation(
					"sequenceAccessExpressionIndexMultiplicity", this));
		}
		Expression primary = this.getPrimary();
		if (primary != null) {
			primary.checkConstraints(violations);
		}
		Expression index = this.getIndex();
		if (index != null) {
			index.checkConstraints(violations);
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
		Expression primary = this.getPrimary();
		if (primary != null) {
			System.out.println(prefix + " primary:");
			primary.print(prefix + "  ", includeDerived);
		}
		Expression index = this.getIndex();
		if (index != null) {
			System.out.println(prefix + " index:");
			index.print(prefix + "  ", includeDerived);
		}
	}
} // SequenceAccessExpression
