/*******************************************************************************
 * Copyright 2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.uml;

public class StereotypeApplication {
    
    private Element element;
    private Stereotype stereotype;
    
    public StereotypeApplication(Element element, Stereotype stereotype) {
        this.element = element;
        this.stereotype = stereotype;
    }
    
    public Element getElement() {
        return this.element;
    }
    
    public Stereotype getStereotype() {
        return this.stereotype;
    }

}
