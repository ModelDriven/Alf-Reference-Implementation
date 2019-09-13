/*******************************************************************************
 * Copyright 2011, 2016 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.expressions;

import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.expressions.InvocationExpression;
import org.modeldriven.alf.syntax.expressions.SequenceAccessExpression;

import org.modeldriven.alf.uml.*;

public class SequenceAccessExpressionMapping extends BehaviorInvocationExpressionMapping {
    
    private InvocationExpression invocation = null;
    private ActivityNode indexSource = null;
    
    /**
     * 1. A sequence access expression is mapped to a call to the primitive
     * behavior Alf::Library::PrimitiveBehaviors::SequenceFunctions::At. The
     * result source element of the primary expression of the sequence access
     * expression is connected by an object flow to the first argument input pin
     * of the call behavior action. The result source element of the index
     * expression is connected by an object flow to the second argument input
     * pin. The result output pin of the call behavior action is the result
     * source element for the sequence access expression.
     * 
     * 2. If indexing from 0 applies to the sequence access expression, then the
     * mapping of its index expression is adjusted as follows. The result source
     * element of the index expression is connected by an object flow to the
     * first argument pin of a call to the primitive behavior
     * Alf::Library::PrimitiveBehaviors::IntegerFunctions::+, and the result of
     * a value specification action for the value 1 is connected to the second
     * argument pin. The result output pin of the call behavior action is then
     * used as the result source element for the index.
     */
    
    @Override
    public Action mapAction() throws MappingError {
        Action action = super.mapAction(false);
        
        /**
         * Add a fork node that allows the result of the index expression to also
         * be used in the mapping of a left-hand side corresponding to this
         * sequence access expression, as in an inout argument, increment/decrement
         * expression or compound assignment.
         */
        InputPin inputPin = action.getInput().get(1);
        this.indexSource = this.graph.addForkNode("Fork(" + inputPin.getName() + ")");
        ActivityEdge flow = inputPin.getIncoming().get(0);
        inputPin.removeIncoming(flow);
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
