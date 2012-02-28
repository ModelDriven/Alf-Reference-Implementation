
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.fuml.expressions.TemplateBindingMapping;

import org.modeldriven.alf.syntax.expressions.PositionalTemplateBinding;

import fUML.Syntax.Classes.Kernel.Element;

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
