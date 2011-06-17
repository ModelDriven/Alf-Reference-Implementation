
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

import org.modeldriven.alf.syntax.expressions.impl.NamedExpressionImpl;

/**
 * A pairing of a parameter name and an argument expression in a tuple.
 **/

public class NamedExpression extends SyntaxElement {

	public NamedExpression() {
		this.impl = new NamedExpressionImpl(this);
	}

	public NamedExpressionImpl getImpl() {
		return (NamedExpressionImpl) this.impl;
	}

	public String getName() {
		return this.getImpl().getName();
	}

	public void setName(String name) {
		this.getImpl().setName(name);
	}

	public Expression getExpression() {
		return this.getImpl().getExpression();
	}

	public void setExpression(Expression expression) {
		this.getImpl().setExpression(expression);
	}

	public Expression getIndex() {
		return this.getImpl().getIndex();
	}

	public void setIndex(Expression index) {
		this.getImpl().setIndex(index);
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

	/**
	 * Collection conversion is required if the type of the corresponding
	 * parameter is a collection class and the type of the argument expression
	 * is not.
	 **/
	public boolean namedExpressionIsCollectionConversionDerivation() {
		return this.getImpl().namedExpressionIsCollectionConversionDerivation();
	}

	/**
	 * Bit string conversion is required if the type of the type of the
	 * corresponding parameter is BitString, or a collection class instantiated
	 * with a BitString type, and the type of the argument expression is not
	 * BitString.
	 **/
	public boolean namedExpressionIsBitStringConversionDerivation() {
		return this.getImpl().namedExpressionIsBitStringConversionDerivation();
	}

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.namedExpressionIsCollectionConversionDerivation()) {
			violations.add(new ConstraintViolation(
					"namedExpressionIsCollectionConversionDerivation", this));
		}
		if (!this.namedExpressionIsBitStringConversionDerivation()) {
			violations.add(new ConstraintViolation(
					"namedExpressionIsBitStringConversionDerivation", this));
		}
		Expression expression = this.getExpression();
		if (expression != null) {
			expression.checkConstraints(violations);
		}
		Expression index = this.getIndex();
		if (index != null) {
			index.checkConstraints(violations);
		}
	}

	public String toString() {
		return this.toString(false);
	}

	public String toString(boolean includeDerived) {
		return "(" + this.hashCode() + ")"
				+ this.getImpl().toString(includeDerived);
	}

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		s.append(" name:");
		s.append(this.getName());
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
		Expression expression = this.getExpression();
		if (expression != null) {
			System.out.println(prefix + " expression:");
			expression.print(prefix + "  ", includeDerived);
		}
		Expression index = this.getIndex();
		if (index != null) {
			System.out.println(prefix + " index:");
			index.print(prefix + "  ", includeDerived);
		}
		if (includeDerived) {
		}
		if (includeDerived) {
		}
	}
} // NamedExpression
