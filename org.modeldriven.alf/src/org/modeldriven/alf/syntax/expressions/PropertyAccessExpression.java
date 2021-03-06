
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
import org.modeldriven.alf.syntax.expressions.impl.PropertyAccessExpressionImpl;

/**
 * An expression comprising a reference to a structural feature.
 **/

public class PropertyAccessExpression extends Expression {

	public PropertyAccessExpression() {
		this.impl = new PropertyAccessExpressionImpl(this);
	}

	public PropertyAccessExpression(Parser parser) {
		this();
		this.init(parser);
	}

	public PropertyAccessExpression(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
    public PropertyAccessExpressionImpl getImpl() {
		return (PropertyAccessExpressionImpl) this.impl;
	}

	public FeatureReference getFeatureReference() {
		return this.getImpl().getFeatureReference();
	}

	public void setFeatureReference(FeatureReference featureReference) {
		this.getImpl().setFeatureReference(featureReference);
	}

	public ElementReference getFeature() {
		return this.getImpl().getFeature();
	}

	public void setFeature(ElementReference feature) {
		this.getImpl().setFeature(feature);
	}

	/**
	 * The feature of a property access expression is the structural feature to
	 * which its feature reference resolves.
	 **/
	public boolean propertyAccessExpressionFeatureDerivation() {
		return this.getImpl().propertyAccessExpressionFeatureDerivation();
	}

	/**
	 * The type of a property access expression is the type of the referenced
	 * feature.
	 **/
	public boolean propertyAccessExpressionTypeDerivation() {
		return this.getImpl().propertyAccessExpressionTypeDerivation();
	}

	/**
	 * The multiplicity upper bound of a property access expression is given by
	 * the product of the multiplicity upper bounds of the referenced feature
	 * and the target expression.
	 **/
	public boolean propertyAccessExpressionUpperDerivation() {
		return this.getImpl().propertyAccessExpressionUpperDerivation();
	}

	/**
	 * The multiplicity lower bound of a property access expression is given by
	 * the product of the multiplicity lower bounds of the referenced feature
	 * and the target expression.
	 **/
	public boolean propertyAccessExpressionLowerDerivation() {
		return this.getImpl().propertyAccessExpressionLowerDerivation();
	}

	/**
	 * The feature reference for a property access expression must resolve to a
	 * single structural feature.
	 **/
	public boolean propertyAccessExpressionFeatureResolution() {
		return this.getImpl().propertyAccessExpressionFeatureResolution();
	}

	/**
	 * The assignments before the expression of the feature reference of a
	 * property access expression are the same as before the property access
	 * expression.
	 **/
	public boolean propertyAccessExpressionAssignmentsBefore() {
		return this.getImpl().propertyAccessExpressionAssignmentsBefore();
	}

	/**
	 * The assignments after a property access expression are the same as those
	 * after the target expression of its feature reference.
	 **/
	@Override
    public Collection<AssignedSource> updateAssignments() {
		return this.getImpl().updateAssignments();
	}

    @Override
    public void _addExternalReferences(Collection<ExternalElementReference> references) {
        super._addExternalReferences(references);
        addExternalReferencesFor(references, this.getFeatureReference());
    }

	@Override
    public void _deriveAll() {
		this.getFeature();
		super._deriveAll();
		FeatureReference featureReference = this.getFeatureReference();
		if (featureReference != null) {
			featureReference.deriveAll();
		}
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.propertyAccessExpressionFeatureDerivation()) {
			violations.add(new ConstraintViolation(
					"propertyAccessExpressionFeatureDerivation", this));
		}
		if (!this.propertyAccessExpressionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"propertyAccessExpressionTypeDerivation", this));
		}
		if (!this.propertyAccessExpressionUpperDerivation()) {
			violations.add(new ConstraintViolation(
					"propertyAccessExpressionUpperDerivation", this));
		}
		if (!this.propertyAccessExpressionLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"propertyAccessExpressionLowerDerivation", this));
		}
		if (!this.propertyAccessExpressionFeatureResolution()) {
			violations.add(new ConstraintViolation(
					"propertyAccessExpressionFeatureResolution", this));
		}
		if (!this.propertyAccessExpressionAssignmentsBefore()) {
			violations.add(new ConstraintViolation(
					"propertyAccessExpressionAssignmentsBefore", this));
		}
		FeatureReference featureReference = this.getFeatureReference();
		if (featureReference != null) {
			featureReference.checkConstraints(violations);
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
		FeatureReference featureReference = this.getFeatureReference();
		if (featureReference != null) {
			System.out.println(prefix + " featureReference:");
			featureReference.print(prefix + "  ", includeDerived);
		}
		if (includeDerived) {
			ElementReference feature = this.getFeature();
			if (feature != null) {
				System.out.println(prefix + " /feature:"
						+ feature.toString(includeDerived));
			}
		}
	}
} // PropertyAccessExpression
