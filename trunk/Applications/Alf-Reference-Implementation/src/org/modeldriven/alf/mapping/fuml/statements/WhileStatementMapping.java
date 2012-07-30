
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.mapping.fuml.statements;

import java.util.Map;

import org.modeldriven.alf.mapping.fuml.statements.LoopStatementMapping;

import org.modeldriven.alf.syntax.common.AssignedSource;
import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.statements.Block;
import org.modeldriven.alf.syntax.statements.WhileStatement;

public class WhileStatementMapping extends LoopStatementMapping {

    /**
     * Loop Node
     * 
     * 1. A while statement maps to a loop node with isTestedFirst=true. The
     * loop node contains the activity nodes and edges mapped from both the
     * condition expression and the block of the while statement. All the
     * actions from the mapping of the condition expression constitute the test
     * part of the loop node. All the actions from the mapping of the block form
     * the body part.
     * 
     * 2. If the result source element from the mapping of the condition
     * expression is an output pin, then this is the decider pin for the loop
     * node. Otherwise, a structured activity node is added inside the loop node
     * as follows: The structured activity node has one input pin and one output
     * pin, with an object flow from the input pin to the output pin contained
     * within the structured activity node. There is an object flow from the
     * result source element from the mapping of the expression to the input pin
     * of the structured activity node. The output pin of the structured
     * activity node is then used as the decider pin.
     * 
     * Loop Variables
     * 
     * 3. Any name that is assigned in the condition expression or block of the
     * while statement is mapped to a loop variable of the loop node. The loop
     * variable corresponding to a name is used as the assigned source before
     * the condition expression when mapping the while statement. If the name is
     * assigned before the while statement, then the corresponding loop variable
     * input pin is the target of an incoming object flow from the assigned
     * source for the name before the while statement. Otherwise the loop
     * variable input pin is unconnected.
     
       [Actually, names that are assigned before the loop need to have
       corresponding loop variables, even it they are NOT assigned within the
       while statement. This is to allow the incoming value of the name to be
       accessed in all iterations of the loop. Otherwise, the token with value
       for the name will be consumed on the first iteration.]       
     
     * 4. If the assigned source for the name after the block of the while
     * statement is an output pin, then this output pin is identified as the
     * body output pin corresponding to the loop variable for the name.
     * Otherwise, a structured activity node is added to the mapping of the body
     * of the loop as follows: The structured activity node has one input pin
     * and one output pin, with an object flow from the input pin to the output
     * pin contained within the structured activity node. There is an object
     * flow from the assigned source for the name after the block to the input
     * pin of the structured activity node. The output pin of the structured
     * activity node is then used as the body output pin corresponding to the
     * name.
     * 
     * 5. If the assigned source of a name after a while statement is the
     * statement, then the source for its assigned value is the result output
     * pin of the loop node corresponding to the loop variable for the name.
     
       [Actually, the loop node result output pins need to have object flows to
       fork nodes, and the fork nodes are the sources for assigned values.]
     
     */
    
    // NOTE: See LoopStatementMapping for implementation of common mapping for
    // while and do statements.
    
    @Override
    public boolean isTestedFirst() {
        return true;
    }
    
    @Override
    public Expression getCondition() {
        return this.getWhileStatement().getCondition();
    }
    
    @Override
    public Block getBody() {
        return this.getWhileStatement().getBody();
    }
    
    @Override
    public Map<String, AssignedSource> getAssignments() {
        return this.getBody().getImpl().getAssignmentAfterMap();
    }
    
    public WhileStatement getWhileStatement() {
		return (WhileStatement) this.getSource();
	}

} // WhileStatementMapping
