
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

import java.util.Map;

/**
 * An expression comprising a reference to a structural feature.
 **/

public class PropertyAccessExpressionImpl extends ExpressionImpl {

	private FeatureReference featureReference = null;
	private ElementReference feature = null; // DERIVED

	public PropertyAccessExpressionImpl(PropertyAccessExpression self) {
		super(self);
	}

	@Override
	public PropertyAccessExpression getSelf() {
		return (PropertyAccessExpression) this.self;
	}
	
	@Override
	public String toString() {
	    PropertyAccessExpression self = this.getSelf();
	    return super.toString() + " feature: (" + self.getFeature() + ")";
	}

	public FeatureReference getFeatureReference() {
		return this.featureReference;
	}

	public void setFeatureReference(FeatureReference featureReference) {
		this.featureReference = featureReference;
	}

	public ElementReference getFeature() {
		if (this.feature == null) {
			this.setFeature(this.deriveFeature());
		}
		return this.feature;
	}

	public void setFeature(ElementReference feature) {
		this.feature = feature;
	}

	/**
	 * The feature of a property access expression is the structural feature to
	 * which its feature reference resolves.
	 **/
	protected ElementReference deriveFeature() {
	    FeatureReference featureReference = this.getSelf().getFeatureReference();
		return featureReference == null? null: 
		            featureReference.getImpl().getStructuralFeatureReferent();
	}

	/**
	 * The type of a property access expression is the type of the referenced
	 * feature.
	 **/
	@Override
	protected ElementReference deriveType() {
	    ElementReference feature = this.getSelf().getFeature();
	    return feature == null? null: feature.getImpl().getType();
	}
	
	/**
	 * The multiplicity upper bound of a property access expression is given by
	 * the product of the multiplicity upper bounds of the referenced feature
	 * and the target expression.
	 **/
	@Override
	protected Integer deriveUpper() {
	    PropertyAccessExpression self = this.getSelf();
	    ElementReference feature = self.getFeature();
	    FeatureReference featureReference = self.getFeatureReference();
	    Expression target = featureReference == null? null:
	                            featureReference.getExpression();
	    return feature == null || target == null? 0:
	                feature.getImpl().getUpper() * target.getUpper();
	}
	
	/**
	 * The multiplicity upper bound of a property access expression is given by
	 * the product of the multiplicity upper bounds of the referenced feature
	 * and the target expression.
	 **/
    @Override
    protected Integer deriveLower() {
        PropertyAccessExpression self = this.getSelf();
        ElementReference feature = self.getFeature();
        FeatureReference featureReference = self.getFeatureReference();
        Expression target = featureReference == null? null:
                                featureReference.getExpression();
        return feature == null || target == null? 0:
                    feature.getImpl().getLower() * target.getLower();
    }
	
	/*
	 * Derivations
	 */
	
	public boolean propertyAccessExpressionFeatureDerivation() {
		this.getSelf().getFeature();
		return true;
	}

	public boolean propertyAccessExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	public boolean propertyAccessExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

	public boolean propertyAccessExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}
	
	/*
	 * Constraints
	 */

	/**
	 * The feature reference for a property access expression must resolve to a
	 * single structural feature.
	 **/
	public boolean propertyAccessExpressionFeatureResolution() {
		return this.getSelf().getFeature() != null;
	}

	/**
	 * The assignments before the expression of the feature reference of a
	 * property access expression are the same as before the property access
	 * expression.
	 **/
	public boolean propertyAccessExpressionAssignmentsBefore() {
	    // Note: This is handled by updateAssignments.
		return true;
	}
	
	/*
	 * Helper Methods
	 */

	/**
	 * The assignments after a property access expression are the same as those
	 * after the target expression of its feature reference.
	 **/
	@Override
	public Map<String, AssignedSource> updateAssignmentMap() {
	    PropertyAccessExpression self = this.getSelf();
	    Map<String, AssignedSource> assignments = this.getAssignmentBeforeMap();
	    FeatureReference featureReference = self.getFeatureReference();
	    Expression expression = featureReference == null? null:
	                                featureReference.getExpression();
	    if (expression != null) {
	        expression.getImpl().setAssignmentBefore(assignments);
	        assignments = expression.getImpl().getAssignmentAfterMap();
	    }
		return assignments;
	} // updateAssignments
	
	@Override
	public void setCurrentScope(NamespaceDefinition currentScope) {
	    FeatureReference featureReference = this.getSelf().getFeatureReference();
	    if (featureReference != null) {
	        featureReference.getImpl().setCurrentScope(currentScope);
	    }
	}

} // PropertyAccessExpressionImpl
