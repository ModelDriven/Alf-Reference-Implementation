
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

public class SequenceExpansionExpression extends Expression {

	private Tuple tuple = null;
	private Expression sequence = null;
	private String operation = "";
	private String variable = "";

	public SequenceExpansionExpression(Expression sequence, String operation,
			String variable, Tuple tuple) {
		this.sequence = sequence;
		this.operation = operation;
		this.variable = variable;
		this.tuple = tuple;
	} // SequenceExpansionExpression

	public Expression getSequence() {
		return this.sequence;
	} // getSequence

	public String getOperation() {
		return this.operation;
	} // getOperation

	public String getVariable() {
		return this.variable;
	} // getVariable

	public Tuple getTuple() {
		return this.tuple;
	} // getTuple

	public String toString() {
		return super.toString() + " operation:" + this.getOperation()
				+ " variable:" + this.getVariable();
	} // toString

	public void print(String prefix) {
		super.print(prefix);
		this.getSequence().printChild(prefix);
		this.getTuple().printChild(prefix);

	} // print

} // SequenceExpansionExpression
