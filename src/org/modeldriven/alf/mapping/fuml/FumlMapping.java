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

import fUML.Semantics.Loci.LociL1.ExecutionFactory;
import fUML.Semantics.Loci.LociL3.ExecutionFactoryL3;
import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.Classes.Kernel.PrimitiveType;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior;

public abstract class FumlMapping extends Mapping {
    
    private static FumlMappingFactory fumlFactory = new FumlMappingFactory();
    private static ExecutionFactory executionFactory = null;
    private static SyntaxElement parsedElement = null;
    
    private static PrimitiveType booleanType = null;
    private static PrimitiveType integerType = null;
    private static PrimitiveType unlimitedNaturalType = null;
    private static PrimitiveType naturalType = null;
    private static PrimitiveType stringType = null;
    
    public static ExecutionFactory getExecutionFactory() {
        return executionFactory;
    }

    public static void setExecutionFactory(ExecutionFactory executionFactory) {
        FumlMapping.executionFactory = executionFactory;
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
                System.out.println("Error mapping primitive type UnlimitedNatural: " +
                        e.getMessage());
            }
        }
        return stringType;
    }
    
    public static PrimitiveType getPrimitiveType(ElementReference typeReference) 
    throws MappingError {
        DataTypeDefinitionMapping mapping = (DataTypeDefinitionMapping)
        ((ElementReferenceMapping)fumlFactory.getMapping(typeReference)).getMapping();
        return (PrimitiveType)mapping.getClassifier();
    }

    public static Behavior getBehavior(ElementReference behaviorReference)
    throws MappingError {
        ActivityDefinitionMapping mapping = (ActivityDefinitionMapping)
        ((ElementReferenceMapping)fumlFactory.getMapping(behaviorReference)).getMapping();
        return (Behavior)mapping.getClassifier();
    }
    
    public FumlMapping() {
        this.setFactory(fumlFactory);
    }
    
    public Element getElement() {
        return null;
    }
    
    public abstract Collection<Element> getModelElements() throws MappingError;
    
    public FumlMapping fumlMap(Object source) {
        return (FumlMapping)this.map(source);
    }
    
    public void mapTo(Element element) throws MappingError {
        /*
        System.out.println("[mapTo] source = " + this.getSource() + 
                " element=" + element);
        */
        /*
        Object source = this.getSource();
        if (source instanceof SyntaxElement) {
            ((SyntaxElement) source).print("***", true);
        } else {
            System.out.println("***" + source);
        }
        */
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

    public static void main(String[] args) {
        String fileName = null;
        
        if (args.length > 0) {
            fileName = args[0];
        }
        
        setExecutionFactory(new ExecutionFactoryL3());       
        FumlMapping mapping = parseAndMap(fileName);
        
        if (mapping != null) {
            try {
                mapping.getModelElements();
                System.out.println("Mapped successfully.");
            } catch (MappingError e) {
                System.out.println("Mapping failed.");
                Mapping errorMapping = e.getMapping();
                System.out.println(errorMapping);
                System.out.println(" error: " + e.getMessage());
                Object source = errorMapping.getSource();
                if (source != null) {
                    System.out.println(" source: " + source);
                    if (source instanceof SyntaxElement) {
                        SyntaxElement element = (SyntaxElement)source;
                        System.out.println(" file: " + element.getFileName() + 
                                " at line " + element.getLine() + 
                                " column " + element.getColumn());
                    }
                }
            }
            mapping.print();
        }
    }
    
}
