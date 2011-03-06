
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0
 * (http://www.opensource.org/licenses/afl-3.0.php)
 *
 */

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.omg.uml.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A tuple in which the arguments are matched to parameters in order by
 * position.
 **/

public class PositionalTupleImpl extends TupleImpl {

	private List<Expression> expression = new ArrayList<Expression>();

	public PositionalTupleImpl(PositionalTuple self) {
		super(self);
	}

	public PositionalTuple getSelf() {
		return (PositionalTuple) this.self;
	}

	public List<Expression> getExpression() {
		return this.expression;
	}

	public void setExpression(List<Expression> expression) {
		this.expression = expression;
	}

	public void addExpression(Expression expression) {
		this.expression.add(expression);
	}

} // PositionalTupleImpl
