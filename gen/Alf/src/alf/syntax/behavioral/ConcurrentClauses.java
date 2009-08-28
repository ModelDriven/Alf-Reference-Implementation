
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

public class ConcurrentClauses extends Node {

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
