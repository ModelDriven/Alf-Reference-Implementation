
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.behavioral;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.SyntaxNode;
import org.modeldriven.alf.syntax.behavioral.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.namespaces.*;
import org.modeldriven.alf.syntax.structural.*;

import java.util.ArrayList;

public class WhileStatement extends Statement {

	private Block body = null;
	private Expression condition = null;

	public WhileStatement(Expression condition, Block body) {
		this.condition = condition;
		this.body = body;
	} // WhileStatement

	public Expression getCondition() {
		return this.condition;
	} // getCondition

	public Block getBody() {
		return this.body;
	} // getBody

	public void print(String prefix) {
		super.print(prefix);
		this.getCondition().printChild(prefix);
		this.getBody().printChild(prefix);
	} // print

} // WhileStatement
