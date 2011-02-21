
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

import org.modeldriven.alf.syntax.expressions.impl.OutputNamedExpressionImpl;

/**
 * A named argument expression for an output parameter.
 **/

public class OutputNamedExpression extends NamedExpression {

	private LeftHandSide leftHandSide = null; // DERIVED

	public OutputNamedExpression() {
		this.impl = new OutputNamedExpressionImpl(this);
	}

	public OutputNamedExpressionImpl getImpl() {
		return (OutputNamedExpressionImpl) this.impl;
	}

	public LeftHandSide getLeftHandSide() {
		if (this.leftHandSide == null) {
			this.leftHandSide = this.getImpl().deriveLeftHandSide();
		}
		return this.leftHandSide;
	}

	/**
	 * The equivalent left-hand side for an output named expression depends on
	 * the kind of expression. If the expression is a name expression with no
	 * disambiguation, then the left-hand side is a name left-hand side with the
	 * name from the name expression. If the expression is a name expression
	 * that disambiguates to a feature reference, then the left-hand side is a
	 * feature left-hand side for that feature reference. If the expression is a
	 * property access expression, then the left-hand side is a feature
	 * left-hand side for the feature reference of the property access
	 * expression. If the expression is a sequence access expression, then the
	 * left-hand side is a name left-hand side or feature left-hand side, as
	 * above, depending on whether the primary expression of the sequence access
	 * expression is a name expression or property access expression, and an
	 * index given by the index expression of the sequence access expression.
	 * Otherwise the left-hand side is empty.
	 **/
	public boolean outputNamedExpressionLeftHandSideDerivation() {
		return this.getImpl().outputNamedExpressionLeftHandSideDerivation();
	}

	/**
	 * The argument for an output parameter must be either be null, a name
	 * expression, a property access expression, or a sequence access expression
	 * whose primary expression is a name expression or a property access
	 * expression.
	 **/
	public boolean outputNamedExpressionForm() {
		return this.getImpl().outputNamedExpressionForm();
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		LeftHandSide leftHandSide = this.getLeftHandSide();
		if (leftHandSide != null) {
			System.out.println(prefix + " /leftHandSide:" + leftHandSide);
		}
	}
} // OutputNamedExpression
