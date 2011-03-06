
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
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.expressions.impl.FeatureInvocationExpressionImpl;

/**
 * An invocation of a feature referenced on a sequence of instances.
 **/

public class FeatureInvocationExpression extends InvocationExpression {

	public FeatureInvocationExpression() {
		this.impl = new FeatureInvocationExpressionImpl(this);
	}

	public FeatureInvocationExpressionImpl getImpl() {
		return (FeatureInvocationExpressionImpl) this.impl;
	}

	public FeatureReference getTarget() {
		return this.getImpl().getTarget();
	}

	public void setTarget(FeatureReference target) {
		this.getImpl().setTarget(target);
	}

	/**
	 * If a feature invocation expression is an implicit object destruction, it
	 * has no referent. Otherwise, its referent is the referent of its feature.
	 **/
	public boolean featureInvocationExpressionReferentDerivation() {
		return this.getImpl().featureInvocationExpressionReferentDerivation();
	}

	/**
	 * If a feature invocation expression has an explicit target, then that is
	 * its feature. Otherwise, it is an alternative constructor call with its
	 * feature determined implicitly.
	 **/
	public boolean featureInvocationExpressionFeatureDerivation() {
		return this.getImpl().featureInvocationExpressionFeatureDerivation();
	}

	/**
	 * If a feature invocation expression is not an implicit destructor call,
	 * then it must be possible to determine a single valid referent for it
	 * according to the overloading resolution rules.
	 **/
	public boolean featureInvocationExpressionReferentExists() {
		return this.getImpl().featureInvocationExpressionReferentExists();
	}

	/**
	 * An alternative constructor invocation may only occur in an expression
	 * statement as the first statement in the definition for the method of a
	 * constructor operation.
	 **/
	public boolean featureInvocationExpressionAlternativeConstructor() {
		return this.getImpl()
				.featureInvocationExpressionAlternativeConstructor();
	}

	/**
	 * If there is no target feature expression, then the implicit feature with
	 * the same name as the target type must be a constructor.
	 **/
	public boolean featureInvocationExpressionImplicitAlternativeConstructor() {
		return this.getImpl()
				.featureInvocationExpressionImplicitAlternativeConstructor();
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		FeatureReference target = this.getTarget();
		if (target != null) {
			System.out.println(prefix + " target:");
			target.print(prefix + "  ");
		}
	}
} // FeatureInvocationExpression
