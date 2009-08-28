
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package alf.syntax.behavioral;

import alf.syntax.behavioral.*;
import alf.syntax.expressions.*;
import alf.syntax.namespaces.*;
import alf.syntax.nodes.*;
import alf.syntax.structural.*;

import java.util.ArrayList;

public class ForStatement extends Statement {

	private Block body = null;
	private ArrayList<ForVariableDefinition> variableDefinitions = new ArrayList<ForVariableDefinition>();
	private Expression condition = null;

	public void addVariableDefinition(ForVariableDefinition definition) {
		this.variableDefinitions.add(definition);
	} // addVariableDefinition

	public ArrayList<ForVariableDefinition> getVariableDefinitions() {
		return this.variableDefinitions;
	} // getVariableDefinitions

	public void setCondition(Expression condition) {
		this.condition = condition;
	} // setCondition

	public Expression getCondition() {
		return this.condition;
	} // getCondition

	public void setBody(Block body) {
		this.body = body;
	} // setBody

	public Block getBody() {
		return this.body;
	} // getBody

	public void print(String prefix) {
		super.print(prefix);

		for (ForVariableDefinition variable : this.getVariableDefinitions()) {
			variable.printChild(prefix);
		}

		if (this.getCondition() != null) {
			this.getCondition().printChild(prefix);
		}

		this.getBody().printChild(prefix);
	} // print

} // ForStatement
