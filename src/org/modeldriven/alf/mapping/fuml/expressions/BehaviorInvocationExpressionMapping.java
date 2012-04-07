
/*
 * Copyright 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions;

import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.syntax.expressions.BehaviorInvocationExpression;
import org.modeldriven.alf.syntax.expressions.InvocationExpression;
import org.modeldriven.alf.syntax.expressions.NamedExpression;
import org.modeldriven.alf.syntax.expressions.Tuple;
import org.modeldriven.alf.syntax.units.RootNamespace;

import fUML.Syntax.Actions.BasicActions.Action;
import fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionNode;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;
import fUML.Syntax.Classes.Kernel.Parameter;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior;

public class BehaviorInvocationExpressionMapping extends
		InvocationExpressionMapping {
    
    private boolean isParallelAdd = false;
    
    @Override
    public Action mapAction() throws MappingError {
        InvocationExpression invocation = this.getInvocationExpression();
        if (invocation.getImpl().isAddInvocation()) {
            Behavior addBehavior = 
                    getBehavior(RootNamespace.getCollectionFunctionAdd());
            List<Parameter> parameters = addBehavior.ownedParameter;
            Tuple tuple = invocation.getTuple();
            FumlMapping mapping = this.fumlMap(
                    tuple.getImpl().getInput(parameters.get(0).name));
            if (mapping instanceof NameExpressionMapping) {
                ActivityNode resultSource = 
                        ((NameExpressionMapping)mapping).getResultSource();
                if (resultSource instanceof ExpansionNode) {
                    mapping = this.fumlMap(
                            tuple.getImpl().getInput(parameters.get(1).name));
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
