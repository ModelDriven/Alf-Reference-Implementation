
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.fuml.mapping.units.gen;

import org.modeldriven.alf.fuml.mapping.units.gen.NamespaceDefinitionMapping;

import org.modeldriven.alf.syntax.units.ClassifierDefinition;

public abstract class ClassifierDefinitionMapping extends
		NamespaceDefinitionMapping {

	public ClassifierDefinition getClassifierDefinition() {
		return (ClassifierDefinition) this.getSource();
	}

} // ClassifierDefinitionMapping
