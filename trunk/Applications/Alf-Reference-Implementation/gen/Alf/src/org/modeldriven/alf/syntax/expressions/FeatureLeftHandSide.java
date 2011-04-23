
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

import org.modeldriven.alf.syntax.expressions.impl.FeatureLeftHandSideImpl;

/**
 * A left-hand side that is a property reference.
 **/

public class FeatureLeftHandSide extends LeftHandSide {

	public FeatureLeftHandSide() {
		this.impl = new FeatureLeftHandSideImpl(this);
	}

	public FeatureLeftHandSideImpl getImpl() {
		return (FeatureLeftHandSideImpl) this.impl;
	}

	public FeatureReference getFeature() {
		return this.getImpl().getFeature();
	}

	public void setFeature(FeatureReference feature) {
		this.getImpl().setFeature(feature);
	}

	/**
	 * The assignments before the expression of the feature reference of a
	 * feature left-hand side are the assignments before the feature left-hand
	 * side.
	 **/
	public boolean featureLeftHandSideAssignmentBeforeDerivation() {
		return this.getImpl().featureLeftHandSideAssignmentBeforeDerivation();
	}

	/**
	 * The assignments after a feature left-hand side are the assignments after
	 * the expression of the feature reference or, if there is an index, those
	 * after the index expression.
	 **/
	public boolean featureLeftHandSideAssignmentAfterDerivation() {
		return this.getImpl().featureLeftHandSideAssignmentAfterDerivation();
	}

	/**
	 * The expression of the feature reference of a feature left-hand side must
	 * have a multiplicity upper bound of 1.
	 **/
	public boolean featureLeftHandSideFeatureExpression() {
		return this.getImpl().featureLeftHandSideFeatureExpression();
	}

	/**
	 * If a feature left-hand side has an index, then the assignments before the
	 * index expression are the assignments after the expression of the feature
	 * reference.
	 **/
	public boolean featureLeftHandSideAssignmentsBefore() {
		return this.getImpl().featureLeftHandSideAssignmentsBefore();
	}

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.featureLeftHandSideAssignmentBeforeDerivation()) {
			violations.add(new ConstraintViolation(
					"featureLeftHandSideAssignmentBeforeDerivation", this));
		}
		if (!this.featureLeftHandSideAssignmentAfterDerivation()) {
			violations.add(new ConstraintViolation(
					"featureLeftHandSideAssignmentAfterDerivation", this));
		}
		if (!this.featureLeftHandSideFeatureExpression()) {
			violations.add(new ConstraintViolation(
					"featureLeftHandSideFeatureExpression", this));
		}
		if (!this.featureLeftHandSideAssignmentsBefore()) {
			violations.add(new ConstraintViolation(
					"featureLeftHandSideAssignmentsBefore", this));
		}
		FeatureReference feature = this.getFeature();
		if (feature != null) {
			feature.checkConstraints(violations);
		}
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print() {
		this.print("");
	}

	public void print(String prefix) {
		super.print(prefix);
		FeatureReference feature = this.getFeature();
		if (feature != null) {
			System.out.println(prefix + " feature:");
			feature.print(prefix + "  ");
		}
	}
} // FeatureLeftHandSide
