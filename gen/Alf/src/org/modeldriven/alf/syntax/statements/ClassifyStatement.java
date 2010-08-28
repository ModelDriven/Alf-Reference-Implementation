
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
 * A statement that changes the classification of an object.
 **/

public class ClassifyStatement extends Statement {

	private Expression expression = null;
	private QualifiedNameList fromList = null;
	private QualifiedNameList toList = null;
	private ArrayList<ElementReference> fromClass = new ArrayList<ElementReference>(); // DERIVED
	private ArrayList<ElementReference> toClass = new ArrayList<ElementReference>(); // DERIVED
	private boolean isReclassifyAll = false;

	public Expression getExpression() {
		return this.expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	public QualifiedNameList getFromList() {
		return this.fromList;
	}

	public void setFromList(QualifiedNameList fromList) {
		this.fromList = fromList;
	}

	public QualifiedNameList getToList() {
		return this.toList;
	}

	public void setToList(QualifiedNameList toList) {
		this.toList = toList;
	}

	public ArrayList<ElementReference> getFromClass() {
		return this.fromClass;
	}

	public void setFromClass(ArrayList<ElementReference> fromClass) {
		this.fromClass = fromClass;
	}

	public void addFromClass(ElementReference fromClass) {
		this.fromClass.add(fromClass);
	}

	public ArrayList<ElementReference> getToClass() {
		return this.toClass;
	}

	public void setToClass(ArrayList<ElementReference> toClass) {
		this.toClass = toClass;
	}

	public void addToClass(ElementReference toClass) {
		this.toClass.add(toClass);
	}

	public boolean getIsReclassifyAll() {
		return this.isReclassifyAll;
	}

	public void setIsReclassifyAll(boolean isReclassifyAll) {
		this.isReclassifyAll = isReclassifyAll;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" isReclassifyAll:");
		s.append(this.isReclassifyAll);
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		if (this.expression != null) {
			this.expression.print(prefix + " ");
		}
		if (this.fromList != null) {
			this.fromList.print(prefix + " ");
		}
		if (this.toList != null) {
			this.toList.print(prefix + " ");
		}
	}
} // ClassifyStatement
