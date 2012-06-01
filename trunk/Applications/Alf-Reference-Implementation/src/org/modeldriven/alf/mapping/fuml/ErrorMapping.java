/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.mapping.fuml;

import java.util.List;

import org.modeldriven.alf.mapping.MappingError;

import fUML.Syntax.Classes.Kernel.Element;

public class ErrorMapping extends FumlMapping {

    public ErrorMapping(Object source, String errorMessage) {
        this.setSource(source);
        this.setErrorMessage(errorMessage);
    }

    @Override
    public Element getElement() {
        return null;
    }
    
    @Override
    public List<Element> getModelElements() throws MappingError {
        throw new MappingError(this, this.getErrorMessage());
    }

}
