
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

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
