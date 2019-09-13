/*******************************************************************************
 * Copyright 2011, 2016 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.common;

import java.util.ArrayList;
import java.util.Collection;

import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.fuml.mapping.common.ElementReferenceMapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.syntax.common.InternalElementReference;
import org.modeldriven.alf.uml.Element;

public class InternalElementReferenceMapping extends ElementReferenceMapping {

    private FumlMapping mapping = null;

    /**
     * An internal element reference maps to model element mapped from its
     * identified syntax element.
     */
    public FumlMapping getMapping() {
        if (this.mapping == null) {
            Object element = this.getInternalElementReference().getElement();
            if (element != null) {
                this.mapping = this.fumlMap(element);
            }
        }
        return this.mapping;
    }

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

    public InternalElementReference getInternalElementReference() {
        return (InternalElementReference) this.getSource();
    }

    @Override
    public void print(String prefix) {
        super.print(prefix);
        if (this.mapping != null) {
            System.out.println(prefix + " target:" + mapping.getElement());
        }
    }

} // InternalElementReferenceMapping
