/*******************************************************************************
 * Copyright 2011-2016 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.uml;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class ElementFactory {
    
    public static final String INTERFACE_PACKAGE_NAME = 
            org.modeldriven.alf.uml.Element.class.getPackage().getName();
    
    public static Class<?> interfaceForName(String name) {
        if (name == null) {
            return null;
        } else {
            if (name.equals("Class")) {
                name = "Class_";
            }
            try {
                return Class.forName(INTERFACE_PACKAGE_NAME + "." + name);
            } catch (Exception e) {
                return null;
            }
        }
    }

    private Class<?> behaviorClass = null;
    private Class<?> classClass = null;
    private Class<?> classifierClass = null;
    private Class<?> namedElementClass = null;
    
    public ElementFactory(
            Class<?> behaviorClass, 
            Class<?> classClass, 
            Class<?> classifierClass,
            Class<?> namedElementClass) {
        this.behaviorClass = behaviorClass;
        this.classClass = classClass;
        this.classifierClass = classifierClass;
        this.namedElementClass = namedElementClass;
    }
    
    @SuppressWarnings("unchecked")
    public <T extends org.modeldriven.alf.uml.Element> T newInstance(Class<T> class_) {
        final String className = class_.getSimpleName();
        try {
            return (T)Class.forName(this.getWrapperClassName(className)).newInstance();
        } catch (Exception e) {
            System.out.println("Could not instantiate " + 
                    this.getWrapperClassName(className) + ": " + e);
            e.printStackTrace();
            return null;
        }
    }
    
    public Element newInstanceFor(Object base) {
        Element newInstance = null;
        try {
            newInstance = (Element)this.createInstanceFor(base.getClass().getSimpleName(), base);
        } catch (Exception e) {
            final String className =
                    this.behaviorClass.isInstance(base)? "Behavior":
                    this.classClass.isInstance(base)? "Class":
                    this.classifierClass.isInstance(base)? "Classifier":
                    this.namedElementClass.isInstance(base)? "NamedElement":
                    "Element";
            newInstance = (Element)this.newInstanceFor(className, base);
        }
        return newInstance;
    }
    
    private Element newInstanceFor(String className, Object base) {
        try {
            return (Element)this.createInstanceFor(className, base);
        } catch (Exception e) {
            System.out.println("Could not instantiate " + 
                    this.getWrapperClassName(className) + ": " + e);
            e.printStackTrace();
            return null;
        }
    }
    
    private Element createInstanceFor(String className, Object base) 
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, 
                   IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        return (Element)getConstructor(Class.forName(this.getWrapperClassName(className))).newInstance(base);
    }
    
    static private Constructor<?> getConstructor(Class<?> class_) {
        for (Constructor<?> constructor: class_.getConstructors()) {
            if (constructor.getParameterTypes().length == 1) {
                return constructor;
            }
        }
        return null;
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
