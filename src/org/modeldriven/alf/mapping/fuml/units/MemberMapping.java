
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.units;

import java.util.ArrayList;
import java.util.List;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.common.DocumentedElementMapping;

import org.modeldriven.alf.syntax.units.Member;

import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.Classes.Kernel.NamedElement;

public abstract class MemberMapping extends DocumentedElementMapping {
    
    public void mapTo(NamedElement namedElement) throws MappingError {
        super.mapTo(namedElement);
        namedElement.setName(this.getMember().getName());
    }
    
    /**
     * Allow for a second pass mapping of expressions or statements within
     * the member being mapped.
     */
    public void mapBody() throws MappingError {        
    }
    
    @Override
    public List<Element> getModelElements() throws MappingError {
        List<Element> elements = new ArrayList<Element>();
        NamedElement element = this.getNamedElement();
        if (element != null) {
             elements.add(element);
        }
        return elements;
    }
    
    public abstract NamedElement getNamedElement() throws MappingError;
    
	public Member getMember() {
		return (Member) this.getSource();
	}
	
	public String toString() {
	    NamedElement element = (NamedElement)this.getElement();
	    return super.toString() + (element == null? "": " name:" + element.name);
	}
	
} // MemberMapping
