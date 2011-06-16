
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

import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.Profile;
import org.modeldriven.alf.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * An expression that comprises a primitive literal.
 **/

public abstract class LiteralExpressionImpl extends
		org.modeldriven.alf.syntax.expressions.impl.gen.ExpressionImpl {

	public LiteralExpressionImpl(LiteralExpression self) {
		super(self);
	}

	public LiteralExpression getSelf() {
		return (LiteralExpression) this.self;
	}

	/**
	 * The type of a literal expression is given by the type of the literal, as
	 * defined for each subclass below.
	 **/
	public boolean literalExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	/**
	 * The multiplicity upper bound of a literal expression is always 1.
	 **/
	public boolean literalExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

	/**
	 * The multiplicity lower bound of a literal expression is always 1.
	 **/
	public boolean literalExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

} // LiteralExpressionImpl
