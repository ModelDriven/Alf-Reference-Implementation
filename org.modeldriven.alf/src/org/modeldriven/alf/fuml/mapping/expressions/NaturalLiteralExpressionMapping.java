
/*******************************************************************************
 * Copyright 2011-2016 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.expressions;

import org.modeldriven.alf.fuml.mapping.expressions.LiteralExpressionMapping;
import org.modeldriven.alf.mapping.MappingError;

import org.modeldriven.alf.syntax.expressions.NaturalLiteralExpression;

import org.modeldriven.alf.uml.ValueSpecificationAction;
import org.modeldriven.alf.uml.LiteralInteger;

public class NaturalLiteralExpressionMapping extends LiteralExpressionMapping {
    
    public static int valueOf(String image) {
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
        return Integer.parseInt(image.replaceAll("_", ""), radix);
    }

    @Override
    public ValueSpecificationAction mapValueSpecificationAction() throws MappingError {
        String image = this.getNaturalLiteralExpression().getImage();
        return this.graph.addNaturalValueSpecificationAction(valueOf(image));
    }

	public NaturalLiteralExpression getNaturalLiteralExpression() {
		return (NaturalLiteralExpression) this.getSource();
	}
	
	@Override
	public String toString() {
	    ValueSpecificationAction action = 
	        (ValueSpecificationAction)this.getElement();
	    return super.toString() + (action == null? "": " value:" + 
	            ((LiteralInteger)action.getValue()).getValue());
	}

} // NaturalLiteralExpressionMapping
