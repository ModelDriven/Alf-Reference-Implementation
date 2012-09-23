
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.parser.AlfParser;
import org.modeldriven.alf.parser.Token;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.expressions.impl.SequenceOperationExpressionImpl;
import org.modeldriven.uml.Element;
import org.modeldriven.uml.Profile;
import org.modeldriven.uml.Stereotype;

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
	 * parameter type must have the same type as the primary expression.
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
	 * of a sequence operation expression is a collection class.
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
	 * The assignments after a sequence operation expression include those made
	 * in the primary expression and those made in the tuple and, for an
	 * "in place" operation (one whose first parameter is inout), that made by
	 * the sequence operation expression itself.
	 **/
	public Collection<AssignedSource> updateAssignments() {
		return this.getImpl().updateAssignments();
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
