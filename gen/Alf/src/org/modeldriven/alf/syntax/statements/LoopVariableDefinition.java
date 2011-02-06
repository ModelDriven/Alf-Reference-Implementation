
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.statements;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

/**
 * The definition of a loop variable in a for statement.
 **/

public class LoopVariableDefinition extends SyntaxElement implements
		ILoopVariableDefinition {

	private String variable = "";
	private IExpression expression1 = null;
	private IExpression expression2 = null;
	private IQualifiedName typeName = null;
	private Boolean typeIsInferred = true;

	public String getVariable() {
		return this.variable;
	}

	public void setVariable(String variable) {
		this.variable = variable;
	}

	public IExpression getExpression1() {
		return this.expression1;
	}

	public void setExpression1(IExpression expression1) {
		this.expression1 = expression1;
	}

	public IExpression getExpression2() {
		return this.expression2;
	}

	public void setExpression2(IExpression expression2) {
		this.expression2 = expression2;
	}

	public IQualifiedName getTypeName() {
		return this.typeName;
	}

	public void setTypeName(IQualifiedName typeName) {
		this.typeName = typeName;
	}

	public Boolean getTypeIsInferred() {
		return this.typeIsInferred;
	}

	public void setTypeIsInferred(Boolean typeIsInferred) {
		this.typeIsInferred = typeIsInferred;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" variable:");
		s.append(this.getVariable());
		s.append(" typeIsInferred:");
		s.append(this.getTypeIsInferred());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		IExpression expression1 = this.getExpression1();
		if (expression1 != null) {
			expression1.print(prefix + " ");
		}
		IExpression expression2 = this.getExpression2();
		if (expression2 != null) {
			expression2.print(prefix + " ");
		}
		IQualifiedName typeName = this.getTypeName();
		if (typeName != null) {
			typeName.print(prefix + " ");
		}
	}
} // LoopVariableDefinition
