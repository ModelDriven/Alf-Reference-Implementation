
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
 * A grouped sequence of statements.
 **/

public class Block extends SyntaxElement {

	private ArrayList<Statement> statement = new ArrayList<Statement>();
	private ArrayList<AssignedSource> assignmentAfter = new ArrayList<AssignedSource>(); // DERIVED
	private ArrayList<AssignedSource> assignmentBefore = new ArrayList<AssignedSource>(); // DERIVED

	public ArrayList<Statement> getStatement() {
		return this.statement;
	}

	public void setStatement(ArrayList<Statement> statement) {
		this.statement = statement;
	}

	public void addStatement(Statement statement) {
		this.statement.add(statement);
	}

	public ArrayList<AssignedSource> getAssignmentAfter() {
		return this.assignmentAfter;
	}

	public void setAssignmentAfter(ArrayList<AssignedSource> assignmentAfter) {
		this.assignmentAfter = assignmentAfter;
	}

	public void addAssignmentAfter(AssignedSource assignmentAfter) {
		this.assignmentAfter.add(assignmentAfter);
	}

	public ArrayList<AssignedSource> getAssignmentBefore() {
		return this.assignmentBefore;
	}

	public void setAssignmentBefore(ArrayList<AssignedSource> assignmentBefore) {
		this.assignmentBefore = assignmentBefore;
	}

	public void addAssignmentBefore(AssignedSource assignmentBefore) {
		this.assignmentBefore.add(assignmentBefore);
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		for (Statement statement : this.getStatement()) {
			if (statement != null) {
				statement.print(prefix + " ");
			} else {
				System.out.println(prefix + " null");
			}
		}
	}
} // Block
