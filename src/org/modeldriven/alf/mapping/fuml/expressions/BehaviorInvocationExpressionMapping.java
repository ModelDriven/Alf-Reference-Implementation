
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.mapping.fuml.expressions;

import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.BehaviorInvocationExpression;
import org.modeldriven.alf.syntax.expressions.InvocationExpression;
import org.modeldriven.alf.syntax.expressions.NamedExpression;
import org.modeldriven.alf.syntax.expressions.Tuple;
import org.modeldriven.alf.syntax.units.FormalParameter;
import org.modeldriven.alf.syntax.units.RootNamespace;

import org.modeldriven.alf.uml.Action;
import org.modeldriven.alf.uml.ExpansionNode;
import org.modeldriven.alf.uml.ActivityNode;

public class BehaviorInvocationExpressionMapping extends
		InvocationExpressionMapping {
    
    private boolean isParallelAdd = false;
    
    @Override
    public Action mapAction() throws MappingError {
        InvocationExpression invocation = this.getInvocationExpression();
        if (invocation.getImpl().isAddInvocation()) {
            ElementReference collectionFunctionAdd = 
                    RootNamespace.getCollectionFunctionAdd();
            if (collectionFunctionAdd != null) {
                List<FormalParameter> parameters = 
                        collectionFunctionAdd.getImpl().getParameters();
                Tuple tuple = invocation.getTuple();
                FumlMapping mapping = this.fumlMap(
                        tuple.getImpl().getInput(parameters.get(0).getName()));
                if (mapping instanceof NameExpressionMapping) {
                    ActivityNode resultSource = 
                            ((NameExpressionMapping)mapping).getResultSource();
                    if (resultSource instanceof ExpansionNode) {
                        mapping = this.fumlMap(
                                tuple.getImpl().getInput(parameters.get(1).getName()));
                        if (!(mapping instanceof ExpressionMapping)) {
                            this.throwError("Error mapping parallel add expression: " + 
                                    mapping.getErrorMessage());
                        } else {
                            ExpressionMapping expressionMapping = 
                                    (ExpressionMapping)mapping;
                            this.graph.addAll(expressionMapping.getGraph());
                            this.graph.addObjectFlow(
                                    expressionMapping.getResultSource(), 
                                    resultSource);
                            this.isParallelAdd = true;
                            return null;
                        }
                    }
                }
            }
        }

        return super.mapAction();
    }
    
    @Override
    public Action getAction() throws MappingError {
        return this.isParallelAdd? null: super.getAction();
    }
    
    public BehaviorInvocationExpression getBehaviorInvocationExpression() {
        return (BehaviorInvocationExpression)this.getSource();
    }

    @Override
    public String toString() {
        return super.toString() + " isParallelAdd:" + this.isParallelAdd;
    }
    
    @Override 
    public void print(String prefix) {
        super.print(prefix);
        
        InvocationExpression invocation = this.getInvocationExpression();
        Collection<NamedExpression> inputs = invocation.getTuple().getInput();
        if (!inputs.isEmpty()) {
            System.out.println(prefix + " tuple:");
            System.out.println(prefix + "  input:");
            for (NamedExpression input: inputs) {
                System.out.println(prefix + "   name: " + input.getName());
                Mapping mapping = input.getExpression().getImpl().getMapping();
                if (mapping != null) {
                    mapping.printChild(prefix + "  ");
                }
            }
        }
        
    }
    
} // BehaviorInvocationExpressionMapping
