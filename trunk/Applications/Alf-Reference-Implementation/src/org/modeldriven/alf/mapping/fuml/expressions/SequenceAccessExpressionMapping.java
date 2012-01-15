
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.syntax.expressions.InvocationExpression;
import org.modeldriven.alf.syntax.expressions.SequenceAccessExpression;

import fUML.Syntax.Actions.BasicActions.InputPin;
import fUML.Syntax.Actions.BasicActions.InvocationAction;
import fUML.Syntax.Activities.IntermediateActivities.ActivityEdge;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;
import fUML.Syntax.Activities.IntermediateActivities.ForkNode;

public class SequenceAccessExpressionMapping extends BehaviorInvocationExpressionMapping {
    
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
    
    InvocationExpression invocation = null;
    
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
    
    /**
     * Add a fork node that allows the result of the index expression to also
     * be used in the mapping of a left-hand side corresponding to this
     * sequence access expression, as in an inout argument, increment/decrement
     * expression or compound assignment.
     */
    public ActivityNode addIndexSource() throws MappingError {
        InvocationAction action = (InvocationAction)this.getAction();
        InputPin inputPin = action.input.get(1);
        ForkNode forkNode = this.graph.addForkNode("Fork(" + inputPin.name + ")");
        ActivityEdge flow = inputPin.incoming.get(0);
        inputPin.incoming.remove(flow);
        flow.setTarget(forkNode);
        this.graph.addObjectFlow(forkNode, inputPin);
        return forkNode;
    }

    public SequenceAccessExpression getSequenceAccessExpression() {
        return (SequenceAccessExpression) this.getSource();
    }

} // SequenceAccessExpressionMapping
