
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

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
 * A statement that provides a value for the return parameter of an activity.
 **/

public class ReturnStatementImpl extends
		org.modeldriven.alf.syntax.statements.impl.gen.StatementImpl {

	private Expression expression = null;
	private ElementReference behavior = null; // DERIVED

	public ReturnStatementImpl(ReturnStatement self) {
		super(self);
	}

	public ReturnStatement getSelf() {
		return (ReturnStatement) this.self;
	}

	public Expression getExpression() {
		return this.expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	public ElementReference getBehavior() {
		if (this.behavior == null) {
			this.setBehavior(this.deriveBehavior());
		}
		return this.behavior;
	}

	public void setBehavior(ElementReference behavior) {
		this.behavior = behavior;
	}

	protected ElementReference deriveBehavior() {
		return null; // STUB
	}

	/**
	 * If the behavior containing the return statement has a return parameter,
	 * then the return statement must have an expression, and the expression
	 * must be assignable to that return parameter.
	 **/
	public boolean returnStatementContext() {
		return true;
	}

	/**
	 * The assignments before the expression of a return statement are the same
	 * as the assignments before the statement.
	 **/
	public boolean returnStatementAssignmentsBefore() {
		return true;
	}

	/**
	 * The assignments after a return statement are the same as the assignments
	 * after the expression of the return statement.
	 **/
	public boolean returnStatementAssignmentsAfter() {
		return true;
	}

} // ReturnStatementImpl
