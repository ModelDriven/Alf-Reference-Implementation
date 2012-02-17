
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.expressions.InvocationExpression;
import org.modeldriven.alf.syntax.expressions.SequenceAccessExpression;

import fUML.Syntax.Actions.BasicActions.Action;
import fUML.Syntax.Actions.BasicActions.InputPin;
import fUML.Syntax.Activities.IntermediateActivities.ActivityEdge;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;

public class SequenceAccessExpressionMapping extends BehaviorInvocationExpressionMapping {
    
    private InvocationExpression invocation = null;
    private ActivityNode indexSource = null;
    
    /*
     * A sequence access expression is mapped to a call to the primitive
     * behavior Alf::Library::PrimitiveBehaviors::SequenceFunctions::At. The
     * result source element of the primary expression of the sequence access
     * expression is connected by an object flow to the first argument input pin
     * of the call behavior action. The result source element of the index
     * expression is connected by an object flow to the second argument input
     * pin. The result output pin of the call behavior action is the result
     * source element for the sequence access expression.
     */
    
    @Override
    public Action mapAction() throws MappingError {
        Action action = super.mapAction();
        
        /**
         * Add a fork node that allows the result of the index expression to also
         * be used in the mapping of a left-hand side corresponding to this
         * sequence access expression, as in an inout argument, increment/decrement
         * expression or compound assignment.
         */
        InputPin inputPin = action.input.get(1);
        this.indexSource = this.graph.addForkNode("Fork(" + inputPin.name + ")");
        ActivityEdge flow = inputPin.incoming.get(0);
        inputPin.incoming.remove(flow);
        flow.setTarget(this.indexSource);
        this.graph.addObjectFlow(this.indexSource, inputPin);
        
        return action;
    }
    
    @Override
    public InvocationExpression getInvocationExpression() {
        if (this.invocation == null) {
            // The getInvocation operation returns the equivalent At function
            // invocation for the sequence access expression.
            this.invocation = 
                this.getSequenceAccessExpression().getImpl().getInvocation();
        }
        return this.invocation;        
    }
    
    @Override
    public ActivityNode getIndexSource() throws MappingError {
        this.getAction();
        return this.indexSource;
    }
    
    @Override
    public ActivityNode getObjectSource() throws MappingError {
        SequenceAccessExpression expression = this.getSequenceAccessExpression();
        Expression primary = expression.getPrimary();
        if (primary == null) {
            return null;
        } else {
            Mapping mapping = primary.getImpl().getMapping();
            return mapping instanceof ExpressionMapping?
                    ((ExpressionMapping)mapping).getObjectSource(): null;
        }
    }
    
    public SequenceAccessExpression getSequenceAccessExpression() {
        return (SequenceAccessExpression) this.getSource();
    }
    
    @Override
    public void print(String prefix) {
        super.print(prefix);
        
        if (this.indexSource != null) {
            System.out.println(prefix + " indexSource: " + this.indexSource);
        }
        
    }

} // SequenceAccessExpressionMapping