
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
 * The target of a sequence operation, reduction or expansion expression, which
 * may be either a primary expression or a class name denoting the class extent.
 **/

public class ExtentOrExpressionImpl {

	protected ExtentOrExpression self;

	public ExtentOrExpressionImpl(ExtentOrExpression self) {
		this.self = self;
	}

	public org.modeldriven.alf.syntax.expressions.ExtentOrExpression getSelf() {
		return (ExtentOrExpression) this.self;
	}

	public Expression deriveExpression() {
		return null; // STUB
	}

	/**
	 * The effective expression for the target is the parsed primary expression,
	 * if the target is not a qualified name, a name expression, if the target
	 * is a qualified name other than a class name, or a class extent
	 * expression, if the target is the qualified name of a class.
	 **/
	public boolean extentOrExpressionExpressionDerivation() {
		this.getSelf().getExpression();
		return true;
	}

} // ExtentOrExpressionImpl
