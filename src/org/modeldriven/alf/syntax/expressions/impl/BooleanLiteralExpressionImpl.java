
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl;

import java.util.List;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

/**
 * An expression that comprises a Boolean literal.
 **/

public class BooleanLiteralExpressionImpl extends LiteralExpressionImpl {

	private String image = "";

	public BooleanLiteralExpressionImpl(BooleanLiteralExpression self) {
		super(self);
	}

	@Override
	public BooleanLiteralExpression getSelf() {
		return (BooleanLiteralExpression) this.self;
	}

	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	/**
	 * The type of a boolean literal expression is Boolean.
	 **/
	@Override
	protected ElementReference deriveType() {
	    return RootNamespace.getBooleanType();
	}
	
	/*
	 * Derivations
	 */

	public boolean booleanLiteralExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}
	
	/*
	 * Helper Methods
	 */
	
	@Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof BooleanLiteralExpression) {
            this.getSelf().setImage(((BooleanLiteralExpression)base).getImage());
        }
    }

} // BooleanLiteralExpressionImpl
