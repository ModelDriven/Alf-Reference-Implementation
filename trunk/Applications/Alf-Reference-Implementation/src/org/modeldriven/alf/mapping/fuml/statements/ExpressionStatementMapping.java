
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
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

import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;

public class ExpressionStatementMapping extends StatementMapping {

    @Override
    public void mapTo(ActivityNode node) throws MappingError {
        super.mapTo(node);

        ExpressionStatement statement = this.getExpressionStatement();
        FumlMapping mapping = this.fumlMap(statement.getExpression());
        // mapping.setContext(this.getContext());
        this.addToNode(node, mapping.getModelElements());
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
