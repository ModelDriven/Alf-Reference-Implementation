/*******************************************************************************
 * Copyright 2011, 2018 Model Driven Solutions, Inc.
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
import org.modeldriven.alf.syntax.expressions.*;
import java.util.Collection;
import org.modeldriven.alf.syntax.statements.impl.WhileStatementImpl;

/**
 * A looping statement for which the continuation condition is first tested
 * before the first iteration.
 **/

public class WhileStatement extends Statement {

	public WhileStatement() {
		this.impl = new WhileStatementImpl(this);
	}

	public WhileStatement(Parser parser) {
		this();
		this.init(parser);
	}

	public WhileStatement(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
    public WhileStatementImpl getImpl() {
		return (WhileStatementImpl) this.impl;
	}

	public Block getBody() {
		return this.getImpl().getBody();
	}

	public void setBody(Block body) {
		this.getImpl().setBody(body);
	}

	public Expression getCondition() {
		return this.getImpl().getCondition();
	}

	public void setCondition(Expression condition) {
		this.getImpl().setCondition(condition);
	}

	/**
     * The assignments before the condition expression of a while statement are
     * the same as the assignments before the while statement, except that any
     * local names with a multiplicity lower bound of 0 after the block are
     * adjusted to also have a multiplicity lower bound of 0 before the
     * condition expression. The assignments before the block of the while
     * statement are the same as the assignments after the condition expression,
     * adjusted for known null and non-null names and type classifications due
     * to the condition expression being true.
	 **/
	public boolean whileStatementAssignmentsBefore() {
		return this.getImpl().whileStatementAssignmentsBefore();
	}

    /**
     * If the assigned source for a name after the block of a while statement is
     * different than before the while statement, then the assigned source of
     * the name after the while statement is the while statement. Otherwise it
     * is the same as before the while statement. If a name is unassigned before
     * the block of a while statement and assigned after the block, then it has
     * multiplicity lower bound of 0 after the while statement. Otherwise, the
     * assignments after the while statement are adjusted for known null and non-
     * null names and type classifications due to the condition expression being
     * false.
     **/
	public boolean whileStatementAssignmentsAfter() {
		return this.getImpl().whileStatementAssignmentsAfter();
	}

    /**
     * The condition expression of a while statement must have a type that
     * conforms to type Boolean and multiplicity [1..1].
     **/
	public boolean whileStatementCondition() {
		return this.getImpl().whileStatementCondition();
	}

	/**
	 * The enclosing statement for all statements in the body of a while
	 * statement are the while statement.
	 **/
	public boolean whileStatementEnclosedStatements() {
		return this.getImpl().whileStatementEnclosedStatements();
	}

    @Override
    public void _addExternalReferences(Collection<ExternalElementReference> references) {
        super._addExternalReferences(references);
        addExternalReferencesFor(references, this.getBody());
        addExternalReferencesFor(references, this.getCondition());
    }

	@Override
    public void _deriveAll() {
		super._deriveAll();
		Block body = this.getBody();
		if (body != null) {
			body.deriveAll();
		}
		Expression condition = this.getCondition();
		if (condition != null) {
			condition.deriveAll();
		}
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.whileStatementAssignmentsBefore()) {
			violations.add(new ConstraintViolation(
					"whileStatementAssignmentsBefore", this));
		}
		if (!this.whileStatementAssignmentsAfter()) {
			violations.add(new ConstraintViolation(
					"whileStatementAssignmentsAfter", this));
		}
		if (!this.whileStatementCondition()) {
			violations.add(new ConstraintViolation("whileStatementCondition",
					this));
		}
		if (!this.whileStatementEnclosedStatements()) {
			violations.add(new ConstraintViolation(
					"whileStatementEnclosedStatements", this));
		}
		Block body = this.getBody();
		if (body != null) {
			body.checkConstraints(violations);
		}
		Expression condition = this.getCondition();
		if (condition != null) {
			condition.checkConstraints(violations);
		}
	}

	@Override
    public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		return s.toString();
	}

	@Override
    public void print() {
		this.print("", false);
	}

	@Override
    public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	@Override
    public void print(String prefix, boolean includeDerived) {
		super.print(prefix, includeDerived);
		Block body = this.getBody();
		if (body != null) {
			System.out.println(prefix + " body:");
			body.print(prefix + "  ", includeDerived);
		}
		Expression condition = this.getCondition();
		if (condition != null) {
			System.out.println(prefix + " condition:");
			condition.print(prefix + "  ", includeDerived);
		}
	}
} // WhileStatement
