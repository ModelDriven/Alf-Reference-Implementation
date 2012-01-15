
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.ActivityGraph;
import org.modeldriven.alf.mapping.fuml.expressions.SequenceExpansionExpressionMapping;

import org.modeldriven.alf.syntax.expressions.CollectOrIterateExpression;

import fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionKind;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;

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
    public void map() throws MappingError {
        super.map();
        if ("iterate".equals(this.getCollectOrIterateExpression().getOperation())) {
            this.region.setMode(ExpansionKind.iterative);
        }
    }

	public CollectOrIterateExpression getCollectOrIterateExpression() {
		return (CollectOrIterateExpression) this.getSource();
	}
	
} // CollectOrIterateExpressionMapping
