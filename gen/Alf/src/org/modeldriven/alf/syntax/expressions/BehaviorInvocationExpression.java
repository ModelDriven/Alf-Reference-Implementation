
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

import org.omg.uml.Element;
import org.omg.uml.Profile;
import org.omg.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.expressions.impl.BehaviorInvocationExpressionImpl;

/**
 * An invocation of a behavior referenced by name.
 **/

public class BehaviorInvocationExpression extends InvocationExpression {

	public BehaviorInvocationExpression() {
		this.impl = new BehaviorInvocationExpressionImpl(this);
	}

	public BehaviorInvocationExpressionImpl getImpl() {
		return (BehaviorInvocationExpressionImpl) this.impl;
	}

	public QualifiedName getTarget() {
		return this.getImpl().getTarget();
	}

	public void setTarget(QualifiedName target) {
		this.getImpl().setTarget(target);
	}

	/**
	 * The referent of a behavior invocation expression is the behavior named by
	 * the target or, if the target disambiguates to a feature reference, the
	 * operation or signal being invoked.
	 **/
	public boolean behaviorInvocationExpressionReferentDerivation() {
		return this.getImpl().behaviorInvocationExpressionReferentDerivation();
	}

	/**
	 * If the target qualified name disambiguates to a feature reference, then
	 * the feature of a behavior invocation expression is that feature
	 * reference.
	 **/
	public boolean behaviorInvocationExpressionFeatureDerivation() {
		return this.getImpl().behaviorInvocationExpressionFeatureDerivation();
	}

	/**
	 * If the target qualified name does not disambiguate to a feature
	 * reference, then it must resolve to a behavior or an association end.
	 * Otherwise it must resolve to a single feature referent according to the
	 * overloading resolution rules, unless it is an implicit destructor call
	 * (in which case it has no referent).
	 **/
	public boolean behaviorInvocationExpressionReferentConstraint() {
		return this.getImpl().behaviorInvocationExpressionReferentConstraint();
	}

	/**
	 * An input argument expression must be assignable to its corresponding
	 * parameter. An output parameter must be assignable to its corresponding
	 * argument expression. (Note that this implies that the type of an argument
	 * expression for an inout parameter must be the same as the type of that
	 * parameter.)
	 **/
	public boolean behaviorInvocationExpressionArgumentCompatibility() {
		return this.getImpl()
				.behaviorInvocationExpressionArgumentCompatibility();
	}

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.behaviorInvocationExpressionReferentDerivation()) {
			violations.add(new ConstraintViolation(
					"behaviorInvocationExpressionReferentDerivation", this));
		}
		if (!this.behaviorInvocationExpressionFeatureDerivation()) {
			violations.add(new ConstraintViolation(
					"behaviorInvocationExpressionFeatureDerivation", this));
		}
		if (!this.behaviorInvocationExpressionReferentConstraint()) {
			violations.add(new ConstraintViolation(
					"behaviorInvocationExpressionReferentConstraint", this));
		}
		if (!this.behaviorInvocationExpressionArgumentCompatibility()) {
			violations.add(new ConstraintViolation(
					"behaviorInvocationExpressionArgumentCompatibility", this));
		}
		QualifiedName target = this.getTarget();
		if (target != null) {
			target.checkConstraints(violations);
		}
	}

	public String toString() {
		return this.getImpl().toString();
	}

	public String _toString() {
		StringBuffer s = new StringBuffer(super._toString());
		return s.toString();
	}

	public void print() {
		this.print("");
	}

	public void print(String prefix) {
		super.print(prefix);
		QualifiedName target = this.getTarget();
		if (target != null) {
			System.out.println(prefix + " target:");
			target.print(prefix + "  ");
		}
	}
} // BehaviorInvocationExpression
