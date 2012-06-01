
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
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.List;
import java.util.Map;

/**
 * An expression used to filter values by type.
 **/

public class CastExpressionImpl extends ExpressionImpl {

	private Expression operand = null;
	private QualifiedName typeName = null;

	public CastExpressionImpl(CastExpression self) {
		super(self);
	}

	@Override
	public CastExpression getSelf() {
		return (CastExpression) this.self;
	}

	public Expression getOperand() {
		return this.operand;
	}

	public void setOperand(Expression operand) {
		this.operand = operand;
	}

	public QualifiedName getTypeName() {
		return this.typeName;
	}

	public void setTypeName(QualifiedName typeName) {
		this.typeName = typeName;
	}

    /**
     * The type of a cast expression is the referent of the given type name (if
     * there is one).
     **/
	@Override
	protected ElementReference deriveType() {
	    CastExpression self = this.getSelf();
	    QualifiedName typeName = self.getTypeName();
	    return typeName == null? null: typeName.getImpl().getNonTemplateClassifierReferent();
	}
	
    /**
     * A cast expression has a multiplicity lower bound of 0.
     **/
	@Override
	protected Integer deriveLower() {
	    return 1;
	}
	
    /**
     * A cast expression has a multiplicity upper bound that is the same as the
     * upper bound of its operand expression.
     **/
	@Override
	protected Integer deriveUpper() {
	    CastExpression self = this.getSelf();
	    Expression operand = self.getOperand();
	    return operand == null? 1: operand.getUpper();
	}
	
	/*
	 * Derivations
	 */

	public boolean castExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	public boolean castExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	public boolean castExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}
	
	/*
	 * Constraints
	 */

	/**
	 * If the cast expression has a type name, then it must resolve to a
	 * classifier.
	 **/
	public boolean castExpressionTypeResolution() {
        CastExpression self = this.getSelf();
        QualifiedName typeName = self.getTypeName();
        return typeName == null || self.getType() != null;
	}

	/**
	 * The assignments before the operand of a cast expression are the same as
	 * those before the cast expression.
	 **/
	public boolean castExpressionAssignmentsBefore() {
	    // Note: This is handled by updateAssignments.
		return true;
	}
	
	/*
	 * Helper Methods
	 */

	/**
	 * The assignments after a cast expression are the same as those after its
	 * operand expression.
	 **/
	@Override
	public Map<String, AssignedSource> updateAssignmentMap() {
        CastExpression self = this.getSelf();
        Expression operand = self.getOperand();
        operand.getImpl().setAssignmentBefore(this.getAssignmentBeforeMap());
		return operand.getImpl().getAssignmentAfterMap();
	} // updateAssignments
	
	@Override
	public void setCurrentScope(NamespaceDefinition currentScope) {
        CastExpression self = this.getSelf();
        Expression operand = self.getOperand();
        QualifiedName typeName = self.getTypeName();
        if (operand != null) {
            operand.getImpl().setCurrentScope(currentScope);
        }
        if (typeName != null) {
            typeName.getImpl().setCurrentScope(currentScope);
        }
	}

	@Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof CastExpression) {
            CastExpression self = this.getSelf();
            CastExpression baseExpression = (CastExpression)base;
            Expression operand = baseExpression.getOperand();
            QualifiedName typeName = baseExpression.getTypeName();
            if (operand != null) {
                self.setOperand((Expression)operand.getImpl().
                        bind(templateParameters, templateArguments));
            }
            if (typeName != null) {
                self.setTypeName(typeName.getImpl().
                        updateForBinding(templateParameters, templateArguments));
            }
        }
    }
    
} // CastExpressionImpl
