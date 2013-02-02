
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.common;

import java.util.ArrayList;
import java.util.List;

import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.fuml.mapping.common.ElementReferenceMapping;

import org.modeldriven.alf.syntax.common.ExternalElementReference;
import org.modeldriven.alf.uml.Element;

public class ExternalElementReferenceMapping extends ElementReferenceMapping {
    
    public ExternalElementReferenceMapping() {
    }

    @Override
    public FumlMapping getMapping() {
        return this;
    }
    
    @Override
    public Element getElement() {
        return this.getExternalElementReference().getElement();
    }
    
    @Override
    public List<Element> getModelElements() {
        List<Element> elements = new ArrayList<Element>();
        elements.add(this.getElement());
        return elements;
    }
    
    public ExternalElementReference getExternalElementReference() {
		return (ExternalElementReference) this.getSource();
	}

} // ExternalElementReferenceMapping
