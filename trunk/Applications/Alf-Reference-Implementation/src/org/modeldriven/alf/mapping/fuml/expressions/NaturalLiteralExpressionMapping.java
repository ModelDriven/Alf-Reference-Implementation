
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.fuml.expressions.LiteralExpressionMapping;

import org.modeldriven.alf.syntax.expressions.NaturalLiteralExpression;

import fUML.Syntax.Classes.Kernel.LiteralInteger;
import fUML.Syntax.Classes.Kernel.ValueSpecification;

public class NaturalLiteralExpressionMapping extends LiteralExpressionMapping {

    @Override
    public ValueSpecification mapValueSpecification() {
        String image = this.getNaturalLiteralExpression().getImage();

        LiteralInteger literal = new LiteralInteger();
        literal.setName("Value(" + image + ")");
        literal.setValue(Integer.parseInt(image));

        return literal;
    }

	public NaturalLiteralExpression getNaturalLiteralExpression() {
		return (NaturalLiteralExpression) this.getSource();
	}

} // NaturalLiteralExpressionMapping
