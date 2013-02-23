
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl;

import java.util.List;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.RootNamespace;

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
		if (this.target != null) {
		    this.target.getImpl().setContainingExpression(this.getSelf());
		}
	}
	
	/**
     * If the target of a behavior invocation expression resolves to a behavior,
     * then the referent of the expression is that behavior. If the target
     * disambiguates to a feature reference, then the reference is the operation
     * or signal being invoked. Otherwise, if the target resolves to a property
     * that is an association end, then the referent is that property.
	 **/
	@Override
	protected ElementReference deriveReferent() {
        BehaviorInvocationExpression self = this.getSelf();
        QualifiedName target = self.getTarget();
        ElementReference referent = null;
	    if (target != null) {
	        if (target.getIsFeatureReference()){
	            this.getFeature().getImpl().setAssignmentBefore(
	                    this.getAssignmentBeforeMap());
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
	    ElementReference referent = self.getReferent();
		return self.getIsImplicit() || referent != null && 
		        // NOTE: This prevents behavior invocation expression from
		        // disambiguating to an illegal constructor invocation.
		        !referent.getImpl().isConstructor();
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
        BehaviorInvocationExpression self = this.getSelf();
        // TODO: Once overloading resolution is implemented, change this to only
        // be for non-feature invocations.
        // if (self.getFeature() == null) {
            Tuple tuple = self.getTuple();
            if (tuple == null || 
                    tuple.getImpl().size() > this.parameterCount()) {
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
        // }
		return true;
	}
	
    /**
     * The referent may only be a constructor (as a result of the target
     * disambiguating to a feature reference) if this behavior invocation
     * expression is the expression of an expression statement that is the first
     * statement in the definition for the method of a constructor operation.
     **/
    public boolean behaviorInvocationExpressionAlternativeConstructor() {
        return this.checkAlternativeConstructorValidity();
    }

	/*
	 * Helper Methods
	 */
	
	@Override
	public void setCurrentScope(NamespaceDefinition currentScope) {
	    super.setCurrentScope(currentScope);
	    QualifiedName target = this.getSelf().getTarget();
	    if (target != null) {
	        target.getImpl().setCurrentScope(currentScope);
	    }
	}
	
	@Override
	public boolean isAddInvocation() {
	    // Note: The behavior referent of the target is used here to avoid
	    // having to deal with the implicit template binding of the invocation
	    // referent.
	    ElementReference collectionFunctionAdd = 
	            RootNamespace.getCollectionFunctionAdd();
	    return collectionFunctionAdd != null && collectionFunctionAdd.getImpl().
	        equals(this.getSelf().getTarget().getImpl().getBehaviorReferent());
	}

    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof BehaviorInvocationExpression) {
            QualifiedName target = 
                ((BehaviorInvocationExpression)base).getTarget();
            if (target != null) {
                this.getSelf().setTarget(target.getImpl().
                        updateBindings(templateParameters, templateArguments));
            }
        }
    }

} // BehaviorInvocationExpressionImpl
