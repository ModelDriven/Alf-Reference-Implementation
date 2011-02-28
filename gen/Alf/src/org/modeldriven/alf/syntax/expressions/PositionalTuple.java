
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

import org.omg.uml.*;

import java.util.ArrayList;

import org.modeldriven.alf.syntax.expressions.impl.PositionalTupleImpl;

/**
 * A tuple in which the arguments are matched to parameters in order by
 * position.
 **/

public class PositionalTuple extends Tuple {

	private ArrayList<Expression> expression = new ArrayList<Expression>();

	public PositionalTuple() {
		this.impl = new PositionalTupleImpl(this);
	}

	public PositionalTupleImpl getImpl() {
		return (PositionalTupleImpl) this.impl;
	}

	public ArrayList<Expression> getExpression() {
		return this.expression;
	}

	public void setExpression(ArrayList<Expression> expression) {
		this.expression = expression;
	}

	public void addExpression(Expression expression) {
		this.expression.add(expression);
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		ArrayList<Expression> expression = this.getExpression();
		if (expression != null) {
			if (expression.size() > 0) {
				System.out.println(prefix + " expression:");
			}
			for (Expression _expression : (ArrayList<Expression>) expression
					.clone()) {
				if (_expression != null) {
					_expression.print(prefix + "  ");
				} else {
					System.out.println(prefix + "  null");
				}
			}
		}
	}
} // PositionalTuple
