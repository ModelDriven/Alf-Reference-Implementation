
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.units;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.common.SyntaxElementMapping;

import org.modeldriven.alf.syntax.units.StereotypeAnnotation;

import fUML.Syntax.Classes.Kernel.Element;

import java.util.List;

public class StereotypeAnnotationMapping extends SyntaxElementMapping {

	public StereotypeAnnotationMapping() {
		this.setErrorMessage("No mapping for StereotypeAnnotation.");
	}

    @Override
    public Element getElement() {
        return null;
    }

	public List<Element> getModelElements() throws MappingError {
        throw new MappingError(this, this.getErrorMessage());
	}

	public StereotypeAnnotation getStereotypeAnnotation() {
		return (StereotypeAnnotation) this.getSource();
	}

} // StereotypeAnnotationMapping
