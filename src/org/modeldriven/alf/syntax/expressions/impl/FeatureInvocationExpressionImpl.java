
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

/**
 * An invocation of a feature referenced on a sequence of instances.
 **/

public class FeatureInvocationExpressionImpl
		extends InvocationExpressionImpl {

	private FeatureReference target = null;
	
	private NamespaceDefinition currentScope = null;

	public FeatureInvocationExpressionImpl(FeatureInvocationExpression self) {
		super(self);
	}

	@Override
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
	@Override
	protected ElementReference deriveReferent() {
	    FeatureInvocationExpression self = this.getSelf();
        FeatureReference feature = self.getFeature();
        return feature == null? null:
	                feature.getImpl().getBehavioralFeatureReferent(self);
	}
	
	/**
	 * If a feature invocation expression has an explicit target, then that is
	 * its feature. Otherwise, it is an alternative constructor call with its
	 * feature determined implicitly.
	 **/
	@Override
	protected FeatureReference deriveFeature() {
        FeatureInvocationExpression self = this.getSelf();
        FeatureReference feature = self.getTarget();
	    if (feature == null && currentScope != null) {
	        feature = new FeatureReference();
	        NameBinding nameBinding = new NameBinding();
	        nameBinding.setName(currentScope.getName());
	        feature.setNameBinding(nameBinding);
	        feature.setExpression(new ThisExpression());
	    }
	    return feature;
	}
	
	/*
	 * Derivations
	 */

	public boolean featureInvocationExpressionReferentDerivation() {
		this.getSelf().getReferent();
		return true;
	}

	public boolean featureInvocationExpressionFeatureDerivation() {
		this.getSelf().getFeature();
		return true;
	}
	
	/*
	 * Constraints
	 */

	/**
	 * If a feature invocation expression is not an implicit destructor call,
	 * then it must be possible to determine a single valid referent for it
	 * according to the overloading resolution rules.
	 **/
	public boolean featureInvocationExpressionReferentExists() {
        FeatureInvocationExpression self = this.getSelf();
		return self.getIsImplicit() || self.getReferent() != null;
	}

	/**
	 * An alternative constructor invocation may only occur in an expression
	 * statement as the first statement in the definition for the method of a
	 * constructor operation.
	 **/
	public boolean featureInvocationExpressionAlternativeConstructor() {
	    // TODO Check the constraint on alternative constructors.
		return true;
	}

	/**
	 * If there is no target feature expression, then the implicit feature with
	 * the same name as the target type must be a constructor.
	 **/
	public boolean featureInvocationExpressionImplicitAlternativeConstructor() {
        FeatureInvocationExpression self = this.getSelf();
        ElementReference referent = self.getReferent();
		return self.getTarget() != null || 
		            referent != null && referent.getImpl().isConstructor();
	}
	
	/*
	 * Helper Methods
	 */
	
	public void setCurrentScope(NamespaceDefinition currentScope) {
	    this.currentScope = currentScope;
        FeatureReference feature = this.getSelf().getFeature();
        feature.getImpl().setCurrentScope(currentScope);
	}

} // FeatureInvocationExpressionImpl
