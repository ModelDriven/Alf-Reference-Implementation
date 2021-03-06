/*******************************************************************************
 * Copyright 2011, 2018 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions;

import java.util.Collection;

import org.modeldriven.alf.parser.ParsedElement;
import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.syntax.common.AssignedSource;
import org.modeldriven.alf.syntax.common.ConstraintViolation;
import org.modeldriven.alf.syntax.common.ExternalElementReference;
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
		this.init(parser);
	}

	public CastExpression(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
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
	@Override
    public Collection<AssignedSource> updateAssignments() {
		return this.getImpl().updateAssignments();
	}

    @Override
    public void _addExternalReferences(Collection<ExternalElementReference> references) {
        super._addExternalReferences(references);
        addExternalReferencesFor(references, this.getOperand());
        addExternalReferencesFor(references, this.getTypeName());
    }

	@Override
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

	@Override
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

	@Override
    public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		return s.toString();
	}

	@Override
    public void print() {
		this.print("", false);
	}

	@Override
    public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	@Override
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
