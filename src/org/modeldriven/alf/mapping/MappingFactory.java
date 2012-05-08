/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.mapping;

import org.modeldriven.alf.syntax.common.SyntaxElement;

public abstract class MappingFactory {
    
    public Mapping getMapping(Object source) {
        Mapping mapping = !(source instanceof SyntaxElement)? null:
            ((SyntaxElement)source).getImpl().getMapping();
        if (mapping == null) {
            mapping = this.instantiateMapping(source);
            mapping.setSource(source);
        }
        return mapping;
    }
    
    public abstract Mapping instantiateMapping(Object source);

}
