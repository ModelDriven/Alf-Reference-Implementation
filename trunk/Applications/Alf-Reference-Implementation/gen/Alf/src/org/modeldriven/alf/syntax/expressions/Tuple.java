
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.parser.AlfParser;
import org.modeldriven.alf.parser.Token;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.expressions.impl.TupleImpl;
import org.modeldriven.uml.Element;
import org.modeldriven.uml.Profile;
import org.modeldriven.uml.Stereotype;

/**
 * A list of expressions used to provide the arguments for an invocation.
 **/

public abstract class Tuple extends SyntaxElement {

	public Tuple() {
	}

	public Tuple(AlfParser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public Tuple(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public TupleImpl getImpl() {
		return (TupleImpl) this.impl;
	}

	public Collection<NamedExpression> getInput() {
		return this.getImpl().getInput();
	}

	public void setInput(Collection<NamedExpression> input) {
		this.getImpl().setInput(input);
	}

	public void addInput(NamedExpression input) {
		this.getImpl().addInput(input);
	}

	public InvocationExpression getInvocation() {
		return this.getImpl().getInvocation();
	}

	public void setInvocation(InvocationExpression invocation) {
		this.getImpl().setInvocation(invocation);
	}

	public Collection<OutputNamedExpression> getOutput() {
		return this.getImpl().getOutput();
	}

	public void setOutput(Collection<OutputNamedExpression> output) {
		this.getImpl().setOutput(output);
	}

	public void addOutput(OutputNamedExpression output) {
		this.getImpl().addOutput(output);
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

	public void _deriveAll() {
		this.getInput();
		this.getOutput();
		super._deriveAll();
		Collection<NamedExpression> input = this.getInput();
		if (input != null) {
			for (Object _input : input.toArray()) {
				((NamedExpression) _input).deriveAll();
			}
		}
		Collection<OutputNamedExpression> output = this.getOutput();
		if (output != null) {
			for (Object _output : output.toArray()) {
				((OutputNamedExpression) _output).deriveAll();
			}
		}
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.tupleInputDerivation()) {
			violations
					.add(new ConstraintViolation("tupleInputDerivation", this));
		}
		if (!this.tupleOutputDerivation()) {
			violations.add(new ConstraintViolation("tupleOutputDerivation",
					this));
		}
		if (!this.tupleNullInputs()) {
			violations.add(new ConstraintViolation("tupleNullInputs", this));
		}
		if (!this.tupleOutputs()) {
			violations.add(new ConstraintViolation("tupleOutputs", this));
		}
		if (!this.tupleAssignmentsBefore()) {
			violations.add(new ConstraintViolation("tupleAssignmentsBefore",
					this));
		}
		if (!this.tupleAssignmentsAfter()) {
			violations.add(new ConstraintViolation("tupleAssignmentsAfter",
					this));
		}
		Collection<NamedExpression> input = this.getInput();
		if (input != null) {
			for (Object _input : input.toArray()) {
				((NamedExpression) _input).checkConstraints(violations);
			}
		}
		Collection<OutputNamedExpression> output = this.getOutput();
		if (output != null) {
			for (Object _output : output.toArray()) {
				((OutputNamedExpression) _output).checkConstraints(violations);
			}
		}
	}

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		return s.toString();
	}

	public void print() {
		this.print("", false);
	}

	public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	public void print(String prefix, boolean includeDerived) {
		super.print(prefix, includeDerived);
		if (includeDerived) {
			Collection<NamedExpression> input = this.getInput();
			if (input != null && input.size() > 0) {
				System.out.println(prefix + " /input:");
				for (Object _object : input.toArray()) {
					NamedExpression _input = (NamedExpression) _object;
					if (_input != null) {
						_input.print(prefix + "  ", includeDerived);
					} else {
						System.out.println(prefix + "  null");
					}
				}
			}
		}
		InvocationExpression invocation = this.getInvocation();
		if (invocation != null) {
			System.out.println(prefix + " invocation:"
					+ invocation.toString(includeDerived));
		}
		if (includeDerived) {
			Collection<OutputNamedExpression> output = this.getOutput();
			if (output != null && output.size() > 0) {
				System.out.println(prefix + " /output:");
				for (Object _object : output.toArray()) {
					OutputNamedExpression _output = (OutputNamedExpression) _object;
					if (_output != null) {
						_output.print(prefix + "  ", includeDerived);
					} else {
						System.out.println(prefix + "  null");
					}
				}
			}
		}
	}
} // Tuple
