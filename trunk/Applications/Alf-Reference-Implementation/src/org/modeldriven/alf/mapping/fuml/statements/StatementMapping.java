
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.statements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.ActivityGraph;
import org.modeldriven.alf.mapping.fuml.common.DocumentedElementMapping;

import org.modeldriven.alf.syntax.statements.Statement;

import fUML.Syntax.Activities.CompleteStructuredActivities.StructuredActivityNode;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;
import fUML.Syntax.Classes.Kernel.Element;

public abstract class StatementMapping extends DocumentedElementMapping {

    private ActivityNode node = null;
    private List<Element> modelElements = new ArrayList<Element>();
    
    public ActivityNode mapNode() {
        return new StructuredActivityNode();
    }
    
    public void mapTo(ActivityNode node) throws MappingError {
        super.mapTo(node);

        Statement statement = this.getStatement();
        String s = statement.getClass().getName();
        node.setName(s.substring(s.lastIndexOf(".")+1) + "@" + statement.getId());
    }
    
    public void addModelElement(Element element) {
        this.modelElements.add(element);
    }
    
    public void addToNode(ActivityNode node, Collection<Element> elements) {
        ActivityGraph.addTo((StructuredActivityNode)node, elements, this.modelElements);
    }
    
    public ActivityNode getNode() throws MappingError {
        if (this.node == null) {
            this.node = this.mapNode();
            this.mapTo(this.node);
          }
          return this.node;
    }
    
	public Statement getStatement() {
		return (Statement) this.getSource();
	}
	
	public Element getElement() {
	    return this.node;
	}
	
	@Override
	public List<Element> getModelElements() throws MappingError {
	    List<Element> elements = new ArrayList<Element>();
	    elements.add(this.getNode());
	    elements.addAll(this.modelElements);
	    return elements;
	}
	
	@Override
	public String toString() {
	    return super.toString() + " node:" + this.node;
	}

} // StatementMapping
