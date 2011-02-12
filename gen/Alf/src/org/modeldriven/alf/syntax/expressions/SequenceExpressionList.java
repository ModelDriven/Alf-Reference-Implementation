
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
 * A specification of the elements of a sequence using a list of expressions.
 **/

public class SequenceExpressionList extends SequenceElements implements
		ISequenceExpressionList {

	private ArrayList<IExpression> element = new ArrayList<IExpression>();

	public ArrayList<IExpression> getElement() {
		return this.element;
	}

	public void setElement(ArrayList<IExpression> element) {
		this.element = element;
	}

	public void addElement(IExpression element) {
		this.element.add(element);
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		ArrayList<IExpression> element = this.getElement();
		if (element != null) {
			for (IExpression item : this.getElement()) {
				if (item != null) {
					item.print(prefix + " ");
				} else {
					System.out.println(prefix + " null");
				}
			}
		}
	}
} // SequenceExpressionList
