
/*******************************************************************************
 * Copyright 2011, 2018 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions;

import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.parser.ParsedElement;
import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.syntax.common.ConstraintViolation;
import org.modeldriven.alf.syntax.common.ExternalElementReference;
import org.modeldriven.alf.syntax.expressions.impl.PositionalTupleImpl;

/**
 * A tuple in which the arguments are matched to parameters in order by
 * position.
 **/

public class PositionalTuple extends Tuple {

	public PositionalTuple() {
		this.impl = new PositionalTupleImpl(this);
	}

	public PositionalTuple(Parser parser) {
		this();
		this.init(parser);
	}

	public PositionalTuple(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
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

	/**
	 * A positional tuple must not have more arguments than the invocation it is
	 * for has parameters.
	 **/
	public boolean positionalTupleArguments() {
		return this.getImpl().positionalTupleArguments();
	}

    @Override
    public void _addExternalReferences(Collection<ExternalElementReference> references) {
        super._addExternalReferences(references);
        addExternalReferencesFor(references, this.getExpression());
    }

	@Override
    public void _deriveAll() {
		super._deriveAll();
		Collection<Expression> expression = this.getExpression();
		if (expression != null) {
			for (Object _expression : expression.toArray()) {
				((Expression) _expression).deriveAll();
			}
		}
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.positionalTupleArguments()) {
			violations.add(new ConstraintViolation("positionalTupleArguments",
					this));
		}
		Collection<Expression> expression = this.getExpression();
		if (expression != null) {
			for (Object _expression : expression.toArray()) {
				((Expression) _expression).checkConstraints(violations);
			}
		}
	}

	@Override
    public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		return s.toString();
	}

	@Override
    public void print() {
		this.print("", false);
	}

	@Override
    public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	@Override
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
