
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.statements.gen;

import org.modeldriven.alf.mapping.fuml.common.gen.SyntaxElementMapping;

import org.modeldriven.alf.syntax.statements.LoopVariableDefinition;

import fUML.Syntax.Classes.Kernel.Element;

import java.util.ArrayList;
import java.util.List;

public class LoopVariableDefinitionMapping extends SyntaxElementMapping {

	public LoopVariableDefinitionMapping() {
		this
				.setErrorMessage("LoopVariableDefinitionMapping not yet implemented.");
	}

	public List<Element> getModelElements() {
		// TODO: Auto-generated stub
		return new ArrayList<Element>();
	}

	public LoopVariableDefinition getLoopVariableDefinition() {
		return (LoopVariableDefinition) this.getSource();
	}

} // LoopVariableDefinitionMapping
