
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions.gen;

import org.modeldriven.alf.mapping.fuml.expressions.gen.TemplateBindingMapping;

import org.modeldriven.alf.syntax.expressions.NamedTemplateBinding;

import fUML.Syntax.Classes.Kernel.Element;

import java.util.ArrayList;
import java.util.List;

public class NamedTemplateBindingMapping extends TemplateBindingMapping {

	public NamedTemplateBindingMapping() {
		this
				.setErrorMessage("NamedTemplateBindingMapping not yet implemented.");
	}

	public List<Element> getModelElements() {
		// TODO: Auto-generated stub
		return new ArrayList<Element>();
	}

	public NamedTemplateBinding getNamedTemplateBinding() {
		return (NamedTemplateBinding) this.getSource();
	}

} // NamedTemplateBindingMapping
