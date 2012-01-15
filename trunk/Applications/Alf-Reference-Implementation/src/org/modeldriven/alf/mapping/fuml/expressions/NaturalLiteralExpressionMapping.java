
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

import org.modeldriven.alf.syntax.expressions.NaturalLiteralExpression;

import fUML.Syntax.Actions.IntermediateActions.ValueSpecificationAction;
import fUML.Syntax.Classes.Kernel.LiteralInteger;

public class NaturalLiteralExpressionMapping extends LiteralExpressionMapping {

    @Override
    public ValueSpecificationAction mapValueSpecificationAction() throws MappingError {
        String image = this.getNaturalLiteralExpression().getImage();
        int radix = 10;
        if (image.length() > 1 && image.charAt(0) == '0') {
            char radixChar = image.charAt(1);
            radix = 
                radixChar == 'b' || radixChar == 'B'? 2: 
                radixChar == 'x' || radixChar == 'X'? 16: 8;
            if (radix != 8) {
                image = image.substring(2);
            }
        }
        return this.graph.addNaturalValueSpecificationAction(
                Integer.parseInt(image.replaceAll("_", ""), radix));
    }

	public NaturalLiteralExpression getNaturalLiteralExpression() {
		return (NaturalLiteralExpression) this.getSource();
	}
	
	@Override
	public String toString() {
	    ValueSpecificationAction action = 
	        (ValueSpecificationAction)this.getElement();
	    return super.toString() + (action == null? "": " value:" + 
	            ((LiteralInteger)action.value).value);
	}

} // NaturalLiteralExpressionMapping
