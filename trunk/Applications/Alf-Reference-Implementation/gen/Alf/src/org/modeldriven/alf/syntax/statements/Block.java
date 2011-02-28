
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

import org.omg.uml.*;

import java.util.ArrayList;

import org.modeldriven.alf.syntax.statements.impl.BlockImpl;

/**
 * A grouped sequence of statements.
 **/

public class Block extends SyntaxElement {

	private ArrayList<Statement> statement = new ArrayList<Statement>();
	private ArrayList<AssignedSource> assignmentAfter = null; // DERIVED
	private ArrayList<AssignedSource> assignmentBefore = null; // DERIVED

	public Block() {
		this.impl = new BlockImpl(this);
	}

	public BlockImpl getImpl() {
		return (BlockImpl) this.impl;
	}

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
		if (this.assignmentAfter == null) {
			this.assignmentAfter = this.getImpl().deriveAssignmentAfter();
		}
		return this.assignmentAfter;
	}

	public ArrayList<AssignedSource> getAssignmentBefore() {
		if (this.assignmentBefore == null) {
			this.assignmentBefore = this.getImpl().deriveAssignmentBefore();
		}
		return this.assignmentBefore;
	}

	/**
	 * The assignments before each statement in a block other than the first are
	 * the same as the assignments after the previous statement.
	 **/
	public boolean blockAssignmentsBeforeStatements() {
		return this.getImpl().blockAssignmentsBeforeStatements();
	}

	public boolean blockAssignmentsBefore() {
		return this.getImpl().blockAssignmentsBefore();
	}

	/**
	 * If a block is not empty, then the assignments after the block are the
	 * same as the assignments after the last statement of the block. Otherwise
	 * they are the same as the assignments before the block.
	 **/
	public boolean blockAssignmentAfterDerivation() {
		return this.getImpl().blockAssignmentAfterDerivation();
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		ArrayList<Statement> statement = this.getStatement();
		if (statement != null) {
			if (statement.size() > 0) {
				System.out.println(prefix + " statement:");
			}
			for (Statement _statement : (ArrayList<Statement>) statement
					.clone()) {
				if (_statement != null) {
					_statement.print(prefix + "  ");
				} else {
					System.out.println(prefix + "  null");
				}
			}
		}
		ArrayList<AssignedSource> assignmentAfter = this.getAssignmentAfter();
		if (assignmentAfter != null) {
			if (assignmentAfter.size() > 0) {
				System.out.println(prefix + " /assignmentAfter:");
			}
			for (AssignedSource _assignmentAfter : (ArrayList<AssignedSource>) assignmentAfter
					.clone()) {
				System.out.println(prefix + "  " + _assignmentAfter);
			}
		}
		ArrayList<AssignedSource> assignmentBefore = this.getAssignmentBefore();
		if (assignmentBefore != null) {
			if (assignmentBefore.size() > 0) {
				System.out.println(prefix + " /assignmentBefore:");
			}
			for (AssignedSource _assignmentBefore : (ArrayList<AssignedSource>) assignmentBefore
					.clone()) {
				System.out.println(prefix + "  " + _assignmentBefore);
			}
		}
	}
} // Block
