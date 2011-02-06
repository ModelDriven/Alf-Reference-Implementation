
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

import java.util.ArrayList;

/**
 * A left-hand side that is a property reference.
 **/

public class FeatureLeftHandSide extends LeftHandSide implements
		IFeatureLeftHandSide {

	private IFeatureReference feature = null;

	public IFeatureReference getFeature() {
		return this.feature;
	}

	public void setFeature(IFeatureReference feature) {
		this.feature = feature;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		IFeatureReference feature = this.getFeature();
		if (feature != null) {
			feature.print(prefix + " ");
		}
	}
} // FeatureLeftHandSide
