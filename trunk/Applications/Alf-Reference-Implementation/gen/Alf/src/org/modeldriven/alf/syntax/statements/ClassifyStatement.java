
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.statements;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

public class ClassifyStatement extends Statement {

	private QualifiedNameList fromList = null;
	private QualifiedNameList toList = null;
	private Expression expression = null;

	public ClassifyStatement(Expression expression) {
		this.expression = expression;
	} // ClassifyStatement

	public Expression getExpression() {
		return this.expression;
	} // getExpression

	public void setFromList(QualifiedNameList fromList) {
		this.fromList = fromList;
	} // setFromList

	public QualifiedNameList getFromList() {
		return this.fromList;
	} // getFromList

	public void setToList(QualifiedNameList toList) {
		this.toList = toList;
	} // setToList

	public QualifiedNameList getToList() {
		return this.toList;
	} // getToList

	public void print(String prefix) {
		super.print(prefix);

		QualifiedNameList fromList = this.getFromList();
		QualifiedNameList toList = this.getToList();

		if (fromList != null) {
			fromList.printChild(prefix);
		}

		if (toList != null) {
			toList.printChild(prefix);
		}
	} // print

} // ClassifyStatement
