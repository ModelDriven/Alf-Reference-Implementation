
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.fuml.expressions.LiteralExpressionMapping;

import org.modeldriven.alf.syntax.expressions.BooleanLiteralExpression;

import fUML.Syntax.Classes.Kernel.LiteralBoolean;
import fUML.Syntax.Classes.Kernel.ValueSpecification;

public class BooleanLiteralExpressionMapping extends LiteralExpressionMapping {

    @Override
    public ValueSpecification mapValueSpecification() {
        String image = this.getBooleanLiteralExpression().getImage();

        LiteralBoolean literal = new LiteralBoolean();
        literal.setName("Value(" + image + ")");
        literal.setValue(image.equals("true"));

        return literal;
    }

	public BooleanLiteralExpression getBooleanLiteralExpression() {
		return (BooleanLiteralExpression) this.getSource();
	}

} // BooleanLiteralExpressionMapping
