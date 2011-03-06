
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
 * A sequence expansion expression with a forAll, exists or one operation.
 **/

public class ForAllOrExistsOrOneExpressionImpl
		extends
		org.modeldriven.alf.syntax.expressions.impl.gen.SequenceExpansionExpressionImpl {

	public ForAllOrExistsOrOneExpressionImpl(ForAllOrExistsOrOneExpression self) {
		super(self);
	}

	public ForAllOrExistsOrOneExpression getSelf() {
		return (ForAllOrExistsOrOneExpression) this.self;
	}

	/**
	 * A forAll, exists or one expression has the type Boolean.
	 **/
	public boolean forAllOrExistsOrOneExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	/**
	 * A forAll, exists or one expression has a multiplicity lower bound of 1.
	 **/
	public boolean forAllOrExistsOrOneExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	/**
	 * A forAll, exists or one expression has a multiplicity upper bound of 1.
	 **/
	public boolean forAllOrExistsOrOneExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

	/**
	 * The argument of a forAll, exists or one expression must have type Boolean
	 * and a multiplicity upper bound of 1.
	 **/
	public boolean forAllOrExistsOrOneExpressionArgument() {
		return true;
	}

} // ForAllOrExistsOrOneExpressionImpl
