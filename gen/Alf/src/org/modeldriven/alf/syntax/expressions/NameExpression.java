
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
 * An expression that comprises a name reference.
 **/

public class NameExpression extends Expression implements INameExpression {

	private IQualifiedName name = null;

	public IQualifiedName getName() {
		return this.name;
	}

	public void setName(IQualifiedName name) {
		this.name = name;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		IQualifiedName name = this.getName();
		if (name != null) {
			name.print(prefix + " ");
		}
	}
} // NameExpression
