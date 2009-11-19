
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
