/*******************************************************************************
 * Copyright 2011, 2017 Data Access Technologies, Inc. (Model Driven Solutions)
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
import org.modeldriven.alf.syntax.statements.impl.NonFinalClauseImpl;

/**
 * A clause of an if statement with a conditional expression and a sequence of
 * statements that may be executed if the condition is true.
 **/

public class NonFinalClause extends SyntaxElement {

	public NonFinalClause() {
		this.impl = new NonFinalClauseImpl(this);
	}

	public NonFinalClause(Parser parser) {
		this();
		this.init(parser);
	}

	public NonFinalClause(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
    public NonFinalClauseImpl getImpl() {
		return (NonFinalClauseImpl) this.impl;
	}

	public Expression getCondition() {
		return this.getImpl().getCondition();
	}

	public void setCondition(Expression condition) {
		this.getImpl().setCondition(condition);
	}

	public Block getBody() {
		return this.getImpl().getBody();
	}

	public void setBody(Block body) {
		this.getImpl().setBody(body);
	}

	/**
     * The assignments before the body of a non-final clause are the assignments
     * after the condition, adjusted for known null and non-null names and type
     * classifications due to the condition being true.
	 **/
	public boolean nonFinalClauseAssignmentsBeforeBody() {
		return this.getImpl().nonFinalClauseAssignmentsBeforeBody();
	}

	/**
	 * If a name is unassigned before the condition expression of a non-final
	 * clause, then it must be unassigned after that expression (i.e., new local
	 * names may not be defined in the condition).
	 **/
	public boolean nonFinalClauseConditionLocalNames() {
		return this.getImpl().nonFinalClauseConditionLocalNames();
	}

    /**
     * The condition of a non-final clause must have that conforms to type
     * Boolean and multiplicity [1..1].
     **/
	public boolean nonFinalClauseConditionType() {
		return this.getImpl().nonFinalClauseConditionType();
	}

	/**
	 * The assignments before a non-final clause are the assignments before the
	 * condition of the clause.
	 **/
	public Collection<AssignedSource> assignmentsBefore() {
		return this.getImpl().assignmentsBefore();
	}

	/**
	 * The assignments after a non-final clause are the assignments after the
	 * block of the clause.
	 **/
	public Collection<AssignedSource> assignmentsAfter() {
		return this.getImpl().assignmentsAfter();
	}

    @Override
    public void _addExternalReferences(Collection<ExternalElementReference> references) {
        super._addExternalReferences(references);
        addExternalReferencesFor(references, this.getCondition());
        addExternalReferencesFor(references, this.getBody());
    }

	@Override
    public void _deriveAll() {
		super._deriveAll();
		Expression condition = this.getCondition();
		if (condition != null) {
			condition.deriveAll();
		}
		Block body = this.getBody();
		if (body != null) {
			body.deriveAll();
		}
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.nonFinalClauseAssignmentsBeforeBody()) {
			violations.add(new ConstraintViolation(
					"nonFinalClauseAssignmentsBeforeBody", this));
		}
		if (!this.nonFinalClauseConditionLocalNames()) {
			violations.add(new ConstraintViolation(
					"nonFinalClauseConditionLocalNames", this));
		}
		if (!this.nonFinalClauseConditionType()) {
			violations.add(new ConstraintViolation(
					"nonFinalClauseConditionType", this));
		}
		Expression condition = this.getCondition();
		if (condition != null) {
			condition.checkConstraints(violations);
		}
		Block body = this.getBody();
		if (body != null) {
			body.checkConstraints(violations);
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
		Expression condition = this.getCondition();
		if (condition != null) {
			System.out.println(prefix + " condition:");
			condition.print(prefix + "  ", includeDerived);
		}
		Block body = this.getBody();
		if (body != null) {
			System.out.println(prefix + " body:");
			body.print(prefix + "  ", includeDerived);
		}
	}
} // NonFinalClause
