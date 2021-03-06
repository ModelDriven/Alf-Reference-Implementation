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
import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.common.ExternalElementReference;
import org.modeldriven.alf.syntax.expressions.impl.ClassificationExpressionImpl;

/**
 * An expression used to test the dynamic type of its operand.
 **/

public class ClassificationExpression extends UnaryExpression {

	public ClassificationExpression() {
		this.impl = new ClassificationExpressionImpl(this);
	}

	public ClassificationExpression(Parser parser) {
		this();
		this.init(parser);
	}

	public ClassificationExpression(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
    public ClassificationExpressionImpl getImpl() {
		return (ClassificationExpressionImpl) this.impl;
	}

	public ElementReference getReferent() {
		return this.getImpl().getReferent();
	}

	public void setReferent(ElementReference referent) {
		this.getImpl().setReferent(referent);
	}

	public Boolean getIsDirect() {
		return this.getImpl().getIsDirect();
	}

	public void setIsDirect(Boolean isDirect) {
		this.getImpl().setIsDirect(isDirect);
	}

	public QualifiedName getTypeName() {
		return this.getImpl().getTypeName();
	}

	public void setTypeName(QualifiedName typeName) {
		this.getImpl().setTypeName(typeName);
	}

	/**
	 * A classification expression is direct if its operator is "hastype".
	 **/
	public boolean classificationExpressionIsDirectDerivation() {
		return this.getImpl().classificationExpressionIsDirectDerivation();
	}

	/**
	 * The referent of a classification expression is the classifier to which
	 * the type name resolves.
	 **/
	public boolean classificationExpressionReferentDerivation() {
		return this.getImpl().classificationExpressionReferentDerivation();
	}

	/**
	 * A classification expression has type Boolean.
	 **/
	public boolean classificationExpressionTypeDerivation() {
		return this.getImpl().classificationExpressionTypeDerivation();
	}

	/**
	 * A classification expression has a multiplicity lower bound of 1.
	 **/
	public boolean classificationExpressionLowerDerivation() {
		return this.getImpl().classificationExpressionLowerDerivation();
	}

	/**
	 * A classification expression has a multiplicity upper bound of 1.
	 **/
	public boolean classificationExpressionUpperDerivation() {
		return this.getImpl().classificationExpressionUpperDerivation();
	}

	/**
	 * The type name in a classification expression must resolve to a
	 * non-template classifier.
	 **/
	public boolean classificationExpressionTypeName() {
		return this.getImpl().classificationExpressionTypeName();
	}

	/**
	 * The operand expression of a classification expression must have
	 * multiplicity lower and upper bounds of 1.
	 **/
	public boolean classificationExpressionOperand() {
		return this.getImpl().classificationExpressionOperand();
	}

    /**
     * If the truth condition is true and the type of the operand of a classification
     * expression does not conform to the referent type of the classification
     * expression, then set the known type of the operand of the classification
     * expression to be the referent type of the classification expression.
     */
    @Override
    public Collection<AssignedSource> adjustAssignments(
            Collection<AssignedSource> assignments, boolean condition) {
        return this.getImpl().adjustAssignments(assignments, condition);
    }

    @Override
    public void _addExternalReferences(Collection<ExternalElementReference> references) {
        super._addExternalReferences(references);
        addExternalReferencesFor(references, this.getTypeName());
    }

    @Override
    public void _deriveAll() {
		this.getReferent();
		this.getIsDirect();
		super._deriveAll();
		QualifiedName typeName = this.getTypeName();
		if (typeName != null) {
			typeName.deriveAll();
		}
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.classificationExpressionIsDirectDerivation()) {
			violations.add(new ConstraintViolation(
					"classificationExpressionIsDirectDerivation", this));
		}
		if (!this.classificationExpressionReferentDerivation()) {
			violations.add(new ConstraintViolation(
					"classificationExpressionReferentDerivation", this));
		}
		if (!this.classificationExpressionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"classificationExpressionTypeDerivation", this));
		}
		if (!this.classificationExpressionLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"classificationExpressionLowerDerivation", this));
		}
		if (!this.classificationExpressionUpperDerivation()) {
			violations.add(new ConstraintViolation(
					"classificationExpressionUpperDerivation", this));
		}
		if (!this.classificationExpressionTypeName()) {
			violations.add(new ConstraintViolation(
					"classificationExpressionTypeName", this));
		}
		if (!this.classificationExpressionOperand()) {
			violations.add(new ConstraintViolation(
					"classificationExpressionOperand", this));
		}
		QualifiedName typeName = this.getTypeName();
		if (typeName != null) {
			typeName.checkConstraints(violations);
		}
	}

	@Override
    public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		if (includeDerived) {
			s.append(" /isDirect:");
			s.append(this.getIsDirect());
		}
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
		if (includeDerived) {
			ElementReference referent = this.getReferent();
			if (referent != null) {
				System.out.println(prefix + " /referent:"
						+ referent.toString(includeDerived));
			}
		}
		QualifiedName typeName = this.getTypeName();
		if (typeName != null) {
			System.out.println(prefix + " typeName:");
			typeName.print(prefix + "  ", includeDerived);
		}
	}
} // ClassificationExpression
