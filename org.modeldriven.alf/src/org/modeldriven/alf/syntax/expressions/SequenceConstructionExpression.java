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
import org.modeldriven.alf.syntax.expressions.impl.SequenceConstructionExpressionImpl;

/**
 * An expression used to construct a sequence of values.
 **/

public class SequenceConstructionExpression extends Expression {

	public SequenceConstructionExpression() {
		this.impl = new SequenceConstructionExpressionImpl(this);
	}

	public SequenceConstructionExpression(Parser parser) {
		this();
		this.init(parser);
	}

	public SequenceConstructionExpression(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
    public SequenceConstructionExpressionImpl getImpl() {
		return (SequenceConstructionExpressionImpl) this.impl;
	}

	public SequenceElements getElements() {
		return this.getImpl().getElements();
	}

	public void setElements(SequenceElements elements) {
		this.getImpl().setElements(elements);
	}

	public Boolean getHasMultiplicity() {
		return this.getImpl().getHasMultiplicity();
	}

	public void setHasMultiplicity(Boolean hasMultiplicity) {
		this.getImpl().setHasMultiplicity(hasMultiplicity);
	}

	public QualifiedName getTypeName() {
		return this.getImpl().getTypeName();
	}

	public void setTypeName(QualifiedName typeName) {
		this.getImpl().setTypeName(typeName);
	}

	/**
	 * If the type name of a sequence construction expression is not empty, then
	 * the type of the expression is the classifier to which the type name
	 * resolves.
	 **/
	public boolean sequenceConstructionExpressionTypeDerivation() {
		return this.getImpl().sequenceConstructionExpressionTypeDerivation();
	}

	/**
	 * If a sequence construction expression has multiplicity, then its
	 * multiplicity upper bound is given by its elements, if this is not empty,
	 * and zero otherwise. If a sequence construction expression does not have
	 * multiplicity, then its multiplicity upper bound is one.
	 **/
	public boolean sequenceConstructionExpressionUpperDerivation() {
		return this.getImpl().sequenceConstructionExpressionUpperDerivation();
	}

	/**
	 * If a sequence construction expression has multiplicity, then its
	 * multiplicity lower bound is given by its elements, if this is not empty,
	 * and zero otherwise. If a sequence construction expression does not have
	 * multiplicity, then its multiplicity lower bound is one.
	 **/
	public boolean sequenceConstructionExpressionLowerDerivation() {
		return this.getImpl().sequenceConstructionExpressionLowerDerivation();
	}

    /**
     * If the type name of a sequence construction expression is not empty, then
     * it must resolve to a non-template classifier. If the expression does not
     * have multiplicity, then its type must be a collection class.
     **/
	public boolean sequenceConstructionExpressionType() {
		return this.getImpl().sequenceConstructionExpressionType();
	}

    /**
     * If the elements of a sequence construction expression are given by a
     * sequence range, then the expression must have a type that conforms to
     * type Integer. If the elements are given by a sequence element list, and
     * the sequence construction expression has a non-empty type, then each
     * expression in the list must have a type that either conforms to the type
     * of the sequence construction expression or is convertible to it by bit
     * string conversion or real conversion.
     **/
	public boolean sequenceConstructionExpressionElementType() {
		return this.getImpl().sequenceConstructionExpressionElementType();
	}

	/**
	 * If the elements of a sequence construction expression are given by a
	 * sequence expression list, then the assignments before the first
	 * expression in the list are the same as the assignments before the
	 * sequence construction expression, and the assignments before each
	 * subsequent expression are the assignments after the previous expression.
	 * If the elements are given by a sequence range, the assignments before
	 * both expressions in the range are the same as the assignments before the
	 * sequence construction expression.
	 **/
	public boolean sequenceConstructionExpressionAssignmentsBefore() {
		return this.getImpl().sequenceConstructionExpressionAssignmentsBefore();
	}

	/**
	 * If the elements of the sequence construction expression are given by a
	 * sequence expression list, then return the assignments after the last
	 * expression in the list (if the list is empty, then return the assignments
	 * before the sequence construction expression). If the elements are given
	 * by a sequence range, then return the union of the assignments after each
	 * of the expressions in the range.
	 **/
	@Override
    public Collection<AssignedSource> updateAssignments() {
		return this.getImpl().updateAssignments();
	}

    @Override
    public void _addExternalReferences(Collection<ExternalElementReference> references) {
        super._addExternalReferences(references);
        addExternalReferencesFor(references, this.getElements());
        addExternalReferencesFor(references, this.getTypeName());
    }

	@Override
    public void _deriveAll() {
		super._deriveAll();
		SequenceElements elements = this.getElements();
		if (elements != null) {
			elements.deriveAll();
		}
		QualifiedName typeName = this.getTypeName();
		if (typeName != null) {
			typeName.deriveAll();
		}
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.sequenceConstructionExpressionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"sequenceConstructionExpressionTypeDerivation", this));
		}
		if (!this.sequenceConstructionExpressionUpperDerivation()) {
			violations.add(new ConstraintViolation(
					"sequenceConstructionExpressionUpperDerivation", this));
		}
		if (!this.sequenceConstructionExpressionLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"sequenceConstructionExpressionLowerDerivation", this));
		}
		if (!this.sequenceConstructionExpressionType()) {
			violations.add(new ConstraintViolation(
					"sequenceConstructionExpressionType", this));
		}
		if (!this.sequenceConstructionExpressionElementType()) {
			violations.add(new ConstraintViolation(
					"sequenceConstructionExpressionElementType", this));
		}
		if (!this.sequenceConstructionExpressionAssignmentsBefore()) {
			violations.add(new ConstraintViolation(
					"sequenceConstructionExpressionAssignmentsBefore", this));
		}
		SequenceElements elements = this.getElements();
		if (elements != null) {
			elements.checkConstraints(violations);
		}
		QualifiedName typeName = this.getTypeName();
		if (typeName != null) {
			typeName.checkConstraints(violations);
		}
	}

	@Override
    public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		s.append(" hasMultiplicity:");
		s.append(this.getHasMultiplicity());
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
		SequenceElements elements = this.getElements();
		if (elements != null) {
			System.out.println(prefix + " elements:");
			elements.print(prefix + "  ", includeDerived);
		}
		QualifiedName typeName = this.getTypeName();
		if (typeName != null) {
			System.out.println(prefix + " typeName:");
			typeName.print(prefix + "  ", includeDerived);
		}
	}
} // SequenceConstructionExpression
