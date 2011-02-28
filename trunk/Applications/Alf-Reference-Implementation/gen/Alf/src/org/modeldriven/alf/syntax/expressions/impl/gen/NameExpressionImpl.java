
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
 * An expression that comprises a name reference.
 **/

public class NameExpressionImpl extends
		org.modeldriven.alf.syntax.expressions.impl.gen.ExpressionImpl {

	public NameExpressionImpl(NameExpression self) {
		super(self);
	}

	public NameExpression getSelf() {
		return (NameExpression) this.self;
	}

	public ElementReference deriveEnumerationLiteral() {
		return null; // STUB
	}

	public AssignedSource deriveAssignment() {
		return null; // STUB
	}

	public PropertyAccessExpression derivePropertyAccess() {
		return null; // STUB
	}

	/**
	 * If the name in a name expression is a local or parameter name, then its
	 * assignment is its assigned source before the expression.
	 **/
	public boolean nameExpressionAssignmentDerivation() {
		this.getSelf().getAssignment();
		return true;
	}

	/**
	 * If the name in a name expression resolves to an enumeration literal name,
	 * then that is the enumeration literal for the expression.
	 **/
	public boolean nameExpressionEnumerationLiteralDerivation() {
		this.getSelf().getEnumerationLiteral();
		return true;
	}

	/**
	 * If the name in a name expression disambiguates to a feature reference,
	 * then the equivalent property access expression has the disambiguation of
	 * the name as its feature. The assignments before the property access
	 * expression are the same as those before the name expression.
	 **/
	public boolean nameExpressionPropertyAccessDerivation() {
		this.getSelf().getPropertyAccess();
		return true;
	}

	/**
	 * The type of a name expression is determined by its name. If the name is a
	 * local name or parameter with an assignment, then the type of the name
	 * expression is the type of that assignment. If the name is an enumeration
	 * literal, then the type of the name expression is the corresponding
	 * enumeration. If the name disambiguates to a feature reference, then the
	 * type of the name expression is the type of the equivalent property access
	 * expression.
	 **/
	public boolean nameExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	/**
	 * The multiplicity upper bound of a name expression is determined by its
	 * name.
	 **/
	public boolean nameExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

	/**
	 * The multiplicity lower bound of a name expression is determined by its
	 * name.
	 **/
	public boolean nameExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	/**
	 * If the name referenced by this expression is not a disambiguated feature
	 * reference or a local or parameter name, then it must resolve to exactly
	 * one enumeration literal.
	 **/
	public boolean nameExpressionResolution() {
		return true;
	}

} // NameExpressionImpl
