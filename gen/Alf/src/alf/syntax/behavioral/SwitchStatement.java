
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

public class SwitchStatement extends Statement {

	private Block defaultClause = null;
	private ArrayList<SwitchClause> nonDefaultClauses = new ArrayList<SwitchClause>();
	private Expression expression = null;

	public SwitchStatement(Expression expression) {
		this.expression = expression;
	} // SwitchStatement

	public Expression getExpression() {
		return this.expression;
	} // getExpression

	public void addNonDefaultClause(SwitchClause clause) {
		this.nonDefaultClauses.add(clause);
	} // addNonDefaultClause

	public ArrayList<SwitchClause> getNonDefaultClauses() {
		return this.nonDefaultClauses;
	} // getNonDefaultClauses

	public void setDefaultClause(Block block) {
		this.defaultClause = block;
	} // setDefaultClause

	public Block getDefaultClause() {
		return this.defaultClause;
	} // getDefaultClause

	public void print(String prefix) {
		super.print(prefix);
		this.getExpression().printChild(prefix);

		for (SwitchClause clause : this.getNonDefaultClauses()) {
			clause.printChild(prefix);
		}

		if (this.getDefaultClause() != null) {
			this.getDefaultClause().printChild(prefix);
		}
	} // print

} // SwitchStatement
