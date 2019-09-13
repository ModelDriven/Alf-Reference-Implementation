/*******************************************************************************
 * Copyright 2016 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.expressions;

import org.modeldriven.alf.fuml.mapping.expressions.LiteralExpressionMapping;
import org.modeldriven.alf.mapping.MappingError;

import org.modeldriven.alf.syntax.expressions.RealLiteralExpression;
import org.modeldriven.alf.uml.ValueSpecificationAction;
import org.modeldriven.alf.uml.LiteralReal;

public class RealLiteralExpressionMapping extends LiteralExpressionMapping {
    
    public static float valueOf(String image) {
        return Float.parseFloat(image.replace("_", ""));
    }

    @Override
    public ValueSpecificationAction mapValueSpecificationAction() throws MappingError {
        String image = this.getRealLiteralExpression().getImage();
        return this.graph.addRealValueSpecificationAction(valueOf(image));
    }

	public RealLiteralExpression getRealLiteralExpression() {
		return (RealLiteralExpression) this.getSource();
	}
	
	@Override
	public String toString() {
	    ValueSpecificationAction action = 
	        (ValueSpecificationAction)this.getElement();
	    return super.toString() + (action == null? "": " value:" + 
	            ((LiteralReal)action.getValue()).getValue());
	}

}
