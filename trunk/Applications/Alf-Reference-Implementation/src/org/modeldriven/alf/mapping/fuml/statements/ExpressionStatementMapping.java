/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.mapping.fuml.statements;

import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.mapping.fuml.statements.StatementMapping;

import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.statements.ExpressionStatement;

public class ExpressionStatementMapping extends StatementMapping {

    /**
     * An expression statement maps to a structured activity node containing the
     * activity nodes and edges mapped from its expression.
     */

    @Override
    public void map() throws MappingError {
        super.map();

        ExpressionStatement statement = this.getExpressionStatement();
        FumlMapping mapping = this.fumlMap(statement.getExpression());
        this.addToNode(mapping.getModelElements());
    }

    public ExpressionStatement getExpressionStatement() {
        return (ExpressionStatement) this.getSource();
    }

    @Override
    public void print(String prefix) {
        super.print(prefix);
        Expression expression = this.getExpressionStatement().getExpression();
        Mapping mapping = expression.getImpl().getMapping();
        if (mapping != null) {
            System.out.println(prefix + " expression:");
            mapping.printChild(prefix);
        }
    }

} // ExpressionStatementMapping
