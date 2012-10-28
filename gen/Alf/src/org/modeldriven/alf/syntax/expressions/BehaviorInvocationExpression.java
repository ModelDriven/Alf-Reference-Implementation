
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

import org.modeldriven.alf.syntax.expressions.impl.BehaviorInvocationExpressionImpl;

/**
 * An invocation of a behavior referenced by name.
 **/

public class BehaviorInvocationExpression extends InvocationExpression {

	public BehaviorInvocationExpression() {
		this.impl = new BehaviorInvocationExpressionImpl(this);
	}

	public BehaviorInvocationExpression(AlfParser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public BehaviorInvocationExpression(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
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
	 * If the target of a behavior invocation expression resolves to a behavior,
	 * then the referent of the expression is that behavior. If the target
	 * disambiguates to a feature reference, then the reference is the operation
	 * or signal being invoked. Otherwise, if the target resolves to a property
	 * that is an association end, then the referent is that property.
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
	 * If the target qualified name does not disambiguate to a feature
	 * reference, then each input argument expression must be assignable to its
	 * corresponding parameter and each output argument expression must be
	 * assignable from its corresponding parameter. (Note that this implies that
	 * the type of an argument expression for an inout parameter must be the
	 * same as the type of that parameter.)
	 **/
	public boolean behaviorInvocationExpressionArgumentCompatibility() {
		return this.getImpl()
				.behaviorInvocationExpressionArgumentCompatibility();
	}

	/**
	 * The referent may only be a constructor (as a result of the target
	 * disambiguating to a feature reference) if this behavior invocation
	 * expression is the expression of an expression statement that is the first
	 * statement in the definition for the method of a constructor operation.
	 **/
	public boolean behaviorInvocationExpressionAlternativeConstructor() {
		return this.getImpl()
				.behaviorInvocationExpressionAlternativeConstructor();
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
		if (!this.behaviorInvocationExpressionAlternativeConstructor()) {
			violations
					.add(new ConstraintViolation(
							"behaviorInvocationExpressionAlternativeConstructor",
							this));
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
} // BehaviorInvocationExpression
