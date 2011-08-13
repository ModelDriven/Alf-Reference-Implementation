
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

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.expressions.ExpressionMapping;

import org.modeldriven.alf.syntax.expressions.LiteralExpression;

import fUML.Syntax.Actions.BasicActions.OutputPin;
import fUML.Syntax.Actions.IntermediateActions.ValueSpecificationAction;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;
import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.Classes.Kernel.ValueSpecification;

public abstract class LiteralExpressionMapping extends ExpressionMapping {

    private ValueSpecificationAction action = null;
    
    public void mapTo(ValueSpecificationAction action) throws MappingError {
        super.mapTo(action);

        ValueSpecification value = this.mapValueSpecification();
        action.setName(value.name);
        action.setValue(value);

        OutputPin result = new OutputPin();
        result.setName(action.name+".result");
        result.setType(value.type);
        result.setLower(1);
        result.setUpper(1);
        action.setResult(result);
    }
    
    public abstract ValueSpecification mapValueSpecification();
    
    @Override
    public ActivityNode getResultSource() throws MappingError {
        ValueSpecificationAction action = this.getAction();
        return action == null? null: action.result;
    }
    
    public ValueSpecificationAction getAction() throws MappingError {
        if (this.action == null) {
            this.action = new ValueSpecificationAction();
            this.mapTo(this.action);
          }
          return this.action;
    }
    
	public LiteralExpression getLiteralExpression() {
		return (LiteralExpression) this.getSource();
	}
	
	@Override
	public Element getElement() {
	    return this.action;
	}
	
	@Override
	public List<Element> getModelElements() throws MappingError {
	    List<Element> elements = new ArrayList<Element>();
	    ValueSpecificationAction action = this.getAction();
	    if (action != null) {
	      elements.add(action);
	    }
	    return elements;
	}
	
	@Override
	public void print(String prefix) {
	    super.print(prefix);
	    System.out.println(prefix + " action: " + this.action);
	}

} // LiteralExpressionMapping
