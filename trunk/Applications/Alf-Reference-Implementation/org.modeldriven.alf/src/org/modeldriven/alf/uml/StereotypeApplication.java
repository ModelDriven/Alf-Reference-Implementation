/*******************************************************************************
 * Copyright 2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.uml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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

    // Record stereotype applications whose actual application is deferred
    // until after their profile is applied.
    private static Map<Element, Collection<Stereotype>> stereotypeApplications = 
            new HashMap<Element, Collection<Stereotype>>();    
    
    public static void addStereotypeApplication(Element element, Stereotype stereotype) {
        if (element != null) {
            Collection<Stereotype> stereotypes = stereotypeApplications.get(element);
            if (stereotypes == null) {
                stereotypes = new ArrayList<Stereotype>();
                stereotypeApplications.put(element, stereotypes);
            }
            stereotypes.add(stereotype);
        }
    }
    
    public static void addStereotypeApplications(Collection<StereotypeApplication> stereotypeApplications) {
        for (StereotypeApplication stereotypeApplication: stereotypeApplications) {
            addStereotypeApplication(
                    stereotypeApplication.getElement(), 
                    stereotypeApplication.getStereotype());
        }
    }
    
    public static boolean hasStereotypeApplication(Element element, Stereotype stereotype) {
        Collection<Stereotype> stereotypes = stereotypeApplications.get(element);
        return stereotypes != null && stereotypes.contains(stereotype);
    }
    
    public static boolean isStereotypeApplied(Element element, Stereotype stereotype) {
        return hasStereotypeApplication(element, stereotype) || 
                element.isStereotypeApplied(stereotype);
    }
    
    public static void applyStereotypes() {
        for (Element element: stereotypeApplications.keySet()) {
            for (Stereotype stereotype: stereotypeApplications.get(element)) {
                element.applyStereotype(stereotype);
            }
        }
    }
}
