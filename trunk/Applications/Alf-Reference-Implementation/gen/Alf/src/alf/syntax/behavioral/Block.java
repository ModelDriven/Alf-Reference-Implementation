
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

public class Block extends Node {

	private ArrayList<Statement> statements = new ArrayList<Statement>();

	public void addStatement(Statement statement) {
		this.statements.add(statement);
	} // addStatement

	public ArrayList<Statement> getStatements() {
		return this.statements;
	} // getStatements

	public void print(String prefix) {
		super.print(prefix);

		for (Statement statement : this.getStatements()) {
			statement.printChild(prefix);
		}
	} // print

} // Block
