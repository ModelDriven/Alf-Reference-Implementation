
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
 * A named argument expression for an output parameter.
 **/

public class OutputNamedExpressionImpl extends
		org.modeldriven.alf.syntax.expressions.impl.gen.NamedExpressionImpl {

	private LeftHandSide leftHandSide = null; // DERIVED

	public OutputNamedExpressionImpl(OutputNamedExpression self) {
		super(self);
	}

	public OutputNamedExpression getSelf() {
		return (OutputNamedExpression) this.self;
	}

	public LeftHandSide getLeftHandSide() {
		if (this.leftHandSide == null) {
			this.setLeftHandSide(this.deriveLeftHandSide());
		}
		return this.leftHandSide;
	}

	public void setLeftHandSide(LeftHandSide leftHandSide) {
		this.leftHandSide = leftHandSide;
	}

	protected LeftHandSide deriveLeftHandSide() {
		return null; // STUB
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
		this.getSelf().getLeftHandSide();
		return true;
	}

	/**
	 * The argument for an output parameter must be either be null, a name
	 * expression, a property access expression, or a sequence access expression
	 * whose primary expression is a name expression or a property access
	 * expression.
	 **/
	public boolean outputNamedExpressionForm() {
		return true;
	}

} // OutputNamedExpressionImpl
