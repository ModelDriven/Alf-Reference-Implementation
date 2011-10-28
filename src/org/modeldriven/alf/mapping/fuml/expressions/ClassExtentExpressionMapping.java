
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
import org.modeldriven.alf.mapping.fuml.expressions.ExpressionMapping;

import org.modeldriven.alf.syntax.expressions.ClassExtentExpression;

import fUML.Syntax.Actions.CompleteActions.ReadExtentAction;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;
import fUML.Syntax.Classes.Kernel.Class_;
import fUML.Syntax.Classes.Kernel.Element;

public class ClassExtentExpressionMapping extends ExpressionMapping {

    private ReadExtentAction action = null;
    
    public ReadExtentAction mapAction() throws MappingError {
        return this.graph.addReadExtentAction((Class_)this.getType());
    }
    
    @Override
    public ActivityNode getResultSource() throws MappingError {
        ActivityNode result = this.getAction().result;
        return result == null? null: result;
    }
    
    public ReadExtentAction getAction() throws MappingError {
        if (this.action == null) {
            this.action = this.mapAction();
            this.mapTo(this.action);
          }
          return this.action;
    }
    
    @Override
    public Element getElement() {
        return this.action;
    }
    
    @Override
    public ActivityGraph getGraph() throws MappingError {
        this.getAction();
        return super.getGraph();
	}

	public ClassExtentExpression getClassExtentExpression() {
		return (ClassExtentExpression) this.getSource();
	}
	
	@Override
	public void print(String prefix) {
	    super.print(prefix);
	    if (this.action != null) {
	        System.out.println(prefix + " action: " + action);
	    }
	}

} // ClassExtentExpressionMapping
