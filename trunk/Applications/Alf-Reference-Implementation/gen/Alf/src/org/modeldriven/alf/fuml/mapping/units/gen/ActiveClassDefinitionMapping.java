
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.fuml.mapping.units.gen;

import org.modeldriven.alf.fuml.mapping.units.gen.ClassDefinitionMapping;

import org.modeldriven.alf.syntax.units.ActiveClassDefinition;

import org.modeldriven.alf.uml.Element;

import java.util.ArrayList;
import java.util.List;

public class ActiveClassDefinitionMapping extends ClassDefinitionMapping {

	public ActiveClassDefinitionMapping() {
		this
				.setErrorMessage("ActiveClassDefinitionMapping not yet implemented.");
	}

	public List<Element> getModelElements() {
		// TODO: Auto-generated stub
		return new ArrayList<Element>();
	}

	public ActiveClassDefinition getActiveClassDefinition() {
		return (ActiveClassDefinition) this.getSource();
	}

} // ActiveClassDefinitionMapping
