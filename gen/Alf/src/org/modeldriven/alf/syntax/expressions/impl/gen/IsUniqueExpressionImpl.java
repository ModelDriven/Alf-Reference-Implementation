
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions.impl.gen;

import org.modeldriven.alf.parser.AlfParser;
import org.modeldriven.alf.parser.Token;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.Profile;
import org.modeldriven.alf.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A sequence expansion expression with a isUnique.
 **/

public class IsUniqueExpressionImpl
		extends
		org.modeldriven.alf.syntax.expressions.impl.gen.SequenceExpansionExpressionImpl {

	public IsUniqueExpressionImpl(IsUniqueExpression self) {
		super(self);
	}

	public IsUniqueExpression getSelf() {
		return (IsUniqueExpression) this.self;
	}

	/**
	 * An isUnique expression has the type Boolean.
	 **/
	public boolean isUniqueExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	/**
	 * An isUnique expression has a multiplicity lower bound of 1.
	 **/
	public boolean isUniqueExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	/**
	 * An isUnique expression has a multiplicity upper bound of 1.
	 **/
	public boolean isUniqueExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

	/**
	 * The argument of an isUnique expression must have a multiplicity upper
	 * bound of 1.
	 **/
	public boolean isUniqueExpressionExpressionArgument() {
		return true;
	}

} // IsUniqueExpressionImpl
