
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

public class IfStatement extends Statement {

	private ArrayList<ConcurrentClauses> nonFinalClauses = new ArrayList<ConcurrentClauses>();
	private Block finalClause = null;

	public void addNonFinalClauses(ConcurrentClauses clauses) {
		this.nonFinalClauses.add(clauses);
	} // addNonFinalClauses

	public ArrayList<ConcurrentClauses> getNonFinalClauses() {
		return this.nonFinalClauses;
	} // getNonFinalClauses

	public void setFinalClause(Block block) {
		this.finalClause = block;
	} // setFinalClause

	public Block getFinalClause() {
		return this.finalClause;
	} // getFinalClause

	public void print(String prefix) {
		super.print(prefix);

		for (ConcurrentClauses clauses : this.getNonFinalClauses()) {
			clauses.printChild(prefix);
		}

		if (this.getFinalClause() != null) {
			this.getFinalClause().printChild(prefix);
		}
	} // print

} // IfStatement
