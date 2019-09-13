/*******************************************************************************
 * Copyright 2011, 2016 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.expressions;

import org.modeldriven.alf.fuml.mapping.ActivityGraph;
import org.modeldriven.alf.fuml.mapping.expressions.SequenceExpansionExpressionMapping;
import org.modeldriven.alf.mapping.MappingError;

import org.modeldriven.alf.syntax.expressions.CollectOrIterateExpression;

import org.modeldriven.alf.uml.ActivityNode;
import org.modeldriven.alf.uml.Classifier;

public class CollectOrIterateExpressionMapping extends
		SequenceExpansionExpressionMapping {

    /**
     * 1. A collect or iterate expression is mapped as a sequence expansion
     * expression. The expansion region has an output expansion node of the same
     * type as the argument expression. The result source element of the mapping
     * of the argument expression is connected by an object flow inside the
     * expansion region to the output expansion node.
     * 
     * 2. For an iterate operation, the expansion region has mode=iterative.
     * Otherwise it has the normal mode=parallel.
     */

    @Override
    protected ActivityNode mapNestedGraph(
            String operation,
            ActivityNode variableSource, 
            ActivityGraph nestedGraph,
            ActivityNode resultNode) throws MappingError {
        return resultNode == null? nestedGraph.addMergeNode("Merge"): resultNode;
    }
    
    @Override
    public Classifier map() throws MappingError {
        Classifier argumentType = super.map();
        this.region.getOutputElement().get(0).setType(argumentType);
        if ("iterate".equals(this.getCollectOrIterateExpression().getOperation())) {
            this.region.setMode("iterative");
        }
        return argumentType;
    }

	public CollectOrIterateExpression getCollectOrIterateExpression() {
		return (CollectOrIterateExpression) this.getSource();
	}
	
} // CollectOrIterateExpressionMapping
