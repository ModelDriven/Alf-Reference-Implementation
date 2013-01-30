
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.expressions.gen;

import org.modeldriven.alf.fuml.mapping.expressions.gen.TemplateBindingMapping;

import org.modeldriven.alf.syntax.expressions.PositionalTemplateBinding;

import org.modeldriven.alf.uml.Element;

import java.util.ArrayList;
import java.util.List;

public class PositionalTemplateBindingMapping extends TemplateBindingMapping {

	public PositionalTemplateBindingMapping() {
		this
				.setErrorMessage("PositionalTemplateBindingMapping not yet implemented.");
	}

	public List<Element> getModelElements() {
		// TODO: Auto-generated stub
		return new ArrayList<Element>();
	}

	public PositionalTemplateBinding getPositionalTemplateBinding() {
		return (PositionalTemplateBinding) this.getSource();
	}

} // PositionalTemplateBindingMapping
