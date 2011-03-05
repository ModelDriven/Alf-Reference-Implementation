
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.statements.impl;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

/**
 * A statement that executes a block.
 **/

public class BlockStatementImpl extends
		org.modeldriven.alf.syntax.statements.impl.StatementImpl {

	public BlockStatementImpl(BlockStatement self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.statements.BlockStatement getSelf() {
		return (BlockStatement) this.self;
	}

	public Boolean deriveIsParallel() {
		return null; // STUB
	}

	/**
	 * In a parallel block statement, any name assigned in one statement of the
	 * block may not be further assigned in any subsequent statement in the same
	 * block.
	 **/
	public boolean blockStatementParallelAssignments() {
		return true;
	}

	/**
	 * The assignments before the block of a block statement are the same as the
	 * assignments before the block statement.
	 **/
	public boolean blockStatementAssignmentsBefore() {
		return true;
	}

	/**
	 * The assignments after a block statement are the same as the assignments
	 * after the block of the block statement.
	 **/
	public boolean blockStatementAssignmentsAfter() {
		return true;
	}

	/**
	 * The enclosing statement for all the statements in the block of a block
	 * statement is the block statement.
	 **/
	public boolean blockStatementEnclosedStatements() {
		return true;
	}

	/**
	 * A block statement is parallel if it has a @parallel annotation.
	 **/
	public boolean blockStatementIsParallelDerivation() {
		this.getSelf().getIsParallel();
		return true;
	}

	/**
	 * In addition to an @isolated annotation, a block statement may have a @parallel
	 * annotation. It may not have any arguments.
	 **/
	public Boolean annotationAllowed(Annotation annotation) {
		return false; // STUB
	} // annotationAllowed

    @Override
    public void setCurrentScope(NamespaceDefinition currentScope) {
        Block block = this.getSelf().getBlock();
        if (block != null) {
            block.getImpl().setCurrentScope(currentScope);
        }
    }

} // BlockStatementImpl
