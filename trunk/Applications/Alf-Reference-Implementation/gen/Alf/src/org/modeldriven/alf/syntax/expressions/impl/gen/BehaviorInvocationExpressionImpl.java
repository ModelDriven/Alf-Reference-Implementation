
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
import java.util.TreeSet;

/**
 * An invocation of a behavior referenced by name.
 **/

public class BehaviorInvocationExpressionImpl
		extends
		org.modeldriven.alf.syntax.expressions.impl.gen.InvocationExpressionImpl {

	private QualifiedName target = null;

	public BehaviorInvocationExpressionImpl(BehaviorInvocationExpression self) {
		super(self);
	}

	public BehaviorInvocationExpression getSelf() {
		return (BehaviorInvocationExpression) this.self;
	}

	public QualifiedName getTarget() {
		return this.target;
	}

	public void setTarget(QualifiedName target) {
		this.target = target;
	}

	/**
	 * If the target of a behavior invocation expression resolves to a behavior,
	 * then the referent of the expression is that behavior. If the target
	 * disambiguates to a feature reference, then the reference is the operation
	 * or signal being invoked. Otherwise, if the target resolves to a property
	 * that is an association end, then the referent is that property.
	 **/
	public boolean behaviorInvocationExpressionReferentDerivation() {
		this.getSelf().getReferent();
		return true;
	}

	/**
	 * If the target qualified name disambiguates to a feature reference, then
	 * the feature of a behavior invocation expression is that feature
	 * reference.
	 **/
	public boolean behaviorInvocationExpressionFeatureDerivation() {
		this.getSelf().getFeature();
		return true;
	}

	/**
	 * If the target qualified name does not disambiguate to a feature
	 * reference, then it must resolve to a behavior or an association end.
	 * Otherwise it must resolve to a single feature referent according to the
	 * overloading resolution rules, unless it is an implicit destructor call
	 * (in which case it has no referent).
	 **/
	public boolean behaviorInvocationExpressionReferentConstraint() {
		return true;
	}

	/**
	 * If the target qualified name does not disambiguate to a feature
	 * reference, then each input argument expression must be assignable to its
	 * corresponding parameter and each output argument expression must be
	 * assignable from its corresponding parameter. (Note that this implies that
	 * the type of an argument expression for an inout parameter must be the
	 * same as the type of that parameter.)
	 **/
	public boolean behaviorInvocationExpressionArgumentCompatibility() {
		return true;
	}

	/**
	 * The referent may only be a constructor (as a result of the target
	 * disambiguating to a feature reference) if this behavior invocation
	 * expression is the expression of an expression statement that is the first
	 * statement in the definition for the method of a constructor operation.
	 **/
	public boolean behaviorInvocationExpressionAlternativeConstructor() {
		return true;
	}

} // BehaviorInvocationExpressionImpl
