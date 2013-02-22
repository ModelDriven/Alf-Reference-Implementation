/*******************************************************************************
 * Copyright 2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.execution;

import java.io.IOException;

import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.syntax.common.SyntaxElement;
import org.modeldriven.alf.syntax.common.impl.ElementReferenceImpl;
import org.modeldriven.alf.syntax.units.ModelNamespace;
import org.modeldriven.alf.syntax.units.RootNamespace;
import org.modeldriven.alf.syntax.units.UnitDefinition;
import org.modeldriven.alf.uml.Package;

public abstract class AlfCompiler extends AlfBase {
    
    protected Package getModel(UnitDefinition unit) {
        ModelNamespace modelScope = 
                (ModelNamespace)RootNamespace.getModelScope(unit);
        
        SyntaxElement modelDefinition = 
                unit.getIsModelLibrary() && 
                unit.getDefinition().getNamespace() == modelScope?
                        unit: modelScope;
        FumlMapping mapping = FumlMapping.getMapping(modelDefinition);
        return (Package)mapping.getElement();        
    }
    
    protected abstract void saveModel(String name, Package model) throws IOException;
    
    @Override
    public UnitDefinition process(UnitDefinition unit) {
        unit = super.process(unit);
        
        if (unit != null) {
            Package model = this.getModel(unit);
            
            RootNamespace.addAdditionalElementsTo(model);        
            ElementReferenceImpl.replaceTemplateBindingsIn(model);          
            
            try {
                this.saveModel(unit.getDefinition().getName(), model);
            } catch (IOException e) {
                unit = null;
            }
        }
        
        return unit;
    }
    
    public AlfCompiler() {
        super();
    }
    
    public AlfCompiler(String[] args) {
        super(args);
    }
    
}
