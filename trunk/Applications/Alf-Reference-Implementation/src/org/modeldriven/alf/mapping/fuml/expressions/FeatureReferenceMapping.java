
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.common.SyntaxElementMapping;

import org.modeldriven.alf.syntax.expressions.FeatureReference;

import fUML.Syntax.Classes.Kernel.Element;

import java.util.List;

public class FeatureReferenceMapping extends SyntaxElementMapping {

	public FeatureReferenceMapping() {
		this.setErrorMessage("No mapping for FeatureReference.");
	}

	public List<Element> getModelElements() throws MappingError {
		throw new MappingError(this, this.getErrorMessage());
	}

	public FeatureReference getFeatureReference() {
		return (FeatureReference) this.getSource();
	}

} // FeatureReferenceMapping
