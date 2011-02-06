
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
 * A reference to a structural or behavioral feature of the type of its target
 * expression or a binary association end the opposite end of which is typed by
 * the type of its target expression.
 **/

public class FeatureReference extends SyntaxElement implements
		IFeatureReference {

	private IExpression expression = null;
	private INameBinding nameBinding = null;

	public IExpression getExpression() {
		return this.expression;
	}

	public void setExpression(IExpression expression) {
		this.expression = expression;
	}

	public INameBinding getNameBinding() {
		return this.nameBinding;
	}

	public void setNameBinding(INameBinding nameBinding) {
		this.nameBinding = nameBinding;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		IExpression expression = this.getExpression();
		if (expression != null) {
			expression.print(prefix + " ");
		}
		INameBinding nameBinding = this.getNameBinding();
		if (nameBinding != null) {
			nameBinding.print(prefix + " ");
		}
	}
} // FeatureReference
