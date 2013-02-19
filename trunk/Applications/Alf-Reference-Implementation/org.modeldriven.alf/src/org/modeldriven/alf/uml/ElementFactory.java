/*******************************************************************************
 * Copyright 2011-2013 Data Access Technologies, Inc. (Model Driven Solutions)
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
        String implementationClassName = this.getWrapperClassName(className);
        try {
            return (Element)Class.forName(implementationClassName).newInstance();
        } catch (Exception e) {
            System.out.println("Could not instantiate " + 
                    implementationClassName + ": " + e);
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean supportsTemplates() {
        try {
            Class.forName(this.getWrapperClassName("RedefinableTemplateSignature"));
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
    
    public abstract String getWrapperClassName(String className);
    
}
