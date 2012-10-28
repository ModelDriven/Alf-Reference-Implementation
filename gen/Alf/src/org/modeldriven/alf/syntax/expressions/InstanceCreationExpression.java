
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
	 * The referent of an instance creation expression is normally the
	 * constructor operation, class or data type to which the constructor name
	 * resolves. However, if the referent is an operation whose class is
	 * abstract or is a class that is itself abstract, and there is an
	 * associated Impl class constructor, then the referent is the Impl class
	 * constructor.
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
	 * If the expression is constructorless, then its tuple must be empty and
	 * the referent class must not have any owned operations that are
	 * constructors.
	 **/
	public boolean instanceCreationExpressionConstructorlessLegality() {
		return this.getImpl()
				.instanceCreationExpressionConstructorlessLegality();
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
	 * If the referent of an instance creation expression is an operation, then
	 * the class of that operation must not be abstract. Otherwise, the referent
	 * is a class or data type, which must not be abstract.
	 **/
	public boolean instanceCreationExpressionReferent() {
		return this.getImpl().instanceCreationExpressionReferent();
	}

	/**
	 * Returns the parameters of a constructor operation or the attributes of a
	 * data type, or an empty set for a constructorless instance creation.
	 **/
	public List<ElementReference> parameterElements() {
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
		if (!this.instanceCreationExpressionConstructorlessLegality()) {
			violations.add(new ConstraintViolation(
					"instanceCreationExpressionConstructorlessLegality", this));
		}
		if (!this.instanceCreationExpressionDataTypeCompatibility()) {
			violations.add(new ConstraintViolation(
					"instanceCreationExpressionDataTypeCompatibility", this));
		}
		if (!this.instanceCreationExpressionReferent()) {
			violations.add(new ConstraintViolation(
					"instanceCreationExpressionReferent", this));
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
