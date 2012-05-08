
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

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
