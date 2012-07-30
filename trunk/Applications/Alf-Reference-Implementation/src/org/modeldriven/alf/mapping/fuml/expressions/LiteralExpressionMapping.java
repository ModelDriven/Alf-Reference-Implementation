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

import org.modeldriven.alf.syntax.expressions.LiteralExpression;

import fUML.Syntax.Actions.IntermediateActions.ValueSpecificationAction;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;
import fUML.Syntax.Classes.Kernel.Element;

public abstract class LiteralExpressionMapping extends ExpressionMapping {

    private ValueSpecificationAction action = null;

    /**
     * A literal expression maps to a value specification action with the
     * literal mapping to an appropriate literal primitive element.
     */
    public abstract ValueSpecificationAction mapValueSpecificationAction() 
        throws MappingError;

    /**
     * The result pin of the value specification action is the result source
     * element for the expression.
     */
    @Override
    public ActivityNode getResultSource() throws MappingError {
        ValueSpecificationAction action = this.getAction();
        return action == null ? null : action.result;
    }

    public ValueSpecificationAction getAction() throws MappingError {
        if (this.action == null) {
            this.action = this.mapValueSpecificationAction();
            this.mapTo(this.action);
        }
        return this.action;
    }

    public LiteralExpression getLiteralExpression() {
        return (LiteralExpression) this.getSource();
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

    @Override
    public void print(String prefix) {
        super.print(prefix);
        System.out.println(prefix + " action: " + this.action);
    }

} // LiteralExpressionMapping
