
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.statements.impl.gen;

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

/**
 * A conditional statement that executes (at most) one of a set of clauses based
 * on boolean conditions.
 **/

public class IfStatementImpl extends
		org.modeldriven.alf.syntax.statements.impl.gen.StatementImpl {

	private List<ConcurrentClauses> nonFinalClauses = new ArrayList<ConcurrentClauses>();
	private Block finalClause = null;
	private Boolean isAssured = null; // DERIVED
	private Boolean isDetermined = null; // DERIVED

	public IfStatementImpl(IfStatement self) {
		super(self);
	}

	public IfStatement getSelf() {
		return (IfStatement) this.self;
	}

	public List<ConcurrentClauses> getNonFinalClauses() {
		return this.nonFinalClauses;
	}

	public void setNonFinalClauses(List<ConcurrentClauses> nonFinalClauses) {
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
			this.setIsAssured(this.deriveIsAssured());
		}
		return this.isAssured;
	}

	public void setIsAssured(Boolean isAssured) {
		this.isAssured = isAssured;
	}

	public Boolean getIsDetermined() {
		if (this.isDetermined == null) {
			this.setIsDetermined(this.deriveIsDetermined());
		}
		return this.isDetermined;
	}

	public void setIsDetermined(Boolean isDetermined) {
		this.isDetermined = isDetermined;
	}

	protected Boolean deriveIsAssured() {
		return null; // STUB
	}

	protected Boolean deriveIsDetermined() {
		return null; // STUB
	}

	/**
	 * The assignments before all the non-final clauses of an if statement are
	 * the same as the assignments before the if statement. If the statement has
	 * a final clause, then the assignments before that clause are also the same
	 * as the assignments before the if statement.
	 **/
	public boolean ifStatementAssignmentsBefore() {
		return true;
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
		return true;
	}

	/**
	 * The enclosing statement of all the statements in the bodies of all
	 * non-final clauses and in the final clause (if any) of an if statement is
	 * the if statement.
	 **/
	public boolean ifStatementEnclosedStatements() {
		return true;
	}

	/**
	 * An if statement is assured if it has an @assured annotation.
	 **/
	public boolean ifStatementIsAssuredDerivation() {
		this.getSelf().getIsAssured();
		return true;
	}

	/**
	 * An if statement is determined if it has an @determined annotation.
	 **/
	public boolean ifStatementIsDeterminedDerivation() {
		this.getSelf().getIsDetermined();
		return true;
	}

	/**
	 * In addition to an @isolated annotation, an if statement may have @assured
	 * and @determined annotations. They may not have arguments.
	 **/
	public Boolean annotationAllowed(Annotation annotation) {
		return false; // STUB
	} // annotationAllowed

} // IfStatementImpl
