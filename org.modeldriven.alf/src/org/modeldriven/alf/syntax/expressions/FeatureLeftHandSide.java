
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
import org.modeldriven.alf.syntax.common.ConstraintViolation;
import org.modeldriven.alf.syntax.common.ExternalElementReference;
import org.modeldriven.alf.syntax.expressions.impl.FeatureLeftHandSideImpl;

/**
 * A left-hand side that is a property reference.
 **/

public class FeatureLeftHandSide extends LeftHandSide {

	public FeatureLeftHandSide() {
		this.impl = new FeatureLeftHandSideImpl(this);
	}

	public FeatureLeftHandSide(Parser parser) {
		this();
		this.init(parser);
	}

	public FeatureLeftHandSide(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
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

	/**
	 * The referent of a feature left-hand side is the structural feature to
	 * which the feature reference of the left-hand side resolves.
	 **/
	public boolean featureLeftHandSideReferentDerivation() {
		return this.getImpl().featureLeftHandSideReferentDerivation();
	}

	/**
	 * The type of a feature left-hand side is the type of its referent.
	 **/
	public boolean featureLeftHandSideTypeDerivation() {
		return this.getImpl().featureLeftHandSideTypeDerivation();
	}

	/**
	 * If a feature left-hand side is indexed, then its lower bound is 0.
	 * Otherwise, its lower bound is that of its referent.
	 **/
	public boolean featureLeftHandSideLowerDerivation() {
		return this.getImpl().featureLeftHandSideLowerDerivation();
	}

	/**
	 * If a feature left-hand side is indexed, then its upper bound is 1.
	 * Otherwise, its upper bound is that of its referent.
	 **/
	public boolean featureLeftHandSideUpperDerivation() {
		return this.getImpl().featureLeftHandSideUpperDerivation();
	}

	/**
	 * The feature of a feature-left hand side must have a single referent that
	 * is a structural feature.
	 **/
	public boolean featureLeftHandSideReferentConstraint() {
		return this.getImpl().featureLeftHandSideReferentConstraint();
	}

	/**
	 * If a feature left-hand side has an index, then the referent of the
	 * feature must be ordered and non-unique.
	 **/
	public boolean featureLeftHandSideIndexedFeature() {
		return this.getImpl().featureLeftHandSideIndexedFeature();
	}

    @Override
    public void _addExternalReferences(Collection<ExternalElementReference> references) {
        super._addExternalReferences(references);
        addExternalReferencesFor(references, this.getFeature());
    }

	@Override
    public void _deriveAll() {
		super._deriveAll();
		FeatureReference feature = this.getFeature();
		if (feature != null) {
			feature.deriveAll();
		}
	}

	@Override
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
		if (!this.featureLeftHandSideReferentDerivation()) {
			violations.add(new ConstraintViolation(
					"featureLeftHandSideReferentDerivation", this));
		}
		if (!this.featureLeftHandSideTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"featureLeftHandSideTypeDerivation", this));
		}
		if (!this.featureLeftHandSideLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"featureLeftHandSideLowerDerivation", this));
		}
		if (!this.featureLeftHandSideUpperDerivation()) {
			violations.add(new ConstraintViolation(
					"featureLeftHandSideUpperDerivation", this));
		}
		if (!this.featureLeftHandSideReferentConstraint()) {
			violations.add(new ConstraintViolation(
					"featureLeftHandSideReferentConstraint", this));
		}
		if (!this.featureLeftHandSideIndexedFeature()) {
			violations.add(new ConstraintViolation(
					"featureLeftHandSideIndexedFeature", this));
		}
		FeatureReference feature = this.getFeature();
		if (feature != null) {
			feature.checkConstraints(violations);
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
		FeatureReference feature = this.getFeature();
		if (feature != null) {
			System.out.println(prefix + " feature:");
			feature.print(prefix + "  ", includeDerived);
		}
	}
} // FeatureLeftHandSide
