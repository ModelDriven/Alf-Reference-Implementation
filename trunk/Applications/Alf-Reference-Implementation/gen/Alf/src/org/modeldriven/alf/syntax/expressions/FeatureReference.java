
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
 * A reference to a structural or behavioral feature of the type of its target
 * expression or a binary association end the opposite end of which is typed by
 * the type of its target expression.
 **/

public class FeatureReference extends SyntaxElement {

	private Expression expression = null;
	private ArrayList<ElementReference> referent = new ArrayList<ElementReference>(); // DERIVED
	private NameBinding nameBinding = null;

	public Expression getExpression() {
		return this.expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	public ArrayList<ElementReference> getReferent() {
		return this.referent;
	}

	public void setReferent(ArrayList<ElementReference> referent) {
		this.referent = referent;
	}

	public void addReferent(ElementReference referent) {
		this.referent.add(referent);
	}

	public NameBinding getNameBinding() {
		return this.nameBinding;
	}

	public void setNameBinding(NameBinding nameBinding) {
		this.nameBinding = nameBinding;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		if (this.expression != null) {
			this.expression.print(prefix + " ");
		}
		if (this.nameBinding != null) {
			this.nameBinding.print(prefix + " ");
		}
	}
} // FeatureReference
