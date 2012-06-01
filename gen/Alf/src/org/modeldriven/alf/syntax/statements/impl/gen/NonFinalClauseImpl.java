
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for under the terms of the GNU General Public License 
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

/**
 * A clause of an if statement with a conditional expression and a sequence of
 * statements that may be executed if the condition is true.
 **/

public class NonFinalClauseImpl extends
		org.modeldriven.alf.syntax.common.impl.gen.SyntaxElementImpl {

	private Expression condition = null;
	private Block body = null;

	public NonFinalClauseImpl(NonFinalClause self) {
		super(self);
	}

	public NonFinalClause getSelf() {
		return (NonFinalClause) this.self;
	}

	public Expression getCondition() {
		return this.condition;
	}

	public void setCondition(Expression condition) {
		this.condition = condition;
	}

	public Block getBody() {
		return this.body;
	}

	public void setBody(Block body) {
		this.body = body;
	}

	/**
	 * The assignments before the body of a non-final clause are the assignments
	 * after the condition.
	 **/
	public boolean nonFinalClauseAssignmentsBeforeBody() {
		return true;
	}

	/**
	 * If a name is unassigned before the condition expression of a non-final
	 * clause, then it must be unassigned after that expression (i.e., new local
	 * names may not be defined in the condition).
	 **/
	public boolean nonFinalClauseConditionLocalNames() {
		return true;
	}

	/**
	 * The condition of a non-final clause must have type Boolean and a
	 * multiplicity upper bound no greater than 1.
	 **/
	public boolean nonFinalClauseConditionType() {
		return true;
	}

	/**
	 * The assignments before a non-final clause are the assignments before the
	 * condition of the clause.
	 **/
	public Collection<AssignedSource> assignmentsBefore() {
		return new ArrayList<AssignedSource>(); // STUB
	} // assignmentsBefore

	/**
	 * The assignments after a non-final clause are the assignments after the
	 * block of the clause.
	 **/
	public Collection<AssignedSource> assignmentsAfter() {
		return new ArrayList<AssignedSource>(); // STUB
	} // assignmentsAfter

} // NonFinalClauseImpl
