
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
 * A binary expression with a relational operator.
 **/

public class RelationalExpressionImpl extends
		org.modeldriven.alf.syntax.expressions.impl.gen.BinaryExpressionImpl {

	public RelationalExpressionImpl(RelationalExpression self) {
		super(self);
	}

	public RelationalExpression getSelf() {
		return (RelationalExpression) this.self;
	}

	public Boolean deriveIsUnlimitedNatural() {
		return null; // STUB
	}

	/**
	 * A relational expression is an UnlimitedNatural comparison if either one
	 * of its operands has type UnlimitedNatural.
	 **/
	public boolean relationalExpressionIsUnlimitedNaturalDerivation() {
		this.getSelf().getIsUnlimitedNatural();
		return true;
	}

	/**
	 * The type of a relational expression is Boolean.
	 **/
	public boolean relationalExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	/**
	 * A relational expression has a multiplicity lower bound of 0 if the lower
	 * bound if either operand expression is 0 and 1 otherwise.
	 **/
	public boolean relationalExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	/**
	 * A relational expression has a multiplicity upper bound of 1.
	 **/
	public boolean relationalExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

	/**
	 * The operand expressions for a comparison operator must have type Integer,
	 * UnlimitedNatural or Natural. However, it is not allowed to have one
	 * operand expression be Integer and the other be UnlimitedNatural.
	 **/
	public boolean relationalExpressionOperandTypes() {
		return true;
	}

} // RelationalExpressionImpl
