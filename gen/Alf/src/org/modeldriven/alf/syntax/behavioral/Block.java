
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.behavioral;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.SyntaxNode;
import org.modeldriven.alf.syntax.behavioral.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.namespaces.*;
import org.modeldriven.alf.syntax.structural.*;

import java.util.ArrayList;

public class Block extends SyntaxNode {

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
