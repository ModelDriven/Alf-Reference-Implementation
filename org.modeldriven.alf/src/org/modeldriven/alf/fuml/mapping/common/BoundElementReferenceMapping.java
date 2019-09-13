/*******************************************************************************
 * Copyright 2017 Model Driven Solutions, Inc.
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
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.syntax.common.BoundElementReference;
import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.uml.Element;

public class BoundElementReferenceMapping extends ElementReferenceMapping {

    private FumlMapping mapping = null;

    public FumlMapping getMapping() {
        if (this.mapping == null) {
            BoundElementReference boundReference = this.getBoundElementReference();
            ElementReference boundElement = boundReference.getImpl().getEffectiveBoundElement();
            if (boundElement != null) {
                this.mapping = 
                        ((ElementReferenceMapping)this.fumlMap(boundElement)).getMapping();
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

    public BoundElementReference getBoundElementReference() {
        return (BoundElementReference) this.getSource();
    }

    @Override
    public void print(String prefix) {
        super.print(prefix);
        if (this.mapping != null) {
            System.out.println(prefix + " target:" + mapping.getElement());
        }
    }

}
