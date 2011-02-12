
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units.impl;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

/**
 * The common properties of the definitions of typed elements.
 **/

public abstract class TypedElementDefinitionImpl extends
		org.modeldriven.alf.syntax.units.impl.MemberImpl {

	public TypedElementDefinitionImpl(TypedElementDefinition self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.units.TypedElementDefinition getSelf() {
		return (TypedElementDefinition) this.self;
	}

	public ElementReference deriveType() {
		return null; // STUB
	}

	public Integer deriveLower() {
		return null; // STUB
	}

	public Integer deriveUpper() {
		return null; // STUB
	}

	/**
	 * If the lower bound string image of a typed element definition is not
	 * empty, then the integer lower bound is the integer value of the lower
	 * bound string. Otherwise the lower bound is equal to the upper bound,
	 * unless the upper bound is unbounded, in which case the lower bound is 0.
	 **/
	public boolean typedElementDefinitionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	/**
	 * The unlimited natural upper bound value is the unlimited natural value of
	 * the uper bound string (with "*" representing the unbounded value).
	 **/
	public boolean typedElementDefinitionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

	/**
	 * The type of a typed element definition is the single classifier referent
	 * of the type name.
	 **/
	public boolean typedElementDefinitionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	/**
	 * The type name of a typed element definition must have a single classifier
	 * referent. This referent may not be a template.
	 **/
	public boolean typedElementDefinitionTypeName() {
		return true;
	}

} // TypedElementDefinitionImpl
