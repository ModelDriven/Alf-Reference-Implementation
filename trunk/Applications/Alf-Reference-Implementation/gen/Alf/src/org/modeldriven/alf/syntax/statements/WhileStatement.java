
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
 * A looping statement for which the continuation condition is first tested
 * before the first iteration.
 **/

public class WhileStatement extends Statement implements IWhileStatement {

	private IBlock body = null;
	private IExpression condition = null;

	public IBlock getBody() {
		return this.body;
	}

	public void setBody(IBlock body) {
		this.body = body;
	}

	public IExpression getCondition() {
		return this.condition;
	}

	public void setCondition(IExpression condition) {
		this.condition = condition;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		IBlock body = this.getBody();
		if (body != null) {
			body.print(prefix + " ");
		}
		IExpression condition = this.getCondition();
		if (condition != null) {
			condition.print(prefix + " ");
		}
	}
} // WhileStatement
