
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
 * A statement that executes (at most) one of a set of statement sequences based
 * on matching a switch value to a set of test cases.
 **/

public class SwitchStatement extends Statement {

	private ArrayList<SwitchClause> nonDefaultClause = new ArrayList<SwitchClause>();
	private Expression expression = null;
	private Block defaultClause = null;
	private boolean isAssured = false; // DERIVED
	private boolean isDetermined = false; // DERIVED

	public ArrayList<SwitchClause> getNonDefaultClause() {
		return this.nonDefaultClause;
	}

	public void setNonDefaultClause(ArrayList<SwitchClause> nonDefaultClause) {
		this.nonDefaultClause = nonDefaultClause;
	}

	public void addNonDefaultClause(SwitchClause nonDefaultClause) {
		this.nonDefaultClause.add(nonDefaultClause);
	}

	public Expression getExpression() {
		return this.expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	public Block getDefaultClause() {
		return this.defaultClause;
	}

	public void setDefaultClause(Block defaultClause) {
		this.defaultClause = defaultClause;
	}

	public boolean getIsAssured() {
		return this.isAssured;
	}

	public void setIsAssured(boolean isAssured) {
		this.isAssured = isAssured;
	}

	public boolean getIsDetermined() {
		return this.isDetermined;
	}

	public void setIsDetermined(boolean isDetermined) {
		this.isDetermined = isDetermined;
	}

	public boolean annotationAllowed(Annotation annotation) {
		/*
		 * In addition to an @isolated annotation, a switch statement may have
		 * @assured and @determined annotations. They may not have arguments.
		 */
		return false; // STUB
	} // annotationAllowed

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		for (SwitchClause nonDefaultClause : this.getNonDefaultClause()) {
			if (nonDefaultClause != null) {
				nonDefaultClause.print(prefix + " ");
			} else {
				System.out.println(prefix + " null");
			}
		}
		if (this.expression != null) {
			this.expression.print(prefix + " ");
		}
		if (this.defaultClause != null) {
			this.defaultClause.print(prefix + " ");
		}
	}
} // SwitchStatement
