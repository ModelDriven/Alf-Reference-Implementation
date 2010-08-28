
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
 * A statement that causes the termination of execution of an immediately
 * enclosing block.
 **/

public class BreakStatement extends Statement {

	private Statement target = null; // DERIVED

	public Statement getTarget() {
		return this.target;
	}

	public void setTarget(Statement target) {
		this.target = target;
	}

	public boolean annotationAllowed(Annotation annotation) {
		/*
		 * A break statement may not have any annotations.
		 */
		return false; // STUB
	} // annotationAllowed

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
	}
} // BreakStatement
