
/*
 * Copyright 2010 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

/**
 * A binary expression with a conditional logical expression, for which the
 * evaluation of the second operand expression is conditioned on the result of
 * evaluating the first operand expression.
 **/

public class ConditionalLogicalExpression extends BinaryExpression {

	public boolean validateAssignments() {
		/*
		 * The assignments before the first operand expression of a conditional
		 * logical expression are the same as those before the conditional
		 * logical expression. The assignments before the second operand
		 * expression are the same as those after the first operand expression.
		 */
		return false; // STUB
	} // validateAssignments

	public ArrayList<AssignedSource> updateAssignments() {
		/*
		 * If a name has the same assigned source after the second operand
		 * expression as before it, then that is its assigned source after the
		 * conditional logical expression. If a name is unassigned before the
		 * second operand expression, then it is considered unassigned after the
		 * conditional logical expression, even if it has an assigned source
		 * after the second operand expression. Otherwise its assigned source
		 * after the conditional logical expression is the conditional logical
		 * expression itself.
		 */
		return new ArrayList<AssignedSource>(); // STUB
	} // updateAssignments

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
	}
} // ConditionalLogicalExpression
