
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
import org.modeldriven.alf.syntax.expressions.impl.FeatureInvocationExpressionImpl;

/**
 * An invocation of a feature referenced on a sequence of instances.
 **/

public class FeatureInvocationExpression extends InvocationExpression {

	public FeatureInvocationExpression() {
		this.impl = new FeatureInvocationExpressionImpl(this);
	}

	public FeatureInvocationExpression(Parser parser) {
		this();
		this.init(parser);
	}

	public FeatureInvocationExpression(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
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
	 * has no referent. Otherwise, its referent is the operation or signal being
	 * invoked.
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

    @Override
    public void _addExternalReferences(Collection<ExternalElementReference> references) {
        super._addExternalReferences(references);
        addExternalReferencesFor(references, this.getTarget());
    }

	@Override
    public void _deriveAll() {
		super._deriveAll();
		FeatureReference target = this.getTarget();
		if (target != null) {
			target.deriveAll();
		}
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.featureInvocationExpressionReferentDerivation()) {
			violations.add(new ConstraintViolation(
					"featureInvocationExpressionReferentDerivation", this));
		}
		if (!this.featureInvocationExpressionFeatureDerivation()) {
			violations.add(new ConstraintViolation(
					"featureInvocationExpressionFeatureDerivation", this));
		}
		if (!this.featureInvocationExpressionReferentExists()) {
			violations.add(new ConstraintViolation(
					"featureInvocationExpressionReferentExists", this));
		}
		if (!this.featureInvocationExpressionAlternativeConstructor()) {
			violations.add(new ConstraintViolation(
					"featureInvocationExpressionAlternativeConstructor", this));
		}
		if (!this.featureInvocationExpressionImplicitAlternativeConstructor()) {
			violations
					.add(new ConstraintViolation(
							"featureInvocationExpressionImplicitAlternativeConstructor",
							this));
		}
		FeatureReference target = this.getTarget();
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
		FeatureReference target = this.getTarget();
		if (target != null) {
			System.out.println(prefix + " target:");
			target.print(prefix + "  ", includeDerived);
		}
	}
} // FeatureInvocationExpression
