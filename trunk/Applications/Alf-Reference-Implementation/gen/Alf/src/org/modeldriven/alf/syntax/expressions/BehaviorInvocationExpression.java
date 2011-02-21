
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

import org.modeldriven.alf.syntax.expressions.impl.BehaviorInvocationExpressionImpl;

/**
 * An invocation of a behavior referenced by name.
 **/

public class BehaviorInvocationExpression extends InvocationExpression {

	private QualifiedName target = null;

	public BehaviorInvocationExpression() {
		this.impl = new BehaviorInvocationExpressionImpl(this);
	}

	public BehaviorInvocationExpressionImpl getImpl() {
		return (BehaviorInvocationExpressionImpl) this.impl;
	}

	public QualifiedName getTarget() {
		return this.target;
	}

	public void setTarget(QualifiedName target) {
		this.target = target;
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

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
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
