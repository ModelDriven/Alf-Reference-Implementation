/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.ActivityGraph;
import org.modeldriven.alf.mapping.fuml.expressions.ExpressionMapping;

import org.modeldriven.alf.syntax.expressions.ThisExpression;

import org.modeldriven.alf.uml.ReadSelfAction;
import org.modeldriven.alf.uml.ActivityNode;
import org.modeldriven.alf.uml.Element;

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
        return this.getAction().getResult();
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
