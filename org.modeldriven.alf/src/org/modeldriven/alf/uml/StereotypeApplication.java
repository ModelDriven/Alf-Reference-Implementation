/*******************************************************************************
 * Copyright 2013-2016 Data Access Technologies, Inc. (Model Driven Solutions)
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
    private Collection<TaggedValue> taggedValues;
    
    public static class TaggedValue {
        
        public String name;
        public Object value;
        
        public TaggedValue(String name, Object value) {
            this.name = name;
            this.value = value;
        }
        
        public String getName() {
            return this.name;
        }
        
        public Object getValue() {
            return this.value;
        }
    }
    
    public StereotypeApplication(Element element, Stereotype stereotype) {
        this(element, stereotype, null);
    }
    
    public StereotypeApplication(Element element, Stereotype stereotype, Collection<TaggedValue> taggedValues) {
        this.element = element;
        this.stereotype = stereotype;
        this.taggedValues = taggedValues == null? new ArrayList<TaggedValue>(): taggedValues;
    }
    
    public Element getElement() {
        return this.element;
    }
    
    public Stereotype getStereotype() {
        return this.stereotype;
    }
    
    public Collection<TaggedValue> getTaggedValues() {
        return this.taggedValues;
    }

    // Record stereotype applications whose actual application is deferred
    // until after their profile is applied.
    private static Map<Element, Collection<StereotypeApplication>> stereotypeApplications = 
            new HashMap<Element, Collection<StereotypeApplication>>();
    
    public static void clearStereotypeApplications() {
        stereotypeApplications.clear();
    }
    
    public static void addStereotypeApplication(Element element, Stereotype stereotype, Collection<TaggedValue> taggedValues) {
        addStereotypeApplication(new StereotypeApplication(element, stereotype, taggedValues));
    }
    
    public static void addStereotypeApplication(StereotypeApplication stereotypeApplication) {
        if (stereotypeApplication != null) {
            Element element = stereotypeApplication.getElement();
            if (element != null) {
                Collection<StereotypeApplication> applications = stereotypeApplications.get(element);
                if (applications == null) {
                    applications = new ArrayList<StereotypeApplication>();
                    stereotypeApplications.put(element, applications);
                }
                applications.add(stereotypeApplication);
            }
        }
    }
    
   public static void addStereotypeApplications(Collection<StereotypeApplication> stereotypeApplications) {
        for (StereotypeApplication stereotypeApplication: stereotypeApplications) {
            addStereotypeApplication(stereotypeApplication);
        }
    }
    
    public static boolean hasStereotypeApplication(Element element, Stereotype stereotype) {
        Collection<StereotypeApplication> applications = stereotypeApplications.get(element);
        if (applications != null) {
            for (StereotypeApplication application: applications) {
                if (stereotype.equals(application.getStereotype())) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static boolean isStereotypeApplied(Element element, Stereotype stereotype) {
        return hasStereotypeApplication(element, stereotype) || 
                element.isStereotypeApplied(stereotype);
    }
    
    public static void applyStereotypes() {
        for (Element element: stereotypeApplications.keySet()) {
            for (StereotypeApplication application: stereotypeApplications.get(element)) {
                element.applyStereotype(application.getStereotype(), application.getTaggedValues());
            }
        }
    }
}
