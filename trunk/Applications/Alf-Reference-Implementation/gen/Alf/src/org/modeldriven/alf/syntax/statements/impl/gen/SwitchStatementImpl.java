
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
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
import java.util.TreeSet;

/**
 * A statement that executes (at most) one of a set of statement sequences based
 * on matching a switch value to a set of test cases.
 **/

public class SwitchStatementImpl extends
		org.modeldriven.alf.syntax.statements.impl.gen.StatementImpl {

	private Collection<SwitchClause> nonDefaultClause = new ArrayList<SwitchClause>();
	private Expression expression = null;
	private Block defaultClause = null;
	private Boolean isAssured = null; // DERIVED
	private Boolean isDeterminate = null; // DERIVED

	public SwitchStatementImpl(SwitchStatement self) {
		super(self);
	}

	public SwitchStatement getSelf() {
		return (SwitchStatement) this.self;
	}

	public Collection<SwitchClause> getNonDefaultClause() {
		return this.nonDefaultClause;
	}

	public void setNonDefaultClause(Collection<SwitchClause> nonDefaultClause) {
		this.nonDefaultClause = nonDefaultClause;
	}

	public void addNonDefaultClause(SwitchClause nonDefaultClause) {
		this.nonDefaultClause.add(nonDefaultClause);
	}

	public Expression getExpression() {
		return this.expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	public Block getDefaultClause() {
		return this.defaultClause;
	}

	public void setDefaultClause(Block defaultClause) {
		this.defaultClause = defaultClause;
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

	public Boolean getIsDeterminate() {
		if (this.isDeterminate == null) {
			this.setIsDeterminate(this.deriveIsDeterminate());
		}
		return this.isDeterminate;
	}

	public void setIsDeterminate(Boolean isDeterminate) {
		this.isDeterminate = isDeterminate;
	}

	protected Boolean deriveIsAssured() {
		return null; // STUB
	}

	protected Boolean deriveIsDeterminate() {
		return null; // STUB
	}

	/**
	 * The assignments before all clauses of a switch statement are the same as
	 * the assignments after the expression of the switch statement.
	 **/
	public boolean switchStatementAssignmentsBefore() {
		return true;
	}

	/**
	 * The same local name may not be assigned in more than one case expression
	 * in a switch statement.
	 **/
	public boolean switchStatementCaseAssignments() {
		return true;
	}

	/**
	 * If a name has an assigned source after any clause of a switch statement
	 * that is different than before that clause (including newly defined
	 * names), the assigned source after the switch statement is the switch
	 * statement. Otherwise, the assigned source of a name after the switch
	 * statement is the same as before the switch statement.
	 **/
	public boolean switchStatementAssignmentsAfter() {
		return true;
	}

	/**
	 * If a switch statement does not have a final default clause, then any name
	 * that is not an out parameter and is unassigned before the switch
	 * statement is unassigned after the switch statement. If a switch statement
	 * does have a final default clause, then any name that is unassigned before
	 * the switch statement and is assigned after any one clause of the switch
	 * statement must also be assigned after every other clause. The type of
	 * such names after the switch statement is the effective common ancestor of
	 * the types of the name in each clause with a multiplicity lower bound that
	 * is the minimum of the lower bound for the name in each clause and a
	 * multiplicity upper bound that is the maximum for the name in each clause.
	 **/
	public boolean switchStatementAssignments() {
		return true;
	}

	/**
	 * A switch statement expression must have a multiplicity no greater than 1.
	 **/
	public boolean switchStatementExpression() {
		return true;
	}

	/**
	 * A switch statement is the enclosing statement for the statements in all
	 * of its switch clauses.
	 **/
	public boolean switchStatementEnclosedStatements() {
		return true;
	}

	/**
	 * A switch statement is determinate if it has a @determinate annotation.
	 **/
	public boolean switchStatementIsDeterminateDerivation() {
		this.getSelf().getIsDeterminate();
		return true;
	}

	/**
	 * A switch statement is assured if it has an @assured annotation.
	 **/
	public boolean switchStatementIsAssuredDerivation() {
		this.getSelf().getIsAssured();
		return true;
	}

	/**
	 * In addition to an @isolated annotation, a switch statement may have @assured
	 * and @determinate annotations. They may not have arguments.
	 **/
	public Boolean annotationAllowed(Annotation annotation) {
		return false; // STUB
	} // annotationAllowed

} // SwitchStatementImpl
