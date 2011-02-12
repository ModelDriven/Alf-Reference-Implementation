
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
 * A statement that executes (at most) one of a set of statement sequences based
 * on matching a switch value to a set of test cases.
 **/

public class SwitchStatement extends Statement implements ISwitchStatement {

	private ArrayList<ISwitchClause> nonDefaultClause = new ArrayList<ISwitchClause>();
	private IExpression expression = null;
	private IBlock defaultClause = null;

	public ArrayList<ISwitchClause> getNonDefaultClause() {
		return this.nonDefaultClause;
	}

	public void setNonDefaultClause(ArrayList<ISwitchClause> nonDefaultClause) {
		this.nonDefaultClause = nonDefaultClause;
	}

	public void addNonDefaultClause(ISwitchClause nonDefaultClause) {
		this.nonDefaultClause.add(nonDefaultClause);
	}

	public IExpression getExpression() {
		return this.expression;
	}

	public void setExpression(IExpression expression) {
		this.expression = expression;
	}

	public IBlock getDefaultClause() {
		return this.defaultClause;
	}

	public void setDefaultClause(IBlock defaultClause) {
		this.defaultClause = defaultClause;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		ArrayList<ISwitchClause> nonDefaultClause = this.getNonDefaultClause();
		if (nonDefaultClause != null) {
			for (ISwitchClause item : this.getNonDefaultClause()) {
				if (item != null) {
					item.print(prefix + " ");
				} else {
					System.out.println(prefix + " null");
				}
			}
		}
		IExpression expression = this.getExpression();
		if (expression != null) {
			expression.print(prefix + " ");
		}
		IBlock defaultClause = this.getDefaultClause();
		if (defaultClause != null) {
			defaultClause.print(prefix + " ");
		}
	}
} // SwitchStatement
