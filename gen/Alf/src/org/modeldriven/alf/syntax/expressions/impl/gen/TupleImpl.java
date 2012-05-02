
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions.impl.gen;

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
 * A list of expressions used to provide the arguments for an invocation.
 **/

public abstract class TupleImpl extends
		org.modeldriven.alf.syntax.common.impl.gen.SyntaxElementImpl {

	private Collection<NamedExpression> input = null; // DERIVED
	private InvocationExpression invocation = null;
	private Collection<OutputNamedExpression> output = null; // DERIVED

	public TupleImpl(Tuple self) {
		super(self);
	}

	public Tuple getSelf() {
		return (Tuple) this.self;
	}

	public Collection<NamedExpression> getInput() {
		if (this.input == null) {
			this.setInput(this.deriveInput());
		}
		return this.input;
	}

	public void setInput(Collection<NamedExpression> input) {
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

	public Collection<OutputNamedExpression> getOutput() {
		if (this.output == null) {
			this.setOutput(this.deriveOutput());
		}
		return this.output;
	}

	public void setOutput(Collection<OutputNamedExpression> output) {
		this.output = output;
	}

	public void addOutput(OutputNamedExpression output) {
		this.output.add(output);
	}

	protected Collection<NamedExpression> deriveInput() {
		return null; // STUB
	}

	protected Collection<OutputNamedExpression> deriveOutput() {
		return null; // STUB
	}

	/**
	 * A tuple has the same number of inputs as its invocation has input
	 * parameters. For each input parameter, the tuple has a corresponding input
	 * with the same name as the parameter and an expression that is the
	 * matching argument from the tuple, or an empty sequence construction
	 * expression if there is no matching argument.
	 **/
	public boolean tupleInputDerivation() {
		this.getSelf().getInput();
		return true;
	}

	/**
	 * A tuple has the same number of outputs as its invocation has output
	 * parameters. For each output parameter, the tuple has a corresponding
	 * output with the same name as the parameter and an expression that is the
	 * matching argument from the tuple, or an empty sequence construction
	 * expression if there is no matching argument.
	 **/
	public boolean tupleOutputDerivation() {
		this.getSelf().getOutput();
		return true;
	}

	/**
	 * An input parameter may only have a null argument if it has a multiplicity
	 * lower bound of 0.
	 **/
	public boolean tupleNullInputs() {
		return true;
	}

	/**
	 * An output parameter may only have a null argument if it is an out
	 * parameter.
	 **/
	public boolean tupleOutputs() {
		return true;
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
		return true;
	}

	/**
	 * A name may be assigned in at most one argument expression of a tuple.
	 **/
	public boolean tupleAssignmentsAfter() {
		return true;
	}

} // TupleImpl
