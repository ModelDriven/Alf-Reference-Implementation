/*******************************************************************************
 * Copyright 2016 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
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
 * An expression that comprises a real literal.
 **/

public class RealLiteralExpressionImpl extends LiteralExpressionImpl {

	private String image = "";

	public RealLiteralExpressionImpl(RealLiteralExpression self) {
		super(self);
	}

	@Override
	public RealLiteralExpression getSelf() {
		return (RealLiteralExpression) this.self;
	}

	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * The type of a natural literal is the Alf library type Real.
	 **/
	@Override
	protected ElementReference deriveType() {
	    return RootNamespace.getRootScope().getRealType();
	}
	
	/*
	 * Derivations
	 */
	
	public boolean realLiteralExpressionTypeDerivation() {
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
        if (base instanceof RealLiteralExpression) {
            this.getSelf().setImage(((RealLiteralExpression)base).getImage());
        }
    }

}
