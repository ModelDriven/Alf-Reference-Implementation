/*******************************************************************************
 * Copyright 2011, 2017 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.parser.Token;

import org.modeldriven.alf.syntax.common.*;
import java.util.Collection;
import org.modeldriven.alf.syntax.expressions.impl.AssignmentExpressionImpl;

/**
 * An expression used to assign a value to a local name, parameter or property.
 **/

public class AssignmentExpression extends Expression {

	public AssignmentExpression() {
		this.impl = new AssignmentExpressionImpl(this);
	}

	public AssignmentExpression(Parser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public AssignmentExpression(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public AssignmentExpressionImpl getImpl() {
		return (AssignmentExpressionImpl) this.impl;
	}

	public String getOperator() {
		return this.getImpl().getOperator();
	}

	public void setOperator(String operator) {
		this.getImpl().setOperator(operator);
	}

	public LeftHandSide getLeftHandSide() {
		return this.getImpl().getLeftHandSide();
	}

	public void setLeftHandSide(LeftHandSide leftHandSide) {
		this.getImpl().setLeftHandSide(leftHandSide);
	}

	public Expression getRightHandSide() {
		return this.getImpl().getRightHandSide();
	}

	public void setRightHandSide(Expression rightHandSide) {
		this.getImpl().setRightHandSide(rightHandSide);
	}

	public AssignedSource getAssignment() {
		return this.getImpl().getAssignment();
	}

	public void setAssignment(AssignedSource assignment) {
		this.getImpl().setAssignment(assignment);
	}

	public ElementReference getFeature() {
		return this.getImpl().getFeature();
	}

	public void setFeature(ElementReference feature) {
		this.getImpl().setFeature(feature);
	}

	public Boolean getIsIndexed() {
		return this.getImpl().getIsIndexed();
	}

	public void setIsIndexed(Boolean isIndexed) {
		this.getImpl().setIsIndexed(isIndexed);
	}

	public Boolean getIsArithmetic() {
		return this.getImpl().getIsArithmetic();
	}

	public void setIsArithmetic(Boolean isArithmetic) {
		this.getImpl().setIsArithmetic(isArithmetic);
	}

	public Boolean getIsDefinition() {
		return this.getImpl().getIsDefinition();
	}

	public void setIsDefinition(Boolean isDefinition) {
		this.getImpl().setIsDefinition(isDefinition);
	}

	public Boolean getIsSimple() {
		return this.getImpl().getIsSimple();
	}

	public void setIsSimple(Boolean isSimple) {
		this.getImpl().setIsSimple(isSimple);
	}

	public Expression getExpression() {
		return this.getImpl().getExpression();
	}

	public void setExpression(Expression expression) {
		this.getImpl().setExpression(expression);
	}

	public Boolean getIsFeature() {
		return this.getImpl().getIsFeature();
	}

	public void setIsFeature(Boolean isFeature) {
		this.getImpl().setIsFeature(isFeature);
	}

	public Boolean getIsDataValueUpdate() {
		return this.getImpl().getIsDataValueUpdate();
	}

	public void setIsDataValueUpdate(Boolean isDataValueUpdate) {
		this.getImpl().setIsDataValueUpdate(isDataValueUpdate);
	}

	public Boolean getIsCollectionConversion() {
		return this.getImpl().getIsCollectionConversion();
	}

	public void setIsCollectionConversion(Boolean isCollectionConversion) {
		this.getImpl().setIsCollectionConversion(isCollectionConversion);
	}

    public Boolean getIsBitStringConversion() {
        return this.getImpl().getIsBitStringConversion();
    }

    public void setIsBitStringConversion(Boolean isBitStringConversion) {
        this.getImpl().setIsBitStringConversion(isBitStringConversion);
    }

    public Boolean getIsRealConversion() {
        return this.getImpl().getIsRealConversion();
    }

    public void setIsRealConversion(Boolean isRealConversion) {
        this.getImpl().setIsRealConversion(isRealConversion);
    }

	/**
	 * An assignment expression is a simple assignment if the assignment
	 * operator is "=".
	 **/
	public boolean assignmentExpressionIsSimpleDerivation() {
		return this.getImpl().assignmentExpressionIsSimpleDerivation();
	}

	/**
	 * An assignment expression is an arithmetic assignment if its operator is a
	 * compound assignment operator for an arithmetic operation.
	 **/
	public boolean assignmentExpressionIsArithmeticDerivation() {
		return this.getImpl().assignmentExpressionIsArithmeticDerivation();
	}

	/**
	 * An assignment expression is a definition if it is a simple assignment and
	 * its left hand side is a local name for which there is no assignment
	 * before the expression.
	 **/
	public boolean assignmentExpressionIsDefinitionDerivation() {
		return this.getImpl().assignmentExpressionIsDefinitionDerivation();
	}

	/**
     * The left hand side of an assignment expression is a feature if it is a
     * feature left-hand side or a name left-hand side for a name that
     * disambiguates to a feature.
	 **/
	public boolean assignmentExpressionIsFeatureDerivation() {
		return this.getImpl().assignmentExpressionIsFeatureDerivation();
	}

	/**
	 * The left hand side of an assignment expression is indexed if it has an
	 * index.
	 **/
	public boolean assignmentExpressionIsIndexedDerivation() {
		return this.getImpl().assignmentExpressionIsIndexedDerivation();
	}

	/**
	 * An assignment expression is a data value update if its left hand side is
	 * an attribute of a data value held in a local name or parameter.
	 **/
	public boolean assignmentExpressionIsDataValueUpdateDerivation() {
		return this.getImpl().assignmentExpressionIsDataValueUpdateDerivation();
	}

    /**
     * The new assigned source for an assignment to a local name by the
     * assignment expression (including a data value update). If the assignment
     * is a definition, then the type is given by the right-side, otherwise the
     * type is the same as for the previous assigned source for the local name.
     * If the assignment expression does not require any conversions, then the
     * subtype of the assignment is the type of the right-hand side expression;
     * otherwise it is null. The multiplicity lower bound is 0 if the lower
     * bound of the right-hand side is 0 and otherwise 1, and the multiplicity
     * upper bound is 1 if the upper bound of the right-hand side is 1 and *
     * otherwise, except that: if the left-hand side is a data-value update, the
     * multiplicity is the same as for the previous assignment, and, if the
     * left-hand side is indexed (but not a data-value update), the multiplicity
     * is 0..*.
     **/
	public boolean assignmentExpressionAssignmentDerivation() {
		return this.getImpl().assignmentExpressionAssignmentDerivation();
	}

	/**
	 * If the left-hand side of an assignment expression is a feature, then the
	 * feature of the assignment is the referent of the left-hand side.
	 **/
	public boolean assignmentExpressionFeatureDerivation() {
		return this.getImpl().assignmentExpressionFeatureDerivation();
	}

	/**
	 * For a compound assignment, the effective expression is the left-hand side
	 * treated as a name expression, property access expression or sequence
	 * access expression, as appropriate for evaluation to obtain the original
	 * value to be updated.
	 **/
	public boolean assignmentExpressionExpressionDerivation() {
		return this.getImpl().assignmentExpressionExpressionDerivation();
	}

	/**
	 * A simple assignment expression has the same type as its right-hand side
	 * expression. A compound assignment expression has the same type as its
	 * left-hand side.
	 **/
	public boolean assignmentExpressionTypeDerivation() {
		return this.getImpl().assignmentExpressionTypeDerivation();
	}

	/**
	 * An assignment expression has the same multiplicity upper bound as its
	 * right-hand side expression.
	 **/
	public boolean assignmentExpressionUpperDerivation() {
		return this.getImpl().assignmentExpressionUpperDerivation();
	}

	/**
	 * A simple assignment expression has the same multiplicity lower bound as
	 * its right-hand side expression. A compound assignment expression has the
	 * same multiplicity as its left-hand side.
	 **/
	public boolean assignmentExpressionLowerDerivation() {
		return this.getImpl().assignmentExpressionLowerDerivation();
	}

	/**
	 * If the left-hand side of a simple assignment is not a new local name, and
	 * the right-hand side is not null, then either the left-hand side must be
	 * untyped or the right-hand side expression must have a type that conforms
	 * to the type of the left-hand side.
	 **/
	public boolean assignmentExpressionSimpleAssignmentTypeConformance() {
		return this.getImpl()
				.assignmentExpressionSimpleAssignmentTypeConformance();
	}

    /**
     * If the left-hand side of a simple assignment is not a local name and the
     * multiplicity lower bound of the left-hand side is greater than 0, then
     * the multiplicity lower bound of the right-hand side cannot be 0. If the
     * left-hand side is not a new local name and the multiplicity upper bound
     * of the left-hand side is less than or equal to 1, then the multiplicity
     * upper bound of the right-hand side cannot be greater than that of the
     * left-hand side.
     **/
	public boolean assignmentExpressionSimpleAssignmentMultiplicityConformance() {
		return this.getImpl()
				.assignmentExpressionSimpleAssignmentMultiplicityConformance();
	}

    /**
     * For a compound assignment, if the operator is an arithmetic operator,
     * then either the left-hand side and the right-hand side both have types
     * that conform to type Integer, the left-hand side has a type that conforms
     * to type Real and the right-hand side has a type that conforms to type
     * Integer or Real, or they both have types that conform to type String and
     * the operator is +. If the operator is a logical operator, then either the
     * left-hand side and the right-hand side both have types that conform to
     * type Boolean or Bit String or the left-hand side has a type that conforms
     * to type Bit String and the right-hand side has a type that conforms to
     * type Integer. If the operator is a shift operator, then the left-hand
     * side must have a type that conforms to type Bit String and the right-hand
     * side must have a type that conforms to type Integer.
     **/
	public boolean assignmentExpressionCompoundAssignmentTypeConformance() {
		return this.getImpl()
				.assignmentExpressionCompoundAssignmentTypeConformance();
	}

	/**
	 * For a compound assignment, both the left-hand and right-hand sides must
	 * have a multiplicity upper bound of 1.
	 **/
	public boolean assignmentExpressionCompoundAssignmentMultiplicityConformance() {
		return this
				.getImpl()
				.assignmentExpressionCompoundAssignmentMultiplicityConformance();
	}

	/**
	 * The assigned source of a name before the right-hand side expression of an
	 * assignment expression is the same as the assigned source before the
	 * assignment expression. The assigned source of a name before the left-hand
	 * side is the assigned source after the right-hand side expression.
	 **/
	public boolean assignmentExpressionAssignmentsBefore() {
		return this.getImpl().assignmentExpressionAssignmentsBefore();
	}

	/**
	 * An assignment requires collection conversion if the type of the
	 * right-hand side is a collection class and its multiplicity upper bound is
	 * 1, and the type of the left-hand side is not a collection class.
	 **/
	public boolean assignmentExpressionIsCollectionConversionDerivation() {
		return this.getImpl()
				.assignmentExpressionIsCollectionConversionDerivation();
	}

    /**
     * An assignment requires BitString conversion if the type of the left-hand
     * side is BitString and either the type of the right-hand side is Integer
     * or collection conversion is required and the type of the right-hand side
     * is a collection class whose sequence type is Integer.
     **/
    public boolean assignmentExpressionIsBitStringConversionDerivation() {
        return this.getImpl()
                .assignmentExpressionIsBitStringConversionDerivation();
    }

    /**
     * An assignment requires Real conversion if the type of the left-hand
     * side is Real and either the type of the right-hand side is Integer
     * or collection conversion is required and the type of the right-hand side
     * is a collection class whose sequence type is Integer.
     **/
    public boolean assignmentExpressionIsRealConversionDerivation() {
        return this.getImpl()
                .assignmentExpressionIsRealConversionDerivation();
    }

	/**
	 * If an assignment expression has a feature with a primary expression whose
	 * type is a data type, then the assignment expression must be a data value
	 * update.
	 **/
	public boolean assignmentExpressionDataValueUpdateLegality() {
		return this.getImpl().assignmentExpressionDataValueUpdateLegality();
	}

	/**
	 * The assignments after an assignment expression are the assignments after
	 * the left-hand side, updated by the assignment from the assignment
	 * statement, if any.
	 **/
	public Collection<AssignedSource> updateAssignments() {
		return this.getImpl().updateAssignments();
	}

    /**
     * If the left-hand side is not indexed and is not a feature reference,
     * then the assigned name is considered be known null if the condition
     * is true, or known non-null if the condition is false. The right-
     * hand side is then also checked for known nulls or non-nulls.
     */
    @Override
    public Collection<AssignedSource> adjustMultiplicity(
            Collection<AssignedSource> assignments, boolean condition) {
        return this.getImpl().adjustMultiplicity(assignments, condition);
    }
    
    /**
     * If the left-hand side is not indexed and is not a feature reference,
     * then the assigned name is considered to have the given subtype. The 
     * type of the right-hand side is then also adjusted as appropriate.
     */
    @Override
    public Collection<AssignedSource> adjustType(
            Collection<AssignedSource> assignments, ElementReference subtype) {
        return this.getImpl().adjustType(assignments, subtype);
    }
    
    /**
     * If an assignment expression is a simple assignment, then its declared type
     * is the declared type of the right-hand side expression. Otherwise it is
     * the type of the assignment expression.
     */
    @Override
    public ElementReference declaredType() {
        return this.getImpl().declaredType();
    }
    
    @Override
    public void _addExternalReferences(Collection<ExternalElementReference> references) {
        super._addExternalReferences(references);
        addExternalReferencesFor(references, this.getLeftHandSide());
        addExternalReferencesFor(references, this.getRightHandSide());
    }

    public void _deriveAll() {
		this.getAssignment();
		this.getFeature();
		this.getIsIndexed();
		this.getIsArithmetic();
		this.getIsDefinition();
		this.getIsSimple();
		this.getExpression();
		this.getIsFeature();
		this.getIsDataValueUpdate();
		this.getIsCollectionConversion();
		this.getIsBitStringConversion();
		this.getIsRealConversion();
		super._deriveAll();
		LeftHandSide leftHandSide = this.getLeftHandSide();
		if (leftHandSide != null) {
			leftHandSide.deriveAll();
		}
		Expression rightHandSide = this.getRightHandSide();
		if (rightHandSide != null) {
			rightHandSide.deriveAll();
		}
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.assignmentExpressionIsSimpleDerivation()) {
			violations.add(new ConstraintViolation(
					"assignmentExpressionIsSimpleDerivation", this));
		}
		if (!this.assignmentExpressionIsArithmeticDerivation()) {
			violations.add(new ConstraintViolation(
					"assignmentExpressionIsArithmeticDerivation", this));
		}
		if (!this.assignmentExpressionIsDefinitionDerivation()) {
			violations.add(new ConstraintViolation(
					"assignmentExpressionIsDefinitionDerivation", this));
		}
		if (!this.assignmentExpressionIsFeatureDerivation()) {
			violations.add(new ConstraintViolation(
					"assignmentExpressionIsFeatureDerivation", this));
		}
		if (!this.assignmentExpressionIsIndexedDerivation()) {
			violations.add(new ConstraintViolation(
					"assignmentExpressionIsIndexedDerivation", this));
		}
		if (!this.assignmentExpressionIsDataValueUpdateDerivation()) {
			violations.add(new ConstraintViolation(
					"assignmentExpressionIsDataValueUpdateDerivation", this));
		}
		if (!this.assignmentExpressionAssignmentDerivation()) {
			violations.add(new ConstraintViolation(
					"assignmentExpressionAssignmentDerivation", this));
		}
		if (!this.assignmentExpressionFeatureDerivation()) {
			violations.add(new ConstraintViolation(
					"assignmentExpressionFeatureDerivation", this));
		}
		if (!this.assignmentExpressionExpressionDerivation()) {
			violations.add(new ConstraintViolation(
					"assignmentExpressionExpressionDerivation", this));
		}
		if (!this.assignmentExpressionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"assignmentExpressionTypeDerivation", this));
		}
		if (!this.assignmentExpressionUpperDerivation()) {
			violations.add(new ConstraintViolation(
					"assignmentExpressionUpperDerivation", this));
		}
		if (!this.assignmentExpressionLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"assignmentExpressionLowerDerivation", this));
		}
		if (!this.assignmentExpressionSimpleAssignmentTypeConformance()) {
			violations
					.add(new ConstraintViolation(
							"assignmentExpressionSimpleAssignmentTypeConformance",
							this));
		}
		if (!this.assignmentExpressionSimpleAssignmentMultiplicityConformance()) {
			violations
					.add(new ConstraintViolation(
							"assignmentExpressionSimpleAssignmentMultiplicityConformance",
							this));
		}
		if (!this.assignmentExpressionCompoundAssignmentTypeConformance()) {
			violations.add(new ConstraintViolation(
					"assignmentExpressionCompoundAssignmentTypeConformance",
					this));
		}
		if (!this
				.assignmentExpressionCompoundAssignmentMultiplicityConformance()) {
			violations
					.add(new ConstraintViolation(
							"assignmentExpressionCompoundAssignmentMultiplicityConformance",
							this));
		}
		if (!this.assignmentExpressionAssignmentsBefore()) {
			violations.add(new ConstraintViolation(
					"assignmentExpressionAssignmentsBefore", this));
		}
		if (!this.assignmentExpressionIsCollectionConversionDerivation()) {
			violations.add(new ConstraintViolation(
					"assignmentExpressionIsCollectionConversionDerivation",
					this));
		}
		if (!this.assignmentExpressionIsBitStringConversionDerivation()) {
			violations
					.add(new ConstraintViolation(
							"assignmentExpressionIsBitStringConversionDerivation",
							this));
		}
		if (!this.assignmentExpressionDataValueUpdateLegality()) {
			violations.add(new ConstraintViolation(
					"assignmentExpressionDataValueUpdateLegality", this));
		}
		LeftHandSide leftHandSide = this.getLeftHandSide();
		if (leftHandSide != null) {
			leftHandSide.checkConstraints(violations);
		}
		Expression rightHandSide = this.getRightHandSide();
		if (rightHandSide != null) {
			rightHandSide.checkConstraints(violations);
		}
	}

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		s.append(" operator:");
		s.append(this.getOperator());
		if (includeDerived) {
			s.append(" /isIndexed:");
			s.append(this.getIsIndexed());
		}
		if (includeDerived) {
			s.append(" /isArithmetic:");
			s.append(this.getIsArithmetic());
		}
		if (includeDerived) {
			s.append(" /isDefinition:");
			s.append(this.getIsDefinition());
		}
		if (includeDerived) {
			s.append(" /isSimple:");
			s.append(this.getIsSimple());
		}
		if (includeDerived) {
			s.append(" /isFeature:");
			s.append(this.getIsFeature());
		}
		if (includeDerived) {
			s.append(" /isDataValueUpdate:");
			s.append(this.getIsDataValueUpdate());
		}
		if (includeDerived) {
			s.append(" /isCollectionConversion:");
			s.append(this.getIsCollectionConversion());
		}
        if (includeDerived) {
            s.append(" /isBitStringConversion:");
            s.append(this.getIsBitStringConversion());
        }
        if (includeDerived) {
            s.append(" /isRealConversion:");
            s.append(this.getIsRealConversion());
        }
		return s.toString();
	}

	public void print() {
		this.print("", false);
	}

	public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	public void print(String prefix, boolean includeDerived) {
		super.print(prefix, includeDerived);
		LeftHandSide leftHandSide = this.getLeftHandSide();
		if (leftHandSide != null) {
			System.out.println(prefix + " leftHandSide:");
			leftHandSide.print(prefix + "  ", includeDerived);
		}
		Expression rightHandSide = this.getRightHandSide();
		if (rightHandSide != null) {
			System.out.println(prefix + " rightHandSide:");
			rightHandSide.print(prefix + "  ", includeDerived);
		}
		if (includeDerived) {
			AssignedSource assignment = this.getAssignment();
			if (assignment != null) {
				System.out.println(prefix + " /assignment:"
						+ assignment.toString(includeDerived));
			}
		}
		if (includeDerived) {
			ElementReference feature = this.getFeature();
			if (feature != null) {
				System.out.println(prefix + " /feature:"
						+ feature.toString(includeDerived));
			}
		}
		if (includeDerived) {
			Expression expression = this.getExpression();
			if (expression != null) {
				System.out.println(prefix + " /expression:"
						+ expression.toString(includeDerived));
			}
		}
	}
} // AssignmentExpression
