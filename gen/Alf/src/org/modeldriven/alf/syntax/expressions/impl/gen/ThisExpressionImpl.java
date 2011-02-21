
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
 * An expression comprising the keyword “this”.
 **/

public class ThisExpressionImpl extends
		org.modeldriven.alf.syntax.expressions.impl.gen.ExpressionImpl {

	public ThisExpressionImpl(ThisExpression self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.expressions.ThisExpression getSelf() {
		return (ThisExpression) this.self;
	}

	/**
	 * The static type of a this expression is the statically determined context
	 * classifier for the context in which the this expression occurs.
	 **/
	public boolean thisExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	/**
	 * The multiplicity upper bound of a this expression is always 1.
	 **/
	public boolean thisExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

	/**
	 * The multiplicity lower bound of a this expression is always 1.
	 **/
	public boolean thisExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

} // ThisExpressionImpl
