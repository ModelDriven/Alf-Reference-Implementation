
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

import org.omg.uml.*;

import java.util.ArrayList;

import org.modeldriven.alf.syntax.statements.impl.BreakStatementImpl;

/**
 * A statement that causes the termination of execution of an immediately
 * enclosing block.
 **/

public class BreakStatement extends Statement {

	private Statement target = null; // DERIVED

	public BreakStatement() {
		this.impl = new BreakStatementImpl(this);
	}

	public BreakStatementImpl getImpl() {
		return (BreakStatementImpl) this.impl;
	}

	public Statement getTarget() {
		if (this.target == null) {
			this.target = this.getImpl().deriveTarget();
		}
		return this.target;
	}

	/**
	 * The target of a break statement is the innermost switch, while, do or for
	 * statement enclosing the break statement.
	 **/
	public boolean breakStatementTargetDerivation() {
		return this.getImpl().breakStatementTargetDerivation();
	}

	/**
	 * The target of a break statement may not have a @parallel annotation.
	 **/
	public boolean breakStatementNonparallelTarget() {
		return this.getImpl().breakStatementNonparallelTarget();
	}

	/**
	 * A break statement may not have any annotations.
	 **/
	public Boolean annotationAllowed(Annotation annotation) {
		return this.getImpl().annotationAllowed(annotation);
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		Statement target = this.getTarget();
		if (target != null) {
			System.out.println(prefix + " /target:" + target);
		}
	}
} // BreakStatement
