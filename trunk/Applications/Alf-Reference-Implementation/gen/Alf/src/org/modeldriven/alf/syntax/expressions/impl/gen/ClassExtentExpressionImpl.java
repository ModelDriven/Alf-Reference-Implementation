
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
 * An expression used to obtain the objects in the extent of a class.
 **/

public class ClassExtentExpressionImpl extends
		org.modeldriven.alf.syntax.expressions.impl.gen.ExpressionImpl {

	public ClassExtentExpressionImpl(ClassExtentExpression self) {
		super(self);
	}

	public ClassExtentExpression getSelf() {
		return (ClassExtentExpression) this.self;
	}

	/**
	 * The type of a class extent expression is the given class.
	 **/
	public boolean classExtentExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	/**
	 * The multiplicity upper bound of a class expression is *.
	 **/
	public boolean classExtentExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

	/**
	 * The multiplicity lower bound of a class extent expression is 0.
	 **/
	public boolean classExtentExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	/**
	 * The given type name must resolve to a non-template class.
	 **/
	public boolean classExtentExpressionExtentType() {
		return true;
	}

} // ClassExtentExpressionImpl
