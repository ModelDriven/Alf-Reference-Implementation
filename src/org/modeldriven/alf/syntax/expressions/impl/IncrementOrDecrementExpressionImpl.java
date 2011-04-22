
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0
 * (http://www.opensource.org/licenses/afl-3.0.php)
 *
 */

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.common.impl.AssignedSourceImpl;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.HashMap;
import java.util.Map;

/**
 * A unary expression with either an increment or decrement operator.
 **/

public class IncrementOrDecrementExpressionImpl extends ExpressionImpl {

	private String operator = "";
	private AssignedSource assignment = null; // DERIVED
	private LeftHandSide operand = null;
	private Expression expression = null; // DERIVED
	private ElementReference feature = null; // DERIVED
	private Boolean isPrefix = false;
	private Boolean isFeature = null; // DERIVED
	private Boolean isIndexed = null; // DERIVED
	private Boolean isDataValueUpdate = null; // DERIVED

	public IncrementOrDecrementExpressionImpl(
			IncrementOrDecrementExpression self) {
		super(self);
	}

	@Override
	public IncrementOrDecrementExpression getSelf() {
		return (IncrementOrDecrementExpression) this.self;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public AssignedSource getAssignment() {
		if (this.assignment == null) {
			this.setAssignment(this.deriveAssignment());
		}
		return this.assignment;
	}

	public void setAssignment(AssignedSource assignment) {
		this.assignment = assignment;
	}

	public LeftHandSide getOperand() {
		return this.operand;
	}

	public void setOperand(LeftHandSide operand) {
		this.operand = operand;
	}

	public Expression getExpression() {
		if (this.expression == null) {
			this.setExpression(this.deriveExpression());
		}
		return this.expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
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

	public Boolean getIsPrefix() {
		return this.isPrefix;
	}

	public void setIsPrefix(Boolean isPrefix) {
		this.isPrefix = isPrefix;
	}

	public Boolean getIsFeature() {
		if (this.isFeature == null) {
			this.setIsFeature(this.deriveIsFeature());
		}
		return this.isFeature;
	}

	public void setIsFeature(Boolean isFeature) {
		this.isFeature = isFeature;
	}

	public Boolean getIsIndexed() {
		if (this.isIndexed == null) {
			this.setIsIndexed(this.deriveIsIndexed());
		}
		return this.isIndexed;
	}

	public void setIsIndexed(Boolean isIndexed) {
		this.isIndexed = isIndexed;
	}

	public Boolean getIsDataValueUpdate() {
		if (this.isDataValueUpdate == null) {
			this.setIsDataValueUpdate(this.deriveIsDataValueUpdate());
		}
		return this.isDataValueUpdate;
	}

	public void setIsDataValueUpdate(Boolean isDataValueUpdate) {
		this.isDataValueUpdate = isDataValueUpdate;
	}

    /**
     * If the operand of an increment or decrement expression is a name, then
     * the assignment for the expression is a new assigned source for the name
     * with the expression as the source.
     **/
	protected AssignedSource deriveAssignment() {
	    IncrementOrDecrementExpression self = this.getSelf();
	    LeftHandSide operand = self.getOperand();
	    if (self.getIsFeature()) {
	        return null;
	    } else {
	        String name = ((NameLeftHandSide)operand).getTarget().getUnqualifiedName().getName();
	        AssignedSource oldAssignment = this.getAssignmentBefore(name);
	        if (oldAssignment == null) {
	            return AssignedSourceImpl.makeAssignment(name, self, self.getType(), 1, 1);
	        } else {
	            AssignedSource newAssignment = AssignedSourceImpl.makeAssignment(oldAssignment);
	            newAssignment.setSource(self);
	            return newAssignment;
	        }
	    }
	}

    /**
     * The effective expression for the operand of an increment or decrement
     * expression is the operand treated as a name expression, property access
     * expression or sequence access expression, as appropriate for evaluation
     * to obtain the original value to be updated.
     **/
	protected Expression deriveExpression() {
        IncrementOrDecrementExpression self = this.getSelf();
        LeftHandSide operand = self.getOperand();
        return operand == null? null: operand.getImpl().getExpression();
	}

    /**
     * If the operand of an increment or decrement expression is a feature, then
     * the referent for the operand.
     **/
	protected ElementReference deriveFeature() {
        IncrementOrDecrementExpression self = this.getSelf();
        LeftHandSide operand = self.getOperand();
         return !self.getIsFeature()? null:
                     ((FeatureLeftHandSide)operand).getImpl().getReferent();
	}

	/**
	 * An increment or decrement expression has a feature as its operand if the
	 * operand is a kind of FeatureLeftHandSide.
	 **/
	protected Boolean deriveIsFeature() {
		return this.getSelf().getOperand() instanceof FeatureLeftHandSide;
	}

	/**
	 * An increment or decrement expression is indexed if its operand is
	 * indexed.
	 **/
	protected Boolean deriveIsIndexed() {
        IncrementOrDecrementExpression self = this.getSelf();
        LeftHandSide operand = self.getOperand();
		return operand != null && operand.getIndex() != null;
	}

    /**
     * An increment or decrement expression is a data value update if its
     * operand is an attribute of a data value held in a local name or
     * parameter.
     **/
	protected Boolean deriveIsDataValueUpdate() {
        IncrementOrDecrementExpression self = this.getSelf();
        LeftHandSide operand = self.getOperand();
		return operand != null && operand.getImpl().isDataValueUpdate();
	}

    /**
     * An increment or decrement expression has type Integer.
     **/
	@Override
	protected ElementReference deriveType() {
	    return RootNamespace.getIntegerType();
	}
	
    /**
     * An increment or decrement expression has the same multiplicity lower
     * bound as its operand expression.
     **/
    @Override
    protected Integer deriveLower() {
        IncrementOrDecrementExpression self = this.getSelf();
        LeftHandSide operand = self.getOperand();
        return operand == null? 1: operand.getImpl().getLower();
    }
    
    /**
     * An increment or decrement expression has a multiplicity upper bound of 1.
     **/
    @Override
    protected Integer deriveUpper() {
        return 1;
    }
	
	/*
	 * Derivations
	 */
	
    public boolean incrementOrDecrementExpressionAssignment() {
        return true;
    }

	public boolean incrementOrDecrementExpressionIsFeatureDerivation() {
		this.getSelf().getIsFeature();
		return true;
	}

	public boolean incrementOrDecrementExpressionIsIndexedDerivation() {
		this.getSelf().getIsIndexed();
		return true;
	}

    public boolean incrementOrDecrementExpressionExpressionDerivation() {
        this.getSelf().getExpression();
        return true;
    }

    public boolean incrementOrDecrementExpressionIsDataValueUpdate() {
        return true;
    }

    public boolean incrementOrDecrementExpressionFeature() {
        return true;
    }

    public boolean incrementOrDecrementExpressionTypeDerivation() {
        this.getSelf().getType();
        return true;
    }

    public boolean incrementOrDecrementExpressionLowerDerivation() {
        this.getSelf().getLower();
        return true;
    }

    public boolean incrementOrDecrementExpressionUpperDerivation() {
        this.getSelf().getUpper();
        return true;
    }
    
    /*
     * Constraints
     */

	/**
	 * The operand expression must have type Integer and a multiplicity upper
	 * bound of 1.
	 **/
	public boolean incrementOrDecrementExpressionOperand() {
        IncrementOrDecrementExpression self = this.getSelf();
        LeftHandSide operand = self.getOperand();
        ElementReference type = operand == null? null: operand.getImpl().getType();
        return operand != null && operand.getImpl().getUpper() == 1 &&
                    type != null && type.getImpl().isInteger();
	}

	/**
	 * The assignments before the operand of an increment or decrement
	 * expression are the same as those before the increment or decrement
	 * expression.
	 **/
	public boolean incrementOrDecrementExpressionAssignmentsBefore() {
	    // Note: This is handled by updateAssignmentMap.
		return true;
	}
	
	/*
	 * Helper Methods
	 */

	/**
	 * The assignments after an increment and decrement expression include all
	 * those after its operand expression. Further, if the operand expression,
	 * considered as a left hand side, is a local name, then this is reassigned.
	 **/
	@Override
	public Map<String, AssignedSource> updateAssignmentMap() {
        IncrementOrDecrementExpression self = this.getSelf();
        LeftHandSide operand = self.getOperand();
        Map<String, AssignedSource> assignments = this.getAssignmentBeforeMap();
        if (operand != null) {
            operand.getImpl().setAssignmentBefore(assignments);
            assignments = operand.getImpl().getAssignmentAfterMap();
        }
        AssignedSource assignment = self.getAssignment();
        if (assignment != null) {
            assignments = new HashMap<String, AssignedSource>(assignments);
            assignments.put(assignment.getName(), assignment);
        }
        return assignments;
	} // updateAssignments
	
	@Override
	public void setCurrentScope(NamespaceDefinition currentScope) {
        IncrementOrDecrementExpression self = this.getSelf();
        LeftHandSide operand = self.getOperand();
        if (operand != null) {
            operand.getImpl().setCurrentScope(currentScope);
        }
	}

} // IncrementOrDecrementExpressionImpl
