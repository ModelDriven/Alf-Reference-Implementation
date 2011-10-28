
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.units;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.mapping.fuml.common.DocumentedElementMapping;

import org.modeldriven.alf.syntax.units.UnitDefinition;

import fUML.Syntax.Classes.Kernel.Element;

import java.util.Collection;

public class UnitDefinitionMapping extends DocumentedElementMapping {
    
    FumlMapping mapping = null;
    
    public FumlMapping getMapping() {
        if (this.mapping == null) {
            UnitDefinition unit = this.getUnitDefinition();
            this.mapping = this.fumlMap(unit.getDefinition());
        }
        return this.mapping;
    }

    @Override
    public Element getElement() {
        return this.getMapping().getElement();
    }
    
    @Override
	public Collection<Element> getModelElements() throws MappingError {
		return this.getMapping().getModelElements();
	}

	public UnitDefinition getUnitDefinition() {
		return (UnitDefinition) this.getSource();
	}
	
	@Override
	public void print(String prefix) {
	    super.print(prefix);
	    if (mapping != null) {
	        System.out.println(prefix + " definition:");
	        this.mapping.printChild(prefix);
	    }
	}

} // UnitDefinitionMapping
