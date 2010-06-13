
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.statements;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

public class LoopVariableDefinition extends SyntaxNode {

	private String variable = "";
	private Expression expression1 = null;
	private Expression expression2 = null;
	private QualifiedName typeName = null;
	private boolean typeIsInferred = true;

	public void setVariable(String variable) {
		this.variable = variable;
	} // setVariable

	public String getVariable() {
		return this.variable;
	} // getVariable

	public void setTypeName(QualifiedName typeName) {
		this.typeName = typeName;
	} // setTypeName

	public QualifiedName getTypeName() {
		return this.typeName;
	} // getTypeName

	public void setExpression1(Expression expression) {
		this.expression1 = expression;
	} // setExpression1

	public Expression getExpression1() {
		return this.expression1;
	} // getExpression1

	public void setExpression2(Expression expression) {
		this.expression2 = expression;
	} // setExpression2

	public Expression getExpression2() {
		return this.expression2;
	} // getExpression2

	public void setTypeIsInferred() {
		this.typeIsInferred = true;
	} // setTypeIsInferred

	public boolean typeIsInferred() {
		return this.typeIsInferred;
	} // typeIsInferred

	public String toString() {
		return super.toString() + " variable:" + this.getVariable()
				+ " typeName:" + this.getTypeName() + "typeIsInferred:"
				+ this.typeIsInferred();
	} // toString

	public void print(String prefix) {
		super.print(prefix);
		this.getExpression1().printChild(prefix);
		if (this.getExpression2() != null) {
			this.getExpression2().printChild(prefix);
		}
	} // print

} // LoopVariableDefinition
