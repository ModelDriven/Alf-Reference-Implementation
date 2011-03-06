
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

import org.modeldriven.alf.syntax.expressions.impl.SequenceReductionExpressionImpl;

/**
 * An expression used to reduce a sequence of values effectively by inserting a
 * binary operation between the values.
 **/

public class SequenceReductionExpression extends Expression {

	public SequenceReductionExpression() {
		this.impl = new SequenceReductionExpressionImpl(this);
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

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" isOrdered:");
		s.append(this.getIsOrdered());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		ElementReference referent = this.getReferent();
		if (referent != null) {
			System.out.println(prefix + " /referent:" + referent);
		}
		ExtentOrExpression primary = this.getPrimary();
		if (primary != null) {
			System.out.println(prefix + " primary:");
			primary.print(prefix + "  ");
		}
		QualifiedName behaviorName = this.getBehaviorName();
		if (behaviorName != null) {
			System.out.println(prefix + " behaviorName:");
			behaviorName.print(prefix + "  ");
		}
	}
} // SequenceReductionExpression
