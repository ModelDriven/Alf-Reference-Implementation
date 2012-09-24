
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.mapping.fuml.statements;

import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.mapping.fuml.common.SyntaxElementMapping;

import org.modeldriven.alf.syntax.statements.Block;
import org.modeldriven.alf.syntax.statements.Statement;

import org.modeldriven.alf.uml.ActivityNode;
import org.modeldriven.alf.uml.ControlFlow;
import org.modeldriven.alf.uml.Element;

import java.util.ArrayList;
import java.util.List;

public class BlockMapping extends SyntaxElementMapping {
    
    private boolean isParallel = false;
    
    public void setIsParallel(boolean isParallel) {
        this.isParallel = isParallel;
    }
    
    public boolean isParallel() {
        return this.isParallel;
    }

    /**
     * A block maps to the union of the nodes mapped from the statements in it.
     * In addition, unless the block is in a block statement annotated as being
     * parallel, the node mapped from each statement other than the last has a
     * control flow targeted to the node mapped from the next statement.
     */
    
    public void mapTo(List<Element> elements) throws MappingError {
        super.mapTo(null);

        List<Statement> statements = this.getBlock().getStatement();
        ActivityNode previousNode = null;

        for (Statement statement: statements) {
            FumlMapping mapping = (StatementMapping)this.fumlMap(statement);
            if (mapping instanceof StatementMapping) {
                ActivityNode node = 
                    ((StatementMapping)mapping).getNode();

                elements.addAll(mapping.getModelElements());

                if (!this.isParallel() && previousNode != null) {
                    ControlFlow flow = this.create(ControlFlow.class);
                    flow.setSource(previousNode);
                    flow.setTarget(node);
                    elements.add(flow);
                }

                previousNode = node;
            }
        }
    }
    
	public Block getBlock() {
		return (Block) this.getSource();
	}

    @Override
	public List<Element> getModelElements() throws MappingError {
        ArrayList<Element> elements = new ArrayList<Element>();
        this.mapTo(elements);
        return elements;
	}
    
    @Override
    public String toString() {
        return super.toString() + " isParallel:" + this.isParallel();
    }
    
    @Override
    public void print(String prefix) {
        super.print(prefix);
        
        List<Statement> statements = this.getBlock().getStatement();
        if (!statements.isEmpty()) {
            System.out.println(prefix + " statement:");
            for (Statement statement: statements) {
                Mapping mapping = statement.getImpl().getMapping();
                if (mapping != null) {
                    mapping.printChild(prefix);
                }
            }
        }
    }

} // BlockMapping
