
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

import org.modeldriven.alf.syntax.expressions.impl.SequenceReductionExpressionImpl;

/**
 * An expression used to reduce a sequence of values effectively by inserting a
 * binary operation between the values.
 **/

public class SequenceReductionExpression extends Expression {

	private ElementReference referent = null; // DERIVED
	private Boolean isOrdered = false;
	private ExtentOrExpression primary = null;
	private QualifiedName behaviorName = null;

	public SequenceReductionExpression() {
		this.impl = new SequenceReductionExpressionImpl(this);
	}

	public SequenceReductionExpressionImpl getImpl() {
		return (SequenceReductionExpressionImpl) this.impl;
	}

	public ElementReference getReferent() {
		if (this.referent == null) {
			this.referent = this.getImpl().deriveReferent();
		}
		return this.referent;
	}

	public Boolean getIsOrdered() {
		return this.isOrdered;
	}

	public void setIsOrdered(Boolean isOrdered) {
		this.isOrdered = isOrdered;
	}

	public ExtentOrExpression getPrimary() {
		return this.primary;
	}

	public void setPrimary(ExtentOrExpression primary) {
		this.primary = primary;
	}

	public QualifiedName getBehaviorName() {
		return this.behaviorName;
	}

	public void setBehaviorName(QualifiedName behaviorName) {
		this.behaviorName = behaviorName;
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
	public ArrayList<AssignedSource> updateAssignments() {
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
			System.out.println(prefix + " /" + referent);
		}
		ExtentOrExpression primary = this.getPrimary();
		if (primary != null) {
			primary.print(prefix + " ");
		}
		QualifiedName behaviorName = this.getBehaviorName();
		if (behaviorName != null) {
			behaviorName.print(prefix + " ");
		}
	}
} // SequenceReductionExpression
