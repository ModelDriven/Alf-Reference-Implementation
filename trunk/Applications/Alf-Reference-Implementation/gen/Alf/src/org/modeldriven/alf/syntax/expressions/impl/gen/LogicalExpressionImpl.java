
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
 * A binary expression with a logical operator.
 **/

public class LogicalExpressionImpl extends
		org.modeldriven.alf.syntax.expressions.impl.gen.BinaryExpressionImpl {

	private Boolean isBitWise = null; // DERIVED
	private Boolean isBitStringConversion1 = null; // DERIVED
	private Boolean isBitStringConversion2 = null; // DERIVED

	public LogicalExpressionImpl(LogicalExpression self) {
		super(self);
	}

	public LogicalExpression getSelf() {
		return (LogicalExpression) this.self;
	}

	public Boolean getIsBitWise() {
		if (this.isBitWise == null) {
			this.setIsBitWise(this.deriveIsBitWise());
		}
		return this.isBitWise;
	}

	public void setIsBitWise(Boolean isBitWise) {
		this.isBitWise = isBitWise;
	}

	public Boolean getIsBitStringConversion1() {
		if (this.isBitStringConversion1 == null) {
			this.setIsBitStringConversion1(this.deriveIsBitStringConversion1());
		}
		return this.isBitStringConversion1;
	}

	public void setIsBitStringConversion1(Boolean isBitStringConversion1) {
		this.isBitStringConversion1 = isBitStringConversion1;
	}

	public Boolean getIsBitStringConversion2() {
		if (this.isBitStringConversion2 == null) {
			this.setIsBitStringConversion2(this.deriveIsBitStringConversion2());
		}
		return this.isBitStringConversion2;
	}

	public void setIsBitStringConversion2(Boolean isBitStringConversion2) {
		this.isBitStringConversion2 = isBitStringConversion2;
	}

	protected Boolean deriveIsBitWise() {
		return null; // STUB
	}

	protected Boolean deriveIsBitStringConversion1() {
		return null; // STUB
	}

	protected Boolean deriveIsBitStringConversion2() {
		return null; // STUB
	}

	/**
	 * A logical expression has type Boolean if it is not bit-wise and type
	 * BitString if it is bit-wise.
	 **/
	public boolean logicalExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	/**
	 * A logical expression has a multiplicity lower bound of 0 if the lower
	 * bound if either operand expression is 0 and 1 otherwise.
	 **/
	public boolean logicalExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	/**
	 * A logical expression has a multiplicity upper bound of 1.
	 **/
	public boolean logicalExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

	/**
	 * The operands of a logical expression must have type Boolean, Integer or
	 * BitString. However, if one of the operands is Boolean, then the other
	 * must also be Boolean.
	 **/
	public boolean logicalExpressionOperands() {
		return true;
	}

	/**
	 * BitString conversion is required if the first operand expression of a
	 * logical expression has type Integer.
	 **/
	public boolean logicalExpressionIsBitStringConversion1Derivation() {
		this.getSelf().getIsBitStringConversion1();
		return true;
	}

	/**
	 * BitString conversion is required if the second operand expression of a
	 * logical expression has type Integer.
	 **/
	public boolean logicalExpressionIsBitStringConversion2Derivation() {
		this.getSelf().getIsBitStringConversion2();
		return true;
	}

	/**
	 * A logical expression is bit-wise if the type of its first operand is not
	 * Boolean.
	 **/
	public boolean logicalExpressionIsBitWiseDerivation() {
		this.getSelf().getIsBitWise();
		return true;
	}

} // LogicalExpressionImpl
