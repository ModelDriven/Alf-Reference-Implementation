/*******************************************************************************
 * Copyright 2011, 2018 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.syntax.units;

import org.modeldriven.alf.syntax.expressions.QualifiedName;

public class MissingUnit extends UnitDefinition {
    
    private String pathName;
    
    public MissingUnit(String pathName) {
        super();
        this.pathName = pathName;
        this.getImpl().setHasImplicitImports(true);
    }
    
   public MissingUnit(QualifiedName qualifiedName) {
        this(qualifiedName.getPathName());
    }
    
    @Override
    public String toString(boolean includeDerived) {
        return super.toString(includeDerived) + " pathName:" + pathName;
    }

}
