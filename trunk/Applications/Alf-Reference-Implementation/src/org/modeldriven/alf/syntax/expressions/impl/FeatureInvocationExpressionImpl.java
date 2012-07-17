
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl;

import java.util.List;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.Block;
import org.modeldriven.alf.syntax.statements.ExpressionStatement;
import org.modeldriven.alf.syntax.statements.Statement;
import org.modeldriven.alf.syntax.units.*;

/**
 * An invocation of a feature referenced on a sequence of instances.
 **/

public class FeatureInvocationExpressionImpl
		extends InvocationExpressionImpl {

	private FeatureReference target = null;
	
	private NamespaceDefinition currentScope = null;
	private Block enclosingBlock = null;

	public FeatureInvocationExpressionImpl(FeatureInvocationExpression self) {
		super(self);
	}

	@Override
	public FeatureInvocationExpression getSelf() {
		return (FeatureInvocationExpression) this.self;
	}
	
	@Override
	public void deriveAll() {
	    FeatureReference target = this.getSelf().getTarget();
	    if (target != null) {
	        target.getImpl().setAssignmentBefore(this.getAssignmentBeforeMap());
	    }
	    super.deriveAll();
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
        ElementReference referent = feature == null? null:
            feature.getImpl().getBehavioralFeatureReferent(self);
        // NOTE: If the feature referent is to a reception, then the invocation
        // referent should be the signal being received, not the reception.
        if (referent.getImpl().isReception()) {
            referent = referent.getImpl().getSignal();
        }
        return referent;
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
	    if (feature == null && this.currentScope != null) {
	        NamespaceDefinition outerScope = this.currentScope.getImpl().getOuterScope();
	        if (outerScope instanceof ClassDefinition) {
    	        feature = new FeatureReference();
    	        NameBinding nameBinding = new NameBinding();
    	        nameBinding.setName(outerScope.getName());
    	        feature.setNameBinding(nameBinding);
    	        ThisExpression thisExpression = new ThisExpression();
    	        thisExpression.getImpl().setCurrentScope(this.currentScope);
    	        feature.setExpression(thisExpression);
	        }
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
		// return self.getIsImplicit() || self.getReferent() != null;
        if (self.getReferent() == null) {
            return self.getIsImplicit();
        } else {
            // TODO: Remove this check once overloading resolution is implemented.
            Tuple tuple = self.getTuple();
            if (tuple == null || 
                    tuple.getImpl().size() > this.parameters().size()) {
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
            return true;
        }
	}

	/**
	 * An alternative constructor invocation may only occur in an expression
	 * statement as the first statement in the definition for the method of a
	 * constructor operation.
	 **/
	public boolean featureInvocationExpressionAlternativeConstructor() {
	    FeatureInvocationExpression self = this.getSelf();
        ElementReference referent = self.getReferent();
        NamespaceDefinition currentScope = this.getCurrentScope();
        if (referent == null || !referent.getImpl().isConstructor() || 
                currentScope == null) {
            return true;
        } else {
            // Note: This will work, even it the operation definition is not an
            // Alf unit.
            ElementReference operation = currentScope.getImpl().getReferent();
            if (!operation.getImpl().isConstructor() || this.enclosingBlock == null) {
                return false;
            } else {
                List<Statement> statements = this.enclosingBlock.getStatement();
                if (statements.size() == 0) {
                    return false;
                } else {
                    Statement statement = statements.get(0);
                    return statement instanceof ExpressionStatement &&
                            ((ExpressionStatement)statement).getExpression() == self &&
                            statement.getImpl().getEnclosingStatement() == null &&
                            // NOTE: This ensures that the invoked constructor
                            // the is from the same class as the containing
                            // constructor.
                            operation.getImpl().getNamespace().getImpl().
                                equals(referent.getImpl().getNamespace()) &&
                            // NOTE: An alternative constructor invocation should
                            // only be allowed on "this".
                            self.getFeature().getExpression() instanceof ThisExpression;
                }
            }
        }
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
	
	@Override
	public void setCurrentScope(NamespaceDefinition currentScope) {
	    super.setCurrentScope(currentScope);
	    this.currentScope = currentScope;
        FeatureReference feature = this.getSelf().getFeature();
        if (feature != null) {
            feature.getImpl().setCurrentScope(currentScope);
        }
	}
	
	@Override
	public void setEnclosingBlock(Block enclosingBlock) {
	    this.enclosingBlock = enclosingBlock;
	}
	
	@Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof FeatureInvocationExpression) {
            FeatureReference target = 
                ((FeatureInvocationExpression)base).getTarget();
            if (target != null) {
                this.getSelf().setTarget((FeatureReference)target.getImpl().
                        bind(templateParameters, templateArguments));
            }
        }
    }

} // FeatureInvocationExpressionImpl
