
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.mapping.fuml.statements;

import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.mapping.fuml.expressions.AssignmentExpressionMapping;
import org.modeldriven.alf.mapping.fuml.statements.StatementMapping;

import org.modeldriven.alf.syntax.expressions.AssignmentExpression;
import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.statements.LocalNameDeclarationStatement;

import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;

public class LocalNameDeclarationStatementMapping extends StatementMapping {
    
    private AssignmentExpressionMapping assignmentMapping = null;

    /**
     *A local name declaration statement is mapped as if it was an expression
     * statement with an assignment expression having the local name as its
     * left-hand side and the expression as its right-hand side.
     */
    
    @Override
    public void map() throws MappingError {
        super.map();
        
        LocalNameDeclarationStatement statement = 
            this.getLocalNameDeclarationStatement();
        AssignmentExpression expression = 
            statement.getImpl().getAssignmentExpression();
        
        FumlMapping mapping = this.fumlMap(expression);
        this.addToNode(mapping.getModelElements());
        this.assignmentMapping = (AssignmentExpressionMapping)mapping;
    }
    
    @Override
    public ActivityNode getAssignedValueSource(String name) throws MappingError {
        this.map();
        return this.assignmentMapping == null? null:
            this.assignmentMapping.getAssignedValueSource(name);
    }
    
    public LocalNameDeclarationStatement getLocalNameDeclarationStatement() {
		return (LocalNameDeclarationStatement) this.getSource();
	}
    
    @Override
    public void print(String prefix) {
        super.print(prefix);
        
        Expression expression = 
            this.getLocalNameDeclarationStatement().getExpression();
        if (expression != null) {
            System.out.println(prefix + " expression: ");
            Mapping mapping = expression.getImpl().getMapping();
            if (mapping != null) {
                mapping.printChild(prefix);
            }
        }

    }

} // LocalNameDeclarationStatementMapping
