
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

import org.modeldriven.alf.syntax.expressions.StringLiteralExpression;

import org.modeldriven.alf.uml.ValueSpecificationAction;
import org.modeldriven.alf.uml.LiteralString;

public class StringLiteralExpressionMapping extends LiteralExpressionMapping {
    
    public static String valueOf(String image) {
        return image.length() < 2? "": image.substring(1,image.length()-1);
    }

    @Override
    public ValueSpecificationAction mapValueSpecificationAction() throws MappingError {
        String image = this.getStringLiteralExpression().getImage();
        return this.graph.addStringValueSpecificationAction(valueOf(image));
    }

	public StringLiteralExpression getStringLiteralExpression() {
		return (StringLiteralExpression) this.getSource();
	}

    @Override
    public String toString() {
        ValueSpecificationAction action = 
            (ValueSpecificationAction)this.getElement();
        return super.toString() + (action == null? "": " value:" + 
                ((LiteralString)action.getValue()).getValue());
    }

} // StringLiteralExpressionMapping
