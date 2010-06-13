
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

public class SwitchStatement extends Statement {

	private ArrayList<SwitchClause> nonDefaultClauses = new ArrayList<SwitchClause>();
	private Expression expression = null;
	private Block defaultClause = null;

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
