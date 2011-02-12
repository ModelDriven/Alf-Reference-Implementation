
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

/**
 * An expression used to test the dynamic type of its operand.
 **/

public class ClassificationExpressionImpl extends
		org.modeldriven.alf.syntax.expressions.impl.UnaryExpressionImpl {

	public ClassificationExpressionImpl(ClassificationExpression self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.expressions.ClassificationExpression getSelf() {
		return (ClassificationExpression) this.self;
	}

	public ElementReference deriveReferent() {
		return null; // STUB
	}

	public Boolean deriveIsDirect() {
		return null; // STUB
	}

	/**
	 * A classification expression is direct if its operator is "hastype".
	 **/
	public boolean classificationExpressionIsDirectDerivation() {
		this.getSelf().getIsDirect();
		return true;
	}

	/**
	 * The referent of a classification expression is the classifier to which
	 * the type name resolves.
	 **/
	public boolean classificationExpressionReferentDerivation() {
		this.getSelf().getReferent();
		return true;
	}

	/**
	 * A classification expression has type Boolean.
	 **/
	public boolean classificationExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	/**
	 * A classification expression has a multiplicity lower bound that is the
	 * same as the lower bound of its operand expression.
	 **/
	public boolean classificationExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	/**
	 * A classification expression has a multiplicity upper bound of 1.
	 **/
	public boolean classificationExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

	/**
	 * The type name in a classification expression must resolve to a
	 * classifier.
	 **/
	public boolean classificationExpressionTypeName() {
		return true;
	}

	/**
	 * The operand expression of a classification expression must have a
	 * multiplicity upper bound of 1.
	 **/
	public boolean classificationExpressionOperand() {
		return true;
	}

} // ClassificationExpressionImpl
