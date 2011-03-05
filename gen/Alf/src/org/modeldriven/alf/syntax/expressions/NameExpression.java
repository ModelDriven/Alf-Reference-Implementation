
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.omg.uml.*;

import java.util.ArrayList;

import org.modeldriven.alf.syntax.expressions.impl.NameExpressionImpl;

/**
 * An expression that comprises a name reference.
 **/

public class NameExpression extends Expression {

	private ElementReference enumerationLiteral = null; // DERIVED
	private AssignedSource assignment = null; // DERIVED
	private PropertyAccessExpression propertyAccess = null; // DERIVED
	private QualifiedName name = null;

	public NameExpression() {
		this.impl = new NameExpressionImpl(this);
	}

	public NameExpressionImpl getImpl() {
		return (NameExpressionImpl) this.impl;
	}

	public ElementReference getEnumerationLiteral() {
		if (this.enumerationLiteral == null) {
			this.setEnumerationLiteral(this.getImpl()
					.deriveEnumerationLiteral());
		}
		return this.enumerationLiteral;
	}

	public void setEnumerationLiteral(ElementReference enumerationLiteral) {
		this.enumerationLiteral = enumerationLiteral;
	}

	public AssignedSource getAssignment() {
		if (this.assignment == null) {
			this.setAssignment(this.getImpl().deriveAssignment());
		}
		return this.assignment;
	}

	public void setAssignment(AssignedSource assignment) {
		this.assignment = assignment;
	}

	public PropertyAccessExpression getPropertyAccess() {
		if (this.propertyAccess == null) {
			this.setPropertyAccess(this.getImpl().derivePropertyAccess());
		}
		return this.propertyAccess;
	}

	public void setPropertyAccess(PropertyAccessExpression propertyAccess) {
		this.propertyAccess = propertyAccess;
	}

	public QualifiedName getName() {
		return this.name;
	}

	public void setName(QualifiedName name) {
		this.name = name;
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

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		ElementReference enumerationLiteral = this.getEnumerationLiteral();
		if (enumerationLiteral != null) {
			System.out.println(prefix + " /enumerationLiteral:"
					+ enumerationLiteral);
		}
		AssignedSource assignment = this.getAssignment();
		if (assignment != null) {
			System.out.println(prefix + " /assignment:" + assignment);
		}
		PropertyAccessExpression propertyAccess = this.getPropertyAccess();
		if (propertyAccess != null) {
			System.out.println(prefix + " /propertyAccess:" + propertyAccess);
		}
		QualifiedName name = this.getName();
		if (name != null) {
			System.out.println(prefix + " name:");
			name.print(prefix + "  ");
		}
	}
} // NameExpression
