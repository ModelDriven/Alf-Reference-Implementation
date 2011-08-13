
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.units;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.units.MemberMapping;

import org.modeldriven.alf.syntax.units.EnumerationLiteralName;

import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.Classes.Kernel.EnumerationLiteral;

import java.util.ArrayList;
import java.util.List;

public class EnumerationLiteralNameMapping extends MemberMapping {
    
    private EnumerationLiteral enumerationLiteral = null;

    @Override
    public Element getElement() {
        return this.enumerationLiteral;
    }
    
    @Override
	public List<Element> getModelElements() throws MappingError {
		List<Element> elements = new ArrayList<Element>();
		elements.add(this.getEnumerationLiteral());
		return elements;
	}
	
	public EnumerationLiteral getEnumerationLiteral() throws MappingError {
	    if (this.enumerationLiteral == null) {
	        this.enumerationLiteral = new EnumerationLiteral();
	        this.mapTo(enumerationLiteral);
	    }
	    return this.enumerationLiteral;
	}

	public EnumerationLiteralName getEnumerationLiteralName() {
		return (EnumerationLiteralName) this.getSource();
	}

} // EnumerationLiteralNameMapping
