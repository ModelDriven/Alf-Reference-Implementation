
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
 * A binary expression that tests the equality of its operands.
 **/

public class EqualityExpressionImpl extends
		org.modeldriven.alf.syntax.expressions.impl.gen.BinaryExpressionImpl {

	private Boolean isNegated = null; // DERIVED

	public EqualityExpressionImpl(EqualityExpression self) {
		super(self);
	}

	public EqualityExpression getSelf() {
		return (EqualityExpression) this.self;
	}

	public Boolean getIsNegated() {
		if (this.isNegated == null) {
			this.setIsNegated(this.deriveIsNegated());
		}
		return this.isNegated;
	}

	public void setIsNegated(Boolean isNegated) {
		this.isNegated = isNegated;
	}

	protected Boolean deriveIsNegated() {
		return null; // STUB
	}

	/**
	 * An equality expression is negated if its operator is "!=".
	 **/
	public boolean equalityExpressionIsNegatedDerivation() {
		this.getSelf().getIsNegated();
		return true;
	}

	/**
	 * An equality expression has type Boolean.
	 **/
	public boolean equalityExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	/**
	 * An equality expression has a multiplicity lower bound of 1.
	 **/
	public boolean equalityExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	/**
	 * An equality expression has a multiplicity upper bound of 1.
	 **/
	public boolean equalityExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

} // EqualityExpressionImpl
