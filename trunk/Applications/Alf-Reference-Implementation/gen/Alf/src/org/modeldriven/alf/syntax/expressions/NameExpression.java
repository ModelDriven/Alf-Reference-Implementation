
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.parser.Parser;
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

import org.modeldriven.alf.syntax.expressions.impl.NameExpressionImpl;

/**
 * An expression that comprises a name reference.
 **/

public class NameExpression extends Expression {

	public NameExpression() {
		this.impl = new NameExpressionImpl(this);
	}

	public NameExpression(Parser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public NameExpression(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public NameExpressionImpl getImpl() {
		return (NameExpressionImpl) this.impl;
	}

	public ElementReference getEnumerationLiteral() {
		return this.getImpl().getEnumerationLiteral();
	}

	public void setEnumerationLiteral(ElementReference enumerationLiteral) {
		this.getImpl().setEnumerationLiteral(enumerationLiteral);
	}

	public AssignedSource getAssignment() {
		return this.getImpl().getAssignment();
	}

	public void setAssignment(AssignedSource assignment) {
		this.getImpl().setAssignment(assignment);
	}

	public PropertyAccessExpression getPropertyAccess() {
		return this.getImpl().getPropertyAccess();
	}

	public void setPropertyAccess(PropertyAccessExpression propertyAccess) {
		this.getImpl().setPropertyAccess(propertyAccess);
	}

	public QualifiedName getName() {
		return this.getImpl().getName();
	}

	public void setName(QualifiedName name) {
		this.getImpl().setName(name);
	}

	/**
	 * If the name in a name expression is a local or parameter name, then its
	 * assignment is its assigned source before the expression.
	 **/
	public boolean nameExpressionAssignmentDerivation() {
		return this.getImpl().nameExpressionAssignmentDerivation();
	}

	/**
	 * If the name in a name expression resolves to an enumeration literal name,
	 * then that is the enumeration literal for the expression.
	 **/
	public boolean nameExpressionEnumerationLiteralDerivation() {
		return this.getImpl().nameExpressionEnumerationLiteralDerivation();
	}

	/**
	 * If the name in a name expression disambiguates to a feature reference,
	 * then the equivalent property access expression has the disambiguation of
	 * the name as its feature. The assignments before the property access
	 * expression are the same as those before the name expression.
	 **/
	public boolean nameExpressionPropertyAccessDerivation() {
		return this.getImpl().nameExpressionPropertyAccessDerivation();
	}

	/**
	 * The type of a name expression is determined by its name. If the name is a
	 * local name or parameter with an assignment, then the type of the name
	 * expression is the type of that assignment. If the name is an enumeration
	 * literal, then the type of the name expression is the corresponding
	 * enumeration. If the name disambiguates to a feature reference, then the
	 * type of the name expression is the type of the equivalent property access
	 * expression.
	 **/
	public boolean nameExpressionTypeDerivation() {
		return this.getImpl().nameExpressionTypeDerivation();
	}

	/**
	 * The multiplicity upper bound of a name expression is determined by its
	 * name.
	 **/
	public boolean nameExpressionUpperDerivation() {
		return this.getImpl().nameExpressionUpperDerivation();
	}

	/**
	 * The multiplicity lower bound of a name expression is determined by its
	 * name.
	 **/
	public boolean nameExpressionLowerDerivation() {
		return this.getImpl().nameExpressionLowerDerivation();
	}

	/**
	 * If the name referenced by this expression is not a disambiguated feature
	 * reference or a local or parameter name, then it must resolve to exactly
	 * one enumeration literal.
	 **/
	public boolean nameExpressionResolution() {
		return this.getImpl().nameExpressionResolution();
	}

	/**
	 * If propertyAccess is not empty (i.e., if the referenced name
	 * disambiguates to a feature reference), then return the assignments after
	 * the propertyAccess expression. Otherwise, return the result of the
	 * superclass updateAssignments operation.
	 **/
	public Collection<AssignedSource> updateAssignments() {
		return this.getImpl().updateAssignments();
	}

	public void _deriveAll() {
		this.getEnumerationLiteral();
		this.getAssignment();
		this.getPropertyAccess();
		super._deriveAll();
		PropertyAccessExpression propertyAccess = this.getPropertyAccess();
		if (propertyAccess != null) {
			propertyAccess.deriveAll();
		}
		QualifiedName name = this.getName();
		if (name != null) {
			name.deriveAll();
		}
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.nameExpressionAssignmentDerivation()) {
			violations.add(new ConstraintViolation(
					"nameExpressionAssignmentDerivation", this));
		}
		if (!this.nameExpressionEnumerationLiteralDerivation()) {
			violations.add(new ConstraintViolation(
					"nameExpressionEnumerationLiteralDerivation", this));
		}
		if (!this.nameExpressionPropertyAccessDerivation()) {
			violations.add(new ConstraintViolation(
					"nameExpressionPropertyAccessDerivation", this));
		}
		if (!this.nameExpressionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"nameExpressionTypeDerivation", this));
		}
		if (!this.nameExpressionUpperDerivation()) {
			violations.add(new ConstraintViolation(
					"nameExpressionUpperDerivation", this));
		}
		if (!this.nameExpressionLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"nameExpressionLowerDerivation", this));
		}
		if (!this.nameExpressionResolution()) {
			violations.add(new ConstraintViolation("nameExpressionResolution",
					this));
		}
		PropertyAccessExpression propertyAccess = this.getPropertyAccess();
		if (propertyAccess != null) {
			propertyAccess.checkConstraints(violations);
		}
		QualifiedName name = this.getName();
		if (name != null) {
			name.checkConstraints(violations);
		}
	}

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
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
		if (includeDerived) {
			ElementReference enumerationLiteral = this.getEnumerationLiteral();
			if (enumerationLiteral != null) {
				System.out.println(prefix + " /enumerationLiteral:"
						+ enumerationLiteral.toString(includeDerived));
			}
		}
		if (includeDerived) {
			AssignedSource assignment = this.getAssignment();
			if (assignment != null) {
				System.out.println(prefix + " /assignment:"
						+ assignment.toString(includeDerived));
			}
		}
		if (includeDerived) {
			PropertyAccessExpression propertyAccess = this.getPropertyAccess();
			if (propertyAccess != null) {
				System.out.println(prefix + " /propertyAccess:");
				propertyAccess.print(prefix + "  ", includeDerived);
			}
		}
		QualifiedName name = this.getName();
		if (name != null) {
			System.out.println(prefix + " name:");
			name.print(prefix + "  ", includeDerived);
		}
	}
} // NameExpression
