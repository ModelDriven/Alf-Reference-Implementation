
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.common;

import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.mapping.fuml.common.ElementReferenceMapping;

import org.modeldriven.alf.syntax.common.ExternalElementReference;

public class ExternalElementReferenceMapping extends ElementReferenceMapping {
    
    public ExternalElementReferenceMapping() {
        this.setErrorMessage("ExternalElementReferenceMapping not supported.");
    }

    public FumlMapping getMapping() {
        return this;
    }
    
    public ExternalElementReference getExternalElementReference() {
		return (ExternalElementReference) this.getSource();
	}

} // ExternalElementReferenceMapping
