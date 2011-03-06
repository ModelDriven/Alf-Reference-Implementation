
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
import org.modeldriven.alf.syntax.common.impl.SyntaxElementImpl;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.omg.uml.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A reference to a structural or behavioral feature of the type of its target
 * expression or a binary association end the opposite end of which is typed by
 * the type of its target expression.
 **/

public class FeatureReferenceImpl extends SyntaxElementImpl {

	private Expression expression = null;
	private Collection<ElementReference> referent = null; // DERIVED
	private NameBinding nameBinding = null;

	public FeatureReferenceImpl(FeatureReference self) {
		super(self);
	}

	public FeatureReference getSelf() {
		return (FeatureReference) this.self;
	}

	public Expression getExpression() {
		return this.expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	public Collection<ElementReference> getReferent() {
		if (this.referent == null) {
			this.setReferent(this.deriveReferent());
		}
		return this.referent;
	}

	public void setReferent(Collection<ElementReference> referent) {
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

	protected Collection<ElementReference> deriveReferent() {
		return null; // STUB
	}

	/**
	 * The features referenced by a feature reference include the features of
	 * the type of the target expression and the association ends of any binary
	 * associations whose opposite ends are typed by the type of the target
	 * expression.
	 **/
	public boolean featureReferenceReferentDerivation() {
		this.getSelf().getReferent();
		return true;
	}

	/**
	 * The target expression of the feature reference may not be untyped, nor
	 * may it have a primitive or enumeration type.
	 **/
	public boolean featureReferenceTargetType() {
		return true;
	}

} // FeatureReferenceImpl
