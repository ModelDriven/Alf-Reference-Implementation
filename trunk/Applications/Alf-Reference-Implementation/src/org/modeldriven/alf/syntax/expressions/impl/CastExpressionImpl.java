
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

} // CastExpressionImpl
