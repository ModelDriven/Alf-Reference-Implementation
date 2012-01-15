
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.expressions.LiteralExpressionMapping;

import org.modeldriven.alf.syntax.expressions.BooleanLiteralExpression;

import fUML.Syntax.Actions.IntermediateActions.ValueSpecificationAction;
import fUML.Syntax.Classes.Kernel.LiteralBoolean;

public class BooleanLiteralExpressionMapping extends LiteralExpressionMapping {

    @Override
    public ValueSpecificationAction mapValueSpecificationAction() throws MappingError {
        String image = this.getBooleanLiteralExpression().getImage();        
        return this.graph.addBooleanValueSpecificationAction(image.equals("true"));
    }

	public BooleanLiteralExpression getBooleanLiteralExpression() {
		return (BooleanLiteralExpression) this.getSource();
	}

    @Override
    public String toString() {
        ValueSpecificationAction action = 
            (ValueSpecificationAction)this.getElement();
        return super.toString() + (action == null? "": " value:" + 
                ((LiteralBoolean)action.value).value);
    }

} // BooleanLiteralExpressionMapping
