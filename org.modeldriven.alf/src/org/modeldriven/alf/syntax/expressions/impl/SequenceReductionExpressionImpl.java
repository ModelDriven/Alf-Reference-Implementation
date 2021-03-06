/*******************************************************************************
 * Copyright 2011, 2018 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * An expression used to reduce a sequence of values effectively by inserting a
 * binary operation between the values.
 **/

public class SequenceReductionExpressionImpl extends ExpressionImpl {

	private ElementReference referent = null; // DERIVED
	private Boolean isOrdered = false;
	private ExtentOrExpression primary = null;
	private QualifiedName behaviorName = null;

	public SequenceReductionExpressionImpl(SequenceReductionExpression self) {
		super(self);
	}

	@Override
	public SequenceReductionExpression getSelf() {
		return (SequenceReductionExpression) this.self;
	}

    @Override
    public void addExternalReferences(Collection<ExternalElementReference> references) {
        super.addExternalReferences(references);
        SyntaxElement.addExternalReference(references, this.getSelf().getReferent());
    }
    
	public ElementReference getReferent() {
		if (this.referent == null) {
			this.setReferent(this.deriveReferent());
		}
		return this.referent;
	}

	public void setReferent(ElementReference referent) {
		this.referent = referent;
	}

	public Boolean getIsOrdered() {
		return this.isOrdered;
	}

	public void setIsOrdered(Boolean isOrdered) {
		this.isOrdered = isOrdered;
	}

	public ExtentOrExpression getPrimary() {
		return this.primary;
	}

	public void setPrimary(ExtentOrExpression primary) {
		this.primary = primary;
		if (primary != null) {
		    primary.getImpl().setContainingExpression(this.getSelf());
		}
	}

	public QualifiedName getBehaviorName() {
		return this.behaviorName;
	}

	public void setBehaviorName(QualifiedName behaviorName) {
		this.behaviorName = behaviorName;
	}

	/**
	 * The referent for a sequence reduction expression is the behavior denoted
	 * by the behavior name of the expression.
	 **/
	protected ElementReference deriveReferent() {
	    QualifiedName behaviorName = this.getSelf().getBehaviorName();
		return behaviorName == null? null: behaviorName.getImpl().getBehaviorReferent();
	}

	/**
	 * A sequence reduction expression has the same type as its primary
	 * expression.
	 **/
	@Override
	protected ElementReference deriveType() {
	    this.getAssignmentAfterMap(); // Force computation of assignments.
	    ExtentOrExpression primary = this.getSelf().getPrimary();
	    Expression expression = primary == null? null: primary.getExpression();
	    return expression == null? null: expression.getType();
	}
	
	/**
	 * A sequence reduction expression has a multiplicity upper bound of 1.
	 **/
	@Override
	protected Integer deriveUpper() {
	    return 1;
	}
	
	/**
	 * A sequence reduction expression has a multiplicity lower bound of 1.
	 **/
	// Actually the multiplicity should be 0, unless the multiplicity lower
	// bound of the primary is greater than 0.
	// TODO: Update this in the specification.
    @Override
    protected Integer deriveLower() {
        return this.getSelf().getPrimary().getExpression().getLower() > 0? 1: 0;
    }
	
	/*
	 * Derivations
	 */
	
	public boolean sequenceReductionExpressionReferentDerivation() {
		this.getSelf().getReferent();
		return true;
	}

	public boolean sequenceReductionExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	public boolean sequenceReductionExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

	public boolean sequenceReductionExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	/*
	 * Constraints
	 */
	
	/**
	 * The behavior name in a sequence reduction expression must denote a
	 * behavior.
	 **/
	public boolean sequenceReductionExpressionBehavior() {
		return this.getSelf().getReferent() != null;
	}

	/**
	 * The referent behavior must have two in parameters, a return parameter and
	 * no other parameters. The parameters must all have the same type as the
	 * argument expression and multiplicity [1..1].
	 **/
	public boolean sequenceReductionExpressionBehaviorParameters() {
	    SequenceReductionExpression self = this.getSelf();
	    ElementReference referent = self.getReferent();
        ElementReference type = self.getType();
	    if (referent == null || type == null) {
	        return true;
	    } else {
	        List<ElementReference> parameters = referent.getImpl().getParameters();
            ElementReference returnParameter = referent.getImpl().getReturnParameter();
	        if (parameters.size() != 3 || returnParameter == null) {
	            return false;
	        } else {
	            for (ElementReference parameter: parameters) {
	                ElementReference parameterType = parameter.getImpl().getType();
	                if (parameterType != null &&
	                    !((parameter.getImpl().equals(returnParameter) ||
	                            "in".equals(parameter.getImpl().getDirection())) &&
	                       parameterType.getImpl().equals(type) &&
	                       parameter.getImpl().getLower() == 1 && 
	                       parameter.getImpl().getUpper() == 1)) {
	                    return false;
	                }
	            }
	            return true;
	        }
	    }
	}

	/**
	 * The assignments before the target expression of a sequence reduction
	 * expression are the same as the assignments before the sequence reduction
	 * expression.
	 **/
	public boolean sequenceReductionExpressionAssignmentsBefore() {
	    // Note: This is handled by updateAssignments.
		return true;
	}

	/*
	 * Helper Methods
	 */
	
	/**
	 * The assignments after a sequence reduction expression are the same as
	 * after its primary expression.
	 **/
	@Override
	public Map<String, AssignedSource> updateAssignmentMap() {
		ExtentOrExpression primary = this.getSelf().getPrimary();
		Map<String, AssignedSource> assignments = this.getAssignmentBeforeMap();
		if (primary != null) {
		    primary.getImpl().setAssignmentBefore(assignments);
		    Expression expression = primary.getExpression();
    		if (expression != null) {
    		    assignments = expression.getImpl().getAssignmentAfterMap();
    		}
		}
		return assignments;
	} // updateAssignmentMap
	
	@Override
	public void setCurrentScope(NamespaceDefinition currentScope) {
	    SequenceReductionExpression self = this.getSelf();
	    ExtentOrExpression primary = self.getPrimary();
	    QualifiedName behaviorName = self.getBehaviorName();
	    if (primary != null) {
	        primary.getImpl().setCurrentScope(currentScope);
	    }
	    if (behaviorName != null) {
	        behaviorName.getImpl().setCurrentScope(currentScope);
	    }
	}

    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof SequenceReductionExpression) {
            SequenceReductionExpression self = this.getSelf();
            SequenceReductionExpression baseExpression = 
                (SequenceReductionExpression)base;
            ExtentOrExpression primary = baseExpression.getPrimary();
            QualifiedName behaviorName = baseExpression.getBehaviorName();
            self.setIsOrdered(baseExpression.getIsOrdered());
            if (primary != null) {
                self.setPrimary((ExtentOrExpression)primary.getImpl().
                        bind(templateParameters, templateArguments));
            }
            if (behaviorName != null) {
                self.setBehaviorName(behaviorName.getImpl().
                        updateBindings(templateParameters, templateArguments));
            }
        }
    }

} // SequenceReductionExpressionImpl
