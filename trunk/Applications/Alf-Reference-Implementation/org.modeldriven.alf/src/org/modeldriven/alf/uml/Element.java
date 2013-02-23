/*******************************************************************************
 * Copyright 2011-2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.uml;

import java.util.List;

public interface Element {
    public List<Element> getOwnedElement();

    public Element getOwner();

    public List<Comment> getOwnedComment();

    public void addOwnedComment(Comment ownedComment);
    
    public void applyStereotype(Stereotype stereotype);
    
    public boolean isStereotypeApplied(Stereotype stereotype);
    
    // Note: The hashCode for an element should be the hashCode for its base
    // implementation object.
    public int hashCode();
    
    // Replace references to all given elements with references to corresponding 
    // newElements, in the properties of all elements contained within this 
    // templateable element.
    public void replaceAll(
            List<? extends Element> elements, 
            List<? extends Element> newElements);
    
    // Note: For a given implementation, an element should equal another element
    // with the same base implementation, or a base implementation object
    // equal to the element base implementation.
    public boolean equals(Object other);

    public String toString(boolean includeDerived);

    public void print(String prefix);
}
