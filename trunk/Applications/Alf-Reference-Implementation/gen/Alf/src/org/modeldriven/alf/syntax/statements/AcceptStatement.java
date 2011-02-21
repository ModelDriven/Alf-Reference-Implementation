
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

import org.modeldriven.alf.syntax.statements.impl.AcceptStatementImpl;

/**
 * A statement used to accept the receipt of instances of one or more signals.
 **/

public class AcceptStatement extends Statement {

	private ArrayList<AcceptBlock> acceptBlock = new ArrayList<AcceptBlock>();
	private ElementReference behavior = null; // DERIVED
	private Boolean isSimple = null; // DERIVED

	public AcceptStatement() {
		this.impl = new AcceptStatementImpl(this);
	}

	public AcceptStatementImpl getImpl() {
		return (AcceptStatementImpl) this.impl;
	}

	public ArrayList<AcceptBlock> getAcceptBlock() {
		return this.acceptBlock;
	}

	public void setAcceptBlock(ArrayList<AcceptBlock> acceptBlock) {
		this.acceptBlock = acceptBlock;
	}

	public void addAcceptBlock(AcceptBlock acceptBlock) {
		this.acceptBlock.add(acceptBlock);
	}

	public ElementReference getBehavior() {
		if (this.behavior == null) {
			this.behavior = this.getImpl().deriveBehavior();
		}
		return this.behavior;
	}

	public Boolean getIsSimple() {
		if (this.isSimple == null) {
			this.isSimple = this.getImpl().deriveIsSimple();
		}
		return this.isSimple;
	}

	/**
	 * An accept statement can only be used within the definition of an active
	 * behavior or the classifier behavior of an active class.
	 **/
	public boolean acceptStatementContext() {
		return this.getImpl().acceptStatementContext();
	}

	/**
	 * The containing behavior of an accept statement must have receptions for
	 * all signals from all accept blocks of the accept statement. No signal may
	 * be referenced in more than one accept block of an accept statement.
	 **/
	public boolean acceptStatementSignals() {
		return this.getImpl().acceptStatementSignals();
	}

	/**
	 * Any name defined in an accept block of an accept statement must be
	 * unassigned before the accept statement.
	 **/
	public boolean acceptStatementNames() {
		return this.getImpl().acceptStatementNames();
	}

	/**
	 * A local name specified in the accept block of a simple accept statement
	 * has the accept statement as its assigned source after the accept
	 * statement. The type of the local name is the effective common ancestor of
	 * the specified signals, if one exists, and it is untyped otherwise.
	 **/
	public boolean acceptStatementSimpleAcceptLocalName() {
		return this.getImpl().acceptStatementSimpleAcceptLocalName();
	}

	/**
	 * For a compound accept statement, a local name defined in an accept block
	 * has the accept block as its assigned source before the block associated
	 * with the accept block. The type of the local name is the effective common
	 * ancestor of the specified signals for that accept clause, if one exists,
	 * and it is untyped otherwise. However, the local name is considered
	 * unassigned after the accept statement.
	 **/
	public boolean acceptStatementCompoundAcceptLocalName() {
		return this.getImpl().acceptStatementCompoundAcceptLocalName();
	}

	/**
	 * The assignments before any block of an accept statement are the
	 * assignments before the accept statement.
	 **/
	public boolean acceptStatementAssignmentsBefore() {
		return this.getImpl().acceptStatementAssignmentsBefore();
	}

	/**
	 * If a name is assigned in any block of an accept statement, then the
	 * assigned source of the name after the accept statement is the accept
	 * statement itself.
	 **/
	public boolean acceptStatementAssignmentsAfter() {
		return this.getImpl().acceptStatementAssignmentsAfter();
	}

	/**
	 * If a name is unassigned before an accept statement and assigned in any
	 * block of an accept statement, then it must be assigned in every block.
	 **/
	public boolean acceptStatementNewAssignments() {
		return this.getImpl().acceptStatementNewAssignments();
	}

	/**
	 * An accept statement is simple if it has exactly one accept block and that
	 * accept block does not have a block.
	 **/
	public boolean acceptStatementIsSimpleDerivation() {
		return this.getImpl().acceptStatementIsSimpleDerivation();
	}

	/**
	 * The enclosing statement for all statements in the blocks of all accept
	 * blocks of an accept statement is the accept statement.
	 **/
	public boolean acceptStatementEnclosedStatements() {
		return this.getImpl().acceptStatementEnclosedStatements();
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		Boolean isSimple = this.getIsSimple();
		if (isSimple != null) {
			s.append(" /isSimple:");
			s.append(isSimple);
		}
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		ArrayList<AcceptBlock> acceptBlock = this.getAcceptBlock();
		if (acceptBlock != null) {
			if (acceptBlock.size() > 0) {
				System.out.println(prefix + " acceptBlock:");
			}
			for (AcceptBlock item : this.getAcceptBlock()) {
				if (item != null) {
					item.print(prefix + "  ");
				} else {
					System.out.println(prefix + "  null");
				}
			}
		}
		ElementReference behavior = this.getBehavior();
		if (behavior != null) {
			System.out.println(prefix + " /behavior:" + behavior);
		}
	}
} // AcceptStatement
