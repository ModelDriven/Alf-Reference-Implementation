
/*******************************************************************************
 * Copyright 2011, 2013 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.expressions;

import org.modeldriven.alf.fuml.mapping.expressions.TemplateBindingMapping;

import org.modeldriven.alf.syntax.expressions.PositionalTemplateBinding;

import org.modeldriven.alf.uml.Element;

import java.util.ArrayList;
import java.util.List;

public class PositionalTemplateBindingMapping extends TemplateBindingMapping {

	public PositionalTemplateBindingMapping() {
		this.setErrorMessage("No mapping for PositionalTemplateBindingMapping.");
	}

	public List<Element> getModelElements() {
		return new ArrayList<Element>();
	}

	public PositionalTemplateBinding getPositionalTemplateBinding() {
		return (PositionalTemplateBinding) this.getSource();
	}

} // PositionalTemplateBindingMapping
