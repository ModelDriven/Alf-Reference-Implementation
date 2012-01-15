
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

import org.modeldriven.alf.syntax.expressions.StringLiteralExpression;

import fUML.Syntax.Actions.IntermediateActions.ValueSpecificationAction;
import fUML.Syntax.Classes.Kernel.LiteralString;

public class StringLiteralExpressionMapping extends LiteralExpressionMapping {

    @Override
    public ValueSpecificationAction mapValueSpecificationAction() throws MappingError {
        String image = this.getStringLiteralExpression().getImage();
        return this.graph.addStringValueSpecificationAction(
                image.substring(1,image.length()-1));
    }

	public StringLiteralExpression getStringLiteralExpression() {
		return (StringLiteralExpression) this.getSource();
	}

    @Override
    public String toString() {
        ValueSpecificationAction action = 
            (ValueSpecificationAction)this.getElement();
        return super.toString() + (action == null? "": " value:" + 
                ((LiteralString)action.value).value);
    }

} // StringLiteralExpressionMapping
