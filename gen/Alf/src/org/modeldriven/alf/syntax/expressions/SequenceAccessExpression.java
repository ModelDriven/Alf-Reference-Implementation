
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

import org.modeldriven.alf.syntax.expressions.impl.SequenceAccessExpressionImpl;

/**
 * An expression used to access a specific element of a sequence.
 **/

public class SequenceAccessExpression extends Expression {

	private Expression primary = null;
	private Expression index = null;

	public SequenceAccessExpression() {
		this.impl = new SequenceAccessExpressionImpl(this);
	}

	public SequenceAccessExpressionImpl getImpl() {
		return (SequenceAccessExpressionImpl) this.impl;
	}

	public Expression getPrimary() {
		return this.primary;
	}

	public void setPrimary(Expression primary) {
		this.primary = primary;
	}

	public Expression getIndex() {
		return this.index;
	}

	public void setIndex(Expression index) {
		this.index = index;
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

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		Expression primary = this.getPrimary();
		if (primary != null) {
			primary.print(prefix + " ");
		}
		Expression index = this.getIndex();
		if (index != null) {
			index.print(prefix + " ");
		}
	}
} // SequenceAccessExpression
