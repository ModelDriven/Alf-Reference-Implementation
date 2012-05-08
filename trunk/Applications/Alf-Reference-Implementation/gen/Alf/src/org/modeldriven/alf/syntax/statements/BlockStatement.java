
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.statements;

import org.modeldriven.alf.parser.AlfParser;
import org.modeldriven.alf.parser.Token;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.Profile;
import org.modeldriven.alf.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.statements.impl.BlockStatementImpl;

/**
 * A statement that executes a block.
 **/

public class BlockStatement extends Statement {

	public BlockStatement() {
		this.impl = new BlockStatementImpl(this);
	}

	public BlockStatement(AlfParser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public BlockStatement(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public BlockStatementImpl getImpl() {
		return (BlockStatementImpl) this.impl;
	}

	public Block getBlock() {
		return this.getImpl().getBlock();
	}

	public void setBlock(Block block) {
		this.getImpl().setBlock(block);
	}

	public Boolean getIsParallel() {
		return this.getImpl().getIsParallel();
	}

	public void setIsParallel(Boolean isParallel) {
		this.getImpl().setIsParallel(isParallel);
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

	public void _deriveAll() {
		this.getIsParallel();
		super._deriveAll();
		Block block = this.getBlock();
		if (block != null) {
			block.deriveAll();
		}
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.blockStatementParallelAssignments()) {
			violations.add(new ConstraintViolation(
					"blockStatementParallelAssignments", this));
		}
		if (!this.blockStatementAssignmentsBefore()) {
			violations.add(new ConstraintViolation(
					"blockStatementAssignmentsBefore", this));
		}
		if (!this.blockStatementAssignmentsAfter()) {
			violations.add(new ConstraintViolation(
					"blockStatementAssignmentsAfter", this));
		}
		if (!this.blockStatementEnclosedStatements()) {
			violations.add(new ConstraintViolation(
					"blockStatementEnclosedStatements", this));
		}
		if (!this.blockStatementIsParallelDerivation()) {
			violations.add(new ConstraintViolation(
					"blockStatementIsParallelDerivation", this));
		}
		Block block = this.getBlock();
		if (block != null) {
			block.checkConstraints(violations);
		}
	}

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		if (includeDerived) {
			s.append(" /isParallel:");
			s.append(this.getIsParallel());
		}
		return s.toString();
	}

	public void print() {
		this.print("", false);
	}

	public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	public void print(String prefix, boolean includeDerived) {
		super.print(prefix, includeDerived);
		Block block = this.getBlock();
		if (block != null) {
			System.out.println(prefix + " block:");
			block.print(prefix + "  ", includeDerived);
		}
	}
} // BlockStatement
