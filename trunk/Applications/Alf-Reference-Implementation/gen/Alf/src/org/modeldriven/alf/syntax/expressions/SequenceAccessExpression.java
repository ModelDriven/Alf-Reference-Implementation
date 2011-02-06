
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
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

public class SequenceAccessExpression extends Expression implements
		ISequenceAccessExpression {

	private IExpression primary = null;
	private IExpression index = null;

	public IExpression getPrimary() {
		return this.primary;
	}

	public void setPrimary(IExpression primary) {
		this.primary = primary;
	}

	public IExpression getIndex() {
		return this.index;
	}

	public void setIndex(IExpression index) {
		this.index = index;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		IExpression primary = this.getPrimary();
		if (primary != null) {
			primary.print(prefix + " ");
		}
		IExpression index = this.getIndex();
		if (index != null) {
			index.print(prefix + " ");
		}
	}
} // SequenceAccessExpression
