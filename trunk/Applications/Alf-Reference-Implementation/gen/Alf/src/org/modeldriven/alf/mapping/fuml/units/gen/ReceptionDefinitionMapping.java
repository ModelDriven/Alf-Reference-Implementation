
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.units.gen;

import org.modeldriven.alf.mapping.fuml.units.gen.MemberMapping;

import org.modeldriven.alf.syntax.units.ReceptionDefinition;

import fUML.Syntax.Classes.Kernel.Element;

import java.util.ArrayList;
import java.util.List;

public class ReceptionDefinitionMapping extends MemberMapping {

	public ReceptionDefinitionMapping() {
		this.setErrorMessage("ReceptionDefinitionMapping not yet implemented.");
	}

	public List<Element> getModelElements() {
		// TODO: Auto-generated stub
		return new ArrayList<Element>();
	}

	public ReceptionDefinition getReceptionDefinition() {
		return (ReceptionDefinition) this.getSource();
	}

} // ReceptionDefinitionMapping