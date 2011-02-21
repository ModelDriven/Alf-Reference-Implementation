
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

import org.modeldriven.alf.syntax.statements.impl.BlockStatementImpl;

/**
 * A statement that executes a block.
 **/

public class BlockStatement extends Statement {

	private Block block = null;
	private Boolean isParallel = null; // DERIVED

	public BlockStatement() {
		this.impl = new BlockStatementImpl(this);
	}

	public BlockStatementImpl getImpl() {
		return (BlockStatementImpl) this.impl;
	}

	public Block getBlock() {
		return this.block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	public Boolean getIsParallel() {
		if (this.isParallel == null) {
			this.isParallel = this.getImpl().deriveIsParallel();
		}
		return this.isParallel;
	}

	/**
	 * In a parallel block statement, any name assigned in one statement of the
	 * block may not be further assigned in any subsequent statement in the same
	 * block.
	 **/
	public boolean blockStatementParallelAssignments() {
		return this.getImpl().blockStatementParallelAssignments();
	}

	/**
	 * The assignments before the block of a block statement are the same as the
	 * assignments before the block statement.
	 **/
	public boolean blockStatementAssignmentsBefore() {
		return this.getImpl().blockStatementAssignmentsBefore();
	}

	/**
	 * The assignments after a block statement are the same as the assignments
	 * after the block of the block statement.
	 **/
	public boolean blockStatementAssignmentsAfter() {
		return this.getImpl().blockStatementAssignmentsAfter();
	}

	/**
	 * The enclosing statement for all the statements in the block of a block
	 * statement is the block statement.
	 **/
	public boolean blockStatementEnclosedStatements() {
		return this.getImpl().blockStatementEnclosedStatements();
	}

	/**
	 * A block statement is parallel if it has a @parallel annotation.
	 **/
	public boolean blockStatementIsParallelDerivation() {
		return this.getImpl().blockStatementIsParallelDerivation();
	}

	/**
	 * In addition to an @isolated annotation, a block statement may have a @parallel
	 * annotation. It may not have any arguments.
	 **/
	public Boolean annotationAllowed(Annotation annotation) {
		return this.getImpl().annotationAllowed(annotation);
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		Boolean isParallel = this.getIsParallel();
		if (isParallel != null) {
			s.append(" /isParallel:");
			s.append(isParallel);
		}
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		Block block = this.getBlock();
		if (block != null) {
			System.out.println(prefix + " block:");
			block.print(prefix + "  ");
		}
	}
} // BlockStatement
