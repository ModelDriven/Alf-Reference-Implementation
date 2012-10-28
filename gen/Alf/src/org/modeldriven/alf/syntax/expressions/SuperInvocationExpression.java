
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions;

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

import org.modeldriven.alf.syntax.expressions.impl.SuperInvocationExpressionImpl;

/**
 * An invocation expression used to invoke an operation of a superclass.
 **/

public class SuperInvocationExpression extends InvocationExpression {

	public SuperInvocationExpression() {
		this.impl = new SuperInvocationExpressionImpl(this);
	}

	public SuperInvocationExpression(AlfParser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public SuperInvocationExpression(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public SuperInvocationExpressionImpl getImpl() {
		return (SuperInvocationExpressionImpl) this.impl;
	}

	public QualifiedName getTarget() {
		return this.getImpl().getTarget();
	}

	public void setTarget(QualifiedName target) {
		this.getImpl().setTarget(target);
	}

	/**
	 * The referent of a super invocation expression is the method behavior of
	 * the operation identified using the overloading resolution rules.
	 **/
	public boolean superInvocationExpressionReferentDerivation() {
		return this.getImpl().superInvocationExpressionReferentDerivation();
	}

	/**
	 * There is no feature for a super invocation.
	 **/
	public boolean superInvocationExpressionFeatureDerivation() {
		return this.getImpl().superInvocationExpressionFeatureDerivation();
	}

	/**
	 * If the target has a qualification, then this must resolve to one of the
	 * superclasses of the current context class.
	 **/
	public boolean superInvocationExpressionQualification() {
		return this.getImpl().superInvocationExpressionQualification();
	}

	/**
	 * If the target is empty, the referent must be the method for a constructor
	 * operation and the context class for the behavior containing the super
	 * invocation expression must have exactly one superclass.
	 **/
	public boolean superInvocationExpressionImplicitTarget() {
		return this.getImpl().superInvocationExpressionImplicitTarget();
	}

	/**
	 * If the referent is the method of a constructor operation, the super
	 * invocation expression must occur in an expression statement at the start
	 * of the definition for the method of a constructor operation, such that
	 * any statements preceding it are also super constructor invocations.
	 **/
	public boolean superInvocationExpressionConstructorCall() {
		return this.getImpl().superInvocationExpressionConstructorCall();
	}

	/**
	 * If the referent is the method of a destructor operation, the super
	 * invocation expression must occur in an within the method of a destructor
	 * operation.
	 **/
	public boolean superInvocationExpressionDestructorCall() {
		return this.getImpl().superInvocationExpressionDestructorCall();
	}

	/**
	 * It must be possible to identify a single valid operation denoted by the
	 * target of a super invocation expression that satisfies the overloading
	 * resolution rules.
	 **/
	public boolean superInvocationExpressionOperation() {
		return this.getImpl().superInvocationExpressionOperation();
	}

	public void _deriveAll() {
		super._deriveAll();
		QualifiedName target = this.getTarget();
		if (target != null) {
			target.deriveAll();
		}
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.superInvocationExpressionReferentDerivation()) {
			violations.add(new ConstraintViolation(
					"superInvocationExpressionReferentDerivation", this));
		}
		if (!this.superInvocationExpressionFeatureDerivation()) {
			violations.add(new ConstraintViolation(
					"superInvocationExpressionFeatureDerivation", this));
		}
		if (!this.superInvocationExpressionQualification()) {
			violations.add(new ConstraintViolation(
					"superInvocationExpressionQualification", this));
		}
		if (!this.superInvocationExpressionImplicitTarget()) {
			violations.add(new ConstraintViolation(
					"superInvocationExpressionImplicitTarget", this));
		}
		if (!this.superInvocationExpressionConstructorCall()) {
			violations.add(new ConstraintViolation(
					"superInvocationExpressionConstructorCall", this));
		}
		if (!this.superInvocationExpressionDestructorCall()) {
			violations.add(new ConstraintViolation(
					"superInvocationExpressionDestructorCall", this));
		}
		if (!this.superInvocationExpressionOperation()) {
			violations.add(new ConstraintViolation(
					"superInvocationExpressionOperation", this));
		}
		QualifiedName target = this.getTarget();
		if (target != null) {
			target.checkConstraints(violations);
		}
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
		QualifiedName target = this.getTarget();
		if (target != null) {
			System.out.println(prefix + " target:");
			target.print(prefix + "  ", includeDerived);
		}
	}
} // SuperInvocationExpression
