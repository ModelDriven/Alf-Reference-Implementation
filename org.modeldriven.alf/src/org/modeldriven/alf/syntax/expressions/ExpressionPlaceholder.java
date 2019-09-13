/*******************************************************************************
 *  Copyright 2019 Model Driven Solutions, Inc.
 *  All rights reserved worldwide. This program and the accompanying materials
 *  are made available for use under the terms of the GNU General Public License 
 *  (GPL) version 3 that accompanies this distribution and is available at 
 *  http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 *  contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.syntax.expressions.impl.ExpressionPlaceholderImpl;

public class ExpressionPlaceholder extends Expression {
    public ExpressionPlaceholder() {
        this.impl = new ExpressionPlaceholderImpl(this);
    }
}
