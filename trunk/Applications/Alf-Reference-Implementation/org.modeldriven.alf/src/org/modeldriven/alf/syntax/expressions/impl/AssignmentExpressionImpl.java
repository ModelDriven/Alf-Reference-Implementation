
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
import org.modeldriven.alf.syntax.common.impl.AssignedSourceImpl;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An expression used to assign a value to a local name, parameter or property.
 **/

public class AssignmentExpressionImpl extends ExpressionImpl {

	private String operator = "";
	private LeftHandSide leftHandSide = null;
	private Expression rightHandSide = null;
	private AssignedSource assignment = null; // DERIVED
	private ElementReference feature = null; // DERIVED
	private Boolean isIndexed = null; // DERIVED
	private Boolean isArithmetic = null; // DERIVED
	private Boolean isDefinition = null; // DERIVED
	private Boolean isSimple = null; // DERIVED
	private Expression expression = null; // DERIVED
	private Boolean isFeature = null; // DERIVED
	private Boolean isDataValueUpdate = null; // DERIVED
	private Boolean isCollectionConversion = null; // DERIVED
	private Boolean isBitStringConversion = null; // DERIVED

	public AssignmentExpressionImpl(AssignmentExpression self) {
		super(self);
	}

	@Override
	public AssignmentExpression getSelf() {
		return (AssignmentExpression) this.self;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public LeftHandSide getLeftHandSide() {
		return this.leftHandSide;
	}

	public void setLeftHandSide(LeftHandSide leftHandSide) {
		this.leftHandSide = leftHandSide;
	}

	public Expression getRightHandSide() {
		return this.rightHandSide;
	}

	public void setRightHandSide(Expression rightHandSide) {
		this.rightHandSide = rightHandSide;
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

	public ElementReference getFeature() {
		if (this.feature == null) {
			this.setFeature(this.deriveFeature());
		}
		return this.feature;
	}

	public void setFeature(ElementReference feature) {
		this.feature = feature;
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

	public Boolean getIsArithmetic() {
		if (this.isArithmetic == null) {
			this.setIsArithmetic(this.deriveIsArithmetic());
		}
		return this.isArithmetic;
	}

	public void setIsArithmetic(Boolean isArithmetic) {
		this.isArithmetic = isArithmetic;
	}

	public Boolean getIsDefinition() {
		if (this.isDefinition == null) {
			this.setIsDefinition(this.deriveIsDefinition());
		}
		return this.isDefinition;
	}

	public void setIsDefinition(Boolean isDefinition) {
		this.isDefinition = isDefinition;
	}

	public Boolean getIsSimple() {
		if (this.isSimple == null) {
			this.setIsSimple(this.deriveIsSimple());
		}
		return this.isSimple;
	}

	public void setIsSimple(Boolean isSimple) {
		this.isSimple = isSimple;
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

	public Boolean getIsFeature() {
		if (this.isFeature == null) {
			this.setIsFeature(this.deriveIsFeature());
		}
		return this.isFeature;
	}

	public void setIsFeature(Boolean isFeature) {
		this.isFeature = isFeature;
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

	public Boolean getIsCollectionConversion() {
		if (this.isCollectionConversion == null) {
			this.setIsCollectionConversion(this.deriveIsCollectionConversion());
		}
		return this.isCollectionConversion;
	}

	public void setIsCollectionConversion(Boolean isCollectionConversion) {
		this.isCollectionConversion = isCollectionConversion;
	}

	public Boolean getIsBitStringConversion() {
		if (this.isBitStringConversion == null) {
			this.setIsBitStringConversion(this.deriveIsBitStringConversion());
		}
		return this.isBitStringConversion;
	}

	public void setIsBitStringConversion(Boolean isBitStringConversion) {
		this.isBitStringConversion = isBitStringConversion;
	}

    /**
     * The new assigned source for an assignment to a local name is the
     * assignment expression. If the assignment is a definition, then the type
     * is given by the right hand side, the multiplicity upper bound is 1 if the
     * upper bound of the right hand side is 1 and otherwise * and the
     * multiplicity lower bound is 0. Otherwise, the type and multiplicity are
     * the same as the left hand side.
     * multiplicity lower bound is 0. Otherwise, the type is the same as the
     * left-hand side and the multiplicity is also the same as the left-hand
     * side, if the left-hand side is not indexed, and is * if it is indexed.
     **/
	protected AssignedSource deriveAssignment() {
	    AssignmentExpression self = this.getSelf();
	    LeftHandSide lhs = self.getLeftHandSide();
        Expression rhs = self.getRightHandSide();
	    String name = lhs == null? null: lhs.getImpl().getAssignedName();
	    if (name == null || rhs == null) {
	        return null;
	    } else if (self.getIsDefinition()) {
	        int upper = rhs.getUpper() == 1? 1: -1;
	        return AssignedSourceImpl.makeAssignment(name, self, rhs.getType(), 0, upper);
	    } else {
	        AssignedSource oldAssignment = this.getAssignmentBefore(name);
	        if (oldAssignment == null) {
	            ElementReference referent = lhs.getImpl().getReferent();
	            if (referent != null && referent.getImpl().isParameter() &&
	                    !self.getIsIndexed()) {
	                return AssignedSourceImpl.makeAssignment
	                        (referent.getImpl().getName(), self, 
	                         referent.getImpl().getType(), 
	                         referent.getImpl().getLower(), 
	                         referent.getImpl().getUpper());
	            } else {
	                return null;
	            }
	        } else {
    	        AssignedSource assignment = AssignedSourceImpl.makeAssignment(oldAssignment);
    	        assignment.setSource(self);
    	        return assignment;
	        }
	    }
	}

    /**
     * If the left-hand side of an assignment expression is a feature, then the
     * feature of the assignment is the referent of the left-hand side.
     **/
	protected ElementReference deriveFeature() {
	    LeftHandSide lhs = this.getSelf().getLeftHandSide();
	    if (lhs == null) {
            return null;
	    } else {
	        FeatureReference feature = lhs.getImpl().getFeature();
	        if (feature == null) {
	            return null;
	        } else {
	            this.getAssignmentAfterMap(); // Force computatio of assignments.
	            Object[] referents = feature.getReferent().toArray();
	            if (referents.length == 0) {
	                return null;
	            } else {
	                return (ElementReference)referents[0];
	            }
	        }
	    }
	}

    /**
     * The left hand side of an assignment expression is indexed if it has an
     * index.
     **/
	protected Boolean deriveIsIndexed() {
	    LeftHandSide lhs = this.getSelf().getLeftHandSide();
		return lhs != null && lhs.getIndex() != null;
	}

    /**
     * An assignment expression is an arithmetic assignment if its operator is a
     * compound assignment operator for an arithmetic operation.
     **/
	protected Boolean deriveIsArithmetic() {
        AssignmentExpression self = this.getSelf();
        return !self.getIsSimple() && this.isArithmeticOperator();
	}
	
    /**
     * An assignment expression is a definition if it is a simple assignment and
     * its left hand side is a local name for which there is no assignment
     * before the expression.
     **/
	protected Boolean deriveIsDefinition() {
	    AssignmentExpression self = this.getSelf();
	    LeftHandSide lhs = self.getLeftHandSide();
		return self.getIsSimple() && !self.getIsIndexed() &&
		        lhs != null && lhs.getImpl().getReferent() == null &&
		        lhs.getImpl().getLocalName() != null;    
	}

    /**
     * An assignment expression is a simple assignment if the assignment
     * operator is "=".
     **/
	protected Boolean deriveIsSimple() {
	    String operator = this.getSelf().getOperator();
		return operator != null && operator.equals("=");
	}

    /**
     * For a compound assignment, the effective expression is the left-hand side
     * treated as a name expression, property access expression or sequence
     * access expression, as appropriate for evaluation to obtain the original
     * value to be updated.
     **/
	protected Expression deriveExpression() {
	    LeftHandSide lhs = this.getSelf().getLeftHandSide();
		return lhs == null? null: lhs.getImpl().getExpression();
	}

    /**
     * The left hand side of an assignment expression is a feature if it is a
     * kind of FeatureLeftHandSide.
     **/
	protected Boolean deriveIsFeature() {
	    LeftHandSide lhs = this.getSelf().getLeftHandSide();
		return lhs != null && lhs.getImpl().getFeature() != null;
	}

    /**
     * An assignment expression is a data value update if its left hand side is
     * an attribute of a data value held in a local name or parameter.
     **/
	protected Boolean deriveIsDataValueUpdate() {
	    LeftHandSide lhs = this.getSelf().getLeftHandSide();
	    return lhs != null && lhs.getImpl().isDataValueUpdate();
	}

    /**
     * An assignment requires collection conversion if the type of the
     * right-hand side is a collection class and its multiplicity upper bound is
     * 1, and the type of the left-hand side is not a collection class.
     **/
	protected Boolean deriveIsCollectionConversion() {
	    AssignmentExpression self = this.getSelf();
	    LeftHandSide lhs = self.getLeftHandSide();
	    Expression rhs = self.getRightHandSide();
		if (lhs == null || rhs == null) {
		    return false;
		} else {
		    ElementReference lhsType = lhs.getImpl().getType();
		    ElementReference rhsType = rhs.getType();
		    return rhsType != null && lhsType != null && 
		           rhsType.getImpl().isCollectionClass() && rhs.getUpper() == 1 &&
		           !lhsType.getImpl().isCollectionClass();
		}
	}

    /**
     * An assignment requires BitString conversion if the type of the left-hand
     * side is BitString and either the type of the right-hand side is Integer
     * or collection conversion is required and the type of the right-hand side
     * is a collection class whose argument type is Integer.
     **/
	protected Boolean deriveIsBitStringConversion() {
        AssignmentExpression self = this.getSelf();
        LeftHandSide lhs = self.getLeftHandSide();
        ElementReference lhsType = lhs == null? null: lhs.getImpl().getType();
        
        // NOTE: Using self.getType() as the rhsType works for both simple and
        // compound assignments.
        ElementReference rhsType = self.getType();
        
        return rhsType != null && lhsType != null && 
                lhsType.getImpl().isBitString() &&
                (rhsType.getImpl().isInteger() ||
                        self.getIsCollectionConversion() &&
                        rhsType.getImpl().isIntegerCollection());
	}
	
	/**
     * A simple assignment expression has the same type as its right-hand side
     * expression. A compound assignment expression has the same type as its
     * left-hand side.
	 **/
	@Override
	protected ElementReference deriveType() {
	    AssignmentExpression self = this.getSelf();
	    if (self.getIsSimple()) {
    	    Expression rhs = self.getRightHandSide();
    	    return rhs == null? null: rhs.getType();
	    } else {
	        LeftHandSide lhs = self.getLeftHandSide();
	        return lhs == null? null: lhs.getImpl().getType();
	    }
	}
	
    /**
     * An assignment expression has the same multiplicity upper bound as its
     * right-hand side expression.
     **/
	@Override
	protected Integer deriveUpper() {
        this.getAssignmentAfterMap(); // Force computation of assignments.
        Expression rhs = this.getSelf().getRightHandSide();
        return rhs == null? null: rhs.getUpper();
	}
	
    /**
     * A simple assignment expression has the same multiplicity lower bound as
     * its right-hand side expression. A compound assignment expression has the
     * same multiplicity as its left-hand side.
     **/
	@Override
	protected Integer deriveLower() {
        AssignmentExpression self = this.getSelf();
        this.getAssignmentAfterMap(); // Force computation of assignments.
        if (self.getIsSimple()) {
            Expression rhs = self.getRightHandSide();
            return rhs == null? null: rhs.getLower();
        } else {
            LeftHandSide lhs = self.getLeftHandSide();
            return lhs == null? null: lhs.getImpl().getLower();
        }
	}
	
	/*
	 * Derivations
	 */

	public boolean assignmentExpressionIsSimpleDerivation() {
		this.getSelf().getIsSimple();
		return true;
	}

	public boolean assignmentExpressionIsArithmeticDerivation() {
		this.getSelf().getIsArithmetic();
		return true;
	}

	public boolean assignmentExpressionIsDefinitionDerivation() {
		this.getSelf().getIsDefinition();
		return true;
	}

	public boolean assignmentExpressionIsFeatureDerivation() {
		this.getSelf().getIsFeature();
		return true;
	}

	public boolean assignmentExpressionIsIndexedDerivation() {
		this.getSelf().getIsIndexed();
		return true;
	}

	public boolean assignmentExpressionIsDataValueUpdateDerivation() {
		this.getSelf().getIsDataValueUpdate();
		return true;
	}

	public boolean assignmentExpressionAssignmentDerivation() {
		this.getSelf().getAssignment();
		return true;
	}

	public boolean assignmentExpressionFeatureDerivation() {
		this.getSelf().getFeature();
		return true;
	}

	public boolean assignmentExpressionExpressionDerivation() {
		this.getSelf().getExpression();
		return true;
	}

	public boolean assignmentExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	public boolean assignmentExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

	public boolean assignmentExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}
	
    public boolean assignmentExpressionIsCollectionConversionDerivation() {
        this.getSelf().getIsCollectionConversion();
        return true;
    }

    public boolean assignmentExpressionIsBitStringConversionDerivation() {
        this.getSelf().getIsBitStringConversion();
        return true;
    }
    
	/*
	 * Constraints
	 */

	/**
     * If the left-hand side of a simple assignment is not a new local name, and
     * the right-hand side is not null, then the left-hand side must either be
     * untyped or the right-hand side expression must have a type that conforms
     * to the type of the left-hand side.
     **/
	public boolean assignmentExpressionSimpleAssignmentTypeConformance() {
	    AssignmentExpression self = this.getSelf();
	    LeftHandSide lhs = self.getLeftHandSide();
	    Expression rhs = self.getRightHandSide();
	    return !self.getIsSimple() || lhs == null || rhs == null ||
	            self.getIsDefinition() || 
	            lhs.getImpl().isTypeConformantWith(rhs.getImpl());
	}

	/**
	 * If the left-hand side of a simple assignment is not a new local name and
	 * the multiplicity upper bound of the left-hand side is less than or equal
	 * to 1, then the multiplicity upper bound of the right-hand side cannot be
	 * greater than that of the left-hand side.
	 **/
	public boolean assignmentExpressionSimpleAssignmentMultiplicityConformance() {
        AssignmentExpression self = this.getSelf();
        LeftHandSide lhs = self.getLeftHandSide();
        Expression rhs = self.getRightHandSide();
        return !self.getIsSimple() || lhs == null || rhs == null || 
               self.getIsDefinition() ||
               lhs.getImpl().isMultiplicityConformantWith(rhs.getImpl());
	}

	/**
     * For a compound assignment, if the operator is an arithmetic operator,
     * then either the left-hand side and the right-hand side both have type
     * Integer or they both have type String and the operator is +. If the
     * operator is a logical operator, then either the left-hand side and the
     * right-hand side both have type Boolean or Bit String or the left-hand
     * side has type Bit String and the right-hand side has type Integer. If the
     * operator is a shift operator, then the left-hand side must have type Bit
     * String and the right-hand side must have type Integer.
	 **/
	public boolean assignmentExpressionCompoundAssignmentTypeConformance() {
	    AssignmentExpression self = this.getSelf();
	    if (self.getIsSimple()) {
	        return true;
	    } else {
    	    LeftHandSide lhs = self.getLeftHandSide();
    	    Expression rhs = self.getRightHandSide();
    	    if (lhs == null || rhs == null) {
    	        return false;
    	    } else {
    	        ElementReference lhsType = lhs.getImpl().getType();
    	        ElementReference rhsType = rhs.getType();
    	        return lhsType != null && rhsType != null &&
    	               (this.isArithmeticOperator() && 
    	                       lhsType.getImpl().isInteger() &&
    	                       rhsType.getImpl().isInteger() ||
    	                this.isLogicalOperator() &&
    	                       (lhsType.getImpl().isBoolean() &&
    	                        rhsType.getImpl().isBoolean() ||
                                lhsType.getImpl().isBitString() &&
                                (rhsType.getImpl().isBitString() ||
                                       rhsType.getImpl().isInteger())) ||
    	                this.isShiftOperator() &&
    	                       lhsType.getImpl().isBitString() &&
    	                       rhsType.getImpl().isInteger() ||
                        this.isStringOperator() &&
                               lhsType.getImpl().isString() &&
                               rhsType.getImpl().isString()
    	                ); 
    	    }
	    }
	}

	/**
	 * For a compound assignment, both the left-hand and right-hand sides must
	 * have a multiplicity upper bound of 1.
	 **/
	public boolean assignmentExpressionCompoundAssignmentMultiplicityConformance() {
        AssignmentExpression self = this.getSelf();
        if (self.getIsSimple()) {
            return true;
        } else {
            LeftHandSide lhs = self.getLeftHandSide();
            Expression rhs = self.getRightHandSide();
            return lhs != null && rhs != null && 
                        lhs.getImpl().getUpper() == 1 && rhs.getUpper() == 1;
        }
	}

	/**
	 * The assigned source of a name before the right-hand side expression of an
	 * assignment expression is the same as the assigned source before the
	 * assignment expression. The assigned source of a name before the left-hand
	 * side is the assigned source after the right-hand side expression.
	 **/
	public boolean assignmentExpressionAssignmentsBefore() {
	    // Note: This is handled by updateAssignmentMap.
		return true;
	}

    /**
     * If an assignment expression has a feature with a primary expression whose
     * type is a data type, then the assignment expression must be a data value
     * update.
     **/
    public boolean assignmentExpressionDataValueUpdateLegality() {
        AssignmentExpression self = this.getSelf();
        LeftHandSide leftHandSide = self.getLeftHandSide();
        FeatureReference feature = leftHandSide == null? null: 
            leftHandSide.getImpl().getFeature();
        Expression primary = feature == null? null: feature.getExpression();
        ElementReference type = primary == null? null: primary.getType();
        return type == null || !type.getImpl().isDataType() || 
                    self.getIsDataValueUpdate();
    }

	/*
	 * Helper Methods
	 */

	/**
	 * The assignments after an assignment expression are the assignments after
	 * the left-hand side, updated by the assignment from the assignment
	 * statement, if any.
	 **/
	@Override
	protected Map<String, AssignedSource> updateAssignmentMap() {
	    AssignmentExpression self = this.getSelf();
        LeftHandSide lhs = self.getLeftHandSide();
        Expression rhs = self.getRightHandSide();
        Map<String, AssignedSource> assignments = this.getAssignmentBeforeMap();
        if (rhs != null) {
            rhs.getImpl().setAssignmentBefore(assignments);
            assignments = rhs.getImpl().getAssignmentAfterMap();
        }
        if (lhs != null) {
            lhs.getImpl().setAssignmentBefore(assignments);
            assignments = lhs.getImpl().getAssignmentAfterMap();
            AssignedSource assignment = self.getAssignment();
            if (assignment != null) {
                assignments = new HashMap<String, AssignedSource>(assignments);
                assignments.put(assignment.getName(), assignment);
            }
        }
        return assignments;
	} // updateAssignments
		
    public boolean isArithmeticOperator() {
        String operator = this.getSelf().getOperator();
        return operator != null && (
                operator.equals("+=") ||
                operator.equals("-=") ||
                operator.equals("*=") ||
                operator.equals("/=") ||
                operator.equals("%="));
    }

    public boolean isLogicalOperator() {
        String operator = this.getSelf().getOperator();
        return operator != null && (
                operator.equals("&=") ||
                operator.equals("|=") ||
                operator.equals("^="));
    }

    public boolean isShiftOperator() {
        String operator = this.getSelf().getOperator();
        return operator != null && (
                operator.equals("<<=") ||
                operator.equals(">>=") ||
                operator.equals(">>>="));
    }
    
    public boolean isStringOperator() {
        String operator = this.getSelf().getOperator();
        return operator != null && operator.equals("+=");
    }
    
    @Override
    public void setCurrentScope(NamespaceDefinition currentScope) {
        AssignmentExpression self = this.getSelf();
        LeftHandSide lhs = self.getLeftHandSide();
        Expression rhs = self.getRightHandSide();
        if (lhs != null) {
            lhs.getImpl().setCurrentScope(currentScope);
        }
        if (rhs != null) {
            rhs.getImpl().setCurrentScope(currentScope);
        }
    }
    
    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof AssignmentExpression) {
            AssignmentExpression self = this.getSelf();
            AssignmentExpression baseExpression = (AssignmentExpression)base;
            LeftHandSide lhs = baseExpression.getLeftHandSide();
            Expression rhs = baseExpression.getRightHandSide();
            self.setOperator(baseExpression.getOperator());
            if (lhs != null) {
                self.setLeftHandSide((LeftHandSide)lhs.getImpl().
                        bind(templateParameters, templateArguments));
            }
            if (rhs != null) {
                self.setRightHandSide((Expression)rhs.getImpl().
                        bind(templateParameters, templateArguments));
            }
        }
    }

} // AssignmentExpressionImpl
