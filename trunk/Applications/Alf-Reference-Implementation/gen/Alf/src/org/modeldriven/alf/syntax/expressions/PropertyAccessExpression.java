
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

import org.modeldriven.alf.syntax.expressions.impl.PropertyAccessExpressionImpl;

/**
 * An expression comprising a reference to a structural feature.
 **/

public class PropertyAccessExpression extends Expression {

	public PropertyAccessExpression() {
		this.impl = new PropertyAccessExpressionImpl(this);
	}

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
	 * The multiplicity upper bound of a property access expression is given by
	 * the product of the multiplicity upper bounds of the referenced feature
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
		FeatureReference featureReference = this.getFeatureReference();
		if (featureReference != null) {
			System.out.println(prefix + " featureReference:");
			featureReference.print(prefix + "  ");
		}
		ElementReference feature = this.getFeature();
		if (feature != null) {
			System.out.println(prefix + " /feature:" + feature);
		}
	}
} // PropertyAccessExpression
