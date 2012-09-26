/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.mapping.fuml;

import java.util.Collection;

import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.common.ElementReferenceMapping;
import org.modeldriven.alf.mapping.fuml.units.ActivityDefinitionMapping;
import org.modeldriven.alf.mapping.fuml.units.DataTypeDefinitionMapping;
import org.modeldriven.alf.parser.AlfParser;
import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.common.SyntaxElement;
import org.modeldriven.alf.syntax.units.RootNamespace;
import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.PrimitiveType;
import org.modeldriven.alf.uml.Behavior;
import org.modeldriven.alf.uml.ElementFactory;

import org.modeldriven.alf.execution.fuml.ExecutionFactory;

public abstract class FumlMapping extends Mapping {

    private static FumlMappingFactory fumlFactory = null;
    private static ElementFactory fumlElementFactory = null;
    private static ExecutionFactory executionFactory = null;
    private static SyntaxElement parsedElement = null;
    
    private static PrimitiveType booleanType = null;
    private static PrimitiveType integerType = null;
    private static PrimitiveType unlimitedNaturalType = null;
    private static PrimitiveType naturalType = null;
    private static PrimitiveType stringType = null;
    private static PrimitiveType bitStringType = null;
    
    public static FumlMappingFactory getFumlFactory() {
        if (fumlFactory == null) {
            fumlFactory = new FumlMappingFactory();
        }
        return fumlFactory;
    }
    public static void setFumlFactory(FumlMappingFactory fumlFactory) {
        FumlMapping.fumlFactory = fumlFactory;
    }
    
    public static ExecutionFactory getExecutionFactory() {
        return executionFactory;
    }

    public static void setExecutionFactory(ExecutionFactory executionFactory) {
        FumlMapping.executionFactory = executionFactory;
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
        if (booleanType == null) {
            try {
                booleanType = getPrimitiveType(RootNamespace.getBooleanType());
            } catch (Exception e) {
                System.out.println("Error mapping primitive type Boolean: " +
                        e.getMessage());
            }
        }
        return booleanType;
    }
    
    public static PrimitiveType getIntegerType() {
        if (integerType == null) {
            try {
                integerType = getPrimitiveType(RootNamespace.getIntegerType());
            } catch (Exception e) {
                System.out.println("Error mapping primitive type Integer: " +
                        e.getMessage());
            }
        }
        return integerType;
    }
    
    public static PrimitiveType getUnlimitedNaturalType() {
        if (unlimitedNaturalType == null) {
            try {
                unlimitedNaturalType = getPrimitiveType(RootNamespace.getUnlimitedNaturalType());
            } catch (Exception e) {
                System.out.println("Error mapping primitive type UnlimitedNatural: " +
                        e.getMessage());
            }
        }
        return unlimitedNaturalType;
    }
    
    public static PrimitiveType getNaturalType() {
        if (naturalType == null) {
            try {
                naturalType = getPrimitiveType(RootNamespace.getNaturalType());
            } catch (Exception e) {
                System.out.println("Error mapping primitive type Integer: " +
                        e.getMessage());
            }
        }
        return naturalType;
    }
    
    public static PrimitiveType getStringType() {
        if (stringType == null) {
            try {
                stringType = getPrimitiveType(RootNamespace.getStringType());
            } catch (Exception e) {
                System.out.println("Error mapping primitive type String: " +
                        e.getMessage());
            }
        }
        return stringType;
    }
    
    public static PrimitiveType getBitStringType() {
        if (bitStringType == null) {
            try {
                bitStringType = getPrimitiveType(RootNamespace.getBitStringType());
            } catch (Exception e) {
                System.out.println("Error mapping primitive type BitString: " +
                        e.getMessage());
            }
        }
        return bitStringType;
    }
    
    public static PrimitiveType getPrimitiveType(ElementReference typeReference) 
    throws MappingError {
        DataTypeDefinitionMapping mapping = (DataTypeDefinitionMapping)
        ((ElementReferenceMapping)getFumlFactory().getMapping(typeReference)).getMapping();
        return (PrimitiveType)mapping.getClassifier();
    }

    public static Behavior getBehavior(ElementReference behaviorReference)
    throws MappingError {
        Mapping mapping = getFumlFactory().getMapping(behaviorReference);
        if (mapping instanceof ElementReferenceMapping) {
            mapping = ((ElementReferenceMapping)mapping).getMapping();
        }
        if (mapping instanceof ActivityDefinitionMapping) {
            return (Behavior)((ActivityDefinitionMapping)mapping).getClassifier();
        } else {
            throw new MappingError(mapping,
                    "Error mapping behavior: " +mapping.getErrorMessage());
        }
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
        
        AlfParser.parseOptions("uC");
        parsedElement = AlfParser.parse(fileName);
        
        if (parsedElement != null && AlfParser.constraintsCheck()) {
            mapping = getMapping(RootNamespace.getRootScope());
        }
        
        return mapping;
    }
    
}
