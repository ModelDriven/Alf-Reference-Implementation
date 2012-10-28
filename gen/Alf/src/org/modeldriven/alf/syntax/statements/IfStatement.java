
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
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
import java.util.TreeSet;

import org.modeldriven.alf.syntax.statements.impl.IfStatementImpl;

/**
 * A conditional statement that executes (at most) one of a set of clauses based
 * on boolean conditions.
 **/

public class IfStatement extends Statement {

	public IfStatement() {
		this.impl = new IfStatementImpl(this);
	}

	public IfStatement(AlfParser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public IfStatement(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public IfStatementImpl getImpl() {
		return (IfStatementImpl) this.impl;
	}

	public List<ConcurrentClauses> getNonFinalClauses() {
		return this.getImpl().getNonFinalClauses();
	}

	public void setNonFinalClauses(List<ConcurrentClauses> nonFinalClauses) {
		this.getImpl().setNonFinalClauses(nonFinalClauses);
	}

	public void addNonFinalClauses(ConcurrentClauses nonFinalClauses) {
		this.getImpl().addNonFinalClauses(nonFinalClauses);
	}

	public Block getFinalClause() {
		return this.getImpl().getFinalClause();
	}

	public void setFinalClause(Block finalClause) {
		this.getImpl().setFinalClause(finalClause);
	}

	public Boolean getIsAssured() {
		return this.getImpl().getIsAssured();
	}

	public void setIsAssured(Boolean isAssured) {
		this.getImpl().setIsAssured(isAssured);
	}

	public Boolean getIsDeterminate() {
		return this.getImpl().getIsDeterminate();
	}

	public void setIsDeterminate(Boolean isDeterminate) {
		this.getImpl().setIsDeterminate(isDeterminate);
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
	 * is not an out parameter and is unassigned before the if statement is
	 * unassigned after the if statement. If an if statement does have a final
	 * else clause, then any name that is unassigned before the if statement and
	 * is assigned after any one clause of the if statement must also be
	 * assigned after every other clause. The type of such names after the if
	 * statement is the effective common ancestor of the types of the name in
	 * each clause with a multiplicity lower bound that is the minimum of the
	 * lower bound for the name in each clause and a multiplicity upper bound
	 * that is the maximum for the name in each clause. For a name that has an
	 * assigned source after any clause of an if statement that is different
	 * than before that clause, then the assigned source after the if statement
	 * is the if statement. Otherwise, the assigned source of a name after the
	 * if statement is the same as before the if statement.
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
	 * An if statement is determinate if it has an @determinate annotation.
	 **/
	public boolean ifStatementIsDeterminateDerivation() {
		return this.getImpl().ifStatementIsDeterminateDerivation();
	}

	/**
	 * In addition to an @isolated annotation, an if statement may have @assured
	 * and @determinate annotations. They may not have arguments.
	 **/
	public Boolean annotationAllowed(Annotation annotation) {
		return this.getImpl().annotationAllowed(annotation);
	}

	public void _deriveAll() {
		this.getIsAssured();
		this.getIsDeterminate();
		super._deriveAll();
		Collection<ConcurrentClauses> nonFinalClauses = this
				.getNonFinalClauses();
		if (nonFinalClauses != null) {
			for (Object _nonFinalClauses : nonFinalClauses.toArray()) {
				((ConcurrentClauses) _nonFinalClauses).deriveAll();
			}
		}
		Block finalClause = this.getFinalClause();
		if (finalClause != null) {
			finalClause.deriveAll();
		}
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.ifStatementAssignmentsBefore()) {
			violations.add(new ConstraintViolation(
					"ifStatementAssignmentsBefore", this));
		}
		if (!this.ifStatementAssignmentsAfter()) {
			violations.add(new ConstraintViolation(
					"ifStatementAssignmentsAfter", this));
		}
		if (!this.ifStatementEnclosedStatements()) {
			violations.add(new ConstraintViolation(
					"ifStatementEnclosedStatements", this));
		}
		if (!this.ifStatementIsAssuredDerivation()) {
			violations.add(new ConstraintViolation(
					"ifStatementIsAssuredDerivation", this));
		}
		if (!this.ifStatementIsDeterminateDerivation()) {
			violations.add(new ConstraintViolation(
					"ifStatementIsDeterminateDerivation", this));
		}
		Collection<ConcurrentClauses> nonFinalClauses = this
				.getNonFinalClauses();
		if (nonFinalClauses != null) {
			for (Object _nonFinalClauses : nonFinalClauses.toArray()) {
				((ConcurrentClauses) _nonFinalClauses)
						.checkConstraints(violations);
			}
		}
		Block finalClause = this.getFinalClause();
		if (finalClause != null) {
			finalClause.checkConstraints(violations);
		}
	}

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		if (includeDerived) {
			s.append(" /isAssured:");
			s.append(this.getIsAssured());
		}
		if (includeDerived) {
			s.append(" /isDeterminate:");
			s.append(this.getIsDeterminate());
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
		List<ConcurrentClauses> nonFinalClauses = this.getNonFinalClauses();
		if (nonFinalClauses != null && nonFinalClauses.size() > 0) {
			System.out.println(prefix + " nonFinalClauses:");
			for (Object _object : nonFinalClauses.toArray()) {
				ConcurrentClauses _nonFinalClauses = (ConcurrentClauses) _object;
				if (_nonFinalClauses != null) {
					_nonFinalClauses.print(prefix + "  ", includeDerived);
				} else {
					System.out.println(prefix + "  null");
				}
			}
		}
		Block finalClause = this.getFinalClause();
		if (finalClause != null) {
			System.out.println(prefix + " finalClause:");
			finalClause.print(prefix + "  ", includeDerived);
		}
	}
} // IfStatement
