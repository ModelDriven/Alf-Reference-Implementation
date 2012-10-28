
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions.impl.gen;

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

/**
 * The target of a sequence operation, reduction or expansion expression, which
 * may be either a primary expression or a class name denoting the class extent.
 **/

public class ExtentOrExpressionImpl {

	private QualifiedName name = null;
	private Expression expression = null; // DERIVED
	private Expression nonNameExpression = null;

	protected ExtentOrExpression self;

	public ExtentOrExpressionImpl(ExtentOrExpression self) {
		this.self = self;
	}

	public String toString(boolean includeDerived) {
		return this.getSelf()._toString(includeDerived);
	}

	public void deriveAll() {
		this.getSelf()._deriveAll();
	}

	public ExtentOrExpression getSelf() {
		return (ExtentOrExpression) this.self;
	}

	public QualifiedName getName() {
		return this.name;
	}

	public void setName(QualifiedName name) {
		this.name = name;
	}

	public Expression getExpression() {
		if (this.expression == null) {
			this.setExpression(this.deriveExpression());
		}
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

	protected Expression deriveExpression() {
		return null; // STUB
	}

	/**
	 * The effective expression for the target is the parsed primary expression,
	 * if the target is not a qualified name, a name expression, if the target
	 * is a qualified name other than a class name, or a class extent
	 * expression, if the target is the qualified name of a class.
	 **/
	public boolean extentOrExpressionExpressionDerivation() {
		this.getSelf().getExpression();
		return true;
	}

} // ExtentOrExpressionImpl
