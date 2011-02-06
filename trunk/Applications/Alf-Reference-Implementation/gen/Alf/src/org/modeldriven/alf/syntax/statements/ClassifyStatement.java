
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
 * A statement that changes the classification of an object.
 **/

public class ClassifyStatement extends Statement implements IClassifyStatement {

	private IExpression expression = null;
	private IQualifiedNameList fromList = null;
	private IQualifiedNameList toList = null;
	private Boolean isReclassifyAll = false;

	public IExpression getExpression() {
		return this.expression;
	}

	public void setExpression(IExpression expression) {
		this.expression = expression;
	}

	public IQualifiedNameList getFromList() {
		return this.fromList;
	}

	public void setFromList(IQualifiedNameList fromList) {
		this.fromList = fromList;
	}

	public IQualifiedNameList getToList() {
		return this.toList;
	}

	public void setToList(IQualifiedNameList toList) {
		this.toList = toList;
	}

	public Boolean getIsReclassifyAll() {
		return this.isReclassifyAll;
	}

	public void setIsReclassifyAll(Boolean isReclassifyAll) {
		this.isReclassifyAll = isReclassifyAll;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" isReclassifyAll:");
		s.append(this.getIsReclassifyAll());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		IExpression expression = this.getExpression();
		if (expression != null) {
			expression.print(prefix + " ");
		}
		IQualifiedNameList fromList = this.getFromList();
		if (fromList != null) {
			fromList.print(prefix + " ");
		}
		IQualifiedNameList toList = this.getToList();
		if (toList != null) {
			toList.print(prefix + " ");
		}
	}
} // ClassifyStatement
