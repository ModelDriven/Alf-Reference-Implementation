
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl.gen;

import org.modeldriven.alf.parser.Parser;
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
 * An expression comprising a reference to a structural feature.
 **/

public class PropertyAccessExpressionImpl extends
		org.modeldriven.alf.syntax.expressions.impl.gen.ExpressionImpl {

	private FeatureReference featureReference = null;
	private ElementReference feature = null; // DERIVED

	public PropertyAccessExpressionImpl(PropertyAccessExpression self) {
		super(self);
	}

	public PropertyAccessExpression getSelf() {
		return (PropertyAccessExpression) this.self;
	}

	public FeatureReference getFeatureReference() {
		return this.featureReference;
	}

	public void setFeatureReference(FeatureReference featureReference) {
		this.featureReference = featureReference;
	}

	public ElementReference getFeature() {
		if (this.feature == null) {
			this.setFeature(this.deriveFeature());
		}
		return this.feature;
	}

	public void setFeature(ElementReference feature) {
		this.feature = feature;
	}

	protected ElementReference deriveFeature() {
		return null; // STUB
	}

	/**
	 * The feature of a property access expression is the structural feature to
	 * which its feature reference resolves.
	 **/
	public boolean propertyAccessExpressionFeatureDerivation() {
		this.getSelf().getFeature();
		return true;
	}

	/**
	 * The type of a property access expression is the type of the referenced
	 * feature.
	 **/
	public boolean propertyAccessExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	/**
	 * The multiplicity upper bound of a property access expression is given by
	 * the product of the multiplicity upper bounds of the referenced feature
	 * and the target expression.
	 **/
	public boolean propertyAccessExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

	/**
	 * The multiplicity lower bound of a property access expression is given by
	 * the product of the multiplicity lower bounds of the referenced feature
	 * and the target expression.
	 **/
	public boolean propertyAccessExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	/**
	 * The feature reference for a property access expression must resolve to a
	 * single structural feature.
	 **/
	public boolean propertyAccessExpressionFeatureResolution() {
		return true;
	}

	/**
	 * The assignments before the expression of the feature reference of a
	 * property access expression are the same as before the property access
	 * expression.
	 **/
	public boolean propertyAccessExpressionAssignmentsBefore() {
		return true;
	}

	/**
	 * The assignments after a property access expression are the same as those
	 * after the target expression of its feature reference.
	 **/
	public Collection<AssignedSource> updateAssignments() {
		return new ArrayList<AssignedSource>(); // STUB
	} // updateAssignments

} // PropertyAccessExpressionImpl
