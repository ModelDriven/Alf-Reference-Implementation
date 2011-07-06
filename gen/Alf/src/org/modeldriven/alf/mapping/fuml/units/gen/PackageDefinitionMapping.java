
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.units.gen;

import org.modeldriven.alf.mapping.fuml.units.gen.NamespaceDefinitionMapping;

import org.modeldriven.alf.syntax.units.PackageDefinition;

import fUML.Syntax.Classes.Kernel.Element;

import java.util.ArrayList;
import java.util.List;

public class PackageDefinitionMapping extends NamespaceDefinitionMapping {

	public PackageDefinitionMapping() {
		this.setErrorMessage("PackageDefinitionMapping not yet implemented.");
	}

	public List<Element> getModelElements() {
		// TODO: Auto-generated stub
		return new ArrayList<Element>();
	}

	public PackageDefinition getPackageDefinition() {
		return (PackageDefinition) this.getSource();
	}

} // PackageDefinitionMapping
