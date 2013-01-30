/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fuml.impl.uml;

public class ElementFactory implements org.modeldriven.alf.uml.ElementFactory {
    
    static final String packageName = ElementFactory.class.getPackage().getName();

    @Override
    @SuppressWarnings("unchecked")
    public <T extends org.modeldriven.alf.uml.Element> T newInstance(Class<T> class_) {
        return (T)newInstance(class_.getSimpleName());
    }
    
    public static Element newInstance(String className) {
        try {
            return (Element)Class.forName(packageName + "." + className).newInstance();
        } catch (Exception e) {
            System.out.println("Could not instantiate " + 
                    packageName + "." + className + ": " + e);
            return null;
        }
    }
    

}
