
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

import org.modeldriven.alf.syntax.expressions.impl.FeatureLeftHandSideImpl;

/**
 * A left-hand side that is a property reference.
 **/

public class FeatureLeftHandSide extends LeftHandSide {

	private FeatureReference feature = null;

	public FeatureLeftHandSide() {
		this.impl = new FeatureLeftHandSideImpl(this);
	}

	public FeatureLeftHandSideImpl getImpl() {
		return (FeatureLeftHandSideImpl) this.impl;
	}

	public FeatureReference getFeature() {
		return this.feature;
	}

	public void setFeature(FeatureReference feature) {
		this.feature = feature;
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

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
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
