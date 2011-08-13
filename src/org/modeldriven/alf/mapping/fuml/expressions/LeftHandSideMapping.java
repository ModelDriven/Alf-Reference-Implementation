
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions;

import java.util.ArrayList;
import java.util.List;

import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.common.gen.SyntaxElementMapping;

import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.expressions.FeatureReference;
import org.modeldriven.alf.syntax.expressions.LeftHandSide;

import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;
import fUML.Syntax.Activities.IntermediateActivities.ForkNode;
import fUML.Syntax.Classes.Kernel.Element;

public abstract class LeftHandSideMapping extends SyntaxElementMapping {
    
    private ForkNode resultSource = null;

    public void mapTo(ForkNode resultSource) throws MappingError {
        super.mapTo(resultSource);
        
        LeftHandSide lhs = this.getLeftHandSide();
        FeatureReference feature = lhs.getImpl().getFeature();
        if (feature != null) {
            this.setErrorMessage("FeatureLeftHandSideMapping not yet implemented.");
            this.resultSource = new ForkNode();
        }
    }
    
    public ForkNode getResultSource() throws MappingError {
        if (this.resultSource == null) {
            this.resultSource = new ForkNode();
            this.mapTo(this.resultSource);
        }
        return this.resultSource;
    }
    
    public ActivityNode getAssignedValueSource() throws MappingError {
        return this.getResultSource();
    }

    public ActivityNode getAssignmentTarget() throws MappingError {
        return this.getResultSource();
    }
    
    @Override
    public Element getElement() {
        return this.resultSource;
    }
    
    @Override
    public List<Element> getModelElements() throws MappingError {
        List<Element> elements = new ArrayList<Element>();
        ForkNode resultSource = this.getResultSource();
        if (resultSource != null) {
            elements.add(resultSource);
        }
        return elements;
    }

	public LeftHandSide getLeftHandSide() {
		return (LeftHandSide) this.getSource();
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
