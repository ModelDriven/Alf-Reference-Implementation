
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
 * A clause of an if statement with a conditional expression and a sequence of
 * statements that may be executed if the condition is true.
 **/

public class NonFinalClause extends SyntaxElement {

	private Expression condition = null;
	private Block body = null;

	public Expression getCondition() {
		return this.condition;
	}

	public void setCondition(Expression condition) {
		this.condition = condition;
	}

	public Block getBody() {
		return this.body;
	}

	public void setBody(Block body) {
		this.body = body;
	}

	public ArrayList<AssignedSource> assignmentsBefore() {
		/*
		 * The assignments before a non-final clause are the assignments before
		 * the condition of the clause.
		 */
		return new ArrayList<AssignedSource>(); // STUB
	} // assignmentsBefore

	public ArrayList<AssignedSource> assignmentsAfter() {
		/*
		 * The assignments after a non-final clause are the assignments after
		 * the block of the clause.
		 */
		return new ArrayList<AssignedSource>(); // STUB
	} // assignmentsAfter

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		if (this.condition != null) {
			this.condition.print(prefix + " ");
		}
		if (this.body != null) {
			this.body.print(prefix + " ");
		}
	}
} // NonFinalClause
