
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

import org.modeldriven.alf.syntax.expressions.UnboundedLiteralExpression;

import fUML.Syntax.Actions.IntermediateActions.ValueSpecificationAction;

public class UnboundedLiteralExpressionMapping extends LiteralExpressionMapping {

    @Override
    public ValueSpecificationAction mapValueSpecificationAction() throws MappingError {
        return this.graph.addUnlimitedNaturalValueSpecificationAction(-1);
    }

	public UnboundedLiteralExpression getUnboundedLiteralExpression() {
		return (UnboundedLiteralExpression) this.getSource();
	}

} // UnboundedLiteralExpressionMapping
