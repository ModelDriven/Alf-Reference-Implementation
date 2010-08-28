
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
 * An expression used to access a specific element of a sequence.
 **/

public class SequenceAccessExpression extends Expression {

	private Expression primary = null;
	private Expression index = null;

	public Expression getPrimary() {
		return this.primary;
	}

	public void setPrimary(Expression primary) {
		this.primary = primary;
	}

	public Expression getIndex() {
		return this.index;
	}

	public void setIndex(Expression index) {
		this.index = index;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		if (this.primary != null) {
			this.primary.print(prefix + " ");
		}
		if (this.index != null) {
			this.index.print(prefix + " ");
		}
	}
} // SequenceAccessExpression
