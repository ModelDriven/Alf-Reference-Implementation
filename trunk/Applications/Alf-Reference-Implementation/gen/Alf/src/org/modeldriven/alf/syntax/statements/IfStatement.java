
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

import org.modeldriven.alf.syntax.statements.impl.IfStatementImpl;

/**
 * A conditional statement that executes (at most) one of a set of clauses based
 * on boolean conditions.
 **/

public class IfStatement extends Statement {

	private ArrayList<ConcurrentClauses> nonFinalClauses = new ArrayList<ConcurrentClauses>();
	private Block finalClause = null;
	private Boolean isAssured = null; // DERIVED
	private Boolean isDetermined = null; // DERIVED

	public IfStatement() {
		this.impl = new IfStatementImpl(this);
	}

	public IfStatementImpl getImpl() {
		return (IfStatementImpl) this.impl;
	}

	public ArrayList<ConcurrentClauses> getNonFinalClauses() {
		return this.nonFinalClauses;
	}

	public void setNonFinalClauses(ArrayList<ConcurrentClauses> nonFinalClauses) {
		this.nonFinalClauses = nonFinalClauses;
	}

	public void addNonFinalClauses(ConcurrentClauses nonFinalClauses) {
		this.nonFinalClauses.add(nonFinalClauses);
	}

	public Block getFinalClause() {
		return this.finalClause;
	}

	public void setFinalClause(Block finalClause) {
		this.finalClause = finalClause;
	}

	public Boolean getIsAssured() {
		if (this.isAssured == null) {
			this.isAssured = this.getImpl().deriveIsAssured();
		}
		return this.isAssured;
	}

	public Boolean getIsDetermined() {
		if (this.isDetermined == null) {
			this.isDetermined = this.getImpl().deriveIsDetermined();
		}
		return this.isDetermined;
	}

	/**
	 * The assignments before all the non-final clauses of an if statement are
	 * the same as the assignments before the if statement. If the statement has
	 * a final clause, then the assignments before that clause are also the same
	 * as the assignments before the if statement.
	 **/
	public boolean ifStatementAssignmentsBefore() {
		return this.getImpl().ifStatementAssignmentsBefore();
	}

	/**
	 * If an if statement does not have a final else clause, then any name that
	 * is unassigned before the if statement is unassigned after the if
	 * statement. If an if statement does have a final else clause, then any
	 * name that is unassigned before the if statement and is assigned after any
	 * one clause of the if statement must also be assigned after every other
	 * clause. The type of such names after the if statement is the effective
	 * common ancestor of the types of the name in each clause with a
	 * multiplicity lower bound that is the minimum of the lower bound for the
	 * name in each clause and a multiplicity upper bound that is the maximum
	 * for the name in each clause. For a name that has an assigned source after
	 * any clause of an if statement that is different than before that clause,
	 * then the assigned source after the if statement is the if statement.
	 * Otherwise, the assigned source of a name after the if statement is the
	 * same as before the if statement.
	 **/
	public boolean ifStatementAssignmentsAfter() {
		return this.getImpl().ifStatementAssignmentsAfter();
	}

	/**
	 * The enclosing statement of all the statements in the bodies of all
	 * non-final clauses and in the final clause (if any) of an if statement is
	 * the if statement.
	 **/
	public boolean ifStatementEnclosedStatements() {
		return this.getImpl().ifStatementEnclosedStatements();
	}

	/**
	 * An if statement is assured if it has an @assured annotation.
	 **/
	public boolean ifStatementIsAssuredDerivation() {
		return this.getImpl().ifStatementIsAssuredDerivation();
	}

	/**
	 * An if statement is determined if it has an @determined annotation.
	 **/
	public boolean ifStatementIsDeterminedDerivation() {
		return this.getImpl().ifStatementIsDeterminedDerivation();
	}

	/**
	 * In addition to an @isolated annotation, an if statement may have @assured
	 * and @determined annotations. They may not have arguments.
	 **/
	public Boolean annotationAllowed(Annotation annotation) {
		return this.getImpl().annotationAllowed(annotation);
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		Boolean isAssured = this.getIsAssured();
		if (isAssured != null) {
			s.append(" /isAssured:");
			s.append(isAssured);
		}
		Boolean isDetermined = this.getIsDetermined();
		if (isDetermined != null) {
			s.append(" /isDetermined:");
			s.append(isDetermined);
		}
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		ArrayList<ConcurrentClauses> nonFinalClauses = this
				.getNonFinalClauses();
		if (nonFinalClauses != null) {
			if (nonFinalClauses.size() > 0) {
				System.out.println(prefix + " nonFinalClauses:");
			}
			for (ConcurrentClauses _nonFinalClauses : (ArrayList<ConcurrentClauses>) nonFinalClauses
					.clone()) {
				if (_nonFinalClauses != null) {
					_nonFinalClauses.print(prefix + "  ");
				} else {
					System.out.println(prefix + "  null");
				}
			}
		}
		Block finalClause = this.getFinalClause();
		if (finalClause != null) {
			System.out.println(prefix + " finalClause:");
			finalClause.print(prefix + "  ");
		}
	}
} // IfStatement
