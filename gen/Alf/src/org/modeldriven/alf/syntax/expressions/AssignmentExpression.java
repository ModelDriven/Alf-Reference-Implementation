
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

public class AssignmentExpression extends Expression {

	private String operator = "";
	private LeftHandSide leftHandSide = null;
	private Expression rightHandSide = null;

	public AssignmentExpression(LeftHandSide leftHandSide, String operator,
			Expression rightHandSide) {
		this.leftHandSide = leftHandSide;
		this.operator = operator;
		this.rightHandSide = rightHandSide;
	} // AssignmentExpression

	public LeftHandSide getLeftHandSide() {
		return this.leftHandSide;
	} // getLeftHandSide

	public String getOperator() {
		return this.operator;
	} // getOperator

	public Expression getRightHandSide() {
		return this.rightHandSide;
	} // getRightHandSide

	public String toString() {
		return super.toString() + " operator:" + this.getOperator();
	} // toString

	public void print(String prefix) {
		super.print(prefix);
		this.getLeftHandSide().printChild(prefix);
		this.getRightHandSide().printChild(prefix);
	} // print

} // AssignmentExpression
