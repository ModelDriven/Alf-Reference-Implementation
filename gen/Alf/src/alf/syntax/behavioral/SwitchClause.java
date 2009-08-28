
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package alf.syntax.behavioral;

import alf.syntax.behavioral.*;
import alf.syntax.expressions.*;
import alf.syntax.namespaces.*;
import alf.syntax.nodes.*;
import alf.syntax.structural.*;

import java.util.ArrayList;

public class SwitchClause extends Node {

	private ArrayList<Expression> cases = new ArrayList<Expression>();
	private Block block = null;

	public void addCase(Expression expression) {
		this.cases.add(expression);
	} // addCase

	public ArrayList<Expression> getCases() {
		return this.cases;
	} // getCases

	public void setBlock(Block block) {
		this.block = block;
	} // setBlock

	public Block getBlock() {
		return this.block;
	} // getBlock

	public void print(String prefix) {
		super.print(prefix);

		for (Expression expr : this.getCases()) {
			expr.printChild(prefix);
		}

		this.getBlock().printChild(prefix);
	} // print

} // SwitchClause
