
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

public class ConcurrentClauses extends SyntaxNode {

	private ArrayList<NonFinalClause> clauses = new ArrayList<NonFinalClause>();

	public void addClause(NonFinalClause clause) {
		this.clauses.add(clause);
	} // addClause

	public ArrayList<NonFinalClause> getClauses() {
		return this.clauses;
	} // getClauses

	public void print(String prefix) {
		super.print(prefix);

		for (NonFinalClause clause : this.getClauses()) {
			clause.printChild(prefix);
		}
	} // print

} // ConcurrentClauses
