/*******************************************************************************
 * Copyright 2011, 2016 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.*;

/**
 * A named argument expression for an output parameter.
 **/

public class OutputNamedExpressionImpl extends NamedExpressionImpl {

	private LeftHandSide leftHandSide = null; // DERIVED

	public OutputNamedExpressionImpl(OutputNamedExpression self) {
		super(self);
	}
	
	@Override
	public OutputNamedExpression getSelf() {
		return (OutputNamedExpression) this.self;
	}

	public LeftHandSide getLeftHandSide() {
		if (this.leftHandSide == null) {
			this.setLeftHandSide(this.deriveLeftHandSide());
		}
		return this.leftHandSide;
	}

	public void setLeftHandSide(LeftHandSide leftHandSide) {
		this.leftHandSide = leftHandSide;
	}

	/**
	 * The equivalent left-hand side for an output named expression depends on
	 * the kind of expression. If the expression is a name expression with no
	 * disambiguation, then the left-hand side is a name left-hand side with the
	 * name from the name expression. If the expression is a name expression
	 * that disambiguates to a feature reference, then the left-hand side is a
	 * feature left-hand side for that feature reference. If the expression is a
	 * property access expression, then the left-hand side is a feature
	 * left-hand side for the feature reference of the property access
	 * expression. If the expression is a sequence access expression, then the
	 * left-hand side is a name left-hand side or feature left-hand side, as
	 * above, depending on whether the primary expression of the sequence access
	 * expression is a name expression or property access expression, and an
	 * index given by the index expression of the sequence access expression.
	 * Otherwise the left-hand side is empty.
	 **/
	protected LeftHandSide deriveLeftHandSide() {
	    OutputNamedExpression self = this.getSelf();
	    Expression expression = self.getExpression();
	    
	    Expression index = null;
	    if (expression instanceof SequenceAccessExpression) {
	        index = ((SequenceAccessExpression)expression).getIndex();
	        expression = ((SequenceAccessExpression)expression).getPrimary();
	    }
	    
        LeftHandSide lhs = null;
	    if (expression instanceof NameExpression) {
	        QualifiedName name = ((NameExpression)expression).getName();
	        if (name != null && name.getIsFeatureReference()) {
	            lhs = new FeatureLeftHandSide(name);
	            ((FeatureLeftHandSide)lhs).setFeature(name.getDisambiguation());
	        } else {
	            lhs = new NameLeftHandSide(name);
	            ((NameLeftHandSide)lhs).setTarget(name);
	        }
	    } else if (expression instanceof PropertyAccessExpression) {
	        lhs = new FeatureLeftHandSide(expression);
	        ((FeatureLeftHandSide)lhs).setFeature
	            (((PropertyAccessExpression)expression).getFeatureReference());
	    }
	    
	    if (lhs != null) {
	        lhs.setIndex(index);
	        if (expression != null) {
	            lhs.getImpl().setAssignmentBefore(expression.getImpl().getAssignmentBeforeMap());
	        }
	    }
	    
		return lhs;
	}
	
	/*
	 * Derivations
	 */

	public boolean outputNamedExpressionLeftHandSideDerivation() {
		this.getSelf().getLeftHandSide();
		return true;
	}
	
	/*
	 * Constraints
	 */

	/**
	 * The argument for an output parameter must be either be null, a name
	 * expression, a property access expression, or a sequence access expression
	 * whose primary expression is a name expression or a property access
	 * expression.
	 **/
	public boolean outputNamedExpressionForm() {
	    Expression expression = this.getSelf().getExpression();
	    if (expression == null) {
	        return true;
	    } else {
	        if (expression instanceof SequenceAccessExpression) {
	            expression = ((SequenceAccessExpression)expression).getPrimary();
	        }
	        return expression instanceof NameExpression ||
	               expression instanceof PropertyAccessExpression;
	    }
	}
	
	/*
	 * Helper Methods
	 */
	
    // Derives isCollectionConversion for this named expression as an output for
    // the given parameter.
    public boolean getIsCollectionConversion(ElementReference parameter) {
        NamedExpression self = this.getSelf();
        Expression expression = self.getExpression();
        ElementReference rhsType = parameter.getImpl().getType();
        ElementReference lhsType = self.getExpression().getType(); 
        int rhsUpper = expression.getUpper();
        return rhsType != null && lhsType != null && 
            rhsType.getImpl().isCollectionClass() && rhsUpper == 1 &&
            !lhsType.getImpl().isCollectionClass();
    }
    
    // Derives isBitStringConversion for this named expression as an output for
    // the given parameter.
    public boolean getIsBitStringConversion(ElementReference parameter) {
        NamedExpression self = this.getSelf();
        ElementReference rhsType = parameter.getImpl().getType();
        ElementReference lhsType = self.getExpression().getType(); 
        return rhsType != null && lhsType != null && 
            lhsType.getImpl().isBitString() &&
            (rhsType.getImpl().isInteger() ||
                    this.getIsCollectionConversion(parameter) &&
                    rhsType.getImpl().isIntegerCollection());
    }

    // Derives isRealConversion for this named expression as an output for
    // the given parameter.
    public boolean getIsRealConversion(ElementReference parameter) {
        NamedExpression self = this.getSelf();
        ElementReference rhsType = parameter.getImpl().getType();
        ElementReference lhsType = self.getExpression().getType(); 
        return rhsType != null && lhsType != null && 
            lhsType.getImpl().isReal() &&
            (rhsType.getImpl().isInteger() ||
                    this.getIsCollectionConversion(parameter) &&
                    rhsType.getImpl().isIntegerCollection());
    }

    // Checks whether Natural to UnlimitedNatural conversion is required
    // for this named expression as an output for the given parameter.
    public boolean isUnlimitedNaturalConversion(ElementReference parameter) {
        NamedExpression self = this.getSelf();
        ElementReference rhsType = parameter.getImpl().getType();
        ElementReference lhsType = self.getExpression().getType(); 
        return rhsType != null && lhsType != null && 
            lhsType.getImpl().isUnlimitedNatural() &&
            !lhsType.getImpl().isNatural() &&
            (rhsType.getImpl().isNatural() ||
                    this.getIsCollectionConversion(parameter) &&
                    rhsType.getImpl().isNaturalCollection());
    }

} // OutputNamedExpressionImpl
