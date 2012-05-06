
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.fuml.expressions.TemplateBindingMapping;

import org.modeldriven.alf.syntax.expressions.NamedTemplateBinding;

import fUML.Syntax.Classes.Kernel.Element;

import java.util.ArrayList;
import java.util.List;

public class NamedTemplateBindingMapping extends TemplateBindingMapping {

	public NamedTemplateBindingMapping() {
		this.setErrorMessage("No mapping for NamedTemplateBindingMapping.");
	}

	public List<Element> getModelElements() {
		return new ArrayList<Element>();
	}

	public NamedTemplateBinding getNamedTemplateBinding() {
		return (NamedTemplateBinding) this.getSource();
	}

} // NamedTemplateBindingMapping
