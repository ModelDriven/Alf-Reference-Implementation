
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package alf.syntax.behavioral;

import alf.nodes.*;
import alf.syntax.SyntaxNode;
import alf.syntax.behavioral.*;
import alf.syntax.expressions.*;
import alf.syntax.namespaces.*;
import alf.syntax.structural.*;

import java.util.ArrayList;

public class ClassifyStatement extends Statement {

	private Expression expression = null;
	private QualifiedNameList fromList = null;
	private QualifiedNameList toList = null;

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
