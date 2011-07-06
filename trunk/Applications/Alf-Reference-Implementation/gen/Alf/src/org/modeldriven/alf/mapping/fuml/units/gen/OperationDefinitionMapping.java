
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.units.gen;

import org.modeldriven.alf.mapping.fuml.units.gen.NamespaceDefinitionMapping;

import org.modeldriven.alf.syntax.units.OperationDefinition;

import fUML.Syntax.Classes.Kernel.Element;

import java.util.ArrayList;
import java.util.List;

public class OperationDefinitionMapping extends NamespaceDefinitionMapping {

	public OperationDefinitionMapping() {
		this.setErrorMessage("OperationDefinitionMapping not yet implemented.");
	}

	public List<Element> getModelElements() {
		// TODO: Auto-generated stub
		return new ArrayList<Element>();
	}

	public OperationDefinition getOperationDefinition() {
		return (OperationDefinition) this.getSource();
	}

} // OperationDefinitionMapping
