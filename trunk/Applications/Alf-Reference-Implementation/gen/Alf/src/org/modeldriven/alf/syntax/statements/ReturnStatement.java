
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.statements;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

public class ReturnStatement extends Statement {

	private Expression expression = null;

	public ReturnStatement(Expression expression) {
		this.expression = expression;
	} // ReturnStatement

	public Expression getExpression() {
		return this.expression;
	} // getExpression

	public void print(String prefix) {
		super.print(prefix);
		this.getExpression().printChild(prefix);
	} // print

} // ReturnStatement
