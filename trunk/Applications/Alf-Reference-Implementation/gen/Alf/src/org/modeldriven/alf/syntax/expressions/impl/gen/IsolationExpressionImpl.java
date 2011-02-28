
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
 * An expression used to evaluate its operand expression in isolation.
 **/

public class IsolationExpressionImpl extends
		org.modeldriven.alf.syntax.expressions.impl.gen.UnaryExpressionImpl {

	public IsolationExpressionImpl(IsolationExpression self) {
		super(self);
	}

	public IsolationExpression getSelf() {
		return (IsolationExpression) this.self;
	}

	/**
	 * An isolation expression has the type of its operand expression.
	 **/
	public boolean isolationExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	/**
	 * An isolation expression has the multiplicity lower bound of its operand
	 * expression.
	 **/
	public boolean isolationExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	/**
	 * An isolation expression has the multiplicity upper bound of its operand
	 * expression.
	 **/
	public boolean isolationExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

} // IsolationExpressionImpl
