/*******************************************************************************
 * Copyright 2011-2015 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.uml;

public abstract class ElementFactory {
    
    @SuppressWarnings("unchecked")
    public <T extends org.modeldriven.alf.uml.Element> T newInstance(Class<T> class_) {
        return (T)this.newInstance(class_.getSimpleName());
    }
    
    public Element newInstance(String className) {
        try {
            return (Element)this.createInstance(className);
        } catch (Exception e) {
            System.out.println("Could not instantiate " + 
                    this.getWrapperClassName(className) + ": " + e);
            e.printStackTrace();
            return null;
        }
    }
    
    public Element newInstance(Object base, Class<?> classClass, Class<?> namedElementClass) {
        final String baseClassName = base.getClass().getSimpleName();
        Element newInstance = null;
        try {
            newInstance = (Element)this.createInstance(baseClassName);
        } catch (Exception e) {
            final String className =
                    classClass.isInstance(base)? "Class":
                    namedElementClass.isInstance(base)? "NamedElement":
                    "Element";
            newInstance = (Element)this.newInstance(className);
        }
        return newInstance;
    }
        
    public Element createInstance(String className) 
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return (Element)Class.forName(this.getWrapperClassName(className)).newInstance();
    }
    
    public boolean supportsTemplates() {
        try {
            Class.forName(this.getWrapperClassName("RedefinableTemplateSignature"));
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
    
    public String getWrapperClassName(String className) {
        int len = className.length();
        if (len > 4 && className.substring(len-4).equals("Impl")) {
            className = className.substring(0, len-4);
        }
        if (className.equals("Class")) {
            className = "Class_";
        }
        return this.getPackageName() + "." + className;
    }
    
    public String getPackageName() {
        return this.getClass().getPackage().getName();
    }
    
}
