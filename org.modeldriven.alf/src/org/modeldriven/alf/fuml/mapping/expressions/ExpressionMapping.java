/*******************************************************************************
 * Copyright 2011, 2018 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.expressions;

import java.util.Collection;

import org.modeldriven.alf.fuml.mapping.ActivityGraph;
import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.fuml.mapping.common.ElementReferenceMapping;
import org.modeldriven.alf.fuml.mapping.common.SyntaxElementMapping;
import org.modeldriven.alf.fuml.mapping.units.ClassifierDefinitionMapping;
import org.modeldriven.alf.mapping.MappingError;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.Expression;

import org.modeldriven.alf.uml.ActivityNode;
import org.modeldriven.alf.uml.Classifier;
import org.modeldriven.alf.uml.Element;

public abstract class ExpressionMapping extends SyntaxElementMapping {
    
    private boolean isIndexFrom0 = false;
    private Classifier type = null;
    protected ActivityGraph graph = this.createActivityGraph();
    
    public boolean isIndexFrom0() {
        return this.isIndexFrom0;
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
                if (reference.getImpl().isClassifierTemplateParameter()) {
                    reference = reference.getImpl().getParameteredElement();
                }
                this.type = (Classifier)reference.getImpl().getUml();
                if (this.type == null) {
                    FumlMapping mapping = this.fumlMap(reference);
                    if (mapping instanceof ElementReferenceMapping) {
                        mapping = ((ElementReferenceMapping)mapping).getMapping();
                        if (mapping instanceof ClassifierDefinitionMapping) {
                            this.type = ((ClassifierDefinitionMapping)mapping).
                                    getClassifier();
                        }
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
	
    public void setIsIndexFrom0(boolean isIndexFrom0) {
        this.isIndexFrom0 = isIndexFrom0;
    }

    public ActivityNode getResultSource() throws MappingError {
        return null;
    }
    
    public FumlMapping exprMap(Expression expression) {
        FumlMapping mapping = this.fumlMap(expression);
        if (mapping instanceof ExpressionMapping) {
            ((ExpressionMapping)mapping).setIsIndexFrom0(this.isIndexFrom0());
        }
        return mapping;
    }
    
	/**
	 * Return the source node for the value of an index of this expression, if
	 * any.
	 */
	public ActivityNode getIndexSource() throws MappingError {
	    return null;
	}
	
	/**
	 * Return the source node for the value of the feature object of this
	 * expression, if any.
	 */
	public ActivityNode getObjectSource() throws MappingError {
	    return null;
	}
	
	@Override
	public String toString() {
	    return super.toString() + " type:" + this.type;  
	}

} // ExpressionMapping