
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
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.expressions.impl.PositionalTupleImpl;

/**
 * A tuple in which the arguments are matched to parameters in order by
 * position.
 **/

public class PositionalTuple extends Tuple {

	public PositionalTuple() {
		this.impl = new PositionalTupleImpl(this);
	}

	public PositionalTupleImpl getImpl() {
		return (PositionalTupleImpl) this.impl;
	}

	public List<Expression> getExpression() {
		return this.getImpl().getExpression();
	}

	public void setExpression(List<Expression> expression) {
		this.getImpl().setExpression(expression);
	}

	public void addExpression(Expression expression) {
		this.getImpl().addExpression(expression);
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		List<Expression> expression = this.getExpression();
		if (expression != null) {
			if (expression.size() > 0) {
				System.out.println(prefix + " expression:");
			}
			for (Expression _expression : expression) {
				if (_expression != null) {
					_expression.print(prefix + "  ");
				} else {
					System.out.println(prefix + "  null");
				}
			}
		}
	}
} // PositionalTuple
