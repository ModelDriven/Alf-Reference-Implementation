
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package alf.syntax.expressions;

import alf.syntax.behavioral.*;
import alf.syntax.expressions.*;
import alf.syntax.namespaces.*;
import alf.syntax.nodes.*;
import alf.syntax.structural.*;

import java.util.ArrayList;

public class ReductionExpression extends Expression {

	private String operator = "";
	private QualifiedName behavior = null;
	private Expression argument = null;

	public ReductionExpression(String operator, QualifiedName behavior,
			Expression argument) {
		this.operator = operator;
		this.behavior = behavior;
		this.argument = argument;
	} // ReductionExpression

	public String getOperator() {
		return this.operator;
	} // getOperator

	public QualifiedName getBehavior() {
		return this.behavior;
	} // getBehavior

	public Expression getArgument() {
		return this.argument;
	} // getArgument

	public String toString() {
		return super.toString() + " operator:" + this.getOperator();
	} // toString

	public void print(String prefix) {
		super.print(prefix);
		this.getBehavior().printChild(prefix);
		this.getArgument().printChild(prefix);
	} // print

} // ReductionExpression
