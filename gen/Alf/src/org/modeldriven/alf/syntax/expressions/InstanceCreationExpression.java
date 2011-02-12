
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

import java.util.ArrayList;

import org.modeldriven.alf.syntax.expressions.impl.InstanceCreationExpressionImpl;

/**
 * An expression used to create a new instance of a class or data type.
 **/

public class InstanceCreationExpression extends InvocationExpression {

	private Boolean isConstructorless = null; // DERIVED
	private Boolean isObjectCreation = null; // DERIVED
	private QualifiedName constructor = null;

	public InstanceCreationExpression() {
		this.impl = new InstanceCreationExpressionImpl(this);
	}

	public InstanceCreationExpressionImpl getImpl() {
		return (InstanceCreationExpressionImpl) this.impl;
	}

	public Boolean getIsConstructorless() {
		if (this.isConstructorless == null) {
			this.isConstructorless = this.getImpl().deriveIsConstructorless();
		}
		return this.isConstructorless;
	}

	public Boolean getIsObjectCreation() {
		if (this.isObjectCreation == null) {
			this.isObjectCreation = this.getImpl().deriveIsObjectCreation();
		}
		return this.isObjectCreation;
	}

	public QualifiedName getConstructor() {
		return this.constructor;
	}

	public void setConstructor(QualifiedName constructor) {
		this.constructor = constructor;
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
	public ArrayList<ElementReference> parameterElements() {
		return this.getImpl().parameterElements();
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		Boolean isConstructorless = this.getIsConstructorless();
		if (isConstructorless != null) {
			s.append(" /isConstructorless:");
			s.append(isConstructorless);
		}
		Boolean isObjectCreation = this.getIsObjectCreation();
		if (isObjectCreation != null) {
			s.append(" /isObjectCreation:");
			s.append(isObjectCreation);
		}
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		QualifiedName constructor = this.getConstructor();
		if (constructor != null) {
			constructor.print(prefix + " ");
		}
	}
} // InstanceCreationExpression
