
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0
 * (http://www.opensource.org/licenses/afl-3.0.php)
 *
 */

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

/**
 * An expression that comprises a String literal.
 **/

public class StringLiteralExpressionImpl extends LiteralExpressionImpl {

	private String image = "";

	public StringLiteralExpressionImpl(StringLiteralExpression self) {
		super(self);
	}

	@Override
	public StringLiteralExpression getSelf() {
		return (StringLiteralExpression) this.self;
	}

	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * The type of a string literal expression is String.
	 **/
	@Override
	public ElementReference deriveType() {
	    return RootNamespace.getStringType();
	}
	
	/*
	 * Derivations
	 */
	
	public boolean stringLiteralExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

} // StringLiteralExpressionImpl
