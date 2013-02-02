/*******************************************************************************
 * Copyright 2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.execution;

import org.modeldriven.alf.syntax.units.UnitDefinition;

public abstract class AlfCompiler extends AlfBase {
    
    protected abstract UnitDefinition saveModel(UnitDefinition definition);
    
    @Override
    public UnitDefinition process(UnitDefinition unit) {
        return this.saveModel(super.process(unit));
    }
    
    public AlfCompiler() {
        super();
    }
    
    public AlfCompiler(String[] args) {
        super(args);
    }
    
}
