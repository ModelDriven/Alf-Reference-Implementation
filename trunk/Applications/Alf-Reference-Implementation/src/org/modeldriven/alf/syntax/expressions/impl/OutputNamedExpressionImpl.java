
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0
 * (http://www.opensource.org/licenses/afl-3.0.php)
 *
 */

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.FormalParameter;

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
	            lhs = new FeatureLeftHandSide();
	            ((FeatureLeftHandSide)lhs).setFeature(name.getDisambiguation());
	        } else {
	            lhs = new NameLeftHandSide();
	            ((NameLeftHandSide)lhs).setTarget(name);
	        }
	    } else if (expression instanceof PropertyAccessExpression) {
	        lhs = new FeatureLeftHandSide();
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
	
	// Helper methods
	
    /**
     * Derives isCollectionConversion for this output named expression as an
     * output from the given parameter.
     */
	@Override
    public boolean getIsCollectionConversion(FormalParameter parameter) {
        OutputNamedExpression self = this.getSelf();
        Expression expression = self.getExpression();
        return isCollectionConversion(
                expression.getType(), parameter.getType(), parameter.getUpper());
    }

	/**
     * Derives isBitStringConversion for this output named expression as an 
     * output from the given parameter.
     */
	@Override
    public boolean getIsBitStringConversion(FormalParameter parameter) {
        OutputNamedExpression self = this.getSelf();
        return isBitStringConversion(
                self.getExpression().getType(), parameter.getType());
    }

} // OutputNamedExpressionImpl
