
/*
 * Copyright 2010 Data Access Technologies, Inc. (Model Driven Solutions)
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
 * A list of expressions used to provide the arguments for an invocation.
 **/

public abstract class Tuple extends SyntaxElement {

	private ArrayList<NamedExpression> input = new ArrayList<NamedExpression>(); // DERIVED
	private InvocationExpression invocation = null;
	private ArrayList<OutputNamedExpression> output = new ArrayList<OutputNamedExpression>(); // DERIVED

	public ArrayList<NamedExpression> getInput() {
		return this.input;
	}

	public void setInput(ArrayList<NamedExpression> input) {
		this.input = input;
	}

	public void addInput(NamedExpression input) {
		this.input.add(input);
	}

	public InvocationExpression getInvocation() {
		return this.invocation;
	}

	public void setInvocation(InvocationExpression invocation) {
		this.invocation = invocation;
	}

	public ArrayList<OutputNamedExpression> getOutput() {
		return this.output;
	}

	public void setOutput(ArrayList<OutputNamedExpression> output) {
		this.output = output;
	}

	public void addOutput(OutputNamedExpression output) {
		this.output.add(output);
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		if (this.invocation != null) {
			this.invocation.print(prefix + " ");
		}
	}
} // Tuple
