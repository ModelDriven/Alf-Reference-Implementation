/*******************************************************************************
 * Copyright 2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.execution;

import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.RootNamespace;
import org.modeldriven.alf.syntax.units.UnitDefinition;

public abstract class AlfCompiler extends AlfBase {
    
    protected abstract void saveModel(NamespaceDefinition definition);
    
    public void process(UnitDefinition unit) {
        if (unit != null) {
            FumlMapping mapping = this.map(RootNamespace.getRootScope());
            if (mapping != null) {
                this.saveModel(unit.getDefinition());
            }
        }
    }
    
    public AlfCompiler() {
        super();
    }
    
    public AlfCompiler(String[] args) {
        super(args);
    }
    
}
