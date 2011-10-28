
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions;

import java.util.Collection;

import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.ActivityGraph;
import org.modeldriven.alf.mapping.fuml.common.SyntaxElementMapping;

import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.expressions.FeatureReference;
import org.modeldriven.alf.syntax.expressions.LeftHandSide;

import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;
import fUML.Syntax.Activities.IntermediateActivities.ForkNode;
import fUML.Syntax.Classes.Kernel.Element;

public abstract class LeftHandSideMapping extends SyntaxElementMapping {
    
    protected ActivityGraph graph = new ActivityGraph();
    protected ForkNode resultSource;
    protected ActivityNode node = null;

    public void mapTo(ActivityNode node) throws MappingError {
        super.mapTo(node);
        
        LeftHandSide lhs = this.getLeftHandSide();
        FeatureReference feature = lhs.getImpl().getFeature();
        if (feature != null) {
            this.setErrorMessage("FeatureLeftHandSideMapping not yet implemented.");
        }
    }
    
    protected ActivityNode mapNode() throws MappingError {
        this.resultSource = this.graph.addForkNode("Fork(LeftHandSide@" + 
                Integer.toHexString(this.getLeftHandSide().hashCode()) + ")");
        return this.resultSource;
    }
    
    public ActivityNode getNode() throws MappingError {
        if (this.node == null) {
            this.node = this.mapNode();
            this.mapTo(this.node);
        }
        return this.node;
    }
    
    public ForkNode getResultSource() throws MappingError {
        this.getNode();
        return this.resultSource;
    }
    
    /**
     * The assigned value source is the activity node that is the source for
     * the value of a local name whose assigned source is the result of an
     * assignment to this left hand side. This will be different from the
     * result source for a feature left hand side or an indexed left hand side.
     */
    public ActivityNode getAssignedValueSource() throws MappingError {
        return this.getResultSource();
    }
    
    /**
     * The assignment target is the activity node which should receive the value
     * to be assigned.
     */
    public ActivityNode getAssignmentTarget() throws MappingError {
        return this.getResultSource();
    }
    
    @Override
    public Element getElement() {
        return this.node;
    }
    
    @Override
    public Collection<Element> getModelElements() throws MappingError {
        return this.getGraph().getModelElements();
    }

	public LeftHandSide getLeftHandSide() {
		return (LeftHandSide) this.getSource();
	}
    public ActivityGraph getGraph() throws MappingError {
        this.getNode();
        return this.graph;
    }
	
	@Override
	public void print(String prefix) {
	    super.print(prefix);
	    
	    LeftHandSide source = this.getLeftHandSide();
	    Expression index = source.getIndex();
	    if (index != null) {
	        Mapping indexMapping = index.getImpl().getMapping();
	        if (indexMapping != null) {
	            indexMapping.printChild(prefix);
	        }
	    }
	}

} // LeftHandSideMapping
