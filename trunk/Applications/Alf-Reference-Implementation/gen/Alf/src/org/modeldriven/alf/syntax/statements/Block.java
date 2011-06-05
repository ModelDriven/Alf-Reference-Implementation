
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

import org.omg.uml.Element;
import org.omg.uml.Profile;
import org.omg.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.statements.impl.BlockImpl;

/**
 * A grouped sequence of statements.
 **/

public class Block extends SyntaxElement {

	public Block() {
		this.impl = new BlockImpl(this);
	}

	public BlockImpl getImpl() {
		return (BlockImpl) this.impl;
	}

	public List<Statement> getStatement() {
		return this.getImpl().getStatement();
	}

	public void setStatement(List<Statement> statement) {
		this.getImpl().setStatement(statement);
	}

	public void addStatement(Statement statement) {
		this.getImpl().addStatement(statement);
	}

	public Collection<AssignedSource> getAssignmentAfter() {
		return this.getImpl().getAssignmentAfter();
	}

	public void setAssignmentAfter(Collection<AssignedSource> assignmentAfter) {
		this.getImpl().setAssignmentAfter(assignmentAfter);
	}

	public void addAssignmentAfter(AssignedSource assignmentAfter) {
		this.getImpl().addAssignmentAfter(assignmentAfter);
	}

	public Collection<AssignedSource> getAssignmentBefore() {
		return this.getImpl().getAssignmentBefore();
	}

	public void setAssignmentBefore(Collection<AssignedSource> assignmentBefore) {
		this.getImpl().setAssignmentBefore(assignmentBefore);
	}

	public void addAssignmentBefore(AssignedSource assignmentBefore) {
		this.getImpl().addAssignmentBefore(assignmentBefore);
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

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.blockAssignmentsBeforeStatements()) {
			violations.add(new ConstraintViolation(
					"blockAssignmentsBeforeStatements", this));
		}
		if (!this.blockAssignmentsBefore()) {
			violations.add(new ConstraintViolation("blockAssignmentsBefore",
					this));
		}
		if (!this.blockAssignmentAfterDerivation()) {
			violations.add(new ConstraintViolation(
					"blockAssignmentAfterDerivation", this));
		}
		for (Object _statement : this.getStatement().toArray()) {
			((Statement) _statement).checkConstraints(violations);
		}
	}

	public String toString() {
		return this.getImpl().toString();
	}

	public String _toString() {
		StringBuffer s = new StringBuffer(super._toString());
		return s.toString();
	}

	public void print() {
		this.print("");
	}

	public void print(String prefix) {
		super.print(prefix);
		List<Statement> statement = this.getStatement();
		if (statement != null) {
			if (statement.size() > 0) {
				System.out.println(prefix + " statement:");
			}
			for (Statement _statement : statement) {
				if (_statement != null) {
					_statement.print(prefix + "  ");
				} else {
					System.out.println(prefix + "  null");
				}
			}
		}
		Collection<AssignedSource> assignmentAfter = this.getAssignmentAfter();
		if (assignmentAfter != null) {
			if (assignmentAfter.size() > 0) {
				System.out.println(prefix + " /assignmentAfter:");
			}
			for (AssignedSource _assignmentAfter : assignmentAfter) {
				System.out.println(prefix + "  " + _assignmentAfter);
			}
		}
		Collection<AssignedSource> assignmentBefore = this
				.getAssignmentBefore();
		if (assignmentBefore != null) {
			if (assignmentBefore.size() > 0) {
				System.out.println(prefix + " /assignmentBefore:");
			}
			for (AssignedSource _assignmentBefore : assignmentBefore) {
				System.out.println(prefix + "  " + _assignmentBefore);
			}
		}
	}
} // Block
