
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0
 * (http://www.opensource.org/licenses/afl-3.0.php)
 *
 */

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.common.impl.SyntaxElementImpl;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A reference to a structural or behavioral feature of the type of its target
 * expression or a binary association end the opposite end of which is typed by
 * the type of its target expression.
 **/

public class FeatureReferenceImpl extends SyntaxElementImpl {

	private Expression expression = null;
	private Collection<ElementReference> referent = null; // DERIVED
	private NameBinding nameBinding = null;

	public FeatureReferenceImpl(FeatureReference self) {
		super(self);
	}

	@Override
	public FeatureReference getSelf() {
		return (FeatureReference) this.self;
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
	    // TODO Handle opposite association ends as feature references.
	    // TODO Handle feature references with template bindings.
	    FeatureReference self = this.getSelf();
	    Expression target = self.getExpression();
	    NameBinding nameBinding = self.getNameBinding();
	    ElementReference targetType = target == null? null: target.getType();
	    Collection<ElementReference> referents = new ArrayList<ElementReference>();
	    if (targetType != null && nameBinding != null) {
	        String name = nameBinding.getName();
	        for (ElementReference feature: targetType.getImpl().getFeatures()) {
	            String featureName = feature.getImpl().getName();
	            if (name != null && name.equals(featureName)) {
	                referents.add(feature);
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
        return feature;
    }

    public void setCurrentScope(NamespaceDefinition currentScope) {
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

} // FeatureReferenceImpl
