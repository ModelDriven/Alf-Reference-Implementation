
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.mapping.fuml.units;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.mapping.fuml.common.DocumentedElementMapping;

import org.modeldriven.alf.syntax.units.UnitDefinition;

import fUML.Syntax.Classes.Kernel.Element;

import java.util.Collection;

public class UnitDefinitionMapping extends DocumentedElementMapping {
    
    FumlMapping mapping = null;
    
    /**
     * 1. A unit definition maps to a specific kind of namespace according to
     * the namespace definition for the unit definition, as given in the
     * appropriate subsequent subclause. If a namespace declaration is given,
     * the namespace mapped from the unit is an owned member of the declared
     * namespace. If no namespace declaration is given, then the unit must be a
     * model unit and what namespace owns it, if any, is not defined by the Alf
     * specification.
     * 
     * 2. If the unit is a model unit, then it has empty visibility. Otherwise,
     * the unit visibility is given by the stub declaration for it in the
     * definition of its owning namespace.
     */
    
    // The mapping for a unit is handled through the mapping for its definition.

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
