
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.common.impl.SyntaxElementImpl;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * A reference to a structural or behavioral feature of the type of its target
 * expression or a binary association end the opposite end of which is typed by
 * the type of its target expression.
 **/

public class FeatureReferenceImpl extends SyntaxElementImpl {

	private Expression expression = null;
	private Collection<ElementReference> referent = null; // DERIVED
	private NameBinding nameBinding = null;
	
	private NamespaceDefinition currentScope = null;

	public FeatureReferenceImpl(FeatureReference self) {
		super(self);
	}

	@Override
	public FeatureReference getSelf() {
		return (FeatureReference) this.self;
	}
	
	@Override
	public String toString(boolean includeDerived) {
	    FeatureReference self = this.getSelf();
	    return self._toString(includeDerived) + " nameBinding:" + self.getNameBinding() +
	            " expression:(" + self.getExpression() + ")";
	}

	public Expression getExpression() {
		return this.expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	public Collection<ElementReference> getReferent() {
		if (this.referent == null) {
			this.setReferent(this.deriveReferent());
		}
		return this.referent;
	}

	public void setReferent(Collection<ElementReference> referent) {
		this.referent = referent;
	}

	public void addReferent(ElementReference referent) {
		this.referent.add(referent);
	}

	public NameBinding getNameBinding() {
		return this.nameBinding;
	}

	public void setNameBinding(NameBinding nameBinding) {
		this.nameBinding = nameBinding;
	}

	/**
	 * The features referenced by a feature reference include the features of
	 * the type of the target expression and the association ends of any binary
	 * associations whose opposite ends are typed by the type of the target
	 * expression.
	 **/
	protected Collection<ElementReference> deriveReferent() {
	    // TODO Handle feature references with template bindings.
	    FeatureReference self = this.getSelf();
	    Expression target = self.getExpression();
	    NameBinding nameBinding = self.getNameBinding();
	    ElementReference targetType = target == null? null: target.getType();
	    Collection<ElementReference> referents = new ArrayList<ElementReference>();
	    if (targetType != null && nameBinding != null) {
	        String name = nameBinding.getName();
	        if (name != null) {
	            for (Member member: targetType.getImpl().asNamespace().
	                    getImpl().resolveVisible(name, this.currentScope, false)) {
	                referents.add(member.getImpl().getReferent());
	            }
    	        if (this.currentScope != null) {
    	            referents.addAll(this.currentScope.getImpl().
    	                    resolveAssociationEnd(targetType, name));
    	        }
	        }
	    }
	    return referents;
	}
	
	/*
	 * Derivations
	 */

	public boolean featureReferenceReferentDerivation() {
		this.getSelf().getReferent();
		return true;
	}
	
	/*
	 * Constraints
	 */

	/**
	 * The target expression of the feature reference may not be untyped, nor
	 * may it have a primitive or enumeration type.
	 **/
	public boolean featureReferenceTargetType() {
	    FeatureReference self = this.getSelf();
	    Expression target = self.getExpression();
	    ElementReference targetType = target == null? null: target.getType();
		return targetType != null && 
		            !targetType.getImpl().isPrimitive() && 
		            !targetType.getImpl().isEnumeration();
	}
	
	/*
	 * Helper Methods
	 */

    public ElementReference getStructuralFeatureReferent() {
        FeatureReference self = this.getSelf();
        ElementReference property = null;
        for (ElementReference referent: self.getReferent()) {
            if (referent.getImpl().isProperty()) {
                if (property != null) {
                    return null;
                }
                property = referent;
            }
        }
        return property;
    }

    public ElementReference getBehavioralFeatureReferent(InvocationExpression invocation) {
        // TODO Handle overloading resolution.
        FeatureReference self = this.getSelf();
        ElementReference feature = null;
        for (ElementReference referent: self.getReferent()) {
            if (referent.getImpl().isOperation() ||
                    referent.getImpl().isReception()) {
                if (feature != null) {
                    return null;
                }
                feature = referent;
            }
        }
        // NOTE: If the feature is a reception, then the referent should be the
        // signal being received, not the reception itself.
        if (feature.getImpl().isReception()) {
            feature = feature.getImpl().getSignal();
        }
        return feature;
    }

    public void setCurrentScope(NamespaceDefinition currentScope) {
        this.currentScope = currentScope;
        
        FeatureReference self = this.getSelf();
        Expression expression = self.getExpression();
        NameBinding nameBinding = self.getNameBinding();
        
        if (expression != null) {
            expression.getImpl().setCurrentScope(currentScope);
        }
        
        if (nameBinding != null) {
            nameBinding.getImpl().setCurrentScope(currentScope);
        }
    }
    
    public void setAssignmentBefore(Map<String, AssignedSource> assignmentsBefore) {
        Expression expression = this.getSelf().getExpression();
        if (expression != null) {
            expression.getImpl().setAssignmentBefore(assignmentsBefore);
        }
    }
    
    public Map<String, AssignedSource> getAssignmentAfterMap() {
        Expression expression = this.getSelf().getExpression();
        return expression == null? null: 
            expression.getImpl().getAssignmentAfterMap();
    }

    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof FeatureReference) {
            FeatureReference self = this.getSelf();
            FeatureReference baseFeature = (FeatureReference)base;
            Expression expression = baseFeature.getExpression();
            NameBinding nameBinding = baseFeature.getNameBinding();
            if (expression != null) {
                self.setExpression((Expression)expression.getImpl().
                        bind(templateParameters, templateArguments));
            }
            if (nameBinding != null) {
                self.setNameBinding(nameBinding.getImpl().
                        updateBinding(templateParameters, templateArguments));
            }
        }
    }

} // FeatureReferenceImpl
