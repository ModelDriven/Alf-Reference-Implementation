
/*******************************************************************************
 * Copyright 2011, 2016 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.parser.Token;

import org.modeldriven.alf.syntax.common.*;
import java.util.Collection;
import org.modeldriven.alf.syntax.expressions.impl.CastExpressionImpl;

/**
 * An expression used to filter values by type.
 **/

public class CastExpression extends Expression {

	public CastExpression() {
		this.impl = new CastExpressionImpl(this);
	}

	public CastExpression(Parser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public CastExpression(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
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
     * If the type of a cast expression is empty, or its type conforms to
     * Integer and the type of its operand expression conforms to BitString or
     * Real, or its type conforms to BitString or Real and its operand's type
     * conforms to Integer, or its operand's type conforms to its type, then the
     * multiplicity lower bound of the cast expression is the same as that of
     * its operand expression. Otherwise it is 0.
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
	 * non-template classifier.
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

	public void _deriveAll() {
		super._deriveAll();
		Expression operand = this.getOperand();
		if (operand != null) {
			operand.deriveAll();
		}
		QualifiedName typeName = this.getTypeName();
		if (typeName != null) {
			typeName.deriveAll();
		}
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
		Expression operand = this.getOperand();
		if (operand != null) {
			System.out.println(prefix + " operand:");
			operand.print(prefix + "  ", includeDerived);
		}
		QualifiedName typeName = this.getTypeName();
		if (typeName != null) {
			System.out.println(prefix + " typeName:");
			typeName.print(prefix + "  ", includeDerived);
		}
	}
} // CastExpression
