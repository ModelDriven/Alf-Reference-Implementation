
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.statements;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

/**
 * A statement that evaluates an expression when executed.
 **/

public class ExpressionStatement extends Statement implements
		IExpressionStatement {

	private IExpression expression = null;

	public IExpression getExpression() {
		return this.expression;
	}

	public void setExpression(IExpression expression) {
		this.expression = expression;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		IExpression expression = this.getExpression();
		if (expression != null) {
			expression.print(prefix + " ");
		}
	}
} // ExpressionStatement
