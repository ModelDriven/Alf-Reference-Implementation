
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
import java.util.TreeSet;

/**
 * An expression comprising the keyword “this”.
 **/

public class ThisExpressionImpl extends
		org.modeldriven.alf.syntax.expressions.impl.gen.ExpressionImpl {

	public ThisExpressionImpl(ThisExpression self) {
		super(self);
	}

	public ThisExpression getSelf() {
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
