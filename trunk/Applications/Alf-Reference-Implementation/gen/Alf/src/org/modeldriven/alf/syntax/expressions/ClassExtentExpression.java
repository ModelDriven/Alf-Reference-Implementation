
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

import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.Profile;
import org.modeldriven.alf.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.expressions.impl.ClassExtentExpressionImpl;

/**
 * An expression used to obtain the objects in the extent of a class.
 **/

public class ClassExtentExpression extends Expression {

	public ClassExtentExpression() {
		this.impl = new ClassExtentExpressionImpl(this);
	}

	public ClassExtentExpressionImpl getImpl() {
		return (ClassExtentExpressionImpl) this.impl;
	}

	public QualifiedName getClassName() {
		return this.getImpl().getClassName();
	}

	public void setClassName(QualifiedName className) {
		this.getImpl().setClassName(className);
	}

	/**
	 * The type of a class extent expression is the given class.
	 **/
	public boolean classExtentExpressionTypeDerivation() {
		return this.getImpl().classExtentExpressionTypeDerivation();
	}

	/**
	 * The multiplicity upper bound of a class expression is *.
	 **/
	public boolean classExtentExpressionUpperDerivation() {
		return this.getImpl().classExtentExpressionUpperDerivation();
	}

	/**
	 * The multiplicity lower bound of a class extent expression is 0.
	 **/
	public boolean classExtentExpressionLowerDerivation() {
		return this.getImpl().classExtentExpressionLowerDerivation();
	}

	/**
	 * The given type name must resolve to a non-template class.
	 **/
	public boolean classExtentExpressionExtentType() {
		return this.getImpl().classExtentExpressionExtentType();
	}

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.classExtentExpressionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"classExtentExpressionTypeDerivation", this));
		}
		if (!this.classExtentExpressionUpperDerivation()) {
			violations.add(new ConstraintViolation(
					"classExtentExpressionUpperDerivation", this));
		}
		if (!this.classExtentExpressionLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"classExtentExpressionLowerDerivation", this));
		}
		if (!this.classExtentExpressionExtentType()) {
			violations.add(new ConstraintViolation(
					"classExtentExpressionExtentType", this));
		}
		QualifiedName className = this.getClassName();
		if (className != null) {
			className.checkConstraints(violations);
		}
	}

	public String toString() {
		return "(" + this.hashCode() + ")" + this.getImpl().toString();
	}

	public String _toString() {
		StringBuffer s = new StringBuffer(super._toString());
		return s.toString();
	}

	public void print() {
		this.print("");
	}

	public void print(String prefix) {
		super.print(prefix);
		QualifiedName className = this.getClassName();
		if (className != null) {
			System.out.println(prefix + " className:");
			className.print(prefix + "  ");
		}
	}
} // ClassExtentExpression
