
/*
 * Copyright 2010 Data Access Technologies, Inc. (Model Driven Solutions)
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

import java.util.ArrayList;

/**
 * An expression comprising a reference to a structural feature.
 **/

public class PropertyAccessExpression extends Expression {

	private FeatureReference featureReference = null;
	private ElementReference feature = null; // DERIVED

	public FeatureReference getFeatureReference() {
		return this.featureReference;
	}

	public void setFeatureReference(FeatureReference featureReference) {
		this.featureReference = featureReference;
	}

	public ElementReference getFeature() {
		return this.feature;
	}

	public void setFeature(ElementReference feature) {
		this.feature = feature;
	}

	public ArrayList<AssignedSource> updateAssignments() {
		/*
		 * The assignments after a property access expression are the same as
		 * those after the target expression of its feature reference.
		 */
		return new ArrayList<AssignedSource>(); // STUB
	} // updateAssignments

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		if (this.featureReference != null) {
			this.featureReference.print(prefix + " ");
		}
	}
} // PropertyAccessExpression
