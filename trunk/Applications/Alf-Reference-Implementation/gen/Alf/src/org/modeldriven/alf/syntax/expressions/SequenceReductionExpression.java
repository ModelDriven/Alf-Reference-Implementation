
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for under the terms of the GNU General Public License 
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

import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.Profile;
import org.modeldriven.alf.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.expressions.impl.SequenceReductionExpressionImpl;

/**
 * An expression used to reduce a sequence of values effectively by inserting a
 * binary operation between the values.
 **/

public class SequenceReductionExpression extends Expression {

	public SequenceReductionExpression() {
		this.impl = new SequenceReductionExpressionImpl(this);
	}

	public SequenceReductionExpression(AlfParser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public SequenceReductionExpression(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public SequenceReductionExpressionImpl getImpl() {
		return (SequenceReductionExpressionImpl) this.impl;
	}

	public ElementReference getReferent() {
		return this.getImpl().getReferent();
	}

	public void setReferent(ElementReference referent) {
		this.getImpl().setReferent(referent);
	}

	public Boolean getIsOrdered() {
		return this.getImpl().getIsOrdered();
	}

	public void setIsOrdered(Boolean isOrdered) {
		this.getImpl().setIsOrdered(isOrdered);
	}

	public ExtentOrExpression getPrimary() {
		return this.getImpl().getPrimary();
	}

	public void setPrimary(ExtentOrExpression primary) {
		this.getImpl().setPrimary(primary);
	}

	public QualifiedName getBehaviorName() {
		return this.getImpl().getBehaviorName();
	}

	public void setBehaviorName(QualifiedName behaviorName) {
		this.getImpl().setBehaviorName(behaviorName);
	}

	/**
	 * The referent for a sequence reduction expression is the behavior denoted
	 * by the behavior name of the expression.
	 **/
	public boolean sequenceReductionExpressionReferentDerivation() {
		return this.getImpl().sequenceReductionExpressionReferentDerivation();
	}

	/**
	 * A sequence reduction expression has the same type as its primary
	 * expression.
	 **/
	public boolean sequenceReductionExpressionTypeDerivation() {
		return this.getImpl().sequenceReductionExpressionTypeDerivation();
	}

	/**
	 * A sequence reduction expression has a multiplicity upper bound of 1.
	 **/
	public boolean sequenceReductionExpressionUpperDerivation() {
		return this.getImpl().sequenceReductionExpressionUpperDerivation();
	}

	/**
	 * A sequence reduction expression has a multiplicity lower bound of 1.
	 **/
	public boolean sequenceReductionExpressionLowerDerivation() {
		return this.getImpl().sequenceReductionExpressionLowerDerivation();
	}

	/**
	 * The behavior name in a sequence reduction expression must denote a
	 * behavior.
	 **/
	public boolean sequenceReductionExpressionBehavior() {
		return this.getImpl().sequenceReductionExpressionBehavior();
	}

	/**
	 * The referent behavior must have two in parameters, a return parameter and
	 * no other parameters. The parameters must all have the same type as the
	 * argument expression and multiplicity [1..1].
	 **/
	public boolean sequenceReductionExpressionBehaviorParameters() {
		return this.getImpl().sequenceReductionExpressionBehaviorParameters();
	}

	/**
	 * The assignments before the target expression of a sequence reduction
	 * expression are the same as the assignments before the sequence reduction
	 * expression.
	 **/
	public boolean sequenceReductionExpressionAssignmentsBefore() {
		return this.getImpl().sequenceReductionExpressionAssignmentsBefore();
	}

	/**
	 * The assignments after a sequence reduction expression are the same as
	 * after its primary expression.
	 **/
	public Collection<AssignedSource> updateAssignments() {
		return this.getImpl().updateAssignments();
	}

	public void _deriveAll() {
		this.getReferent();
		super._deriveAll();
		ExtentOrExpression primary = this.getPrimary();
		if (primary != null) {
			primary.deriveAll();
		}
		QualifiedName behaviorName = this.getBehaviorName();
		if (behaviorName != null) {
			behaviorName.deriveAll();
		}
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.sequenceReductionExpressionReferentDerivation()) {
			violations.add(new ConstraintViolation(
					"sequenceReductionExpressionReferentDerivation", this));
		}
		if (!this.sequenceReductionExpressionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"sequenceReductionExpressionTypeDerivation", this));
		}
		if (!this.sequenceReductionExpressionUpperDerivation()) {
			violations.add(new ConstraintViolation(
					"sequenceReductionExpressionUpperDerivation", this));
		}
		if (!this.sequenceReductionExpressionLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"sequenceReductionExpressionLowerDerivation", this));
		}
		if (!this.sequenceReductionExpressionBehavior()) {
			violations.add(new ConstraintViolation(
					"sequenceReductionExpressionBehavior", this));
		}
		if (!this.sequenceReductionExpressionBehaviorParameters()) {
			violations.add(new ConstraintViolation(
					"sequenceReductionExpressionBehaviorParameters", this));
		}
		if (!this.sequenceReductionExpressionAssignmentsBefore()) {
			violations.add(new ConstraintViolation(
					"sequenceReductionExpressionAssignmentsBefore", this));
		}
		ExtentOrExpression primary = this.getPrimary();
		if (primary != null) {
			primary.checkConstraints(violations);
		}
		QualifiedName behaviorName = this.getBehaviorName();
		if (behaviorName != null) {
			behaviorName.checkConstraints(violations);
		}
	}

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		s.append(" isOrdered:");
		s.append(this.getIsOrdered());
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
		if (includeDerived) {
			ElementReference referent = this.getReferent();
			if (referent != null) {
				System.out.println(prefix + " /referent:"
						+ referent.toString(includeDerived));
			}
		}
		ExtentOrExpression primary = this.getPrimary();
		if (primary != null) {
			System.out.println(prefix + " primary:");
			primary.print(prefix + "  ", includeDerived);
		}
		QualifiedName behaviorName = this.getBehaviorName();
		if (behaviorName != null) {
			System.out.println(prefix + " behaviorName:");
			behaviorName.print(prefix + "  ", includeDerived);
		}
	}
} // SequenceReductionExpression
