
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

import org.omg.uml.*;

import java.util.ArrayList;

import org.modeldriven.alf.syntax.expressions.impl.FeatureReferenceImpl;

/**
 * A reference to a structural or behavioral feature of the type of its target
 * expression or a binary association end the opposite end of which is typed by
 * the type of its target expression.
 **/

public class FeatureReference extends SyntaxElement {

	private Expression expression = null;
	private ArrayList<ElementReference> referent = null; // DERIVED
	private NameBinding nameBinding = null;

	public FeatureReference() {
		this.impl = new FeatureReferenceImpl(this);
	}

	public FeatureReferenceImpl getImpl() {
		return (FeatureReferenceImpl) this.impl;
	}

	public Expression getExpression() {
		return this.expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	public ArrayList<ElementReference> getReferent() {
		if (this.referent == null) {
			this.setReferent(this.getImpl().deriveReferent());
		}
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

	/**
	 * The features referenced by a feature reference include the features of
	 * the type of the target expression and the association ends of any binary
	 * associations whose opposite ends are typed by the type of the target
	 * expression.
	 **/
	public boolean featureReferenceReferentDerivation() {
		return this.getImpl().featureReferenceReferentDerivation();
	}

	/**
	 * The target expression of the feature reference may not be untyped, nor
	 * may it have a primitive or enumeration type.
	 **/
	public boolean featureReferenceTargetType() {
		return this.getImpl().featureReferenceTargetType();
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		Expression expression = this.getExpression();
		if (expression != null) {
			System.out.println(prefix + " expression:");
			expression.print(prefix + "  ");
		}
		ArrayList<ElementReference> referent = this.getReferent();
		if (referent != null) {
			if (referent.size() > 0) {
				System.out.println(prefix + " /referent:");
			}
			for (ElementReference _referent : referent) {
				System.out.println(prefix + "  " + _referent);
			}
		}
		NameBinding nameBinding = this.getNameBinding();
		if (nameBinding != null) {
			System.out.println(prefix + " nameBinding:");
			nameBinding.print(prefix + "  ");
		}
	}
} // FeatureReference
