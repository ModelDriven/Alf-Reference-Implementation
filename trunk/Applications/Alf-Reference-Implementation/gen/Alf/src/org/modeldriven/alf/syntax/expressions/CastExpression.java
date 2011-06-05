
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

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.castExpressionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"castExpressionTypeDerivation", this));
		}
		if (!this.castExpressionLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"castExpressionLowerDerivation", this));
		}
		if (!this.castExpressionUpperDerivation()) {
			violations.add(new ConstraintViolation(
					"castExpressionUpperDerivation", this));
		}
		if (!this.castExpressionTypeResolution()) {
			violations.add(new ConstraintViolation(
					"castExpressionTypeResolution", this));
		}
		if (!this.castExpressionAssignmentsBefore()) {
			violations.add(new ConstraintViolation(
					"castExpressionAssignmentsBefore", this));
		}
		Expression operand = this.getOperand();
		if (operand != null) {
			operand.checkConstraints(violations);
		}
		QualifiedName typeName = this.getTypeName();
		if (typeName != null) {
			typeName.checkConstraints(violations);
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
