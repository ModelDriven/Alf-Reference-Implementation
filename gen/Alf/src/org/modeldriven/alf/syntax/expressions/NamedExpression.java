
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
 * A pairing of a parameter name and an argument expression in a tuple.
 **/

public class NamedExpression extends SyntaxElement implements INamedExpression {

	private String name = "";
	private IExpression expression = null;
	private IExpression index = null;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public IExpression getExpression() {
		return this.expression;
	}

	public void setExpression(IExpression expression) {
		this.expression = expression;
	}

	public IExpression getIndex() {
		return this.index;
	}

	public void setIndex(IExpression index) {
		this.index = index;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" name:");
		s.append(this.getName());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		IExpression expression = this.getExpression();
		if (expression != null) {
			expression.print(prefix + " ");
		}
		IExpression index = this.getIndex();
		if (index != null) {
			index.print(prefix + " ");
		}
	}
} // NamedExpression
