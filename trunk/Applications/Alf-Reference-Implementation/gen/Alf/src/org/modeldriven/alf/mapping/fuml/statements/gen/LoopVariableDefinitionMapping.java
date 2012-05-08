
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

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
