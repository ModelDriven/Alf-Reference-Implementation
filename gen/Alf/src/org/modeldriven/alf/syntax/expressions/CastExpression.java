
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

public class CastExpression extends Expression {

	private QualifiedName type = null;
	private Expression operand = null;

	public CastExpression(QualifiedName type, Expression operand) {
		this.type = type;
		this.operand = operand;
	} // CastExpression

	public QualifiedName getType() {
		return this.type;
	} // getType

	public Expression getOperand() {
		return this.operand;
	} // getOperand

	public void print(String prefix) {
		super.print(prefix);

		if (this.getType() != null) {
			this.getType().printChild(prefix);
		}

		this.getOperand().printChild(prefix);
	} // print

} // CastExpression
