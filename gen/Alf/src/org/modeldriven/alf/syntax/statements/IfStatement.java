
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
 * A conditional statement that executes (at most) one of a set of clauses based
 * on boolean conditions.
 **/

public class IfStatement extends Statement implements IIfStatement {

	private ArrayList<IConcurrentClauses> nonFinalClauses = new ArrayList<IConcurrentClauses>();
	private IBlock finalClause = null;

	public ArrayList<IConcurrentClauses> getNonFinalClauses() {
		return this.nonFinalClauses;
	}

	public void setNonFinalClauses(ArrayList<IConcurrentClauses> nonFinalClauses) {
		this.nonFinalClauses = nonFinalClauses;
	}

	public void addNonFinalClauses(IConcurrentClauses nonFinalClauses) {
		this.nonFinalClauses.add(nonFinalClauses);
	}

	public IBlock getFinalClause() {
		return this.finalClause;
	}

	public void setFinalClause(IBlock finalClause) {
		this.finalClause = finalClause;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		for (IConcurrentClauses nonFinalClauses : this.getNonFinalClauses()) {
			if (nonFinalClauses != null) {
				nonFinalClauses.print(prefix + " ");
			} else {
				System.out.println(prefix + " null");
			}
		}
		IBlock finalClause = this.getFinalClause();
		if (finalClause != null) {
			finalClause.print(prefix + " ");
		}
	}
} // IfStatement
