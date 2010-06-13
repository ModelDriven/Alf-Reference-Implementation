
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

public class DoStatement extends Statement {

	private Expression condition = null;
	private Block body = null;

	public DoStatement(Block body, Expression condition) {
		this.body = body;
		this.condition = condition;
	} // DoStatement

	public Block getBody() {
		return this.body;
	} // getBody

	public Expression getCondition() {
		return this.condition;
	} // getCondition

	public void print(String prefix) {
		super.print(prefix);
		this.getBody().printChild(prefix);
		this.getCondition().printChild(prefix);
	} // print

} // DoStatement
