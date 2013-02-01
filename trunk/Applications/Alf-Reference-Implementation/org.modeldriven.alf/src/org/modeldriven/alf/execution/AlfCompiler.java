/*******************************************************************************
 * Copyright 2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.execution;

import org.modeldriven.alf.uml.ElementFactory;
import org.modeldriven.alf.fuml.execution.Locus;
import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.fuml.mapping.FumlMappingFactory;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.RootNamespace;

public abstract class AlfCompiler extends org.modeldriven.alf.execution.Alf {
    
    protected abstract FumlMappingFactory createFumlFactory();
    protected abstract ElementFactory createElementFactory();
    
    protected abstract void saveModel(NamespaceDefinition definition);
    
    public void process(NamespaceDefinition definition) {
        FumlMapping.setFumlFactory(this.createFumlFactory());
        FumlMapping.setElementFactory(this.createElementFactory());
        FumlMapping mapping = FumlMapping.getMapping(RootNamespace.getRootScope());
        try {
            mapping.getModelElements();
            printVerbose("Mapped successfully.");
            this.saveModel(definition);
        } catch (MappingError e) {
            this.println("Mapping failed.");
            this.println(e.getMapping().toString());                  
            this.println(" error: " + e.getMessage());
        }
    }
    
}
