
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.mapping.fuml.common;

import java.util.ArrayList;
import java.util.Collection;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.FumlMapping;

import org.modeldriven.alf.syntax.common.ElementReference;

import fUML.Syntax.Classes.Kernel.Element;

public abstract class ElementReferenceMapping extends FumlMapping {
    
    public abstract FumlMapping getMapping();
    
    @Override
    public Element getElement() {
        return this.getMapping().getElement();
    }

    @Override
    public Collection<Element> getModelElements() throws MappingError {
        FumlMapping mapping = this.getMapping();
        return mapping == null? new ArrayList<Element>(): 
            mapping.getModelElements();
    }

	public ElementReference getElementReference() {
		return (ElementReference) this.getSource();
	}
	
} // ElementReferenceMapping
