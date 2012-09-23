
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.parser.AlfParser;
import org.modeldriven.alf.parser.Token;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.expressions.impl.PositionalTupleImpl;
import org.modeldriven.uml.Element;
import org.modeldriven.uml.Profile;
import org.modeldriven.uml.Stereotype;

/**
 * A tuple in which the arguments are matched to parameters in order by
 * position.
 **/

public class PositionalTuple extends Tuple {

	public PositionalTuple() {
		this.impl = new PositionalTupleImpl(this);
	}

	public PositionalTuple(AlfParser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public PositionalTuple(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
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

	public void _deriveAll() {
		super._deriveAll();
		Collection<Expression> expression = this.getExpression();
		if (expression != null) {
			for (Object _expression : expression.toArray()) {
				((Expression) _expression).deriveAll();
			}
		}
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		Collection<Expression> expression = this.getExpression();
		if (expression != null) {
			for (Object _expression : expression.toArray()) {
				((Expression) _expression).checkConstraints(violations);
			}
		}
	}

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		return s.toString();
	}

	public void print() {
		this.print("", false);
	}

	public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	public void print(String prefix, boolean includeDerived) {
		super.print(prefix, includeDerived);
		List<Expression> expression = this.getExpression();
		if (expression != null && expression.size() > 0) {
			System.out.println(prefix + " expression:");
			for (Object _object : expression.toArray()) {
				Expression _expression = (Expression) _object;
				if (_expression != null) {
					_expression.print(prefix + "  ", includeDerived);
				} else {
					System.out.println(prefix + "  null");
				}
			}
		}
	}
} // PositionalTuple
