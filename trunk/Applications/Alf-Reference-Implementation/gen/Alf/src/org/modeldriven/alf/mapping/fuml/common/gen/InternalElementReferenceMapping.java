
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.common.gen;

import org.modeldriven.alf.mapping.fuml.common.gen.ElementReferenceMapping;

import org.modeldriven.alf.syntax.common.InternalElementReference;

import fUML.Syntax.Classes.Kernel.Element;

import java.util.ArrayList;
import java.util.List;

public class InternalElementReferenceMapping extends ElementReferenceMapping {

	public InternalElementReferenceMapping() {
		this
				.setErrorMessage("InternalElementReferenceMapping not yet implemented.");
	}

	public List<Element> getModelElements() {
		// TODO: Auto-generated stub
		return new ArrayList<Element>();
	}

	public InternalElementReference getInternalElementReference() {
		return (InternalElementReference) this.getSource();
	}

} // InternalElementReferenceMapping
