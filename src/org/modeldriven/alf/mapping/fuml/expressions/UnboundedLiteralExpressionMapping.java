
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.fuml.expressions.LiteralExpressionMapping;

import org.modeldriven.alf.syntax.expressions.UnboundedLiteralExpression;

import fUML.Syntax.Classes.Kernel.LiteralUnlimitedNatural;
import fUML.Syntax.Classes.Kernel.ValueSpecification;

public class UnboundedLiteralExpressionMapping extends LiteralExpressionMapping {

    @Override
    public ValueSpecification mapValueSpecification() {
        LiteralUnlimitedNatural literal = new LiteralUnlimitedNatural();
        literal.setName("Value(*)");
        literal.setValue(-1);

        return literal;
    }

	public UnboundedLiteralExpression getUnboundedLiteralExpression() {
		return (UnboundedLiteralExpression) this.getSource();
	}

} // UnboundedLiteralExpressionMapping
