
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

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
