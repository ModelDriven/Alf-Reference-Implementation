
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

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

import org.modeldriven.alf.syntax.expressions.impl.InstanceCreationExpressionImpl;

/**
 * An expression used to create a new instance of a class or data type.
 **/

public class InstanceCreationExpression extends InvocationExpression {

	public InstanceCreationExpression() {
		this.impl = new InstanceCreationExpressionImpl(this);
	}

	public InstanceCreationExpression(AlfParser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public InstanceCreationExpression(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public InstanceCreationExpressionImpl getImpl() {
		return (InstanceCreationExpressionImpl) this.impl;
	}

	public Boolean getIsConstructorless() {
		return this.getImpl().getIsConstructorless();
	}

	public void setIsConstructorless(Boolean isConstructorless) {
		this.getImpl().setIsConstructorless(isConstructorless);
	}

	public Boolean getIsObjectCreation() {
		return this.getImpl().getIsObjectCreation();
	}

	public void setIsObjectCreation(Boolean isObjectCreation) {
		this.getImpl().setIsObjectCreation(isObjectCreation);
	}

	public QualifiedName getConstructor() {
		return this.getImpl().getConstructor();
	}

	public void setConstructor(QualifiedName constructor) {
		this.getImpl().setConstructor(constructor);
	}

	/**
	 * An instance creation expression is an object creation if its referent is
	 * not a data type.
	 **/
	public boolean instanceCreationExpressionIsObjectCreationDerivation() {
		return this.getImpl()
				.instanceCreationExpressionIsObjectCreationDerivation();
	}

	/**
	 * An instance creation expression is constructorless if its referent is a
	 * class.
	 **/
	public boolean instanceCreationExpressionIsConstructorlessDerivation() {
		return this.getImpl()
				.instanceCreationExpressionIsConstructorlessDerivation();
	}

	/**
	 * The referent of an instance creation expression is the constructor
	 * operation, class or data type to which the constructor name resolves.
	 **/
	public boolean instanceCreationExpressionReferentDerivation() {
		return this.getImpl().instanceCreationExpressionReferentDerivation();
	}

	/**
	 * There is no feature for an instance creation expression.
	 **/
	public boolean instanceCreationExpressionFeatureDerivation() {
		return this.getImpl().instanceCreationExpressionFeatureDerivation();
	}

	/**
	 * The constructor name must resolve to a constructor operation (that is
	 * compatible with the tuple argument expressions), a class or a data type,
	 * but not both a class and a data type.
	 **/
	public boolean instanceCreationExpressionConstructor() {
		return this.getImpl().instanceCreationExpressionConstructor();
	}

	/**
	 * If the expression is constructorless, then its tuple must be empty.
	 **/
	public boolean instanceCreationExpressionTuple() {
		return this.getImpl().instanceCreationExpressionTuple();
	}

	/**
	 * If an instance creation expression is a data value creation (not an
	 * object creation), then the tuple argument expressions are matched with
	 * the attributes of the named type.
	 **/
	public boolean instanceCreationExpressionDataTypeCompatibility() {
		return this.getImpl().instanceCreationExpressionDataTypeCompatibility();
	}

	/**
	 * Returns the parameters of a constructor operation or the attributes of a
	 * data type, or an empty set for a constructorless instance creation.
	 **/
	public Collection<ElementReference> parameterElements() {
		return this.getImpl().parameterElements();
	}

	public void _deriveAll() {
		this.getIsConstructorless();
		this.getIsObjectCreation();
		super._deriveAll();
		QualifiedName constructor = this.getConstructor();
		if (constructor != null) {
			constructor.deriveAll();
		}
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.instanceCreationExpressionIsObjectCreationDerivation()) {
			violations.add(new ConstraintViolation(
					"instanceCreationExpressionIsObjectCreationDerivation",
					this));
		}
		if (!this.instanceCreationExpressionIsConstructorlessDerivation()) {
			violations.add(new ConstraintViolation(
					"instanceCreationExpressionIsConstructorlessDerivation",
					this));
		}
		if (!this.instanceCreationExpressionReferentDerivation()) {
			violations.add(new ConstraintViolation(
					"instanceCreationExpressionReferentDerivation", this));
		}
		if (!this.instanceCreationExpressionFeatureDerivation()) {
			violations.add(new ConstraintViolation(
					"instanceCreationExpressionFeatureDerivation", this));
		}
		if (!this.instanceCreationExpressionConstructor()) {
			violations.add(new ConstraintViolation(
					"instanceCreationExpressionConstructor", this));
		}
		if (!this.instanceCreationExpressionTuple()) {
			violations.add(new ConstraintViolation(
					"instanceCreationExpressionTuple", this));
		}
		if (!this.instanceCreationExpressionDataTypeCompatibility()) {
			violations.add(new ConstraintViolation(
					"instanceCreationExpressionDataTypeCompatibility", this));
		}
		QualifiedName constructor = this.getConstructor();
		if (constructor != null) {
			constructor.checkConstraints(violations);
		}
	}

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		if (includeDerived) {
			s.append(" /isConstructorless:");
			s.append(this.getIsConstructorless());
		}
		if (includeDerived) {
			s.append(" /isObjectCreation:");
			s.append(this.getIsObjectCreation());
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
		QualifiedName constructor = this.getConstructor();
		if (constructor != null) {
			System.out.println(prefix + " constructor:");
			constructor.print(prefix + "  ", includeDerived);
		}
	}
} // InstanceCreationExpression
