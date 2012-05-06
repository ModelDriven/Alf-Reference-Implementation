
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
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
 * An expression that comprises an unbounded value literal.
 **/

public class UnboundedLiteralExpressionImpl extends
		org.modeldriven.alf.syntax.expressions.impl.gen.LiteralExpressionImpl {

	public UnboundedLiteralExpressionImpl(UnboundedLiteralExpression self) {
		super(self);
	}

	public UnboundedLiteralExpression getSelf() {
		return (UnboundedLiteralExpression) this.self;
	}

	/**
	 * The type of an unbounded literal expression is UnlimitedNatural.
	 **/
	public boolean unboundedLiteralExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

} // UnboundedLiteralExpressionImpl
