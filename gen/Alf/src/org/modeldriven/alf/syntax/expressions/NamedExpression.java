
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

import org.modeldriven.alf.syntax.expressions.impl.NamedExpressionImpl;

/**
 * A pairing of a parameter name and an argument expression in a tuple.
 **/

public class NamedExpression extends SyntaxElement {

	private String name = "";
	private Expression expression = null;
	private Expression index = null;
	private Boolean isCollectionConversion = null; // DERIVED
	private Boolean isBitStringConversion = null; // DERIVED

	public NamedExpression() {
		this.impl = new NamedExpressionImpl(this);
	}

	public NamedExpressionImpl getImpl() {
		return (NamedExpressionImpl) this.impl;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Expression getExpression() {
		return this.expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	public Expression getIndex() {
		return this.index;
	}

	public void setIndex(Expression index) {
		this.index = index;
	}

	public Boolean getIsCollectionConversion() {
		if (this.isCollectionConversion == null) {
			this.isCollectionConversion = this.getImpl()
					.deriveIsCollectionConversion();
		}
		return this.isCollectionConversion;
	}

	public Boolean getIsBitStringConversion() {
		if (this.isBitStringConversion == null) {
			this.isBitStringConversion = this.getImpl()
					.deriveIsBitStringConversion();
		}
		return this.isBitStringConversion;
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

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" name:");
		s.append(this.getName());
		Boolean isCollectionConversion = this.getIsCollectionConversion();
		if (isCollectionConversion != null) {
			s.append(" /isCollectionConversion:");
			s.append(isCollectionConversion);
		}
		Boolean isBitStringConversion = this.getIsBitStringConversion();
		if (isBitStringConversion != null) {
			s.append(" /isBitStringConversion:");
			s.append(isBitStringConversion);
		}
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		Expression expression = this.getExpression();
		if (expression != null) {
			expression.print(prefix + " ");
		}
		Expression index = this.getIndex();
		if (index != null) {
			index.print(prefix + " ");
		}
	}
} // NamedExpression
