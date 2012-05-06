
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
import org.modeldriven.alf.mapping.fuml.statements.StatementMapping;

import org.modeldriven.alf.syntax.statements.Block;
import org.modeldriven.alf.syntax.statements.BlockStatement;

public class BlockStatementMapping extends StatementMapping {

    /**
     * A block statement maps to a structured activity node containing all the
     * activity nodes and edges mapped from its block. If the block statement is
     * not parallel, then the nodes mapped from the statements of the block have
     * control flows between them enforcing their sequential execution. If the
     * block statement is parallel, then there are no such control flows.
     */
    
    @Override
    public void map() throws MappingError {
        super.map();
        
        BlockStatement statement = this.getBlockStatement();
        FumlMapping mapping = this.fumlMap(statement.getBlock());
        if (!(mapping instanceof BlockMapping)) {
            this.throwError("Error mapping block: " + mapping.getErrorMessage());
        } else {
            ((BlockMapping)mapping).setIsParallel(statement.getIsParallel());
            this.addToNode(mapping.getModelElements());
        }
    }
    
	public BlockStatement getBlockStatement() {
		return (BlockStatement) this.getSource();
	}
	
	@Override
	public void print(String prefix) {
	    super.print(prefix);
	    
	    Block block = this.getBlockStatement().getBlock();
	    if (block != null) {
	        System.out.println(prefix + " block:");
	        Mapping mapping = block.getImpl().getMapping();
	        if (mapping != null) {
	            mapping.printChild(prefix);
	        }
	    }
	}

} // BlockStatementMapping
