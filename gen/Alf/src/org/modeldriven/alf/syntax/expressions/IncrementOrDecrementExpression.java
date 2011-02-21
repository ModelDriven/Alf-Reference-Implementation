
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

import org.modeldriven.alf.syntax.expressions.impl.IncrementOrDecrementExpressionImpl;

/**
 * A unary expression with either an increment or decrement operator.
 **/

public class IncrementOrDecrementExpression extends Expression {

	private String operator = "";
	private AssignedSource assignment = null; // DERIVED
	private LeftHandSide operand = null;
	private Expression expression = null; // DERIVED
	private ElementReference feature = null; // DERIVED
	private Boolean isPrefix = false;
	private Boolean isFeature = null; // DERIVED
	private Boolean isIndexed = null; // DERIVED
	private Boolean isDataValueUpdate = null; // DERIVED

	public IncrementOrDecrementExpression() {
		this.impl = new IncrementOrDecrementExpressionImpl(this);
	}

	public IncrementOrDecrementExpressionImpl getImpl() {
		return (IncrementOrDecrementExpressionImpl) this.impl;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public AssignedSource getAssignment() {
		if (this.assignment == null) {
			this.assignment = this.getImpl().deriveAssignment();
		}
		return this.assignment;
	}

	public LeftHandSide getOperand() {
		return this.operand;
	}

	public void setOperand(LeftHandSide operand) {
		this.operand = operand;
	}

	public Expression getExpression() {
		if (this.expression == null) {
			this.expression = this.getImpl().deriveExpression();
		}
		return this.expression;
	}

	public ElementReference getFeature() {
		if (this.feature == null) {
			this.feature = this.getImpl().deriveFeature();
		}
		return this.feature;
	}

	public Boolean getIsPrefix() {
		return this.isPrefix;
	}

	public void setIsPrefix(Boolean isPrefix) {
		this.isPrefix = isPrefix;
	}

	public Boolean getIsFeature() {
		if (this.isFeature == null) {
			this.isFeature = this.getImpl().deriveIsFeature();
		}
		return this.isFeature;
	}

	public Boolean getIsIndexed() {
		if (this.isIndexed == null) {
			this.isIndexed = this.getImpl().deriveIsIndexed();
		}
		return this.isIndexed;
	}

	public Boolean getIsDataValueUpdate() {
		if (this.isDataValueUpdate == null) {
			this.isDataValueUpdate = this.getImpl().deriveIsDataValueUpdate();
		}
		return this.isDataValueUpdate;
	}

	/**
	 * If the operand of an increment or decrement expression is a name, then
	 * the assignment for the expression is a new assigned source for the name
	 * with the expression as the source.
	 **/
	public boolean incrementOrDecrementExpressionAssignment() {
		return this.getImpl().incrementOrDecrementExpressionAssignment();
	}

	/**
	 * An increment or decrement expression has a feature as its operand if the
	 * operand is a kind of FeatureLeftHandSide.
	 **/
	public boolean incrementOrDecrementExpressionIsFeatureDerivation() {
		return this.getImpl()
				.incrementOrDecrementExpressionIsFeatureDerivation();
	}

	/**
	 * An increment or decrement expression is indexed if its operand is
	 * indexed.
	 **/
	public boolean incrementOrDecrementExpressionIsIndexedDerivation() {
		return this.getImpl()
				.incrementOrDecrementExpressionIsIndexedDerivation();
	}

	/**
	 * An increment or decrement expression is a data value update if its
	 * operand is an attribute of a data value held in a local name or
	 * parameter.
	 **/
	public boolean incrementOrDecrementExpressionIsDataValueUpdate() {
		return this.getImpl().incrementOrDecrementExpressionIsDataValueUpdate();
	}

	/**
	 * If the operand of an increment or decrement expression is a feature, then
	 * the referent for the operand.
	 **/
	public boolean incrementOrDecrementExpressionFeature() {
		return this.getImpl().incrementOrDecrementExpressionFeature();
	}

	/**
	 * The effective expression for the operand of an increment or decrement
	 * expression is the operand treated as a name expression, property access
	 * expression or sequence access expression, as appropriate for evaluation
	 * to obtain the original value to be updated.
	 **/
	public boolean incrementOrDecrementExpressionExpressionDerivation() {
		return this.getImpl()
				.incrementOrDecrementExpressionExpressionDerivation();
	}

	/**
	 * An increment or decrement expression has type Integer.
	 **/
	public boolean incrementOrDecrementExpressionTypeDerivation() {
		return this.getImpl().incrementOrDecrementExpressionTypeDerivation();
	}

	/**
	 * An increment or decrement expression has the same multiplicity lower
	 * bound as its operand expression.
	 **/
	public boolean incrementOrDecrementExpressionLowerDerivation() {
		return this.getImpl().incrementOrDecrementExpressionLowerDerivation();
	}

	/**
	 * An increment or decrement expression has a multiplicity upper bound of 1.
	 **/
	public boolean incrementOrDecrementExpressionUpperDerivation() {
		return this.getImpl().incrementOrDecrementExpressionUpperDerivation();
	}

	/**
	 * The operand expression must have type Integer and a multiplicity upper
	 * bound of 1.
	 **/
	public boolean incrementOrDecrementExpressionOperand() {
		return this.getImpl().incrementOrDecrementExpressionOperand();
	}

	/**
	 * The assignments before the operand of an increment or decrement
	 * expression are the same as those before the increment or decrement
	 * expression.
	 **/
	public boolean incrementOrDecrementExpressionAssignmentsBefore() {
		return this.getImpl().incrementOrDecrementExpressionAssignmentsBefore();
	}

	/**
	 * The assignments after an increment and decrement expression include all
	 * those after its operand expression. Further, if the operand expression,
	 * considered as a left hand side, is a local name, then this is reassigned.
	 **/
	public ArrayList<AssignedSource> updateAssignments() {
		return this.getImpl().updateAssignments();
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" operator:");
		s.append(this.getOperator());
		s.append(" isPrefix:");
		s.append(this.getIsPrefix());
		Boolean isFeature = this.getIsFeature();
		if (isFeature != null) {
			s.append(" /isFeature:");
			s.append(isFeature);
		}
		Boolean isIndexed = this.getIsIndexed();
		if (isIndexed != null) {
			s.append(" /isIndexed:");
			s.append(isIndexed);
		}
		Boolean isDataValueUpdate = this.getIsDataValueUpdate();
		if (isDataValueUpdate != null) {
			s.append(" /isDataValueUpdate:");
			s.append(isDataValueUpdate);
		}
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		AssignedSource assignment = this.getAssignment();
		if (assignment != null) {
			System.out.println(prefix + " /assignment:" + assignment);
		}
		LeftHandSide operand = this.getOperand();
		if (operand != null) {
			System.out.println(prefix + " operand:");
			operand.print(prefix + "  ");
		}
		Expression expression = this.getExpression();
		if (expression != null) {
			System.out.println(prefix + " /expression:" + expression);
		}
		ElementReference feature = this.getFeature();
		if (feature != null) {
			System.out.println(prefix + " /feature:" + feature);
		}
	}
} // IncrementOrDecrementExpression
