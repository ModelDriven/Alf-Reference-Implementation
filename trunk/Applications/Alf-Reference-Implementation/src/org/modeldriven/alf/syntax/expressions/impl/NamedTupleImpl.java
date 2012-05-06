
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
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
 * A tuple in which the arguments are matched to parameters by name.
 **/

public class NamedTupleImpl extends TupleImpl {

	private List<NamedExpression> namedExpression = 
	    new ArrayList<NamedExpression>();

	public NamedTupleImpl(NamedTuple self) {
		super(self);
	}

	@Override
	public NamedTuple getSelf() {
		return (NamedTuple) this.self;
	}

	public List<NamedExpression> getNamedExpression() {
		return this.namedExpression;
	}

	public void setNamedExpression(List<NamedExpression> namedExpression) {
		this.namedExpression = namedExpression;
	}

	public void addNamedExpression(NamedExpression namedExpression) {
		this.namedExpression.add(namedExpression);
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
	    NamedTuple self = this.getSelf();
	    InvocationExpression invocation = self.getInvocation();
	    Collection<NamedExpression> inputs = new ArrayList<NamedExpression>();
	    if (invocation != null) {
	        List<FormalParameter> parameters = invocation.getImpl().parameters();
	        for (FormalParameter parameter: parameters) {
                String direction = parameter == null? null: parameter.getDirection();
                if (direction != null && 
                        (direction.equals("in") || direction.equals("inout"))) {
                    String name = parameter.getName();
                    NamedExpression argument = this.getNamedExpression(name);
                    if (argument == null) {
                        argument = new NamedExpression();
                        argument.setName(name);
                        argument.setExpression(SequenceConstructionExpressionImpl.makeNull());
                    }
                    inputs.add(argument);
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
        NamedTuple self = this.getSelf();
        InvocationExpression invocation = self.getInvocation();
        Collection<OutputNamedExpression> outputs = new ArrayList<OutputNamedExpression>();
        if (invocation != null) {
            List<FormalParameter> parameters = invocation.getImpl().parameters();
            boolean isAddInvocation = invocation.getImpl().isAddInvocation();
            for (FormalParameter parameter: parameters) {
                String direction = parameter == null? null: parameter.getDirection();
                if (direction != null && 
                        (direction.equals("out") || direction.equals("inout"))) {
                    String name = parameter.getName();
                    NamedExpression argument = this.getNamedExpression(name);
                    Expression expression = null;
                    if (argument == null) {
                        expression = SequenceConstructionExpressionImpl.makeNull();
                    } else {
                        expression = argument.getExpression();
                        
                        // Identify the first argument of an invocation of
                        // CollectionFunctions::add, since an @parallel local
                        // name is allowed only in this position.
                        if (isAddInvocation && parameter == parameters.get(0)) {
                            expression.getImpl().setIsAddTarget();
                        }
                    }
                    OutputNamedExpression output = new OutputNamedExpression();
                    output.setName(name);
                    output.setExpression(expression);
                    outputs.add(output);
                }
            }
        }
        return outputs;
    }

	/*
	 * Helper Methods
	 */

    private NamedExpression getNamedExpression(String name) {
        Collection<NamedExpression> namedExpressions = this.getSelf().getNamedExpression();
        for (NamedExpression namedExpression: namedExpressions) {
            if (namedExpression.getName().equals(name)) {
                return namedExpression;
            }
        }
        return null;
    }

    @Override
    public boolean isEmpty() {
        return this.getSelf().getNamedExpression().isEmpty();
    }
    
    @Override
    public int size() {
        return this.getSelf().getNamedExpression().size();
    }
    
    @Override
    public void setCurrentScope(NamespaceDefinition currentScope) {
        super.setCurrentScope(currentScope);
        for (NamedExpression namedExpression: this.getSelf().getNamedExpression()) {
            namedExpression.getImpl().setCurrentScope(currentScope);
        }
    }

    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof NamedTuple) {
            NamedTuple self = this.getSelf();
            for (NamedExpression namedExpression: 
                ((NamedTuple)base).getNamedExpression()) {
                self.addNamedExpression
                    ((NamedExpression)namedExpression.getImpl().
                        bind(templateParameters, templateArguments));
            }
        }
    }

} // NamedTupleImpl
