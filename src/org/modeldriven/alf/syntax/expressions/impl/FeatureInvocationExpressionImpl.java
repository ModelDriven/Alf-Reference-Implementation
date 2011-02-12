
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
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

/**
 * An invocation of a feature referenced on a sequence of instances.
 **/

public class FeatureInvocationExpressionImpl extends
		org.modeldriven.alf.syntax.expressions.impl.InvocationExpressionImpl {

	public FeatureInvocationExpressionImpl(FeatureInvocationExpression self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.expressions.FeatureInvocationExpression getSelf() {
		return (FeatureInvocationExpression) this.self;
	}

	/**
	 * If a feature invocation expression is an implicit object destruction, it
	 * has no referent. Otherwise, its referent is the referent of its feature.
	 **/
	public boolean featureInvocationExpressionReferentDerivation() {
		this.getSelf().getReferent();
		return true;
	}

	/**
	 * If a feature invocation expression has an explicit target, then that is
	 * its feature. Otherwise, it is an alternative constructor call with its
	 * feature determined implicitly.
	 **/
	public boolean featureInvocationExpressionFeatureDerivation() {
		this.getSelf().getFeature();
		return true;
	}

	/**
	 * If a feature invocation expression is not an implicit destructor call,
	 * then it must be possible to determine a single valid referent for it
	 * according to the overloading resolution rules.
	 **/
	public boolean featureInvocationExpressionReferentExists() {
		return true;
	}

	/**
	 * An alternative constructor invocation may only occur in an expression
	 * statement as the first statement in the definition for the method of a
	 * constructor operation.
	 **/
	public boolean featureInvocationExpressionAlternativeConstructor() {
		return true;
	}

	/**
	 * If there is no target feature expression, then the implicit feature with
	 * the same name as the target type must be a constructor.
	 **/
	public boolean featureInvocationExpressionImplicitAlternativeConstructor() {
		return true;
	}

} // FeatureInvocationExpressionImpl
