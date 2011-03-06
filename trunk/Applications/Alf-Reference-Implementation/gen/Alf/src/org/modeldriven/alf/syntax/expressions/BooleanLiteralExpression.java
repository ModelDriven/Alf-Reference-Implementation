
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
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.expressions.impl.BooleanLiteralExpressionImpl;

/**
 * An expression that comprises a Boolean literal.
 **/

public class BooleanLiteralExpression extends LiteralExpression {

	public BooleanLiteralExpression() {
		this.impl = new BooleanLiteralExpressionImpl(this);
	}

	public BooleanLiteralExpressionImpl getImpl() {
		return (BooleanLiteralExpressionImpl) this.impl;
	}

	public String getImage() {
		return this.getImpl().getImage();
	}

	public void setImage(String image) {
		this.getImpl().setImage(image);
	}

	/**
	 * The type of a boolean literal expression is Boolean.
	 **/
	public boolean booleanLiteralExpressionTypeDerivation() {
		return this.getImpl().booleanLiteralExpressionTypeDerivation();
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
} // BooleanLiteralExpression
