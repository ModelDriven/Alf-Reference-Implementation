
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

import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.Profile;
import org.modeldriven.alf.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.expressions.impl.FeatureReferenceImpl;

/**
 * A reference to a structural or behavioral feature of the type of its target
 * expression or a binary association end the opposite end of which is typed by
 * the type of its target expression.
 **/

public class FeatureReference extends SyntaxElement {

	public FeatureReference() {
		this.impl = new FeatureReferenceImpl(this);
	}

	public FeatureReferenceImpl getImpl() {
		return (FeatureReferenceImpl) this.impl;
	}

	public Expression getExpression() {
		return this.getImpl().getExpression();
	}

	public void setExpression(Expression expression) {
		this.getImpl().setExpression(expression);
	}

	public Collection<ElementReference> getReferent() {
		return this.getImpl().getReferent();
	}

	public void setReferent(Collection<ElementReference> referent) {
		this.getImpl().setReferent(referent);
	}

	public void addReferent(ElementReference referent) {
		this.getImpl().addReferent(referent);
	}

	public NameBinding getNameBinding() {
		return this.getImpl().getNameBinding();
	}

	public void setNameBinding(NameBinding nameBinding) {
		this.getImpl().setNameBinding(nameBinding);
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

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.featureReferenceReferentDerivation()) {
			violations.add(new ConstraintViolation(
					"featureReferenceReferentDerivation", this));
		}
		if (!this.featureReferenceTargetType()) {
			violations.add(new ConstraintViolation(
					"featureReferenceTargetType", this));
		}
		Expression expression = this.getExpression();
		if (expression != null) {
			expression.checkConstraints(violations);
		}
		NameBinding nameBinding = this.getNameBinding();
		if (nameBinding != null) {
			nameBinding.checkConstraints(violations);
		}
	}

	public String toString() {
		return this.toString(false);
	}

	public String toString(boolean includeDerived) {
		return "(" + this.hashCode() + ")"
				+ this.getImpl().toString(includeDerived);
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
		Expression expression = this.getExpression();
		if (expression != null) {
			System.out.println(prefix + " expression:");
			expression.print(prefix + "  ", includeDerived);
		}
		if (includeDerived) {
			Collection<ElementReference> referent = this.getReferent();
			if (referent != null && referent.size() > 0) {
				System.out.println(prefix + " /referent:");
				for (Object _object : referent.toArray()) {
					ElementReference _referent = (ElementReference) _object;
					System.out.println(prefix + "  "
							+ _referent.toString(includeDerived));
				}
			}
		}
		NameBinding nameBinding = this.getNameBinding();
		if (nameBinding != null) {
			System.out.println(prefix + " nameBinding:");
			nameBinding.print(prefix + "  ", includeDerived);
		}
	}
} // FeatureReference
