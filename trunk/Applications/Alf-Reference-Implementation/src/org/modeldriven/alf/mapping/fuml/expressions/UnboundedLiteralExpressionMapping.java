
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.expressions.LiteralExpressionMapping;

import org.modeldriven.alf.syntax.expressions.UnboundedLiteralExpression;

import org.modeldriven.alf.uml.ValueSpecificationAction;

public class UnboundedLiteralExpressionMapping extends LiteralExpressionMapping {

    @Override
    public ValueSpecificationAction mapValueSpecificationAction() throws MappingError {
        return this.graph.addUnlimitedNaturalValueSpecificationAction(-1);
    }

	public UnboundedLiteralExpression getUnboundedLiteralExpression() {
		return (UnboundedLiteralExpression) this.getSource();
	}

} // UnboundedLiteralExpressionMapping
