
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.mapping.fuml.expressions.SequenceElementsMapping;

import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.expressions.SequenceExpressionList;

import fUML.Syntax.Activities.CompleteStructuredActivities.StructuredActivityNode;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;

import java.util.Collection;
import java.util.List;

public class SequenceExpressionListMapping extends SequenceElementsMapping {
    
    @Override
    public List<ActivityNode> getResultSources() throws MappingError {
        this.getModelElements();
        return this.resultSources;
    }

    /**
     * Each element expression is mapped, with each expression providing a
     * result source. If there is more than one element expression, then the
     * mapping for each element expression is wrapped in its own structured
     * activity node and they are connected sequentially by control flows.
     * 
     */

    @Override
	public void map() throws MappingError {
        StructuredActivityNode previousNode = null;
        SequenceExpressionList expressionList = this.getSequenceExpressionList();
        Collection<Expression> elements = expressionList.getElement();
        for (Expression element: elements) {
            FumlMapping mapping = this.fumlMap(element);
            if (!(mapping instanceof ExpressionMapping)) {
                this.throwError("Error mapping expression list element: " + 
                        mapping.getErrorMessage());
            } else {
                ExpressionMapping expressionMapping = (ExpressionMapping)mapping;
                this.resultSources.add(expressionMapping.getResultSource());
                if (elements.size() == 1) {
                    this.graph.addAll(expressionMapping.getGraph());
                } else {
                    StructuredActivityNode node = 
                        this.graph.addStructuredActivityNode(
                                "SequenceExpressionList@" + expressionList.getId(), 
                                expressionMapping.getModelElements());
                    if (previousNode != null) {
                        this.graph.addControlFlow(previousNode, node);
                    }
                    previousNode = node;
                }
            }
        }
	}

	public SequenceExpressionList getSequenceExpressionList() {
		return (SequenceExpressionList) this.getSource();
	}
	
	@Override
	public void print(String prefix) {
	    super.print(prefix);
	    
	    Collection<Expression> elements = 
	        this.getSequenceExpressionList().getElement();
	    if (elements != null) {
	        System.out.println(prefix + " element:");
	        for (Expression element: elements) {
	            Mapping mapping = element.getImpl().getMapping();
	            if (mapping != null) {
	                mapping.printChild(prefix);
	            }
	        }
	    }
	}

} // SequenceExpressionListMapping
