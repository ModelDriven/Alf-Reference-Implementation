
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

/**
 * An expression that comprises a Boolean literal.
 **/

public class BooleanLiteralExpressionImpl extends
		org.modeldriven.alf.syntax.expressions.impl.LiteralExpressionImpl {

	public BooleanLiteralExpressionImpl(BooleanLiteralExpression self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.expressions.BooleanLiteralExpression getSelf() {
		return (BooleanLiteralExpression) this.self;
	}

	/**
	 * The type of a boolean literal expression is Boolean.
	 **/
	public boolean booleanLiteralExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

} // BooleanLiteralExpressionImpl
