
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
 * A clause in a switch statement with a set of cases and a sequence of
 * statements that may be executed if one of the cases matches the switch value.
 **/

public class SwitchClause extends SyntaxElement {

	private ArrayList<Expression> case_ = new ArrayList<Expression>();
	private Block block = null;

	public ArrayList<Expression> getCase() {
		return this.case_;
	}

	public void setCase(ArrayList<Expression> case_) {
		this.case_ = case_;
	}

	public void addCase(Expression case_) {
		this.case_.add(case_);
	}

	public Block getBlock() {
		return this.block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	public ArrayList<AssignedSource> assignmentsBefore() {
		/*
		 * The assignments before a switch clause are the assignments before any
		 * case expression of the clause.
		 */
		return new ArrayList<AssignedSource>(); // STUB
	} // assignmentsBefore

	public ArrayList<AssignedSource> assignmentsAfter() {
		/*
		 * The assignments after a switch clause are the assignments after the
		 * block of the switch clause.
		 */
		return new ArrayList<AssignedSource>(); // STUB
	} // assignmentsAfter

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		for (Expression case_ : this.getCase()) {
			if (case_ != null) {
				case_.print(prefix + " ");
			} else {
				System.out.println(prefix + " null");
			}
		}
		if (this.block != null) {
			this.block.print(prefix + " ");
		}
	}
} // SwitchClause
