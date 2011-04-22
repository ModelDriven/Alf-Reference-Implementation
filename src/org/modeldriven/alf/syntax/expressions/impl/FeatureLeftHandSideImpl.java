
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

import java.util.Map;

/**
 * A left-hand side that is a property reference.
 **/

public class FeatureLeftHandSideImpl extends LeftHandSideImpl {

	private FeatureReference feature = null;

	public FeatureLeftHandSideImpl(FeatureLeftHandSide self) {
		super(self);
	}

	@Override
	public FeatureLeftHandSide getSelf() {
		return (FeatureLeftHandSide) this.self;
	}

	public FeatureReference getFeature() {
		return this.feature;
	}

	public void setFeature(FeatureReference feature) {
		this.feature = feature;
	}

	/**
	 * The assignments before the expression of the feature reference of a
	 * feature left-hand side are the assignments before the feature left-hand
	 * side. 

	 * The assignments after a feature left-hand side are the assignments after
	 * the expression of the feature reference or, if there is an index, those
	 * after the index expression.
	 **/
	@Override
	protected Map<String, AssignedSource> deriveAssignmentAfter() {
	    FeatureLeftHandSide self = this.getSelf();
	    FeatureReference feature = self.getFeature();
	    Expression index = self.getIndex();
	    Map<String, AssignedSource> assignments = this.getAssignmentBeforeMap();
	    if (feature != null) {
	        Expression expression = feature.getExpression();
	        if (expression != null) {
	            expression.getImpl().setAssignmentBefore(assignments);
	            assignments = expression.getImpl().getAssignmentAfterMap();
	        }
	    }
	    if (index != null) {
	        index.getImpl().setAssignmentBefore(assignments);
	        assignments = index.getImpl().getAssignmentAfterMap();
	    }
	    return assignments;
	}
	
	/*
	 * Derivations
	 */
	
	public boolean featureLeftHandSideAssignmentBeforeDerivation() {
		this.getSelf().getAssignmentBefore();
		return true;
	}

	public boolean featureLeftHandSideAssignmentAfterDerivation() {
		this.getSelf().getAssignmentAfter();
		return true;
	}
	
	/*
	 * Constraints
	 */

	/**
	 * The expression of the feature reference of a feature left-hand side must
	 * have a multiplicity upper bound of 1.
	 **/
	public boolean featureLeftHandSideFeatureExpression() {
        FeatureLeftHandSide self = this.getSelf();
        FeatureReference feature = self.getFeature();
        Expression expression = feature == null? null: feature.getExpression();
		return expression != null && expression.getUpper() == 1
		            // Note: This referent constraint needs to be added to the spec.
		            && this.getReferent() != null;
	}

	/**
	 * If a feature left-hand side has an index, then the assignments before the
	 * index expression are the assignments after the expression of the feature
	 * reference.
	 **/
	public boolean featureLeftHandSideAssignmentsBefore() {
	    // Note: This is handled by deriveAssignmentAfter.
		return true;
	}
	
	/*
	 * Helper Methods
	 */

	@Override
    protected ElementReference getReferent() {
        FeatureLeftHandSide self = this.getSelf();
        FeatureReference feature = self.getFeature();
        return feature == null? null: 
                    feature.getImpl().getStructuralFeatureReferent();
    }

    /**
     * The primary expression is the feature left-hand side treated as a 
     * property access expression.
     **/
    @Override
    public Expression getPrimaryExpression() {
        FeatureLeftHandSide self = this.getSelf();
        PropertyAccessExpression propertyAccess = new PropertyAccessExpression();
        propertyAccess.setFeatureReference(self.getFeature());
        return propertyAccess;
    }

    @Override
    public String getLocalName() {
        return null;
    }

    @Override
    public void setCurrentScope(NamespaceDefinition currentScope) {
        super.setCurrentScope(currentScope);
        FeatureLeftHandSide self = this.getSelf();
        FeatureReference feature = self.getFeature();
        if (feature != null) {
            feature.getImpl().setCurrentScope(currentScope);
        }
    }

} // FeatureLeftHandSideImpl
