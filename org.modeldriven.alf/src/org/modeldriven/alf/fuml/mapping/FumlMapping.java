/*******************************************************************************
 * Copyright 2011-2015 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fuml.mapping;

import java.util.Collection;

import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.parser.AlfParser;
import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.common.SyntaxElement;
import org.modeldriven.alf.syntax.units.RootNamespace;
import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.PrimitiveType;
import org.modeldriven.alf.uml.Behavior;
import org.modeldriven.alf.uml.ElementFactory;

import org.modeldriven.alf.fuml.mapping.common.ElementReferenceMapping;
import org.modeldriven.alf.fuml.mapping.units.ActivityDefinitionMapping;
import org.modeldriven.alf.fuml.mapping.units.DataTypeDefinitionMapping;

public abstract class FumlMapping extends Mapping {

    private static FumlMappingFactory fumlFactory = null;
    private static ElementFactory fumlElementFactory = null;
    private static SyntaxElement parsedElement = null;
    
    public static FumlMappingFactory getFumlFactory() {
        if (fumlFactory == null) {
            fumlFactory = new FumlMappingFactory();
        }
        return fumlFactory;
    }
    public static void setFumlFactory(FumlMappingFactory fumlFactory) {
        FumlMapping.fumlFactory = fumlFactory;
    }
    
    public static ElementFactory getElementFactory() {
        return fumlElementFactory;
    }
    
    public static void setElementFactory(ElementFactory elementFactory) {
        fumlElementFactory = elementFactory;
    }
    
    public static SyntaxElement getParsedElement() {
        return parsedElement;
    }
    
    public static PrimitiveType getBooleanType() {
        return getPrimitiveType(RootNamespace.getRootScope().getBooleanType());
    }
    
    public static PrimitiveType getIntegerType() {
        return getPrimitiveType(RootNamespace.getRootScope().getIntegerType());
    }
    
    public static PrimitiveType getRealType() {
        return getPrimitiveType(RootNamespace.getRootScope().getRealType());
    }
    
    public static PrimitiveType getUnlimitedNaturalType() {
        return getPrimitiveType(RootNamespace.getRootScope().getUnlimitedNaturalType());
    }
    
    public static PrimitiveType getNaturalType() {
        return getPrimitiveType(RootNamespace.getRootScope().getNaturalType());
    }
    
    public static PrimitiveType getStringType() {
        return getPrimitiveType(RootNamespace.getRootScope().getStringType());
    }
    
    public static PrimitiveType getBitStringType() {
        return getPrimitiveType(RootNamespace.getRootScope().getBitStringType());
    }
    
    public static PrimitiveType getPrimitiveType(ElementReference typeReference) {
        PrimitiveType primitiveType = (PrimitiveType)typeReference.getImpl().getUml();
        if (primitiveType == null) {
            DataTypeDefinitionMapping mapping = 
                    (DataTypeDefinitionMapping)((ElementReferenceMapping)getFumlFactory().
                            getMapping(typeReference)).getMapping();
            try {
                primitiveType = (PrimitiveType)mapping.getClassifier();
            } catch (MappingError e) {
                System.out.println("Error mapping primitive type " + 
                        typeReference.getImpl().getName() + ": " + e.getMessage());
            }
        } 
        return primitiveType;
    }

    public static Behavior getBehavior(ElementReference behaviorReference)
    throws MappingError {
        Behavior behavior = behaviorReference == null? null: 
            (Behavior)behaviorReference.getImpl().getUml();
        if (behavior == null) {
            Mapping mapping = getFumlFactory().getMapping(behaviorReference);
            if (mapping instanceof ElementReferenceMapping) {
                mapping = ((ElementReferenceMapping)mapping).getMapping();
            }
            if (mapping instanceof ActivityDefinitionMapping) {
                behavior = (Behavior)((ActivityDefinitionMapping)mapping).getClassifier();
            } else {
                throw new MappingError(mapping,
                        "Error mapping behavior: " + mapping.getErrorMessage());
            }
        }
        return behavior;
    }
    
    public FumlMapping() {
        this.setFactory(getFumlFactory());
    }
    
    public Element getElement() {
        return null;
    }
    
    public abstract Collection<Element> getModelElements() throws MappingError;
    
    public FumlMapping fumlMap(Object source) {
        return (FumlMapping)this.map(source);
    }
    
    public <T extends Element> T create(Class<T> class_) {
        return getElementFactory().newInstance(class_);
    }
    
    public boolean supportsTemplates() {
        return getElementFactory().supportsTemplates();
    }
    
    public ActivityGraph createActivityGraph() {
        return new ActivityGraph(getElementFactory());
    }
    
    public ActivityGraph createActivityGraph(ActivityGraph graph) {
        return new ActivityGraph(graph);
    }
    
    public void mapTo(Element element) throws MappingError {
    }
    
    public static FumlMapping getMapping(Object source) {
        return (FumlMapping) fumlFactory.getMapping(source);
    }

    public static FumlMapping parseAndMap(String fileName) {
        FumlMapping mapping = null;
        
        AlfParser.parseOptions("C");
        parsedElement = AlfParser.parse(fileName);
        
        if (parsedElement != null && AlfParser.constraintsCheck()) {
            mapping = getMapping(RootNamespace.getRootScope());
        }
        
        return mapping;
    }
    
}
