
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

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
