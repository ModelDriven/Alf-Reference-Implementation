/*******************************************************************************
 * Copyright 2011, 2018, 2020 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.statements;

import org.modeldriven.alf.parser.ParsedElement;
import org.modeldriven.alf.parser.Parser;

import org.modeldriven.alf.syntax.common.*;
import java.util.Collection;
import org.modeldriven.alf.syntax.statements.impl.AcceptStatementImpl;

/**
 * A statement used to accept the receipt of instances of one or more signals.
 **/

public class AcceptStatement extends Statement {

	public AcceptStatement() {
		this.impl = new AcceptStatementImpl(this);
	}

	public AcceptStatement(Parser parser) {
	    this();
	    this.init(parser);
	}

	public AcceptStatement(ParsedElement element) {
        this();
        this.init(element);
	}

	public AcceptStatementImpl getImpl() {
		return (AcceptStatementImpl) this.impl;
	}

	public Collection<AcceptBlock> getAcceptBlock() {
		return this.getImpl().getAcceptBlock();
	}

	public void setAcceptBlock(Collection<AcceptBlock> acceptBlock) {
		this.getImpl().setAcceptBlock(acceptBlock);
	}

	public void addAcceptBlock(AcceptBlock acceptBlock) {
		this.getImpl().addAcceptBlock(acceptBlock);
	}

	public ElementReference getBehavior() {
		return this.getImpl().getBehavior();
	}

	public void setBehavior(ElementReference behavior) {
		this.getImpl().setBehavior(behavior);
	}

	public Boolean getIsSimple() {
		return this.getImpl().getIsSimple();
	}

	public void setIsSimple(Boolean isSimple) {
		this.getImpl().setIsSimple(isSimple);
	}

	/**
	 * An accept statement can only be used within the definition of an active
	 * behavior or the classifier behavior of an active class.
	 **/
	public boolean acceptStatementContext() {
		return this.getImpl().acceptStatementContext();
	}

	/**
	 * No signal may be referenced in more than one accept block of an accept statement.
	 **/
	public boolean acceptStatementSignals() {
		return this.getImpl().acceptStatementSignals();
	}

	/**
	 * The containing behavior of an accept statement must have receptions for
	 * all signals from all accept blocks of the accept statement. 
	 **/
	public boolean acceptStatementReceptions() {
		return this.getImpl().acceptStatementReceptions();
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
     * Any name that is unassigned before an accept statement and is assigned in
     * one or more blocks of the accept statement, has, after the accept
     * statement, a type that is is the effective common ancestor of the types
     * of the name in each block in which it is defined, with a multiplicity
     * lower bound that is the minimum of the lower bound for the name in each
     * block (where it is considered to have multiplicity lower bound of zero
     * for blocks in which it is not defined), and a multiplicity upper bound
     * that is the maximum for the name in each block in which it is defined.
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
	
	/**
	 * An accept statement has a return value if all of its accept clauses
	 * have return values.
	 */
	@Override
	public Boolean hasReturnValue() {
	    return this.getImpl().hasReturnValue();
	}

    @Override
    public void _addExternalReferences(Collection<ExternalElementReference> references) {
        super._addExternalReferences(references);
        addExternalReferencesFor(references, this.getAcceptBlock());
    }

	public void _deriveAll() {
		this.getBehavior();
		this.getIsSimple();
		super._deriveAll();
		Collection<AcceptBlock> acceptBlock = this.getAcceptBlock();
		if (acceptBlock != null) {
			for (Object _acceptBlock : acceptBlock.toArray()) {
				((AcceptBlock) _acceptBlock).deriveAll();
			}
		}
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.acceptStatementContext()) {
			violations.add(new ConstraintViolation("acceptStatementContext",
					this));
		}
		if (!this.acceptStatementSignals()) {
			violations.add(new ConstraintViolation("acceptStatementSignals",
					this));
		}
		if (!this.acceptStatementReceptions()) {
			violations.add(new ConstraintViolation("acceptStatementReceptions",
					this));
		}
		if (!this.acceptStatementNames()) {
			violations
					.add(new ConstraintViolation("acceptStatementNames", this));
		}
		if (!this.acceptStatementSimpleAcceptLocalName()) {
			violations.add(new ConstraintViolation(
					"acceptStatementSimpleAcceptLocalName", this));
		}
		if (!this.acceptStatementCompoundAcceptLocalName()) {
			violations.add(new ConstraintViolation(
					"acceptStatementCompoundAcceptLocalName", this));
		}
		if (!this.acceptStatementAssignmentsBefore()) {
			violations.add(new ConstraintViolation(
					"acceptStatementAssignmentsBefore", this));
		}
		if (!this.acceptStatementAssignmentsAfter()) {
			violations.add(new ConstraintViolation(
					"acceptStatementAssignmentsAfter", this));
		}
		if (!this.acceptStatementNewAssignments()) {
			violations.add(new ConstraintViolation(
					"acceptStatementNewAssignments", this));
		}
		if (!this.acceptStatementIsSimpleDerivation()) {
			violations.add(new ConstraintViolation(
					"acceptStatementIsSimpleDerivation", this));
		}
		if (!this.acceptStatementEnclosedStatements()) {
			violations.add(new ConstraintViolation(
					"acceptStatementEnclosedStatements", this));
		}
		Collection<AcceptBlock> acceptBlock = this.getAcceptBlock();
		if (acceptBlock != null) {
			for (Object _acceptBlock : acceptBlock.toArray()) {
				((AcceptBlock) _acceptBlock).checkConstraints(violations);
			}
		}
	}

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		if (includeDerived) {
			s.append(" /isSimple:");
			s.append(this.getIsSimple());
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
		Collection<AcceptBlock> acceptBlock = this.getAcceptBlock();
		if (acceptBlock != null && acceptBlock.size() > 0) {
			System.out.println(prefix + " acceptBlock:");
			for (Object _object : acceptBlock.toArray()) {
				AcceptBlock _acceptBlock = (AcceptBlock) _object;
				if (_acceptBlock != null) {
					_acceptBlock.print(prefix + "  ", includeDerived);
				} else {
					System.out.println(prefix + "  null");
				}
			}
		}
		if (includeDerived) {
			ElementReference behavior = this.getBehavior();
			if (behavior != null) {
				System.out.println(prefix + " /behavior:"
						+ behavior.toString(includeDerived));
			}
		}
	}
} // AcceptStatement
