
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions.impl.gen;

import org.modeldriven.alf.parser.AlfParser;
import org.modeldriven.alf.parser.Token;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.Profile;
import org.modeldriven.alf.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

/**
 * A left-hand side that is a property reference.
 **/

public class FeatureLeftHandSideImpl extends
		org.modeldriven.alf.syntax.expressions.impl.gen.LeftHandSideImpl {

	private FeatureReference feature = null;

	public FeatureLeftHandSideImpl(FeatureLeftHandSide self) {
		super(self);
	}

	public FeatureLeftHandSide getSelf() {
		return (FeatureLeftHandSide) this.self;
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

	/**
	 * The referent of a feature left-hand side is the structural feature to
	 * which the feature reference of the left-hand side resolves.
	 **/
	public boolean featureLeftHandSideReferentDerivation() {
		this.getSelf().getReferent();
		return true;
	}

	/**
	 * The type of a feature left-hand side is the type of its referent.
	 **/
	public boolean featureLeftHandSideTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	/**
	 * If a feature left-hand side is indexed, then its lower bound is 1.
	 * Otherwise, its lower bound is that of its referent.
	 **/
	public boolean featureLeftHandSideLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	/**
	 * If a feature left-hand side is indexed, then its upper bound is 1.
	 * Otherwise, its upper bound is that of its referent.
	 **/
	public boolean featureLeftHandSideUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

	/**
	 * The feature of a feature-left hand side must have a single referent that
	 * is a structural feature.
	 **/
	public boolean featureLeftHandSideReferentConstraint() {
		return true;
	}

	/**
	 * If a feature left-hand side has an index, then the referent of the
	 * feature must be ordered and non-unique.
	 **/
	public boolean featureLeftHandSideIndexedFeature() {
		return true;
	}

} // FeatureLeftHandSideImpl
