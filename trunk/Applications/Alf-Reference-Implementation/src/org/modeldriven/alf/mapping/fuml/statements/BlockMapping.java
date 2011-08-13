
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
import org.modeldriven.alf.mapping.fuml.common.SyntaxElementMapping;

import org.modeldriven.alf.syntax.statements.Block;
import org.modeldriven.alf.syntax.statements.Statement;

import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;
import fUML.Syntax.Activities.IntermediateActivities.ControlFlow;
import fUML.Syntax.Classes.Kernel.Element;

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

    public void mapTo(List<Element> elements) throws MappingError {
        super.mapTo(null);

        List<Statement> statements = this.getBlock().getStatement();
        ActivityNode previousNode = null;

        for (Statement statement: statements) {
            FumlMapping mapping = (StatementMapping)this.fumlMap(statement);
            if (mapping instanceof StatementMapping) {
                // mapping.setContext(this.getContext());          
                ActivityNode node = 
                    ((StatementMapping)mapping).getNode();

                elements.addAll(mapping.getModelElements());

                if (!this.isParallel() && previousNode != null) {
                    ControlFlow flow = new ControlFlow();
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
