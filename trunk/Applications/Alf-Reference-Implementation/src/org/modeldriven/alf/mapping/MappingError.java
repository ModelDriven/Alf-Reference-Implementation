/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.mapping;

public class MappingError extends Exception {
    
    private static final long serialVersionUID = 7023532129809394745L;
    
    private Mapping mapping = null;
    
    public MappingError(Mapping mapping, String message) {
        super(message);
        this.mapping = mapping;
    }
    
    public Mapping getMapping() {
        return this.mapping;
    }

}
