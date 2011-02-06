
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
 * A looping statement that gives successive values to one or more loop
 * variables on each iteration.
 **/

public class ForStatement extends Statement implements IForStatement {

	private IBlock body = null;
	private ArrayList<ILoopVariableDefinition> variableDefinition = new ArrayList<ILoopVariableDefinition>();

	public IBlock getBody() {
		return this.body;
	}

	public void setBody(IBlock body) {
		this.body = body;
	}

	public ArrayList<ILoopVariableDefinition> getVariableDefinition() {
		return this.variableDefinition;
	}

	public void setVariableDefinition(
			ArrayList<ILoopVariableDefinition> variableDefinition) {
		this.variableDefinition = variableDefinition;
	}

	public void addVariableDefinition(ILoopVariableDefinition variableDefinition) {
		this.variableDefinition.add(variableDefinition);
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
		for (ILoopVariableDefinition variableDefinition : this
				.getVariableDefinition()) {
			if (variableDefinition != null) {
				variableDefinition.print(prefix + " ");
			} else {
				System.out.println(prefix + " null");
			}
		}
	}
} // ForStatement
