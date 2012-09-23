
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl.gen;

import org.modeldriven.alf.parser.AlfParser;
import org.modeldriven.alf.parser.Token;

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

/**
 * An invocation of a feature referenced on a sequence of instances.
 **/

public class FeatureInvocationExpressionImpl
		extends
		org.modeldriven.alf.syntax.expressions.impl.gen.InvocationExpressionImpl {

	private FeatureReference target = null;

	public FeatureInvocationExpressionImpl(FeatureInvocationExpression self) {
		super(self);
	}

	public FeatureInvocationExpression getSelf() {
		return (FeatureInvocationExpression) this.self;
	}

	public FeatureReference getTarget() {
		return this.target;
	}

	public void setTarget(FeatureReference target) {
		this.target = target;
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
