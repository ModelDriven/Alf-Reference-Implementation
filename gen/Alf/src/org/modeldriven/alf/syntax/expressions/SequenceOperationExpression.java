
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.parser.AlfParser;
import org.modeldriven.alf.parser.Token;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.Profile;
import org.modeldriven.alf.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

import org.modeldriven.alf.syntax.expressions.impl.SequenceOperationExpressionImpl;

/**
 * An expression used to invoke a behavior as if it was an operation on a target
 * sequence as a whole.
 **/

public class SequenceOperationExpression extends InvocationExpression {

	public SequenceOperationExpression() {
		this.impl = new SequenceOperationExpressionImpl(this);
	}

	public SequenceOperationExpression(AlfParser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public SequenceOperationExpression(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public SequenceOperationExpressionImpl getImpl() {
		return (SequenceOperationExpressionImpl) this.impl;
	}

	public ExtentOrExpression getPrimary() {
		return this.getImpl().getPrimary();
	}

	public void setPrimary(ExtentOrExpression primary) {
		this.getImpl().setPrimary(primary);
	}

	public QualifiedName getOperation() {
		return this.getImpl().getOperation();
	}

	public void setOperation(QualifiedName operation) {
		this.getImpl().setOperation(operation);
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

	public LeftHandSide getLeftHandSide() {
		return this.getImpl().getLeftHandSide();
	}

	public void setLeftHandSide(LeftHandSide leftHandSide) {
		this.getImpl().setLeftHandSide(leftHandSide);
	}

	/**
	 * The referent for a sequence operation expression is the behavior named by
	 * the operation for the expression.
	 **/
	public boolean sequenceOperationExpressionReferentDerivation() {
		return this.getImpl().sequenceOperationExpressionReferentDerivation();
	}

	/**
	 * There is no feature for a sequence operation expression.
	 **/
	public boolean sequenceOperationExpressionFeatureDerivation() {
		return this.getImpl().sequenceOperationExpressionFeatureDerivation();
	}

	/**
	 * There must be a single behavior that is a resolution of the operation
	 * qualified name of a sequence operation expression with a least one
	 * parameter, whose first parameter has direction in or inout, has
	 * multiplicity [0..*] and to which the target primary expression is
	 * assignable.
	 **/
	public boolean sequenceOperationExpressionOperationReferent() {
		return this.getImpl().sequenceOperationExpressionOperationReferent();
	}

	/**
	 * If the first parameter of the referent has direction inout, then the
	 * parameter type must have the same type as the primary expression, the
	 * primary expression must have the form of a left-hand side and, if the
	 * equivalent left-hand side is for a local name, that name must already
	 * exist.
	 **/
	public boolean sequenceOperationExpressionTargetCompatibility() {
		return this.getImpl().sequenceOperationExpressionTargetCompatibility();
	}

	/**
	 * The type of an input argument expression of a sequence operation
	 * parameter must be assignable to its corresponding parameter. The type of
	 * an output parameter must be assignable to its corresponding argument
	 * expression. (Note that this implies that the type of an argument
	 * expression for an inout parameter must be the same as the type of that
	 * parameter.)
	 **/
	public boolean sequenceOperationExpressionArgumentCompatibility() {
		return this.getImpl()
				.sequenceOperationExpressionArgumentCompatibility();
	}

	/**
	 * The assignments before the primary expression of a sequence operation
	 * expression are the same as the assignments before the sequence operation
	 * expression.
	 **/
	public boolean sequenceOperationExpressionAssignmentsBefore() {
		return this.getImpl().sequenceOperationExpressionAssignmentsBefore();
	}

	/**
	 * Collection conversion is required if the type of the primary expression
	 * of a sequence operation expression is a collection class and the
	 * multiplicity upper bound of the primary expression is 1.
	 **/
	public boolean sequenceOperationExpressionIsCollectionConversionDerivation() {
		return this.getImpl()
				.sequenceOperationExpressionIsCollectionConversionDerivation();
	}

	/**
	 * BitString conversion is required if type of the first parameter of the
	 * referent of a sequence operation expression is BitString and either the
	 * type of its primary expression is Integer or collection conversion is
	 * required and the type of its primary expression is a collection class
	 * whose argument type is Integer.
	 **/
	public boolean sequenceOperationExpressionIsBitStringConversionDerivation() {
		return this.getImpl()
				.sequenceOperationExpressionIsBitStringConversionDerivation();
	}

	/**
	 * A local name that is assigned in the primary expression of a sequence
	 * operation expression may not be assigned in any expression in the tuple
	 * of the sequence operation expression.
	 **/
	public boolean sequenceOperationExpressionAssignmentsAfter() {
		return this.getImpl().sequenceOperationExpressionAssignmentsAfter();
	}

	/**
	 * If the operation of a sequence operation expression has a first parameter
	 * whose direction is inout, then the effective left-hand side for the
	 * expression is constructed as follows: If the primary is a name
	 * expression, then the left-hand side is a name left-hand side with the
	 * name from the name expression as its target. If the primary is a property
	 * access expression, then the left-hand side is a feature left hand side
	 * with the feature reference from the property access expression as its
	 * feature. If the primary is a sequence access expression whose primary is
	 * a name expression or a property access expression, then the left-hand
	 * side is constructed from the primary of the sequence access expression as
	 * given previously and the index of the sequence access expression becomes
	 * the index of the left-hand side.
	 **/
	public boolean sequenceOperationExpressionLeftHandSideDerivation() {
		return this.getImpl()
				.sequenceOperationExpressionLeftHandSideDerivation();
	}

	/**
	 * The assignments after a sequence operation expression include those made
	 * in the primary expression and those made in the tuple and, for an
	 * "in place" operation (one whose first parameter is inout), that made by
	 * the sequence operation expression itself.
	 **/
	public Collection<AssignedSource> updateAssignments() {
		return this.getImpl().updateAssignments();
	}

	/**
	 * Returns the list of parameter elements from the superclass operation,
	 * with the first parameter removed (since the argument for the first
	 * parameter is given by the primary expression of a sequence operation
	 * expression, not in its tuple).
	 **/
	public List<ElementReference> parameterElements() {
		return this.getImpl().parameterElements();
	}

	public void _deriveAll() {
		this.getIsCollectionConversion();
		this.getIsBitStringConversion();
		this.getLeftHandSide();
		super._deriveAll();
		ExtentOrExpression primary = this.getPrimary();
		if (primary != null) {
			primary.deriveAll();
		}
		QualifiedName operation = this.getOperation();
		if (operation != null) {
			operation.deriveAll();
		}
		LeftHandSide leftHandSide = this.getLeftHandSide();
		if (leftHandSide != null) {
			leftHandSide.deriveAll();
		}
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.sequenceOperationExpressionReferentDerivation()) {
			violations.add(new ConstraintViolation(
					"sequenceOperationExpressionReferentDerivation", this));
		}
		if (!this.sequenceOperationExpressionFeatureDerivation()) {
			violations.add(new ConstraintViolation(
					"sequenceOperationExpressionFeatureDerivation", this));
		}
		if (!this.sequenceOperationExpressionOperationReferent()) {
			violations.add(new ConstraintViolation(
					"sequenceOperationExpressionOperationReferent", this));
		}
		if (!this.sequenceOperationExpressionTargetCompatibility()) {
			violations.add(new ConstraintViolation(
					"sequenceOperationExpressionTargetCompatibility", this));
		}
		if (!this.sequenceOperationExpressionArgumentCompatibility()) {
			violations.add(new ConstraintViolation(
					"sequenceOperationExpressionArgumentCompatibility", this));
		}
		if (!this.sequenceOperationExpressionAssignmentsBefore()) {
			violations.add(new ConstraintViolation(
					"sequenceOperationExpressionAssignmentsBefore", this));
		}
		if (!this.sequenceOperationExpressionIsCollectionConversionDerivation()) {
			violations
					.add(new ConstraintViolation(
							"sequenceOperationExpressionIsCollectionConversionDerivation",
							this));
		}
		if (!this.sequenceOperationExpressionIsBitStringConversionDerivation()) {
			violations
					.add(new ConstraintViolation(
							"sequenceOperationExpressionIsBitStringConversionDerivation",
							this));
		}
		if (!this.sequenceOperationExpressionAssignmentsAfter()) {
			violations.add(new ConstraintViolation(
					"sequenceOperationExpressionAssignmentsAfter", this));
		}
		if (!this.sequenceOperationExpressionLeftHandSideDerivation()) {
			violations.add(new ConstraintViolation(
					"sequenceOperationExpressionLeftHandSideDerivation", this));
		}
		ExtentOrExpression primary = this.getPrimary();
		if (primary != null) {
			primary.checkConstraints(violations);
		}
		QualifiedName operation = this.getOperation();
		if (operation != null) {
			operation.checkConstraints(violations);
		}
		LeftHandSide leftHandSide = this.getLeftHandSide();
		if (leftHandSide != null) {
			leftHandSide.checkConstraints(violations);
		}
	}

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		if (includeDerived) {
			s.append(" /isCollectionConversion:");
			s.append(this.getIsCollectionConversion());
		}
		if (includeDerived) {
			s.append(" /isBitStringConversion:");
			s.append(this.getIsBitStringConversion());
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
		ExtentOrExpression primary = this.getPrimary();
		if (primary != null) {
			System.out.println(prefix + " primary:");
			primary.print(prefix + "  ", includeDerived);
		}
		QualifiedName operation = this.getOperation();
		if (operation != null) {
			System.out.println(prefix + " operation:");
			operation.print(prefix + "  ", includeDerived);
		}
		if (includeDerived) {
			LeftHandSide leftHandSide = this.getLeftHandSide();
			if (leftHandSide != null) {
				System.out.println(prefix + " /leftHandSide:");
				leftHandSide.print(prefix + "  ", includeDerived);
			}
		}
	}
} // SequenceOperationExpression
