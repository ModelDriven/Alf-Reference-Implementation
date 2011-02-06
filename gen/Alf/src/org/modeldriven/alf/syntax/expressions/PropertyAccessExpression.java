
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
 * An expression comprising a reference to a structural feature.
 **/

public class PropertyAccessExpression extends Expression implements
		IPropertyAccessExpression {

	private IFeatureReference featureReference = null;

	public IFeatureReference getFeatureReference() {
		return this.featureReference;
	}

	public void setFeatureReference(IFeatureReference featureReference) {
		this.featureReference = featureReference;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		IFeatureReference featureReference = this.getFeatureReference();
		if (featureReference != null) {
			featureReference.print(prefix + " ");
		}
	}
} // PropertyAccessExpression
