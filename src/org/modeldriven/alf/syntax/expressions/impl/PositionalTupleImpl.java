
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0
 * (http://www.opensource.org/licenses/afl-3.0.php)
 *
 */

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

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

	@Override
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

    /**
     * A tuple has the same number of inputs as its invocation has input
     * parameters. For each input parameter, the tuple has a corresponding input
     * with the same name as the parameter and an expression that is the
     * matching argument from the tuple, or an empty sequence construction
     * expression if there is no matching argument.
     **/
    @Override
    protected Collection<NamedExpression> deriveInput() {
        PositionalTuple self = this.getSelf();
        InvocationExpression invocation = self.getInvocation();
        Collection<NamedExpression> inputs = new ArrayList<NamedExpression>();
        if (invocation != null) {
            List<FormalParameter> parameters = invocation.getImpl().parameters();
            List<Expression> expressions = self.getExpression();
            int i = 0;
            for (FormalParameter parameter: parameters) {
                String direction = parameter.getDirection();
                if (direction != null && 
                        (direction.equals("in") || direction.equals("inout"))) {
                    Expression expression = i < expressions.size()?
                            expressions.get(i):
                            new SequenceConstructionExpression();
                    NamedExpression namedExpression = new NamedExpression();
                    namedExpression.setName(parameter.getName());
                    namedExpression.setExpression(expression);
                    inputs.add(namedExpression);
                    i++;
                }
            }
        }
        return inputs;
    }

    /**
     * A tuple has the same number of outputs as its invocation has output
     * parameters. For each output parameter, the tuple has a corresponding
     * output with the same name as the parameter and an expression that is the
     * matching argument from the tuple, or an empty sequence construction
     * expression if there is no matching argument.
     **/
    @Override
    protected Collection<OutputNamedExpression> deriveOutput() {
        PositionalTuple self = this.getSelf();
        InvocationExpression invocation = self.getInvocation();
        Collection<OutputNamedExpression> outputs = new ArrayList<OutputNamedExpression>();
        if (invocation != null) {
            List<FormalParameter> parameters = invocation.getImpl().parameters();
            List<Expression> expressions = self.getExpression();
            int i = 0;
            for (FormalParameter parameter: parameters) {
                String direction = parameter.getDirection();
                if (direction != null && 
                        (direction.equals("inout") || direction.equals("out"))) {
                    Expression expression = i < expressions.size()?
                            expressions.get(i):
                            new SequenceConstructionExpression();
                    OutputNamedExpression namedExpression = new OutputNamedExpression();
                    namedExpression.setName(parameter.getName());
                    namedExpression.setExpression(expression);
                    outputs.add(namedExpression);
                    i++;
                }
            }
        }
        return outputs;
    }

    /*
     * Helper Methods
     */

    @Override
    public boolean isEmpty() {
        return this.getSelf().getExpression().isEmpty();
    }

    @Override
    public void setCurrentScope(NamespaceDefinition currentScope) {
        for (Expression expression: this.getSelf().getExpression()) {
            expression.getImpl().setCurrentScope(currentScope);
        }
    }

} // PositionalTupleImpl
