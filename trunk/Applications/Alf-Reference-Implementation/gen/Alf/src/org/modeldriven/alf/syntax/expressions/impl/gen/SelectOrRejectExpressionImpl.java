
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

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
 * A sequence expansion expression with a select or reject operation.
 **/

public class SelectOrRejectExpressionImpl
		extends
		org.modeldriven.alf.syntax.expressions.impl.gen.SequenceExpansionExpressionImpl {

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
