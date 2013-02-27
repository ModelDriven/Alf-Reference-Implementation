
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.common.SyntaxElement;
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
                            SequenceConstructionExpressionImpl.makeNull();
                    NamedExpression namedExpression = new NamedExpression();
                    namedExpression.setName(parameter.getName());
                    namedExpression.setExpression(expression);
                    inputs.add(namedExpression);
                }
                if (!"return".equals(direction)) {
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
            boolean isAddInvocation = invocation.getImpl().isAddInvocation();
            List<Expression> expressions = self.getExpression();
            int i = 0;
            for (FormalParameter parameter: parameters) {
                if (i >= expressions.size()) {
                    break;
                }
                String direction = parameter.getDirection();
                if (direction != null && 
                        (direction.equals("inout") || direction.equals("out"))) {
                    
                    Expression expression = i < expressions.size()?
                            expressions.get(i):
                            direction.equals("out")? null:
                            SequenceConstructionExpressionImpl.makeNull();
                    if (expression != null) {
                        
                        // Identify the first argument of an invocation of
                        // CollectionFunctions::add, since an @parallel local
                        // name is allowed only in this position.
                        if (isAddInvocation && i == 0) {
                            expression.getImpl().setIsAddTarget();
                        }
                        
                        OutputNamedExpression namedExpression = new OutputNamedExpression();
                        namedExpression.setName(parameter.getName());
                        namedExpression.setExpression(expression);
                        outputs.add(namedExpression);
                    }
                }
                if (!"return".equals(direction)) {
                    i++;
                }
            }
        }
        return outputs;
    }
    
    /*
     * Constraints
     */
    
    /**
     * A positional tuple must not have more arguments than the invocation it is
     * for has parameters.
     **/
    public boolean positionalTupleArguments() {
        PositionalTuple self = this.getSelf();
        List<Expression> expression = self.getExpression();
        InvocationExpression invocation = self.getInvocation();
        return expression == null || invocation == null ||
                expression.size() <= invocation.parameterElements().size();
    }

    /*
     * Helper Methods
     */

    @Override
    public boolean isEmpty() {
        return this.getSelf().getExpression().isEmpty();
    }
    
    @Override
    public int size() {
        return this.getSelf().getExpression().size();
    }

    @Override
    public void setCurrentScope(NamespaceDefinition currentScope) {
        super.setCurrentScope(currentScope);
        for (Expression expression: this.getSelf().getExpression()) {
            expression.getImpl().setCurrentScope(currentScope);
        }
    }

    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof PositionalTuple) {
            PositionalTuple self = this.getSelf();
            for (Expression expression: 
                ((PositionalTuple)base).getExpression()) {
                self.addExpression((Expression)expression.getImpl().
                        bind(templateParameters, templateArguments));
            }
        }
    }
    
} // PositionalTupleImpl
