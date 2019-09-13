/*******************************************************************************
 *  Copyright 2019 Model Driven Solutions, Inc.
 *  All rights reserved worldwide. This program and the accompanying materials
 *  are made available for use under the terms of the GNU General Public License 
 *  (GPL) version 3 that accompanies this distribution and is available at 
 *  http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 *  contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.Expression;

public class ExpressionPlaceholderImpl extends ExpressionImpl {

    public ExpressionPlaceholderImpl(Expression self) {
        super(self);
    }

    @Override
    protected Integer deriveUpper() {
        return null;
    }

    @Override
    protected Integer deriveLower() {
        return null;
    }

    @Override
    protected ElementReference deriveType() {
        return null;
    }

}
