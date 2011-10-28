/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.ActivityGraph;
import org.modeldriven.alf.mapping.fuml.expressions.ExpressionMapping;

import org.modeldriven.alf.syntax.expressions.ThisExpression;

import fUML.Syntax.Actions.IntermediateActions.ReadSelfAction;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;
import fUML.Syntax.Classes.Kernel.Element;

public class ThisExpressionMapping extends ExpressionMapping {
    
    private ReadSelfAction action = null;

    /**
     * A this expression maps to a read self action. The result pin of the read
     * self action is the result source element for the expression.
     */
    
    public ReadSelfAction mapAction() throws MappingError {
        return this.graph.addReadSelfAction(this.getType());
    }

    @Override
    public ActivityNode getResultSource() throws MappingError {
        return this.getAction().result;
    }
    
    public ReadSelfAction getAction() throws MappingError {
        if (this.action == null) {
            this.action = mapAction();
            this.mapTo(this.action);
          }

          return this.action;
    }
    
    @Override
    public Element getElement() {
        return this.action;
    }
    
    @Override
    public ActivityGraph getGraph() throws MappingError {
        this.getAction();
        return super.getGraph();
    }

    public ThisExpression getThisExpression() {
        return (ThisExpression) this.getSource();
    }

    @Override
    public void print(String prefix) {
        super.print(prefix);
        if (this.action != null) {
            System.out.println(prefix + " action: " + action);
        }
    }

} // ThisExpressionMapping
