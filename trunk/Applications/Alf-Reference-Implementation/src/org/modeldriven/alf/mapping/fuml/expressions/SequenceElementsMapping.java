
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.ActivityGraph;
import org.modeldriven.alf.mapping.fuml.common.gen.SyntaxElementMapping;

import org.modeldriven.alf.syntax.expressions.SequenceElements;

import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;
import fUML.Syntax.Classes.Kernel.Element;

public abstract class SequenceElementsMapping extends SyntaxElementMapping {

    protected ActivityGraph graph = null;
    protected List<ActivityNode> resultSources = null;
    
    protected abstract void map() throws MappingError; 
    
    @Override
    public Collection<Element> getModelElements() throws MappingError {
        if (this.graph == null) {
            this.graph = new ActivityGraph();
            this.resultSources = new ArrayList<ActivityNode>();
            this.map();
        }
        return this.graph.getModelElements();
    }
    
    public List<ActivityNode> getResultSources() throws MappingError {
        this.getModelElements();
        return this.resultSources;
    }
    
	public SequenceElements getSequenceElements() {
		return (SequenceElements) this.getSource();
	}

} // SequenceElementsMapping
