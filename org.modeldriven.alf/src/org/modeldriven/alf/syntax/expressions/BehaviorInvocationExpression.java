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
import org.modeldriven.alf.syntax.common.AssignedSource;
import org.modeldriven.alf.syntax.common.ConstraintViolation;
import org.modeldriven.alf.syntax.common.ExternalElementReference;
import org.modeldriven.alf.syntax.expressions.impl.BehaviorInvocationExpressionImpl;

/**
 * An invocation of a behavior referenced by name.
 **/

public class BehaviorInvocationExpression extends InvocationExpression {

	public BehaviorInvocationExpression() {
		this.impl = new BehaviorInvocationExpressionImpl(this);
	}

	public BehaviorInvocationExpression(Parser parser) {
		this();
		this.init(parser);
	}

	public BehaviorInvocationExpression(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
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
     * reference, then it must resolve to a behavior or an association end, and,
     * if it is a template behavior, then the implicit binding of this template
     * must be legal. Otherwise it must resolve to a single feature referent
     * according to the overloading resolution rules, unless it is an implicit
     * destructor call (in which case it has no referent).
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

    /**
     * If the invoked behavior is CollectionFunctions::isEmpty or
     * SequenceFunctions::IsEmpty, then check the argument expression for known
     * nulls and non-nulls using the given truth condition. If the invoked
     * behavior is CollectionFunctions::notEmpty or SequenceFunctions::NotEmpty,
     * then check the argument expression for known nulls and non-nulls using
     * the negation of the given truth condition.
     */
    @Override
    public Collection<AssignedSource> adjustAssignments(
            Collection<AssignedSource> assignments, boolean condition) {
        return this.getImpl().adjustAssignments(assignments, condition);
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
} // BehaviorInvocationExpression
