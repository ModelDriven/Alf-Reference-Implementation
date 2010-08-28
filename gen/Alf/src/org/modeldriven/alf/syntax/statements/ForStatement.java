
/*
 * Copyright 2010 Data Access Technologies, Inc. (Model Driven Solutions)
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

public class ForStatement extends Statement {

	private Block body = null;
	private ArrayList<LoopVariableDefinition> variableDefinition = new ArrayList<LoopVariableDefinition>();
	private boolean isParallel = false; // DERIVED

	public Block getBody() {
		return this.body;
	}

	public void setBody(Block body) {
		this.body = body;
	}

	public ArrayList<LoopVariableDefinition> getVariableDefinition() {
		return this.variableDefinition;
	}

	public void setVariableDefinition(
			ArrayList<LoopVariableDefinition> variableDefinition) {
		this.variableDefinition = variableDefinition;
	}

	public void addVariableDefinition(LoopVariableDefinition variableDefinition) {
		this.variableDefinition.add(variableDefinition);
	}

	public boolean getIsParallel() {
		return this.isParallel;
	}

	public void setIsParallel(boolean isParallel) {
		this.isParallel = isParallel;
	}

	public boolean annotationAllowed(Annotation annotation) {
		/*
		 * In addition to an @isolated annotation, a for statement may have a
		 * @parallel annotation.
		 */
		return false; // STUB
	} // annotationAllowed

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		if (this.body != null) {
			this.body.print(prefix + " ");
		}
		for (LoopVariableDefinition variableDefinition : this
				.getVariableDefinition()) {
			if (variableDefinition != null) {
				variableDefinition.print(prefix + " ");
			} else {
				System.out.println(prefix + " null");
			}
		}
	}
} // ForStatement
