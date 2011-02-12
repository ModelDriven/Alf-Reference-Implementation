
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.statements;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

/**
 * A grouping of non-final conditional clauses to be tested concurrently.
 **/

public class ConcurrentClauses extends SyntaxElement implements
		IConcurrentClauses {

	private ArrayList<INonFinalClause> clause = new ArrayList<INonFinalClause>();

	public ArrayList<INonFinalClause> getClause() {
		return this.clause;
	}

	public void setClause(ArrayList<INonFinalClause> clause) {
		this.clause = clause;
	}

	public void addClause(INonFinalClause clause) {
		this.clause.add(clause);
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		ArrayList<INonFinalClause> clause = this.getClause();
		if (clause != null) {
			for (INonFinalClause item : this.getClause()) {
				if (item != null) {
					item.print(prefix + " ");
				} else {
					System.out.println(prefix + " null");
				}
			}
		}
	}
} // ConcurrentClauses
