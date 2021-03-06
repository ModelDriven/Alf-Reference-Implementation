
/*******************************************************************************
 * Copyright 2011-2015 Model Driven Solutions, Inc.
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
 * An expression that comprises a natural literal.
 **/

public class NaturalLiteralExpressionImpl extends LiteralExpressionImpl {

	private String image = "";

	public NaturalLiteralExpressionImpl(NaturalLiteralExpression self) {
		super(self);
	}

	@Override
	public NaturalLiteralExpression getSelf() {
		return (NaturalLiteralExpression) this.self;
	}

	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * The type of a natural literal is the Alf library type Natural.
	 *
	 * NOTE: If the context of a natural literal expression unambiguously
	 * requires either an Integer or an UnlimitedNatural value, then the result
	 * of the literal expression is implicitly downcast to the required type. If
	 * the context is ambiguous, however, than an explicit cast to Integer or
	 * UnlimitedNatural must be used.
	 **/
	@Override
	protected ElementReference deriveType() {
	    return RootNamespace.getRootScope().getNaturalType();
	}
	
	/*
	 * Derivations
	 */
	
	public boolean naturalLiteralExpressionTypeDerivation() {
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
        if (base instanceof NaturalLiteralExpression) {
            this.getSelf().setImage(((NaturalLiteralExpression)base).getImage());
        }
    }

} // NaturalLiteralExpressionImpl
