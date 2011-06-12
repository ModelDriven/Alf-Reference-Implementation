
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0
 * (http://www.opensource.org/licenses/afl-3.0.php)
 *
 */

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.common.impl.ElementReferenceImpl;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * An invocation expression used to invoke an operation of a superclass.
 **/

public class SuperInvocationExpressionImpl
		extends InvocationExpressionImpl {

	private QualifiedName target = null;
	
	private NamespaceDefinition currentScope = null;
	private ElementReferenceImpl context = null;

	public SuperInvocationExpressionImpl(SuperInvocationExpression self) {
		super(self);
	}

	@Override
	public SuperInvocationExpression getSelf() {
		return (SuperInvocationExpression) this.self;
	}

	public QualifiedName getTarget() {
		return this.target;
	}

	public void setTarget(QualifiedName target) {
		this.target = target;
	}

	/**
	 * The referent of a super invocation expression is the method behavior of
	 * the operation identified using the overloading resolution rules.
	 **/
	@Override
	protected ElementReference deriveReferent() {
	    ElementReferenceImpl context = this.getContext();
	    if (context == null) {
	        return null;
	    } else {
	        Collection<ElementReference> superclasses = null;
	        QualifiedName target = this.getSelf().getTarget();
	        String name = null;
	        if (target == null) {
                superclasses = context.parents();
                if (superclasses.size() != 1) {
                    return null;
                }
                Member superclass = ((ElementReference)superclasses.toArray()[0]).getImpl().asNamespace();
                Member base = superclass.getImpl().getBase();
                if (base != null) {
                    superclass = base;
                }
                name = superclass.getName();
	        } else {
	            QualifiedName qualification = target.getQualification();
	            name = target.getUnqualifiedName().getName();
	            if (qualification == null) {
	                superclasses = context.parents();
	            } else {
	                ElementReference superclass = 
	                    qualification.getImpl().getClassifierReferent();
	                if (superclass == null) {
	                    return null;
	                } else {
    	                superclasses = new ArrayList<ElementReference>();
    	                superclasses.add(superclass);
	                }
	            }
	        }
	        
	        // TODO: Handle overloading resolution.
	        ElementReference referent = null;
	        for (ElementReference superclass: superclasses) {
	            for (ElementReference feature: superclass.getImpl().getFeatures()) {
	                if (feature.getImpl().isOperation() && 
	                        feature.getImpl().getName().equals(name)) {
	                    if (referent != null) {
	                        return null;
	                    }
	                    referent = feature;
	                }
	            }
	        }
	        // Note: This returns the operation, not the method.
	        return referent;
	    }
	}
	
	/**
	 * There is no feature for a super invocation.
	 **/
	@Override
	protected FeatureReference deriveFeature() {
	    return null;
	}
	
	/*
	 * Derivations
	 */
	
	public boolean superInvocationExpressionReferentDerivation() {
		this.getSelf().getReferent();
		return true;
	}

	public boolean superInvocationExpressionFeatureDerivation() {
		this.getSelf().getFeature();
		return true;
	}
	
	/*
	 * Constraints
	 */

	/**
	 * If the target has a qualification, then this must resolve to one of the
	 * superclasses of the current context class.
	 **/
	public boolean superInvocationExpressionQualification() {
	    QualifiedName target = this.getSelf().getTarget();
	    QualifiedName qualification = target == null? null: target.getQualification();
	    if (qualification == null) {
	        return true;
	    } else {
	        ElementReference superclass = qualification.getImpl().getClassifierReferent();
	        ElementReferenceImpl context = this.getContext();
	        return superclass != null && context != null &&
	                    superclass.getImpl().isContainedIn(context.parents());
        }
	}

	/**
	 * If the target is empty, the referent must be the method for a constructor
	 * operation and the containing classifier must have exactly one superclass.
	 **/
	public boolean superInvocationExpressionImplicitTarget() {
	    SuperInvocationExpression self = this.getSelf();
	    ElementReference referent = self.getReferent();
        ElementReferenceImpl context = this.getContext();
		return self.getTarget() != null || 
		            referent != null && referent.getImpl().isConstructor() &&
		            context != null && context.parents().size() == 1;
	}

	/**
	 * If the referent is the method of a constructor operation, the super
	 * invocation expression must occur in an expression statement at the start
	 * of the definition for the method of a constructor operation, such that
	 * any statements preceding it are also super constructor invocations.
	 **/
	public boolean superInvocationExpressionConstructorCall() {
	    // TODO: Check that a super constructor invocation occurs within an
	    // expression statement at the start of a constructor operation.
	    ElementReference referent = this.getSelf().getReferent();
        ElementReference operation = this.currentScope == null? null:
            this.currentScope.getImpl().getReferent();
        return referent == null || !referent.getImpl().isConstructor() ||
                    operation != null && operation.getImpl().isConstructor();
	}

	/**
	 * If the referent is the method of a destructor operation, the super
	 * invocation expression must occur in an within the method of a destructor
	 * operation.
	 **/
	public boolean superInvocationExpressionDestructorCall() {
        ElementReference referent = this.getSelf().getReferent();
        ElementReference operation = this.currentScope == null? null:
            this.currentScope.getImpl().getReferent();
	    return referent == null || !referent.getImpl().isDestructor() ||
                    operation != null && operation.getImpl().isDestructor();
	}

    /**
	 * It must be possible to identify a single valid operation denoted by the
	 * target of a super invocation expression that satisfies the overloading
	 * resolution rules.
	 **/
	public boolean superInvocationExpressionOperation() {
		return this.getSelf().getReferent() != null;
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
	    this.currentScope = currentScope;
	}
	
	private ElementReferenceImpl getContext() {
	    if (this.context == null && this.currentScope != null) {
	        this.context = currentScope.getImpl().getReferent().getImpl();
            if (!context.isClassifier()) {
                NamespaceDefinition outerScope = this.currentScope.getImpl().getOuterScope();
                if (outerScope == null) {
                    this.context = null;
                } else {
                    this.context = outerScope.getImpl().getReferent().getImpl();
                    if (!context.isClassifier()) {
                        this.context = null;
                    }
                }
            }
	    }
	    return this.context;
	}

} // SuperInvocationExpressionImpl
