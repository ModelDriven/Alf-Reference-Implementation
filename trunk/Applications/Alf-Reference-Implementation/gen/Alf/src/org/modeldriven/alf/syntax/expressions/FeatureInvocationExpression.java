
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
 * An invocation of a feature referenced on a sequence of instances.
 **/

public class FeatureInvocationExpression extends InvocationExpression implements
		IFeatureInvocationExpression {

	private IFeatureReference target = null;

	public IFeatureReference getTarget() {
		return this.target;
	}

	public void setTarget(IFeatureReference target) {
		this.target = target;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		IFeatureReference target = this.getTarget();
		if (target != null) {
			target.print(prefix + " ");
		}
	}
} // FeatureInvocationExpression
