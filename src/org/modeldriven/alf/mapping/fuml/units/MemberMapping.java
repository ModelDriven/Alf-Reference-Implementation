
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.units;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.common.DocumentedElementMapping;

import org.modeldriven.alf.syntax.units.Member;

import fUML.Syntax.Classes.Kernel.NamedElement;

public abstract class MemberMapping extends DocumentedElementMapping {
    
    public void mapTo(NamedElement namedElement) throws MappingError {
        super.mapTo(namedElement);
        namedElement.setName(this.getMember().getName());
    }
    
	public Member getMember() {
		return (Member) this.getSource();
	}
	
	public String toString() {
	    NamedElement element = (NamedElement)this.getElement();
	    return super.toString() + (element == null? "": " name:" + element.name);
	}
	
} // MemberMapping
