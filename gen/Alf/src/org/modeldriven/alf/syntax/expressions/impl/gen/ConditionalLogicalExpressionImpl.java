
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

import org.modeldriven.uml.Element;
import org.modeldriven.uml.Profile;
import org.modeldriven.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A binary expression with a conditional logical expression, for which the
 * evaluation of the second operand expression is conditioned on the result of
 * evaluating the first operand expression.
 **/

public class ConditionalLogicalExpressionImpl extends
		org.modeldriven.alf.syntax.expressions.impl.gen.BinaryExpressionImpl {

	public ConditionalLogicalExpressionImpl(ConditionalLogicalExpression self) {
		super(self);
	}

	public ConditionalLogicalExpression getSelf() {
		return (ConditionalLogicalExpression) this.self;
	}

	/**
	 * A conditional logical expression has type Boolean.
	 **/
	public boolean conditionalLogicalExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	/**
	 * A conditional logical expression has a multiplicity lower bound of 0 if
	 * the lower bound if either operand expression is 0 and 1 otherwise.
	 **/
	public boolean conditionalLogicalExpressionLower() {
		return true;
	}

	/**
	 * A conditional logical expression has a multiplicity upper bound of 1.
	 **/
	public boolean conditionalLogicalExpressionUpper() {
		return true;
	}

	/**
	 * The operands of a conditional logical expression must have type Boolean.
	 **/
	public boolean conditionalLogicalExpressionOperands() {
		return true;
	}

	/**
	 * The assignments before the first operand expression of a conditional
	 * logical expression are the same as those before the conditional logical
	 * expression. The assignments before the second operand expression are the
	 * same as those after the first operand expression.
	 **/
	public Boolean validateAssignments() {
		return false; // STUB
	} // validateAssignments

	/**
	 * If a name has the same assigned source after the second operand
	 * expression as before it, then that is its assigned source after the
	 * conditional logical expression. If a name is unassigned before the second
	 * operand expression, then it is considered unassigned after the
	 * conditional logical expression, even if it has an assigned source after
	 * the second operand expression. Otherwise its assigned source after the
	 * conditional logical expression is the conditional logical expression
	 * itself.
	 **/
	public Collection<AssignedSource> updateAssignments() {
		return new ArrayList<AssignedSource>(); // STUB
	} // updateAssignments

} // ConditionalLogicalExpressionImpl
