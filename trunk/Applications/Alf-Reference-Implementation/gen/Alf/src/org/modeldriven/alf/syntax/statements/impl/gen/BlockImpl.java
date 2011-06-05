
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.statements.impl.gen;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.omg.uml.Element;
import org.omg.uml.Profile;
import org.omg.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A grouped sequence of statements.
 **/

public class BlockImpl extends
		org.modeldriven.alf.syntax.common.impl.gen.SyntaxElementImpl {

	private List<Statement> statement = new ArrayList<Statement>();
	private Collection<AssignedSource> assignmentAfter = null; // DERIVED
	private Collection<AssignedSource> assignmentBefore = null; // DERIVED

	public BlockImpl(Block self) {
		super(self);
	}

	public Block getSelf() {
		return (Block) this.self;
	}

	public List<Statement> getStatement() {
		return this.statement;
	}

	public void setStatement(List<Statement> statement) {
		this.statement = statement;
	}

	public void addStatement(Statement statement) {
		this.statement.add(statement);
	}

	public Collection<AssignedSource> getAssignmentAfter() {
		if (this.assignmentAfter == null) {
			this.setAssignmentAfter(this.deriveAssignmentAfter());
		}
		return this.assignmentAfter;
	}

	public void setAssignmentAfter(Collection<AssignedSource> assignmentAfter) {
		this.assignmentAfter = assignmentAfter;
	}

	public void addAssignmentAfter(AssignedSource assignmentAfter) {
		this.assignmentAfter.add(assignmentAfter);
	}

	public Collection<AssignedSource> getAssignmentBefore() {
		if (this.assignmentBefore == null) {
			this.setAssignmentBefore(this.deriveAssignmentBefore());
		}
		return this.assignmentBefore;
	}

	public void setAssignmentBefore(Collection<AssignedSource> assignmentBefore) {
		this.assignmentBefore = assignmentBefore;
	}

	public void addAssignmentBefore(AssignedSource assignmentBefore) {
		this.assignmentBefore.add(assignmentBefore);
	}

	protected Collection<AssignedSource> deriveAssignmentAfter() {
		return null; // STUB
	}

	protected Collection<AssignedSource> deriveAssignmentBefore() {
		return null; // STUB
	}

	/**
	 * The assignments before each statement in a block other than the first are
	 * the same as the assignments after the previous statement.
	 **/
	public boolean blockAssignmentsBeforeStatements() {
		return true;
	}

	public boolean blockAssignmentsBefore() {
		return true;
	}

	/**
	 * If a block is not empty, then the assignments after the block are the
	 * same as the assignments after the last statement of the block. Otherwise
	 * they are the same as the assignments before the block.
	 **/
	public boolean blockAssignmentAfterDerivation() {
		this.getSelf().getAssignmentAfter();
		return true;
	}

} // BlockImpl
