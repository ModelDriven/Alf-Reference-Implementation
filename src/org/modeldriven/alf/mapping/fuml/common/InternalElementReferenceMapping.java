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

import org.modeldriven.alf.syntax.common.InternalElementReference;

public class InternalElementReferenceMapping extends ElementReferenceMapping {

    FumlMapping mapping = null;

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
