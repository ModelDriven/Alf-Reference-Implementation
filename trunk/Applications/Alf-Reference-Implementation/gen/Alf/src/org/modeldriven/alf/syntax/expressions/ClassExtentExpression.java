
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
 * An expression used to obtain the objects in the extent of a class.
 **/

public class ClassExtentExpression extends Expression implements
		IClassExtentExpression {

	private IQualifiedName className = null;

	public IQualifiedName getClassName() {
		return this.className;
	}

	public void setClassName(IQualifiedName className) {
		this.className = className;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		IQualifiedName className = this.getClassName();
		if (className != null) {
			className.print(prefix + " ");
		}
	}
} // ClassExtentExpression
