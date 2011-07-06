
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.units.gen;

import org.modeldriven.alf.mapping.fuml.units.gen.MemberMapping;

import org.modeldriven.alf.syntax.units.TypedElementDefinition;

public abstract class TypedElementDefinitionMapping extends MemberMapping {

	public TypedElementDefinition getTypedElementDefinition() {
		return (TypedElementDefinition) this.getSource();
	}

} // TypedElementDefinitionMapping
