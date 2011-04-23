
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
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.statements.impl.SwitchStatementImpl;

/**
 * A statement that executes (at most) one of a set of statement sequences based
 * on matching a switch value to a set of test cases.
 **/

public class SwitchStatement extends Statement {

	public SwitchStatement() {
		this.impl = new SwitchStatementImpl(this);
	}

	public SwitchStatementImpl getImpl() {
		return (SwitchStatementImpl) this.impl;
	}

	public Collection<SwitchClause> getNonDefaultClause() {
		return this.getImpl().getNonDefaultClause();
	}

	public void setNonDefaultClause(Collection<SwitchClause> nonDefaultClause) {
		this.getImpl().setNonDefaultClause(nonDefaultClause);
	}

	public void addNonDefaultClause(SwitchClause nonDefaultClause) {
		this.getImpl().addNonDefaultClause(nonDefaultClause);
	}

	public Expression getExpression() {
		return this.getImpl().getExpression();
	}

	public void setExpression(Expression expression) {
		this.getImpl().setExpression(expression);
	}

	public Block getDefaultClause() {
		return this.getImpl().getDefaultClause();
	}

	public void setDefaultClause(Block defaultClause) {
		this.getImpl().setDefaultClause(defaultClause);
	}

	public Boolean getIsAssured() {
		return this.getImpl().getIsAssured();
	}

	public void setIsAssured(Boolean isAssured) {
		this.getImpl().setIsAssured(isAssured);
	}

	public Boolean getIsDetermined() {
		return this.getImpl().getIsDetermined();
	}

	public void setIsDetermined(Boolean isDetermined) {
		this.getImpl().setIsDetermined(isDetermined);
	}

	/**
	 * The assignments before all clauses of a switch statement are the same as
	 * the assignments before the switch statement.
	 **/
	public boolean switchStatementAssignmentsBefore() {
		return this.getImpl().switchStatementAssignmentsBefore();
	}

	/**
	 * The same local name may not be assigned in more than one case expression
	 * in a switch statement.
	 **/
	public boolean switchStatementCaseAssignments() {
		return this.getImpl().switchStatementCaseAssignments();
	}

	/**
	 * If a name has an assigned source after any clause of a switch statement
	 * that is different than before that clause (including newly defined
	 * names), the assigned source after the switch statement is the switch
	 * statement. Otherwise, the assigned source of a name after the switch
	 * statement is the same as before the switch statement.
	 **/
	public boolean switchStatementAssignmentsAfter() {
		return this.getImpl().switchStatementAssignmentsAfter();
	}

	/**
	 * If a switch statement does not have a final default clause, then any name
	 * that is unassigned before the switch statement is unassigned after the
	 * switch statement. If a switch statement does have a final default clause,
	 * then any name that is unassigned before the switch statement and is
	 * assigned after any one clause of the switch statement must also be
	 * assigned after every other clause. The type of such names after the
	 * switch statement is the effective common ancestor of the types of the
	 * name in each clause with a multiplicity lower bound that is the minimum
	 * of the lower bound for the name in each clause and a multiplicity upper
	 * bound that is the maximum for the name in each clause.
	 **/
	public boolean switchStatementAssignments() {
		return this.getImpl().switchStatementAssignments();
	}

	public boolean switchStatementExpression() {
		return this.getImpl().switchStatementExpression();
	}

	public boolean switchStatementEnclosedStatements() {
		return this.getImpl().switchStatementEnclosedStatements();
	}

	/**
	 * An switch statement is determined if it has an @determined annotation.
	 **/
	public boolean switchStatementIsDeterminedDerivation() {
		return this.getImpl().switchStatementIsDeterminedDerivation();
	}

	/**
	 * An switch statement is assured if it has an @assured annotation.
	 **/
	public boolean switchStatementIsAssuredDerivation() {
		return this.getImpl().switchStatementIsAssuredDerivation();
	}

	/**
	 * In addition to an @isolated annotation, a switch statement may have @assured
	 * and @determined annotations. They may not have arguments.
	 **/
	public Boolean annotationAllowed(Annotation annotation) {
		return this.getImpl().annotationAllowed(annotation);
	}

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.switchStatementAssignmentsBefore()) {
			violations.add(new ConstraintViolation(
					"switchStatementAssignmentsBefore", this));
		}
		if (!this.switchStatementCaseAssignments()) {
			violations.add(new ConstraintViolation(
					"switchStatementCaseAssignments", this));
		}
		if (!this.switchStatementAssignmentsAfter()) {
			violations.add(new ConstraintViolation(
					"switchStatementAssignmentsAfter", this));
		}
		if (!this.switchStatementAssignments()) {
			violations.add(new ConstraintViolation(
					"switchStatementAssignments", this));
		}
		if (!this.switchStatementExpression()) {
			violations.add(new ConstraintViolation("switchStatementExpression",
					this));
		}
		if (!this.switchStatementEnclosedStatements()) {
			violations.add(new ConstraintViolation(
					"switchStatementEnclosedStatements", this));
		}
		if (!this.switchStatementIsDeterminedDerivation()) {
			violations.add(new ConstraintViolation(
					"switchStatementIsDeterminedDerivation", this));
		}
		if (!this.switchStatementIsAssuredDerivation()) {
			violations.add(new ConstraintViolation(
					"switchStatementIsAssuredDerivation", this));
		}
		for (SwitchClause _nonDefaultClause : this.getNonDefaultClause()) {
			_nonDefaultClause.checkConstraints(violations);
		}
		Expression expression = this.getExpression();
		if (expression != null) {
			expression.checkConstraints(violations);
		}
		Block defaultClause = this.getDefaultClause();
		if (defaultClause != null) {
			defaultClause.checkConstraints(violations);
		}
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

	public void print() {
		this.print("");
	}

	public void print(String prefix) {
		super.print(prefix);
		Collection<SwitchClause> nonDefaultClause = this.getNonDefaultClause();
		if (nonDefaultClause != null) {
			if (nonDefaultClause.size() > 0) {
				System.out.println(prefix + " nonDefaultClause:");
			}
			for (SwitchClause _nonDefaultClause : nonDefaultClause) {
				if (_nonDefaultClause != null) {
					_nonDefaultClause.print(prefix + "  ");
				} else {
					System.out.println(prefix + "  null");
				}
			}
		}
		Expression expression = this.getExpression();
		if (expression != null) {
			System.out.println(prefix + " expression:");
			expression.print(prefix + "  ");
		}
		Block defaultClause = this.getDefaultClause();
		if (defaultClause != null) {
			System.out.println(prefix + " defaultClause:");
			defaultClause.print(prefix + "  ");
		}
	}
} // SwitchStatement
