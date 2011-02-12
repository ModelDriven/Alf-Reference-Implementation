
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
 * An expression comprising a reference to a structural feature.
 **/

public class PropertyAccessExpressionImpl extends
		org.modeldriven.alf.syntax.expressions.impl.ExpressionImpl {

	public PropertyAccessExpressionImpl(PropertyAccessExpression self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.expressions.PropertyAccessExpression getSelf() {
		return (PropertyAccessExpression) this.self;
	}

	public ElementReference deriveFeature() {
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
	 * The multiplicity upper bound of a property access expression is given by
	 * the product of the multiplicity upper bounds of the referenced feature
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
	public ArrayList<AssignedSource> updateAssignments() {
		return new ArrayList<AssignedSource>(); // STUB
	} // updateAssignments

} // PropertyAccessExpressionImpl
