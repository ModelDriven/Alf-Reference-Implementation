
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
	    return RootNamespace.getNaturalType();
	}
	
	/*
	 * Derivations
	 */
	
	public boolean naturalLiteralExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

} // NaturalLiteralExpressionImpl
