
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

/**
 * The target of a sequence operation, reduction or expansion expression, which
 * may be either a primary expression or a class name denoting the class extent.
 **/

public class ExtentOrExpression implements IExtentOrExpression {

	private IQualifiedName name = null;
	private IExpression nonNameExpression = null;

	public IQualifiedName getName() {
		return this.name;
	}

	public void setName(IQualifiedName name) {
		this.name = name;
	}

	public IExpression getNonNameExpression() {
		return this.nonNameExpression;
	}

	public void setNonNameExpression(IExpression nonNameExpression) {
		this.nonNameExpression = nonNameExpression;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(this.getClass().getSimpleName());
		return s.toString();
	}

	public void print(String prefix) {
		System.out.println(prefix + this.toString());
		IQualifiedName name = this.getName();
		if (name != null) {
			name.print(prefix + " ");
		}
		IExpression nonNameExpression = this.getNonNameExpression();
		if (nonNameExpression != null) {
			nonNameExpression.print(prefix + " ");
		}
	}
} // ExtentOrExpression
