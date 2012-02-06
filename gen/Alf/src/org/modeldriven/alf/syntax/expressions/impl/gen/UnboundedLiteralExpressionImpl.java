
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions.impl.gen;

import org.modeldriven.alf.parser.AlfParser;

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