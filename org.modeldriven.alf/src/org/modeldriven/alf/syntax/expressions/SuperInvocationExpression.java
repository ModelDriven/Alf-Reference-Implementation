
/*******************************************************************************
 * Copyright 2011, 2018 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions;

import java.util.Collection;

import org.modeldriven.alf.parser.ParsedElement;
import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.syntax.common.ConstraintViolation;
import org.modeldriven.alf.syntax.common.ExternalElementReference;
import org.modeldriven.alf.syntax.expressions.impl.SuperInvocationExpressionImpl;

/**
 * An invocation expression used to invoke an operation of a superclass.
 **/

public class SuperInvocationExpression extends InvocationExpression {

	public SuperInvocationExpression() {
		this.impl = new SuperInvocationExpressionImpl(this);
	}

	public SuperInvocationExpression(Parser parser) {
		this();
		this.init(parser);
	}

	public SuperInvocationExpression(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
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

    @Override
    public void _addExternalReferences(Collection<ExternalElementReference> references) {
        super._addExternalReferences(references);
        addExternalReferencesFor(references, this.getTarget());
    }

	@Override
    public void _deriveAll() {
		super._deriveAll();
		QualifiedName target = this.getTarget();
		if (target != null) {
			target.deriveAll();
		}
	}

	@Override
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

	@Override
    public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		return s.toString();
	}

	@Override
    public void print() {
		this.print("", false);
	}

	@Override
    public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	@Override
    public void print(String prefix, boolean includeDerived) {
		super.print(prefix, includeDerived);
		QualifiedName target = this.getTarget();
		if (target != null) {
			System.out.println(prefix + " target:");
			target.print(prefix + "  ", includeDerived);
		}
	}
} // SuperInvocationExpression
