
/*
 * Copyright 2010 Data Access Technologies, Inc. (Model Driven Solutions)
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

/**
 * The target of a sequence operation, reduction or expansion expression, which
 * may be either a primary expression or a class name denoting the class extent.
 **/

public class ExtentOrExpression {

	private QualifiedName name = null;
	private Expression expression = null; // DERIVED
	private Expression nonNameExpression = null;

	public QualifiedName getName() {
		return this.name;
	}

	public void setName(QualifiedName name) {
		this.name = name;
	}

	public Expression getExpression() {
		return this.expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	public Expression getNonNameExpression() {
		return this.nonNameExpression;
	}

	public void setNonNameExpression(Expression nonNameExpression) {
		this.nonNameExpression = nonNameExpression;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(this.getClass().getSimpleName());
		return s.toString();
	}

	public void print(String prefix) {
		System.out.println(prefix + this.toString());
		if (this.name != null) {
			this.name.print(prefix + " ");
		}
		if (this.nonNameExpression != null) {
			this.nonNameExpression.print(prefix + " ");
		}
	}
} // ExtentOrExpression
