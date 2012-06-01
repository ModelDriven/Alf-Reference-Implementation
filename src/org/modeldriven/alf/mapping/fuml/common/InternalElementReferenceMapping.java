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
