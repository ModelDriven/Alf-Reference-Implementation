
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
 * An expression used to filter values by type.
 **/

public class CastExpressionImpl extends
		org.modeldriven.alf.syntax.expressions.impl.gen.ExpressionImpl {

	private Expression operand = null;
	private QualifiedName typeName = null;

	public CastExpressionImpl(CastExpression self) {
		super(self);
	}

	public CastExpression getSelf() {
		return (CastExpression) this.self;
	}

	public Expression getOperand() {
		return this.operand;
	}

	public void setOperand(Expression operand) {
		this.operand = operand;
	}

	public QualifiedName getTypeName() {
		return this.typeName;
	}

	public void setTypeName(QualifiedName typeName) {
		this.typeName = typeName;
	}

	/**
	 * The type of a cast expression is the referent of the given type name (if
	 * there is one).
	 **/
	public boolean castExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	/**
	 * A cast expression has a multiplicity lower bound of 0.
	 **/
	public boolean castExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	/**
	 * A cast expression has a multiplicity upper bound that is the same as the
	 * upper bound of its operand expression.
	 **/
	public boolean castExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

	/**
	 * If the cast expression has a type name, then it must resolve to a
	 * classifier.
	 **/
	public boolean castExpressionTypeResolution() {
		return true;
	}

	/**
	 * The assignments before the operand of a cast expression are the same as
	 * those before the cast expression.
	 **/
	public boolean castExpressionAssignmentsBefore() {
		return true;
	}

	/**
	 * The assignments after a cast expression are the same as those after its
	 * operand expression.
	 **/
	public Collection<AssignedSource> updateAssignments() {
		return new ArrayList<AssignedSource>(); // STUB
	} // updateAssignments

} // CastExpressionImpl
