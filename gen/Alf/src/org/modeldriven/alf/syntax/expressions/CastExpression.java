
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
 * An expression used to filter values by type.
 **/

public class CastExpression extends Expression implements ICastExpression {

	private IExpression operand = null;
	private IQualifiedName typeName = null;

	public IExpression getOperand() {
		return this.operand;
	}

	public void setOperand(IExpression operand) {
		this.operand = operand;
	}

	public IQualifiedName getTypeName() {
		return this.typeName;
	}

	public void setTypeName(IQualifiedName typeName) {
		this.typeName = typeName;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		IExpression operand = this.getOperand();
		if (operand != null) {
			operand.print(prefix + " ");
		}
		IQualifiedName typeName = this.getTypeName();
		if (typeName != null) {
			typeName.print(prefix + " ");
		}
	}
} // CastExpression
