/*
 * Copyright 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.statements;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.ActivityGraph;
import org.modeldriven.alf.mapping.fuml.FumlMapping;

import org.modeldriven.alf.syntax.statements.BreakStatement;

public class BreakStatementMapping extends StatementMapping {

    /**
     * A break statement maps to an empty structured activity node with a
     * control flow to an activity final node. The activity final node is placed
     * in the outermost structured activity node mapped from the target
     * statement of the break statement.
     */
    
    @Override
    public void map() throws MappingError {
        super.map();
        
        BreakStatement statement = this.getBreakStatement();
        FumlMapping mapping = this.fumlMap(statement.getImpl().getTarget());
        if (!(mapping instanceof LoopStatementMapping)) {
            this.throwError("Error mapping target statement: " + 
                    mapping.getErrorMessage());
        } else {
            this.add(ActivityGraph.createControlFlow(
                    this.node, 
                    ((LoopStatementMapping)mapping).getFinalNode()));
        }
    }

    public BreakStatement getBreakStatement() {
        return (BreakStatement) this.getSource();
    }

} // BreakStatementMapping
