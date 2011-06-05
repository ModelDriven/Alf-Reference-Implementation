
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
import org.modeldriven.alf.syntax.units.NamespaceDefinition;

/**
 * An invocation of a behavior referenced by name.
 **/

public class BehaviorInvocationExpressionImpl
		extends InvocationExpressionImpl {

	private QualifiedName target = null;

	public BehaviorInvocationExpressionImpl(BehaviorInvocationExpression self) {
		super(self);
	}

	@Override
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
	 * The referent of a behavior invocation expression is the behavior or 
	 * association end named by the target or, if the target disambiguates to a 
	 * feature reference, the operation or signal being invoked.
	 **/
	@Override
	protected ElementReference deriveReferent() {
        BehaviorInvocationExpression self = this.getSelf();
        QualifiedName target = self.getTarget();
        ElementReference referent = null;
	    if (target != null) {
	        if (target.getIsFeatureReference()){
	            referent = target.getDisambiguation().getImpl().
	                                        getBehavioralFeatureReferent(self);
	        } else {
	            referent = target.getImpl().getBehaviorReferent();
	            if (referent == null) {
	                referent = target.getImpl().getPropertyReferent();
	                if (referent != null && 
	                        !referent.getImpl().isAssociationEnd()) {
	                    referent = null;
	                }
	            } else if (referent.getImpl().isTemplate()) {	                
	                referent = bindTemplateImplicitArguments(target, referent, null);
	            }
	        }
	    }
	    return referent;
	}
	
	/**
	 * If the target qualified name disambiguates to a feature reference, then
	 * the feature of a behavior invocation expression is that feature
	 * reference.
	 **/
	@Override
	protected FeatureReference deriveFeature() {
	    QualifiedName target = this.getSelf().getTarget();
	    return target == null? null: target.getDisambiguation();
	}
	
	/*
	 * Derivations
	 */

	public boolean behaviorInvocationExpressionReferentDerivation() {
		this.getSelf().getReferent();
		return true;
	}

	public boolean behaviorInvocationExpressionFeatureDerivation() {
		this.getSelf().getFeature();
		return true;
	}
	
	/*
	 * Constraints
	 */

	/**
	 * If the target qualified name does not disambiguate to a feature
	 * reference, then it must resolve to a behavior or an association end.
	 * Otherwise it must resolve to a single feature referent according to the
	 * overloading resolution rules, unless it is an implicit destructor call
	 * (in which case it has no referent).
	 **/
	public boolean behaviorInvocationExpressionReferentConstraint() {
	    BehaviorInvocationExpression self = this.getSelf();
		return self.getIsImplicit() || self.getReferent() != null;
	}

	/**
	 * An input argument expression must be assignable to its corresponding
	 * parameter. An output parameter must be assignable to its corresponding
	 * argument expression. (Note that this implies that the type of an argument
	 * expression for an inout parameter must be the same as the type of that
	 * parameter.)
	 **/
	public boolean behaviorInvocationExpressionArgumentCompatibility() {
        BehaviorInvocationExpression self = this.getSelf();
        if (self.getFeature() == null) {
            Tuple tuple = self.getTuple();
            if (tuple == null) {
                return false;
            } else {
                this.getAssignmentAfterMap(); // Force computation of assignments.
                for (NamedExpression input: tuple.getInput()) {
                    if (!this.parameterIsAssignableFrom(input)) {
                       return false;
                    }
                }
                for (NamedExpression output: tuple.getOutput()) {
                    if (!this.parameterIsAssignableTo(output)) {
                        return false;
                    }
                }
            }
        }
		return true;
	}
	
	/*
	 * Helper Methods
	 */
	
	@Override
	public void setCurrentScope(NamespaceDefinition currentScope) {
	    super.setCurrentScope(currentScope);
	    this.getSelf().getTarget().getImpl().setCurrentScope(currentScope);
	}

} // BehaviorInvocationExpressionImpl
