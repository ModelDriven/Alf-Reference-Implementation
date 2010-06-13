
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

public class SequenceExpressionList extends SequenceElements {

	private ArrayList<Expression> elements = new ArrayList<Expression>();

	public void addElement(Expression expression) {
		this.elements.add(expression);
	} // addElement

	public ArrayList<Expression> getElements() {
		return this.elements;
	} // getElements

	public void print(String prefix) {
		super.print(prefix);

		for (Expression expr : this.getElements()) {
			expr.printChild(prefix);
		}
	} // print

} // SequenceExpressionList
