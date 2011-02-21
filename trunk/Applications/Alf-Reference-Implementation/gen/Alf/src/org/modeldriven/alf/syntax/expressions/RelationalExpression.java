
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.omg.uml.*;

import java.util.ArrayList;

import org.modeldriven.alf.syntax.expressions.impl.RelationalExpressionImpl;

/**
 * A binary expression with a relational operator.
 **/

public class RelationalExpression extends BinaryExpression {

	private Boolean isUnlimitedNatural = null; // DERIVED

	public RelationalExpression() {
		this.impl = new RelationalExpressionImpl(this);
	}

	public RelationalExpressionImpl getImpl() {
		return (RelationalExpressionImpl) this.impl;
	}

	public Boolean getIsUnlimitedNatural() {
		if (this.isUnlimitedNatural == null) {
			this.isUnlimitedNatural = this.getImpl().deriveIsUnlimitedNatural();
		}
		return this.isUnlimitedNatural;
	}

	/**
	 * A relational expression is an UnlimitedNatural comparison if either one
	 * of its operands has type UnlimitedNatural.
	 **/
	public boolean relationalExpressionIsUnlimitedNaturalDerivation() {
		return this.getImpl()
				.relationalExpressionIsUnlimitedNaturalDerivation();
	}

	/**
	 * The type of a relational expression is Boolean.
	 **/
	public boolean relationalExpressionTypeDerivation() {
		return this.getImpl().relationalExpressionTypeDerivation();
	}

	/**
	 * A relational expression has a multiplicity lower bound of 0 if the lower
	 * bound if either operand expression is 0 and 1 otherwise.
	 **/
	public boolean relationalExpressionLowerDerivation() {
		return this.getImpl().relationalExpressionLowerDerivation();
	}

	/**
	 * A relational expression has a multiplicity upper bound of 1.
	 **/
	public boolean relationalExpressionUpperDerivation() {
		return this.getImpl().relationalExpressionUpperDerivation();
	}

	/**
	 * The operand expressions for a comparison operator must have type Integer,
	 * UnlimitedNatural or Natural. However, it is not allowed to have one
	 * operand expression be Integer and the other be UnlimitedNatural.
	 **/
	public boolean relationalExpressionOperandTypes() {
		return this.getImpl().relationalExpressionOperandTypes();
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		Boolean isUnlimitedNatural = this.getIsUnlimitedNatural();
		if (isUnlimitedNatural != null) {
			s.append(" /isUnlimitedNatural:");
			s.append(isUnlimitedNatural);
		}
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
	}
} // RelationalExpression
