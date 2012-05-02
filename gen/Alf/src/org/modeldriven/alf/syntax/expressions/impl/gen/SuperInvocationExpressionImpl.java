
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

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
 * An invocation expression used to invoke an operation of a superclass.
 **/

public class SuperInvocationExpressionImpl
		extends
		org.modeldriven.alf.syntax.expressions.impl.gen.InvocationExpressionImpl {

	private QualifiedName target = null;

	public SuperInvocationExpressionImpl(SuperInvocationExpression self) {
		super(self);
	}

	public SuperInvocationExpression getSelf() {
		return (SuperInvocationExpression) this.self;
	}

	public QualifiedName getTarget() {
		return this.target;
	}

	public void setTarget(QualifiedName target) {
		this.target = target;
	}

	/**
	 * The referent of a super invocation expression is the method behavior of
	 * the operation identified using the overloading resolution rules.
	 **/
	public boolean superInvocationExpressionReferentDerivation() {
		this.getSelf().getReferent();
		return true;
	}

	/**
	 * There is no feature for a super invocation.
	 **/
	public boolean superInvocationExpressionFeatureDerivation() {
		this.getSelf().getFeature();
		return true;
	}

	/**
	 * If the target has a qualification, then this must resolve to one of the
	 * superclasses of the current context class.
	 **/
	public boolean superInvocationExpressionQualification() {
		return true;
	}

	/**
	 * If the target is empty, the referent must be the method for a constructor
	 * operation.
	 **/
	public boolean superInvocationExpressionImplicitTarget() {
		return true;
	}

	/**
	 * If the referent is the method of a constructor operation, the super
	 * invocation expression must occur in an expression statement at the start
	 * of the definition for the method of a constructor operation, such that
	 * any statements preceding it are also super constructor invocations.
	 **/
	public boolean superInvocationExpressionConstructorCall() {
		return true;
	}

	/**
	 * If the referent is the method of a destructor operation, the super
	 * invocation expression must occur in an within the method of a destructor
	 * operation.
	 **/
	public boolean superInvocationExpressionDestructorCall() {
		return true;
	}

	/**
	 * It must be possible to identify a single valid operation denoted by the
	 * target of a super invocation expression that satisfies the overloading
	 * resolution rules.
	 **/
	public boolean superInvocationExpressionOperation() {
		return true;
	}

} // SuperInvocationExpressionImpl
