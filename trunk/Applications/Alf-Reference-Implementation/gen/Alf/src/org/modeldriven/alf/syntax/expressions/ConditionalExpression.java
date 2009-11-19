
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.SyntaxNode;
import org.modeldriven.alf.syntax.behavioral.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.namespaces.*;
import org.modeldriven.alf.syntax.structural.*;

import java.util.ArrayList;

public class ConditionalExpression extends Expression {

	private Expression operand1 = null;
	private Expression operand2 = null;
	private Expression operand3 = null;

	public ConditionalExpression(Expression operand1, Expression operand2,
			Expression operand3) {
		this.operand1 = operand1;
		this.operand2 = operand2;
		this.operand3 = operand3;
	} // ConditionalExpression

	public Expression getOperand1() {
		return this.operand1;
	} // getOperand1

	public Expression getOperand2() {
		return this.operand2;
	} // getOperand2

	public Expression getOperand3() {
		return this.operand3;
	} // getOperand3

	public void print(String prefix) {
		super.print(prefix);
		this.getOperand1().printChild(prefix);
		this.getOperand2().printChild(prefix);
		this.getOperand3().printChild(prefix);
	} // print

} // ConditionalExpression
