/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.syntax.units.impl;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.impl.AssignableElementImpl;

public class AssignableTypedElementImpl extends AssignableElementImpl {
    
    private TypedElementDefinitionImpl typedElement = null;

    public AssignableTypedElementImpl(TypedElementDefinitionImpl typedElement) {
        super(typedElement.getSelf());
        this.typedElement = typedElement;
    }

    @Override
    public ElementReference getType() {
        return this.typedElement.getType();
    }

    @Override
    public Integer getLower() {
        return this.typedElement.getLower();
    }

    @Override
    public Integer getUpper() {
        return this.typedElement.getUpper();
    }

}
