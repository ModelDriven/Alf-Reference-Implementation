
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions;

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

import org.modeldriven.alf.syntax.expressions.impl.PropertyAccessExpressionImpl;

/**
 * An expression comprising a reference to a structural feature.
 **/

public class PropertyAccessExpression extends Expression {

	public PropertyAccessExpression() {
		this.impl = new PropertyAccessExpressionImpl(this);
	}

	public PropertyAccessExpression(AlfParser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public PropertyAccessExpression(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
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

	public void _deriveAll() {
		this.getFeature();
		super._deriveAll();
		FeatureReference featureReference = this.getFeatureReference();
		if (featureReference != null) {
			featureReference.deriveAll();
		}
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

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		return s.toString();
	}

	public void print() {
		this.print("", false);
	}

	public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

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
