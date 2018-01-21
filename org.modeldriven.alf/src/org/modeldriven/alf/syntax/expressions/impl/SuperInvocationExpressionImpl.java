/*******************************************************************************
 * Copyright 2011-2017 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.Block;
import org.modeldriven.alf.syntax.statements.ExpressionStatement;
import org.modeldriven.alf.syntax.statements.Statement;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * An invocation expression used to invoke an operation of a superclass.
 **/

public class SuperInvocationExpressionImpl
		extends InvocationExpressionImpl {

	private QualifiedName target = null;
	
	private ElementReference context = null;
	private Block enclosingBlock = null;

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
		if (target != null) {
		    target.getImpl().setIsVisibleOnly(false);
		}
	}
	
	/**
	 * The referent of a super invocation expression is the method behavior of
	 * the operation identified using the overloading resolution rules.
	 **/
	@Override
	protected ElementReference deriveReferent() {
	    ElementReference context = this.getContext();
	    if (context == null) {
	        return null;
	    } else {
	        Collection<ElementReference> superclasses = null;
	        QualifiedName target = this.getSelf().getTarget();
	        String name = null;
	        if (target == null) {
                superclasses = context.getImpl().parents();
                if (superclasses.size() != 1) {
                    return null;
                }
                Member superclass = 
                    ((ElementReference)superclasses.toArray()[0]).getImpl().
                        asNamespace();
                Member base = (Member)superclass.getImpl().getBase();
                if (base != null) {
                    superclass = base;
                }
                name = superclass.getName();
	        } else {
	            QualifiedName qualification = target.getQualification();
	            name = target.getUnqualifiedName().getName();
	            if (qualification == null) {
	                superclasses = context.getImpl().parents();
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
	        
	        Collection<ElementReference> operations = new ArrayList<ElementReference>();
	        for (ElementReference superclass: superclasses) {
	            for (ElementReference member: superclass.getImpl().getMembers()) {
	                if (member.getImpl().isOperation() && 
	                        member.getImpl().getName().equals(name) &&
	                        !"private".equals(member.getImpl().getVisibility())) {
	                    operations.add(member);
	                }
	            }
	        }
	        
	        // Note: This returns the operation, not the method. This is because
	        // if the feature is an Alf operation definition, there will not be
	        // a method for it yet.
	        return this.resolveOverloading(operations);
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
	        ElementReference context = this.getContext();
	        return superclass != null && context != null &&
	                    superclass.getImpl().isContainedIn(context.getImpl().parents());
        }
	}

	/**
	 * If the target is empty, the referent must be the method for a constructor
	 * operation and the containing classifier must have exactly one superclass.
	 **/
	public boolean superInvocationExpressionImplicitTarget() {
	    SuperInvocationExpression self = this.getSelf();
	    ElementReference referent = self.getBoundReferent();
        ElementReference context = this.getContext();
		return self.getTarget() != null || 
		            referent != null && referent.getImpl().isConstructor() &&
		            context != null && context.getImpl().parents().size() == 1;
	}

	/**
	 * If the referent is the method of a constructor operation, the super
	 * invocation expression must occur in an expression statement at the start
	 * of the definition for the method of a constructor operation, such that
	 * any statements preceding it are also super constructor invocations.
	 **/
	public boolean superInvocationExpressionConstructorCall() {
	    SuperInvocationExpression self = this.getSelf();
        ElementReference referent = self.getBoundReferent();
        NamespaceDefinition currentScope = this.getCurrentScope();
        if (referent == null || !referent.getImpl().isConstructor() || 
                currentScope == null) {
            return true;
        } else {
            // Note: This will work, even if the operation definition is not an
            // Alf unit.
            ElementReference operation = 
                    currentScope.getImpl().getReferent().getImpl().getSpecification();
            if (!operation.getImpl().isConstructor() || this.enclosingBlock == null || 
                    this.enclosingBlock.getImpl().hasEnclosingStatement()) {
                return false;
            } else {
                ElementReference classReference = referent.getImpl().getNamespace();
                List<Statement> statements = this.enclosingBlock.getStatement();
                for (int i = 0; i < statements.size(); i++) {
                    Statement statement = statements.get(i);
                    if (!(statement instanceof ExpressionStatement)) {
                        return false;
                    } else {
                        ExpressionStatement expressionStatement = 
                                (ExpressionStatement)statement;
                        Expression expression = expressionStatement.getExpression();
                        if (expression == self) {
                            return true;
                        } else if (!(expression instanceof SuperInvocationExpression) || 
                                // Note: This checks to ensure that no previous
                                // super constructor invocation is for the same
                                // superclass.
                                classReference.getImpl().equals(
                                        ((SuperInvocationExpression)expression).
                                            getBoundReferent().getImpl().getNamespace())) {
                            return false;
                        }
                    }
                }
                return false;
            }
        }
	}

	/**
	 * If the referent is the method of a destructor operation, the super
	 * invocation expression must occur in an within the method of a destructor
	 * operation.
	 **/
	public boolean superInvocationExpressionDestructorCall() {
        ElementReference referent = this.getSelf().getBoundReferent();
        NamespaceDefinition currentScope = this.getCurrentScope();
        ElementReference operation = currentScope == null? null:
            currentScope.getImpl().getReferent();
	    return referent == null || !referent.getImpl().isDestructor() ||
                    operation != null && operation.getImpl().isDestructor();
	}

    /**
	 * It must be possible to identify a single valid operation denoted by the
	 * target of a super invocation expression that satisfies the overloading
	 * resolution rules.
	 **/
	public boolean superInvocationExpressionOperation() {
		return this.getSelf().getBoundReferent() != null;
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
	    // this.currentScope = currentScope;
	}
	
	private ElementReference getContext() {
        NamespaceDefinition currentScope = this.getCurrentScope();
	    if (this.context == null && currentScope != null) {
	        this.context = currentScope.getImpl().getContext();
	    }
	    return this.context;
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
        if (base instanceof SuperInvocationExpression) {
            SuperInvocationExpression baseExpression = 
                (SuperInvocationExpression)base;
            QualifiedName target = baseExpression.getTarget();
            if (target != null) {
                
                // Note: This is to ensure that all referents for all bindings
                // are fully computed.
                target.deriveAll();
                
                this.getSelf().setTarget(target.getImpl().
                        updateBindings(templateParameters, templateArguments));
            }
        }
    }
    
} // SuperInvocationExpressionImpl
