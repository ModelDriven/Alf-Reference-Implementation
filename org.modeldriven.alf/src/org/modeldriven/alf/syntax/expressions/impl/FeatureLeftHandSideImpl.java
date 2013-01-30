
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;

import java.util.List;
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

    @Override 
    public String toString(boolean includeDerived) {
        return super.toString(includeDerived) + " feature:(" + feature + ")";
    }

	public FeatureReference getFeature() {
		return this.feature;
	}

	public void setFeature(FeatureReference feature) {
		this.feature = feature;
	}
	
    /**
     * The referent of a feature left-hand side is the structural feature to
     * which the feature reference of the left-hand side resolves.
     **/
   @Override
    public ElementReference deriveReferent() {
        FeatureLeftHandSide self = this.getSelf();
        FeatureReference feature = self.getFeature();
        return feature == null? null: 
                    feature.getImpl().getStructuralFeatureReferent();
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
	
    public boolean featureLeftHandSideReferentDerivation() {
        this.getSelf().getReferent();
        return true;
    }

    public boolean featureLeftHandSideTypeDerivation() {
        this.getSelf().getType();
        return true;
    }

    public boolean featureLeftHandSideLowerDerivation() {
        this.getSelf().getLower();
        return true;
    }

    public boolean featureLeftHandSideUpperDerivation() {
        this.getSelf().getUpper();
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
		return expression != null && expression.getUpper() == 1 &&
		            // Note: This constraint ensures that there will be an
		            // assigned name for an assignment to an attribute of a
		            // data type.
		            (!expression.getType().getImpl().isDataType() ||
		                    this.isDataValueUpdate());
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
	
    /**
     * The feature of a feature-left hand side must have a single referent that
     * is a structural feature.
     **/
    public boolean featureLeftHandSideReferentConstraint() {
        return this.getSelf().getReferent() != null;
    }

    /**
     * If a feature left-hand side has an index, then the referent of the
     * feature must be ordered and non-unique.
     **/
    public boolean featureLeftHandSideIndexedFeature() {
        FeatureLeftHandSide self = this.getSelf();
        if (self.getIndex() == null) {
            return true;
        } else {
            ElementReference referent = self.getReferent();
            return referent == null || 
                referent.getImpl().isOrdered() && !referent.getImpl().isUnique();
        }
    }

	/*
	 * Helper Methods
	 */

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

    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof FeatureLeftHandSide) {
            FeatureReference feature = 
                ((FeatureLeftHandSide)base).getFeature();
            if (feature != null) {
                this.getSelf().setFeature((FeatureReference)feature.getImpl().
                        bind(templateParameters, templateArguments));
            }
        }
    }

} // FeatureLeftHandSideImpl
