
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
 * A pairing of a parameter name and an argument expression in a tuple.
 **/

public class NamedExpressionImpl extends
		org.modeldriven.alf.syntax.common.impl.SyntaxElementImpl {

	public NamedExpressionImpl(NamedExpression self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.expressions.NamedExpression getSelf() {
		return (NamedExpression) this.self;
	}

	public Boolean deriveIsCollectionConversion() {
		return null; // STUB
	}

	public Boolean deriveIsBitStringConversion() {
		return null; // STUB
	}

	/**
	 * Collection conversion is required if the type of the corresponding
	 * parameter is a collection class and the type of the argument expression
	 * is not.
	 **/
	public boolean namedExpressionIsCollectionConversionDerivation() {
		this.getSelf().getIsCollectionConversion();
		return true;
	}

	/**
	 * Bit string conversion is required if the type of the type of the
	 * corresponding parameter is BitString, or a collection class instantiated
	 * with a BitString type, and the type of the argument expression is not
	 * BitString.
	 **/
	public boolean namedExpressionIsBitStringConversionDerivation() {
		this.getSelf().getIsBitStringConversion();
		return true;
	}

} // NamedExpressionImpl
