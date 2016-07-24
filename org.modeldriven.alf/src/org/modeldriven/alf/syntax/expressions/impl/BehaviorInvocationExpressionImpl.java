
/*******************************************************************************
 * Copyright 2011-2016 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

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
	            }
	        }
	    }
	    return referent;
	}
	
	@Override
    protected ElementReference deriveBoundReferent() {
	    BehaviorInvocationExpression self = this.getSelf();
	    ElementReference referent = self.getReferent();
	    return referent == null || !referent.getImpl().isTemplate()? referent:
	           this.bindTemplateImplicitArguments(self.getTarget(), referent, null);
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
     * reference, then it must resolve to a behavior or an association end, and,
     * if it is a template behavior, then the implicit binding of this template
     * must be legal. Otherwise it must resolve to a single feature referent
     * according to the overloading resolution rules, unless it is an implicit
     * destructor call (in which case it has no referent).
     **/
	public boolean behaviorInvocationExpressionReferentConstraint() {
	    BehaviorInvocationExpression self = this.getSelf();
	    ElementReference referent = self.getBoundReferent();
		return self.getIsImplicit() || referent != null && 
		        // NOTE: This prevents the behavior invocation expression from
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
        return self.getFeature() != null || this.isCompatibleWith(null);
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
	
    /**
     * If the invoked behavior is CollectionFunctions::isEmpty or
     * SequenceFunctions::IsEmpty, then check the argument expression for known
     * nulls and non-nulls using the given truth condition. If the invoked
     * behavior is CollectionFunctions::notEmpty or SequenceFunctions::NotEmpty,
     * then check the argument expression for known nulls and non-nulls using
     * the negation of the given truth condition.
     */
    @Override
    public Map<String, AssignedSource> adjustAssignments(
            Map<String, AssignedSource> assignmentsMap, boolean condition) {
        BehaviorInvocationExpression self = this.getSelf();
        Tuple tuple = self.getTuple();
        ElementReference referent = self.getReferent();
        if (referent != null && tuple != null) {
            Collection<NamedExpression> inputs = tuple.getInput();
            if (inputs.size() > 0) {
                Expression expression = ((NamedExpression)inputs.toArray()[0]).getExpression();
                RootNamespace rootScope = RootNamespace.getRootScope();
                if (referent.getImpl().equals(rootScope.getSequenceFunctionIsEmpty()) ||
                        referent.getImpl().equals(rootScope.getCollectionFunctionIsEmpty())) {
                        assignmentsMap = expression.getImpl().adjustMultiplicity(
                                assignmentsMap, condition);
                    } else if (referent.getImpl().equals(rootScope.getSequenceFunctionNotEmpty()) ||
                            referent.getImpl().equals(rootScope.getCollectionFunctionNotEmpty())) {
                            assignmentsMap = expression.getImpl().adjustMultiplicity(
                                    assignmentsMap, !condition);
                    }       
            }
        }
        return assignmentsMap;
    }
    
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
	            RootNamespace.getRootScope().getCollectionFunctionAdd();
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
