/*******************************************************************************
 * Copyright 2011-2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fuml.impl.uml;

public class ElementFactory extends org.modeldriven.alf.uml.ElementFactory {
    
    static final String packageName = ElementFactory.class.getPackage().getName();

    @Override
    public String getWrapperClassName(String className) {
        return packageName + "." + className;
    }
    

}
