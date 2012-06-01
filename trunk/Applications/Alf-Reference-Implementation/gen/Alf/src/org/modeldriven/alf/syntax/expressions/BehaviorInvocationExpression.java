
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

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
