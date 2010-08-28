
/*
 * Copyright 2010 Data Access Technologies, Inc. (Model Driven Solutions)
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

public class IfStatement extends Statement {

	private ArrayList<ConcurrentClauses> nonFinalClauses = new ArrayList<ConcurrentClauses>();
	private Block finalClause = null;
	private boolean isAssured = false; // DERIVED
	private boolean isDetermined = false; // DERIVED

	public ArrayList<ConcurrentClauses> getNonFinalClauses() {
		return this.nonFinalClauses;
	}

	public void setNonFinalClauses(ArrayList<ConcurrentClauses> nonFinalClauses) {
		this.nonFinalClauses = nonFinalClauses;
	}

	public void addNonFinalClauses(ConcurrentClauses nonFinalClauses) {
		this.nonFinalClauses.add(nonFinalClauses);
	}

	public Block getFinalClause() {
		return this.finalClause;
	}

	public void setFinalClause(Block finalClause) {
		this.finalClause = finalClause;
	}

	public boolean getIsAssured() {
		return this.isAssured;
	}

	public void setIsAssured(boolean isAssured) {
		this.isAssured = isAssured;
	}

	public boolean getIsDetermined() {
		return this.isDetermined;
	}

	public void setIsDetermined(boolean isDetermined) {
		this.isDetermined = isDetermined;
	}

	public boolean annotationAllowed(Annotation annotation) {
		/*
		 * In addition to an @isolated annotation, an if statement may have
		 * @assured and @determined annotations. They may not have arguments.
		 */
		return false; // STUB
	} // annotationAllowed

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		for (ConcurrentClauses nonFinalClauses : this.getNonFinalClauses()) {
			if (nonFinalClauses != null) {
				nonFinalClauses.print(prefix + " ");
			} else {
				System.out.println(prefix + " null");
			}
		}
		if (this.finalClause != null) {
			this.finalClause.print(prefix + " ");
		}
	}
} // IfStatement
