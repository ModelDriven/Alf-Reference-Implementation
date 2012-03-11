/*
 * Copyright 2011-2012 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

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
