
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.units.gen;

import org.modeldriven.alf.mapping.fuml.units.gen.TypedElementDefinitionMapping;

import org.modeldriven.alf.syntax.units.PropertyDefinition;

import fUML.Syntax.Classes.Kernel.Element;

import java.util.ArrayList;
import java.util.List;

public class PropertyDefinitionMapping extends TypedElementDefinitionMapping {

	public PropertyDefinitionMapping() {
		this.setErrorMessage("PropertyDefinitionMapping not yet implemented.");
	}

	public List<Element> getModelElements() {
		// TODO: Auto-generated stub
		return new ArrayList<Element>();
	}

	public PropertyDefinition getPropertyDefinition() {
		return (PropertyDefinition) this.getSource();
	}

} // PropertyDefinitionMapping