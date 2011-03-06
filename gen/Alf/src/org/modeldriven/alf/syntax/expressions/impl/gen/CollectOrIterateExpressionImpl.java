
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
import java.util.Collection;
import java.util.List;

/**
 * A sequence expansion expression with a collect or iterate operation.
 **/

public class CollectOrIterateExpressionImpl
		extends
		org.modeldriven.alf.syntax.expressions.impl.gen.SequenceExpansionExpressionImpl {

	public CollectOrIterateExpressionImpl(CollectOrIterateExpression self) {
		super(self);
	}

	public CollectOrIterateExpression getSelf() {
		return (CollectOrIterateExpression) this.self;
	}

	/**
	 * A collect or iterate expression has the same type as its argument
	 * expression.
	 **/
	public boolean collectOrIterateExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	/**
	 * A collect or iterate expression has a multiplicity lower bound that is
	 * the product of the bounds of its primary and argument expressions.
	 **/
	public boolean collectOrIterateExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	/**
	 * A collect or iterate expression has a multiplicity upper bound that is
	 * the product of the bounds of its primary and argument expressions.
	 **/
	public boolean collectOrIterateExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

} // CollectOrIterateExpressionImpl
