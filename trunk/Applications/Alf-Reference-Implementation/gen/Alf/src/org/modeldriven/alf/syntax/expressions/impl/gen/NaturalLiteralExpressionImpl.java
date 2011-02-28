
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions.impl.gen;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.omg.uml.*;

import java.util.ArrayList;

/**
 * An expression that comprises a natural literal.
 **/

public class NaturalLiteralExpressionImpl extends
		org.modeldriven.alf.syntax.expressions.impl.gen.LiteralExpressionImpl {

	public NaturalLiteralExpressionImpl(NaturalLiteralExpression self) {
		super(self);
	}

	public NaturalLiteralExpression getSelf() {
		return (NaturalLiteralExpression) this.self;
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
	public boolean naturalLiteralExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

} // NaturalLiteralExpressionImpl
