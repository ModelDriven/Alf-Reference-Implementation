
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

/**
 * A left-hand side that is a property reference.
 **/

public class FeatureLeftHandSideImpl extends
		org.modeldriven.alf.syntax.expressions.impl.LeftHandSideImpl {

	public FeatureLeftHandSideImpl(FeatureLeftHandSide self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.expressions.FeatureLeftHandSide getSelf() {
		return (FeatureLeftHandSide) this.self;
	}

	/**
	 * The assignments before the expression of the feature reference of a
	 * feature left-hand side are the assignments before the feature left-hand
	 * side.
	 **/
	public boolean featureLeftHandSideAssignmentBeforeDerivation() {
		this.getSelf().getAssignmentBefore();
		return true;
	}

	/**
	 * The assignments after a feature left-hand side are the assignments after
	 * the expression of the feature reference or, if there is an index, those
	 * after the index expression.
	 **/
	public boolean featureLeftHandSideAssignmentAfterDerivation() {
		this.getSelf().getAssignmentAfter();
		return true;
	}

	/**
	 * The expression of the feature reference of a feature left-hand side must
	 * have a multiplicity upper bound of 1.
	 **/
	public boolean featureLeftHandSideFeatureExpression() {
		return true;
	}

	/**
	 * If a feature left-hand side has an index, then the assignments before the
	 * index expression are the assignments after the expression of the feature
	 * reference.
	 **/
	public boolean featureLeftHandSideAssignmentsBefore() {
		return true;
	}

} // FeatureLeftHandSideImpl
