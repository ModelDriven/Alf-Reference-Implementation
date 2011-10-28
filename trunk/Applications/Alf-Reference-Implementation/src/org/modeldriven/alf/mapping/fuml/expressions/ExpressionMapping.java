
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions;

import java.util.Collection;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.ActivityGraph;
import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.mapping.fuml.common.ElementReferenceMapping;
import org.modeldriven.alf.mapping.fuml.common.SyntaxElementMapping;
import org.modeldriven.alf.mapping.fuml.units.ClassifierDefinitionMapping;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.Expression;

import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;
import fUML.Syntax.Classes.Kernel.Classifier;
import fUML.Syntax.Classes.Kernel.Element;

public abstract class ExpressionMapping extends SyntaxElementMapping {
    
    private Classifier type = null;
    protected ActivityGraph graph = new ActivityGraph();

    public ActivityNode getResultSource() throws MappingError {
        return null;
    }
    
    @Override
    public ActivityNode getAssignedValueSource(String name) throws MappingError {
        return this.getResultSource();
    }
    
    @Override
    public void mapTo(Element element) throws MappingError {
        super.mapTo(element);
        this.getType();
    }
    
    public Classifier getType() throws MappingError {
        if (this.type == null) {
            ElementReference reference = this.getExpression().getType();
            if (reference != null) {
                FumlMapping mapping = this.fumlMap(reference);
                if (mapping instanceof ElementReferenceMapping) {
                    mapping = ((ElementReferenceMapping)mapping).getMapping();
                    if (mapping instanceof ClassifierDefinitionMapping) {
                        this.type = 
                            ((ClassifierDefinitionMapping)mapping).getClassifier();
                    }
                }
            }
        }
        return this.type;
    }
    
    public ActivityGraph getGraph() throws MappingError {
        return this.graph;
    }
    
    @Override
    public Collection<Element> getModelElements() throws MappingError {
        return this.getGraph().getModelElements();
    }
    
	public Expression getExpression() {
		return (Expression) this.getSource();
	}
	
	@Override
	public String toString() {
	    return super.toString() + " type:" + this.type;  
	}

} // ExpressionMapping