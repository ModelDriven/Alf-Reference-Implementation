
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

import org.omg.uml.*;

import java.util.ArrayList;

import org.modeldriven.alf.syntax.expressions.impl.TupleImpl;

/**
 * A list of expressions used to provide the arguments for an invocation.
 **/

public abstract class Tuple extends SyntaxElement {

	private ArrayList<NamedExpression> input = null; // DERIVED
	private InvocationExpression invocation = null;
	private ArrayList<OutputNamedExpression> output = null; // DERIVED

	public TupleImpl getImpl() {
		return (TupleImpl) this.impl;
	}

	public ArrayList<NamedExpression> getInput() {
		if (this.input == null) {
			this.input = this.getImpl().deriveInput();
		}
		return this.input;
	}

	public InvocationExpression getInvocation() {
		return this.invocation;
	}

	public void setInvocation(InvocationExpression invocation) {
		this.invocation = invocation;
	}

	public ArrayList<OutputNamedExpression> getOutput() {
		if (this.output == null) {
			this.output = this.getImpl().deriveOutput();
		}
		return this.output;
	}

	/**
	 * A tuple has the same number of inputs as its invocation has input
	 * parameters. For each input parameter, the tuple has a corresponding input
	 * with the same name as the parameter and an expression that is the
	 * matching argument from the tuple, or an empty sequence construction
	 * expression if there is no matching argument.
	 **/
	public boolean tupleInputDerivation() {
		return this.getImpl().tupleInputDerivation();
	}

	/**
	 * A tuple has the same number of outputs as its invocation has output
	 * parameters. For each output parameter, the tuple has a corresponding
	 * output with the same name as the parameter and an expression that is the
	 * matching argument from the tuple, or an empty sequence construction
	 * expression if there is no matching argument.
	 **/
	public boolean tupleOutputDerivation() {
		return this.getImpl().tupleOutputDerivation();
	}

	/**
	 * An input parameter may only have a null argument if it has a multiplicity
	 * lower bound of 0.
	 **/
	public boolean tupleNullInputs() {
		return this.getImpl().tupleNullInputs();
	}

	/**
	 * An output parameter may only have a null argument if it is an out
	 * parameter.
	 **/
	public boolean tupleOutputs() {
		return this.getImpl().tupleOutputs();
	}

	/**
	 * The assignments before each expression in a tuple are the same as the
	 * assignments before the tuple, except in the case of a name expression
	 * that defines a new local name, in which case the assigned source for the
	 * new name is included in the assignments before the name expression. (Note
	 * that the assigned source for a new name is included before the name
	 * expression so that the nameExpressionResolution constraint is not
	 * violated.) The assignments before the tuple are the same as the
	 * assignments after the feature reference of the invocation of the tuple,
	 * if the invocation has one, or otherwise the assignments before the
	 * invocation.
	 **/
	public boolean tupleAssignmentsBefore() {
		return this.getImpl().tupleAssignmentsBefore();
	}

	/**
	 * A name may be assigned in at most one argument expression of a tuple.
	 **/
	public boolean tupleAssignmentsAfter() {
		return this.getImpl().tupleAssignmentsAfter();
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		ArrayList<NamedExpression> input = this.getInput();
		if (input != null) {
			if (input.size() > 0) {
				System.out.println(prefix + " /input:");
			}
			for (NamedExpression item : this.getInput()) {
				System.out.println(prefix + "  " + item);
			}
		}
		InvocationExpression invocation = this.getInvocation();
		if (invocation != null) {
			System.out.println(prefix + " invocation:" + invocation);
		}
		ArrayList<OutputNamedExpression> output = this.getOutput();
		if (output != null) {
			if (output.size() > 0) {
				System.out.println(prefix + " /output:");
			}
			for (OutputNamedExpression item : this.getOutput()) {
				System.out.println(prefix + "  " + item);
			}
		}
	}
} // Tuple
