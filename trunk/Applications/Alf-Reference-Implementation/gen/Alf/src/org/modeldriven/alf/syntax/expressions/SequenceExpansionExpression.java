
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

/**
 * An expression used to carry out one of a predefined set of operations over
 * each of the elements in a sequence.
 **/

public abstract class SequenceExpansionExpression extends Expression implements
		ISequenceExpansionExpression {

	private String operation = "";
	private String variable = "";
	private IExpression argument = null;
	private IExtentOrExpression primary = null;

	public String getOperation() {
		return this.operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getVariable() {
		return this.variable;
	}

	public void setVariable(String variable) {
		this.variable = variable;
	}

	public IExpression getArgument() {
		return this.argument;
	}

	public void setArgument(IExpression argument) {
		this.argument = argument;
	}

	public IExtentOrExpression getPrimary() {
		return this.primary;
	}

	public void setPrimary(IExtentOrExpression primary) {
		this.primary = primary;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" operation:");
		s.append(this.getOperation());
		s.append(" variable:");
		s.append(this.getVariable());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		IExpression argument = this.getArgument();
		if (argument != null) {
			argument.print(prefix + " ");
		}
		IExtentOrExpression primary = this.getPrimary();
		if (primary != null) {
			primary.print(prefix + " ");
		}
	}
} // SequenceExpansionExpression
