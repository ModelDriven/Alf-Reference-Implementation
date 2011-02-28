
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units.impl.gen;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.omg.uml.*;

import java.util.ArrayList;

/**
 * The definition of a namespace as an Alf unit.
 **/

public class UnitDefinitionImpl extends
		org.modeldriven.alf.syntax.common.impl.gen.DocumentedElementImpl {

	public UnitDefinitionImpl(UnitDefinition self) {
		super(self);
	}

	public UnitDefinition getSelf() {
		return (UnitDefinition) this.self;
	}

	public ElementReference deriveNamespace() {
		return null; // STUB
	}

	public Boolean deriveIsModelLibrary() {
		return null; // STUB
	}

	public ArrayList<Profile> deriveAppliedProfile() {
		return null; // STUB
	}

	/**
	 * If a unit definition has a declared namespace name, then the containing
	 * namespace for the unit is the referent for that name.
	 **/
	public boolean unitDefinitionNamespaceDerivation() {
		this.getSelf().getNamespace();
		return true;
	}

	/**
	 * The declared namespace name for a unit definition, if any, must resolve
	 * to a UML namespace of an Alf unit definition. If it is an Alf unit
	 * definition, then it must have a stub for this unit definition.
	 **/
	public boolean unitDefinitionNamespace() {
		return true;
	}

	/**
	 * A unit definition is for a model library if its associated namespace
	 * definition has a stereotype annotation for the UML standard stereotype
	 * ModelLibrary.
	 **/
	public boolean unitDefinitionIsModelLibraryDerivation() {
		this.getSelf().getIsModelLibrary();
		return true;
	}

	/**
	 * Unless the unit definition is a model library, it has private package
	 * import references for all the sub-packages of the Alf::Library package.
	 **/
	public boolean unitDefinitionImplicitImports() {
		return true;
	}

	/**
	 * The profiles applied to a unit definition include any profiles applied to
	 * the containing namespace of the unit definition. If the unit definition
	 * is for a package, then the applied profiles for the unit definition also
	 * include the applied profiles for its associated package definition.
	 **/
	public boolean unitDefinitionAppliedProfileDerivation() {
		this.getSelf().getAppliedProfile();
		return true;
	}

} // UnitDefinitionImpl
