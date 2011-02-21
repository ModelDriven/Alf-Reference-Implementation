
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

import org.modeldriven.alf.syntax.expressions.impl.StringLiteralExpressionImpl;

/**
 * An expression that comprises a String literal.
 **/

public class StringLiteralExpression extends LiteralExpression {

	private String image = "";

	public StringLiteralExpression() {
		this.impl = new StringLiteralExpressionImpl(this);
	}

	public StringLiteralExpressionImpl getImpl() {
		return (StringLiteralExpressionImpl) this.impl;
	}

	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * The type of a string literal expression is String.
	 **/
	public boolean stringLiteralExpressionTypeDerivation() {
		return this.getImpl().stringLiteralExpressionTypeDerivation();
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" image:");
		s.append(this.getImage());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
	}
} // StringLiteralExpression
