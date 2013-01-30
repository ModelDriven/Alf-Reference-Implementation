
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.expressions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.fuml.mapping.ActivityGraph;
import org.modeldriven.alf.fuml.mapping.common.SyntaxElementMapping;
import org.modeldriven.alf.mapping.MappingError;

import org.modeldriven.alf.syntax.expressions.SequenceElements;

import org.modeldriven.alf.uml.ActivityNode;
import org.modeldriven.alf.uml.Element;

public abstract class SequenceElementsMapping extends SyntaxElementMapping {

    protected ActivityGraph graph = null;
    protected List<ActivityNode> resultSources = null;
    
    protected abstract void map() throws MappingError; 
    
    @Override
    public Collection<Element> getModelElements() throws MappingError {
        if (this.graph == null) {
            this.graph = this.createActivityGraph();
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
