
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
 * An expression used to access a specific element of a sequence.
 **/

public class SequenceAccessExpressionImpl extends
		org.modeldriven.alf.syntax.expressions.impl.gen.ExpressionImpl {

	public SequenceAccessExpressionImpl(SequenceAccessExpression self) {
		super(self);
	}

	public SequenceAccessExpression getSelf() {
		return (SequenceAccessExpression) this.self;
	}

	/**
	 * The type of a sequence access expression is the same as the type of its
	 * primary expression.
	 **/
	public boolean sequenceAccessExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	/**
	 * The multiplicity lower bound of a sequence access expression is 0.
	 **/
	public boolean sequenceAccessExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	/**
	 * The multiplicity upper bound of a sequence access expression is 1.
	 **/
	public boolean sequenceAccessExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

	/**
	 * The type of the index of a sequence access expression must be Integer.
	 **/
	public boolean sequenceAccessExpressionIndexType() {
		return true;
	}

	/**
	 * The multiplicity upper bound of the index of a sequence access expression
	 * must be 1.
	 **/
	public boolean sequenceAccessExpressionIndexMultiplicity() {
		return true;
	}

} // SequenceAccessExpressionImpl
