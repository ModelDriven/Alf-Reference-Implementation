
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.fuml.mapping.units.gen;

import org.modeldriven.alf.fuml.mapping.common.gen.DocumentedElementMapping;

import org.modeldriven.alf.syntax.units.UnitDefinition;

import org.modeldriven.alf.uml.Element;

import java.util.ArrayList;
import java.util.List;

public class UnitDefinitionMapping extends DocumentedElementMapping {

	public UnitDefinitionMapping() {
		this.setErrorMessage("UnitDefinitionMapping not yet implemented.");
	}

	public List<Element> getModelElements() {
		// TODO: Auto-generated stub
		return new ArrayList<Element>();
	}

	public UnitDefinition getUnitDefinition() {
		return (UnitDefinition) this.getSource();
	}

} // UnitDefinitionMapping
