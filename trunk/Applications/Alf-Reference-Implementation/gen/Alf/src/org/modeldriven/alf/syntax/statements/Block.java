
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
 * A grouped sequence of statements.
 **/

public class Block extends SyntaxElement implements IBlock {

	private ArrayList<IStatement> statement = new ArrayList<IStatement>();

	public ArrayList<IStatement> getStatement() {
		return this.statement;
	}

	public void setStatement(ArrayList<IStatement> statement) {
		this.statement = statement;
	}

	public void addStatement(IStatement statement) {
		this.statement.add(statement);
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		ArrayList<IStatement> statement = this.getStatement();
		if (statement != null) {
			for (IStatement item : this.getStatement()) {
				if (item != null) {
					item.print(prefix + " ");
				} else {
					System.out.println(prefix + " null");
				}
			}
		}
	}
} // Block
