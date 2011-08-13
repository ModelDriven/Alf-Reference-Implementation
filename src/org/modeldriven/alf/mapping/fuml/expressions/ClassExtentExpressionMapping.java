
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.expressions.ExpressionMapping;

import org.modeldriven.alf.syntax.expressions.ClassExtentExpression;

import fUML.Syntax.Actions.BasicActions.OutputPin;
import fUML.Syntax.Actions.CompleteActions.ReadExtentAction;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;
import fUML.Syntax.Classes.Kernel.Class_;
import fUML.Syntax.Classes.Kernel.Element;

import java.util.ArrayList;
import java.util.List;

public class ClassExtentExpressionMapping extends ExpressionMapping {

    private ReadExtentAction action = null;
    
    public void mapTo(ReadExtentAction action) throws MappingError {
        super.mapTo(action);

        Class_ class_ = (Class_)this.getType();
        if (class_ != null) {
            action.setName("ReadExtent(" + class_.name + ")");
            action.setClassifier(class_);

            OutputPin result = new OutputPin();
            result.setName(action.name + ".result");
            result.setType(class_);
            result.setLower(0);
            result.setUpper(-1);
            action.setResult(result);
        }
    }
    
    public ActivityNode getResultSource() throws MappingError {
        return this.getAction().result;
    }
    
    public ReadExtentAction getAction() throws MappingError {
        if (this.action == null) {
            this.action = new ReadExtentAction();
            this.mapTo(this.action);
          }

          return this.action;
    }
    
    public Element getElement() {
        return this.action;
    }
    
    public List<Element> getModelElements() throws MappingError {
        ArrayList<Element> elements = new ArrayList<Element>();
        ReadExtentAction action = this.getAction();
        if (action != null) {
          elements.add(action);
        }
        return elements;
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
