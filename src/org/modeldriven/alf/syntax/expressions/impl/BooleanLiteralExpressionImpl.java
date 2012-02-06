
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

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