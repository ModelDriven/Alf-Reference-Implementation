
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

import org.modeldriven.alf.syntax.expressions.impl.ExtentOrExpressionImpl;

/**
 * The target of a sequence operation, reduction or expansion expression, which
 * may be either a primary expression or a class name denoting the class extent.
 **/

public class ExtentOrExpression {

	private QualifiedName name = null;
	private Expression expression = null; // DERIVED
	private Expression nonNameExpression = null;

	protected ExtentOrExpressionImpl impl;

	public ExtentOrExpression() {
		this.impl = new ExtentOrExpressionImpl(this);
	}

	public ExtentOrExpressionImpl getImpl() {
		return (ExtentOrExpressionImpl) this.impl;
	}

	public QualifiedName getName() {
		return this.name;
	}

	public void setName(QualifiedName name) {
		this.name = name;
	}

	public Expression getExpression() {
		if (this.expression == null) {
			this.expression = this.getImpl().deriveExpression();
		}
		return this.expression;
	}

	public Expression getNonNameExpression() {
		return this.nonNameExpression;
	}

	public void setNonNameExpression(Expression nonNameExpression) {
		this.nonNameExpression = nonNameExpression;
	}

	/**
	 * The effective expression for the target is the parsed primary expression,
	 * if the target is not a qualified name, a name expression, if the target
	 * is a qualified name other than a class name, or a class extent
	 * expression, if the target is the qualified name of a class.
	 **/
	public boolean extentOrExpressionExpressionDerivation() {
		return this.getImpl().extentOrExpressionExpressionDerivation();
	}

	public String toString() {
		StringBuffer s = new StringBuffer(this.getClass().getSimpleName());
		return s.toString();
	}

	public void print(String prefix) {
		System.out.println(prefix + this.toString());
		QualifiedName name = this.getName();
		if (name != null) {
			name.print(prefix + " ");
		}
		Expression expression = this.getExpression();
		if (expression != null) {
			System.out.println(prefix + " /" + expression);
		}
		Expression nonNameExpression = this.getNonNameExpression();
		if (nonNameExpression != null) {
			nonNameExpression.print(prefix + " ");
		}
	}
} // ExtentOrExpression
