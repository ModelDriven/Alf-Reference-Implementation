
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

import org.omg.uml.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A sequence expansion expression with a select or reject operation.
 **/

public class SelectOrRejectExpressionImpl
		extends SequenceExpansionExpressionImpl {

	public SelectOrRejectExpressionImpl(SelectOrRejectExpression self) {
		super(self);
	}

	public SelectOrRejectExpression getSelf() {
		return (SelectOrRejectExpression) this.self;
	}

	/**
	 * A select or reject expression has the same type as its primary
	 * expression.
	 **/
	public boolean selectOrRejectExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	/**
	 * A select or reject expression has a multiplicity lower bound of 0.
	 **/
	public boolean selectOrRejectExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	/**
	 * A select or reject expression has a multiplicity upper bound of *.
	 **/
	public boolean selectOrRejectExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

	/**
	 * The argument of a select or reject expression must have type Boolean and
	 * a multiplicity upper bound of 1.
	 **/
	public boolean selectOrRejectExpressionArgument() {
		return true;
	}

} // SelectOrRejectExpressionImpl
