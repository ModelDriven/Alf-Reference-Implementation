
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
 * An expression used to create a new instance of a class or data type.
 **/

public class InstanceCreationExpressionImpl extends
		org.modeldriven.alf.syntax.expressions.impl.InvocationExpressionImpl {

	public InstanceCreationExpressionImpl(InstanceCreationExpression self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.expressions.InstanceCreationExpression getSelf() {
		return (InstanceCreationExpression) this.self;
	}

	public Boolean deriveIsConstructorless() {
		return null; // STUB
	}

	public Boolean deriveIsObjectCreation() {
		return null; // STUB
	}

	/**
	 * An instance creation expression is an object creation if its referent is
	 * not a data type.
	 **/
	public boolean instanceCreationExpressionIsObjectCreationDerivation() {
		this.getSelf().getIsObjectCreation();
		return true;
	}

	/**
	 * An instance creation expression is constructorless if its referent is a
	 * class.
	 **/
	public boolean instanceCreationExpressionIsConstructorlessDerivation() {
		this.getSelf().getIsConstructorless();
		return true;
	}

	/**
	 * The referent of an instance creation expression is the constructor
	 * operation, class or data type to which the constructor name resolves.
	 **/
	public boolean instanceCreationExpressionReferentDerivation() {
		this.getSelf().getReferent();
		return true;
	}

	/**
	 * There is no feature for an instance creation expression.
	 **/
	public boolean instanceCreationExpressionFeatureDerivation() {
		this.getSelf().getFeature();
		return true;
	}

	/**
	 * The constructor name must resolve to a constructor operation (that is
	 * compatible with the tuple argument expressions), a class or a data type,
	 * but not both a class and a data type.
	 **/
	public boolean instanceCreationExpressionConstructor() {
		return true;
	}

	/**
	 * If the expression is constructorless, then its tuple must be empty.
	 **/
	public boolean instanceCreationExpressionTuple() {
		return true;
	}

	/**
	 * If an instance creation expression is a data value creation (not an
	 * object creation), then the tuple argument expressions are matched with
	 * the attributes of the named type.
	 **/
	public boolean instanceCreationExpressionDataTypeCompatibility() {
		return true;
	}

	/**
	 * Returns the parameters of a constructor operation or the attributes of a
	 * data type, or an empty set for a constructorless instance creation.
	 **/
	public ArrayList<ElementReference> parameterElements() {
		return new ArrayList<ElementReference>(); // STUB
	} // parameterElements

} // InstanceCreationExpressionImpl
