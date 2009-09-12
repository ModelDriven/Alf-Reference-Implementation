
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package alf.syntax.behavioral;

import alf.nodes.*;
import alf.syntax.SyntaxNode;
import alf.syntax.behavioral.*;
import alf.syntax.expressions.*;
import alf.syntax.namespaces.*;
import alf.syntax.structural.*;

import java.util.ArrayList;

public class NonFinalClause extends SyntaxNode {

	private Expression condition = null;
	private Block body = null;

	public NonFinalClause(Expression condition, Block body) {
		this.condition = condition;
		this.body = body;
	} // NonFinalClause

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

} // NonFinalClause
