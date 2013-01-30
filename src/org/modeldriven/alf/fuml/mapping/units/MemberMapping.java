
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.units;

import java.util.ArrayList;
import java.util.List;

import org.modeldriven.alf.fuml.mapping.common.DocumentedElementMapping;
import org.modeldriven.alf.mapping.MappingError;

import org.modeldriven.alf.syntax.units.Member;

import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.NamedElement;

public abstract class MemberMapping extends DocumentedElementMapping {
    
    /**
     * Members are mapped to named elements with the name and visibility given
     * for the member.
     */
    
    public void mapTo(NamedElement namedElement) throws MappingError {
        super.mapTo(namedElement);
        
        Member member = this.getMember();        
        namedElement.setName(member.getName());
        
        String visibility = member.getVisibility();
        namedElement.setVisibility(
                visibility == null || visibility.equals("")? "package": visibility);
            
    }
    
    /**
     * Allow for a second pass mapping of expressions or statements within
     * the member being mapped. Returns any additional packageable model
     * elements that result from mapping the body (e.g., instance 
     * specifications and events).
     */
    public List<Element> mapBody() throws MappingError {
        return new ArrayList<Element>();
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
	    return super.toString() + (element == null? "": " name:" + element.getName());
	}
	
} // MemberMapping
