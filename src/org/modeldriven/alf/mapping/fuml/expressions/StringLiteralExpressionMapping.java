
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.fuml.expressions.LiteralExpressionMapping;

import org.modeldriven.alf.syntax.expressions.StringLiteralExpression;

import fUML.Syntax.Classes.Kernel.LiteralString;
import fUML.Syntax.Classes.Kernel.ValueSpecification;

public class StringLiteralExpressionMapping extends LiteralExpressionMapping {

    @Override
    public ValueSpecification mapValueSpecification() {
        String image = this.getStringLiteralExpression().getImage();

        LiteralString literal = new LiteralString();
        literal.setName("Value(" + image + ")");
        literal.setValue(image.substring(1,image.length()-1));

        return literal;
    }

	public StringLiteralExpression getStringLiteralExpression() {
		return (StringLiteralExpression) this.getSource();
	}

} // StringLiteralExpressionMapping
