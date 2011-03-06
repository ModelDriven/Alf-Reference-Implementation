
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

import org.modeldriven.alf.syntax.expressions.impl.CastExpressionImpl;

/**
 * An expression used to filter values by type.
 **/

public class CastExpression extends Expression {

	public CastExpression() {
		this.impl = new CastExpressionImpl(this);
	}

	public CastExpressionImpl getImpl() {
		return (CastExpressionImpl) this.impl;
	}

	public Expression getOperand() {
		return this.getImpl().getOperand();
	}

	public void setOperand(Expression operand) {
		this.getImpl().setOperand(operand);
	}

	public QualifiedName getTypeName() {
		return this.getImpl().getTypeName();
	}

	public void setTypeName(QualifiedName typeName) {
		this.getImpl().setTypeName(typeName);
	}

	/**
	 * The type of a cast expression is the referent of the given type name (if
	 * there is one).
	 **/
	public boolean castExpressionTypeDerivation() {
		return this.getImpl().castExpressionTypeDerivation();
	}

	/**
	 * A cast expression has a multiplicity lower bound of 0.
	 **/
	public boolean castExpressionLowerDerivation() {
		return this.getImpl().castExpressionLowerDerivation();
	}

	/**
	 * A cast expression has a multiplicity upper bound that is the same as the
	 * upper bound of its operand expression.
	 **/
	public boolean castExpressionUpperDerivation() {
		return this.getImpl().castExpressionUpperDerivation();
	}

	/**
	 * If the cast expression has a type name, then it must resolve to a
	 * classifier.
	 **/
	public boolean castExpressionTypeResolution() {
		return this.getImpl().castExpressionTypeResolution();
	}

	/**
	 * The assignments before the operand of a cast expression are the same as
	 * those before the cast expression.
	 **/
	public boolean castExpressionAssignmentsBefore() {
		return this.getImpl().castExpressionAssignmentsBefore();
	}

	/**
	 * The assignments after a cast expression are the same as those after its
	 * operand expression.
	 **/
	public Collection<AssignedSource> updateAssignments() {
		return this.getImpl().updateAssignments();
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		Expression operand = this.getOperand();
		if (operand != null) {
			System.out.println(prefix + " operand:");
			operand.print(prefix + "  ");
		}
		QualifiedName typeName = this.getTypeName();
		if (typeName != null) {
			System.out.println(prefix + " typeName:");
			typeName.print(prefix + "  ");
		}
	}
} // CastExpression
