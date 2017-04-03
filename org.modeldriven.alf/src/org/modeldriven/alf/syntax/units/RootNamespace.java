/*******************************************************************************
 * Copyright 2011-2016 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.syntax.units;

import java.util.HashMap;
import java.util.Map;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.impl.ModelNamespaceImpl;

public class RootNamespace extends ModelNamespace {
    
    /**
     * A RootScopeRegistry allows for dynamic resolution of what the "active"
     * root scope is. This allows, e.g., for a tool to maintain a different
     * root scope for different projects that are open at the same time with
     * different global namespaces, so that root elements are resolved and
     * cached separately for each project.      *
     */
    public interface RootScopeRegistry {
        public RootNamespace getRootScope();
    }
    
    /**
     * GlobalRootScopeRegistry is the default RootScopeRegistry.
     * It simply maintains a single, global root namespace.
     */
    public static class GlobalRootScopeRegistry implements RootScopeRegistry {
        private RootNamespace rootNamespace = null;
        
        public GlobalRootScopeRegistry() {
            this(new RootNamespace());
        }
        
        public GlobalRootScopeRegistry(RootNamespace rootNamespace) {
            this.rootNamespace = rootNamespace;
        }
        
        @Override
        public RootNamespace getRootScope() {
            return this.rootNamespace;
        }
    }
    
    public static RootScopeRegistry rootRegistry = null;
    
    public static RootScopeRegistry getRootRegistry() {
        if (rootRegistry == null) {
            rootRegistry = new GlobalRootScopeRegistry();
        }
        return rootRegistry;
    }
    
    public static void setRootRegistry(RootScopeRegistry registry) {
        rootRegistry = registry;
    }
    
    public static RootNamespace getRootScope() {
        return getRootRegistry().getRootScope();
    }
    
    public static void setRootImpl(ModelNamespaceImpl impl) {
        getRootScope().setImpl(impl);
    }
    
    public static NamespaceDefinition getModelScope(UnitDefinition unit) {
        return getRootScope().getModelNamespace(unit);
    }
    
    private QualifiedName alfStandardLibrary = null;
    private QualifiedName primitiveTypes = null;   
    private QualifiedName primitiveBehaviors = null;
    private QualifiedName booleanFunctions = null;
    private QualifiedName integerFunctions = null;
    private QualifiedName stringFunctions = null;
    private QualifiedName unlimitedNaturalFunctions = null;
    private QualifiedName realFunctions = null;
    private QualifiedName bitStringFunctions = null;
    private QualifiedName basicInputOutput = null;
    private QualifiedName sequenceFunctions = null;
    private QualifiedName collectionFunctions = null;
    private QualifiedName collectionClasses = null;
    
    private ElementReference booleanType = null;
    private ElementReference integerType = null;
    private ElementReference stringType = null;
    private ElementReference unlimitedNaturalType = null;
    private ElementReference realType = null;
    private ElementReference bitStringType = null;
    private ElementReference naturalType = null;
    
    private Map<String, ElementReference> booleanFunctionMap = 
        new HashMap<String, ElementReference>();    
    private Map<String, ElementReference> integerFunctionMap = 
        new HashMap<String, ElementReference>();    
    private Map<String, ElementReference> stringFunctionMap = 
        new HashMap<String, ElementReference>();    
    private Map<String, ElementReference> unlimitedNaturalFunctionMap = 
            new HashMap<String, ElementReference>();    
    private Map<String, ElementReference> realFunctionMap = 
            new HashMap<String, ElementReference>();    
    private Map<String, ElementReference> bitStringFunctionMap = 
            new HashMap<String, ElementReference>();
    private Map<String, ElementReference> sequenceFunctionMap = 
            new HashMap<String, ElementReference>();
    private Map<String, ElementReference> collectionFunctionMap = 
            new HashMap<String, ElementReference>();
    
    private ElementReference collectionClassesPackage = null;
    
    private QualifiedName listFunctions = null;
    
    private ElementReference listFunctionGet = null;
    private ElementReference listFunctionSize = null;
    
    private ElementReference standardProfile = null;
    
    private ElementReference createStereotype = null;
    private ElementReference destroyStereotype = null;
    private ElementReference modelLibraryStereotype = null;
    
    protected QualifiedName getAlfStandardLibraryName() {
        return new QualifiedName().getImpl().
                addName("Alf").getImpl().
                addName("Library");
    }
    
    public QualifiedName getAlfStandardLibrary() {
        if (alfStandardLibrary == null) {
            alfStandardLibrary = getAlfStandardLibraryName();
            alfStandardLibrary.getImpl().setCurrentScope(getRootScope());
        }
        return alfStandardLibrary;
    }
    
    public QualifiedName getPrimitiveTypes() {
        if (primitiveTypes == null) {
            primitiveTypes = getAlfStandardLibrary().getImpl().copy().addName("PrimitiveTypes");
        }
        return primitiveTypes;
    }

   public QualifiedName getPrimitiveBehaviors() {
        if (primitiveBehaviors == null) {
            primitiveBehaviors = getAlfStandardLibrary().getImpl().copy().addName("PrimitiveBehaviors");
        }
        return primitiveBehaviors;
    }

    public QualifiedName getBasicInputOutput() {
        if (basicInputOutput == null) {
            basicInputOutput = getAlfStandardLibrary().getImpl().copy().addName("BasicInputOutput");
        }
        return basicInputOutput;
    }
    
    public QualifiedName getSequenceFunctions() {
        if (sequenceFunctions == null) {
            sequenceFunctions = getPrimitiveBehaviors().getImpl().copy().addName("SequenceFunctions");
        }
        return sequenceFunctions;
    }
    
    public QualifiedName getCollectionFunctions() {
        if (collectionFunctions == null) {
            collectionFunctions = getAlfStandardLibrary().getImpl().copy().addName("CollectionFunctions");
        }
        return collectionFunctions;
    }
    
    public QualifiedName getCollectionClasses() {
        if (collectionClasses == null) {
            collectionClasses = getAlfStandardLibrary().getImpl().copy().addName("CollectionClasses");
        }
        return collectionClasses;
    }
    
   public ElementReference getBooleanType() {
        if (booleanType == null) {
            booleanType = getPrimitiveTypes().getImpl().copy().
                            addName("Boolean").getImpl().getClassifierReferent();
        }
        return booleanType;
    }

    public ElementReference getIntegerType() {
        if (integerType == null) {
            integerType = getPrimitiveTypes().getImpl().copy().
                            addName("Integer").getImpl().getClassifierReferent();
        }
        return integerType;
    }

    public ElementReference getStringType() {
        if (stringType == null) {
            stringType = getPrimitiveTypes().getImpl().copy().
                            addName("String").getImpl().getClassifierReferent();
        }
        return stringType;
    }

    public ElementReference getUnlimitedNaturalType() {
        if (unlimitedNaturalType == null) {
            unlimitedNaturalType = getPrimitiveTypes().getImpl().copy().
                            addName("UnlimitedNatural").getImpl().getClassifierReferent();
        }
        return unlimitedNaturalType;
    }

    public ElementReference getRealType() {
        if (realType == null) {
            realType = getPrimitiveTypes().getImpl().copy().
                            addName("Real").getImpl().getClassifierReferent();
        }
        return realType;
    }

    public ElementReference getBitStringType() {
        if (bitStringType  == null) {
            bitStringType = getPrimitiveTypes().getImpl().copy().
                            addName("BitString").getImpl().getClassifierReferent();
        }
        return bitStringType;
    }

    public ElementReference getNaturalType() {
        if (naturalType == null) {
            naturalType = getPrimitiveTypes().getImpl().copy().
                            addName("Natural").getImpl().getClassifierReferent();
        }
        return naturalType;
    }

    public ElementReference getSequenceFunction(String name) {
        ElementReference sequenceFunction = sequenceFunctionMap.get(name);
        if (sequenceFunction == null) {
            sequenceFunction = getSequenceFunctions().getImpl().copy().
                addName(name).getImpl().getBehaviorReferent();
            sequenceFunctionMap.put(name, sequenceFunction);
        }
        return sequenceFunction;
    }

    public ElementReference getSequenceFunctionIncluding() {
        return this.getSequenceFunction("Including");
    }

    public ElementReference getSequenceFunctionIsEmpty() {
        return this.getSequenceFunction("IsEmpty");
    }

    public ElementReference getSequenceFunctionNotEmpty() {
        return this.getSequenceFunction("NotEmpty");
    }

    public ElementReference getSequenceFunctionCount() {
        return this.getSequenceFunction("Count");
    }

    public ElementReference getSequenceFunctionExcludeAt() {
        return this.getSequenceFunction("ExcludeAt");
    }

    public ElementReference getSequenceFunctionReplacingAt() {
        return this.getSequenceFunction("ReplacingAt");
    }

    public ElementReference getCollectionFunction(String name) {
        ElementReference collectionFunction = collectionFunctionMap.get(name);
        if (collectionFunction == null) {
            collectionFunction = getCollectionFunctions().getImpl().copy().
                addName(name).getImpl().getBehaviorReferent();
            collectionFunctionMap.put(name, collectionFunction);
        }
        return collectionFunction;
    }

    public ElementReference getCollectionFunctionAdd() {
        return this.getCollectionFunction("add");
    }

    public ElementReference getCollectionFunctionIsEmpty() {
        return this.getCollectionFunction("isEmpty");
    }

    public ElementReference getCollectionFunctionNotEmpty() {
        return this.getCollectionFunction("notEmpty");
    }

    public ElementReference getCollectionClassesPackage() {
        if (collectionClassesPackage == null) {
            collectionClassesPackage = getCollectionClasses().getImpl().
                getNamespaceReferent();
        }
        return collectionClassesPackage;
    }
    
    protected QualifiedName getFoundationalModelLibraryName() {
        return new QualifiedName().getImpl().addName("FoundationalModelLibrary");
    }
    
    public QualifiedName getListFunctions() {
        if (listFunctions == null) {
            QualifiedName foundationalModelLibraryName = getFoundationalModelLibraryName();
            foundationalModelLibraryName.getImpl().setCurrentScope(getRootScope());
            listFunctions = foundationalModelLibraryName.getImpl().
                addName("PrimitiveBehaviors").getImpl().addName("ListFunctions");
        }
        return listFunctions;
    }
    
    public ElementReference getListFunctionGet() {
        if (listFunctionGet == null) {
            listFunctionGet = getListFunctions().getImpl().copy().
                            addName("ListGet").getImpl().getBehaviorReferent();
        }
        return listFunctionGet;
    }

    public ElementReference getListFunctionSize() {
        if (listFunctionSize == null) {
            listFunctionSize = getListFunctions().getImpl().copy().
                            addName("ListSize").getImpl().getBehaviorReferent();
        }
        return listFunctionSize;
    }
    
    public QualifiedName getBooleanFunctions() {
        if (booleanFunctions == null) {
            booleanFunctions = getPrimitiveBehaviors().getImpl().copy().
                addName("BooleanFunctions");
            booleanFunctions.getImpl().setCurrentScope(getRootScope());
        }
        return booleanFunctions;
    }
    
    public ElementReference getBooleanFunction(String name) {
        ElementReference booleanFunction = booleanFunctionMap.get(name);
        if (booleanFunction == null) {
            booleanFunction = getBooleanFunctions().getImpl().copy().
                addName(name).getImpl().getBehaviorReferent();
            booleanFunctionMap.put(name, booleanFunction);
        }
        return booleanFunction;
    }

    public ElementReference getBooleanFunctionNot() {
        return getBooleanFunction("!");
    }
    
    public ElementReference getBooleanFunctionOr() {
        return getBooleanFunction("|");
    }
    
    public QualifiedName getIntegerFunctions() {
        if (integerFunctions == null) {
            integerFunctions = getPrimitiveBehaviors().getImpl().copy().
                addName("IntegerFunctions");
            integerFunctions.getImpl().setCurrentScope(getRootScope());
        }
        return integerFunctions;
    }
    
    public ElementReference getIntegerFunction(String name) {
        ElementReference integerFunction = integerFunctionMap.get(name);
        if (integerFunction == null) {
            integerFunction = getIntegerFunctions().getImpl().copy().
                addName(name).getImpl().getBehaviorReferent();
            integerFunctionMap.put(name, integerFunction);
        }
        return integerFunction;
    }

    public ElementReference getIntegerFunctionNeg() {
        return getIntegerFunction("Neg");
    }

    public ElementReference getIntegerFunctionPlus() {
        return getIntegerFunction("+");
    }

    public ElementReference getIntegerFunctionMinus() {
        return getIntegerFunction("-");
    }

    public ElementReference getIntegerFunctionLessThanOrEqual() {
        return getIntegerFunction("<=");
    }

    public ElementReference getIntegerFunctionToUnlimitedNatural() {
        return getIntegerFunction("ToUnlimitedNatural");
    }

    public ElementReference getIntegerFunctionToReal() {
        return getIntegerFunction("ToReal");
    }

    public QualifiedName getStringFunctions() {
        if (stringFunctions == null) {
            stringFunctions = getPrimitiveBehaviors().getImpl().copy().
                addName("StringFunctions");
            stringFunctions.getImpl().setCurrentScope(getRootScope());
        }
        return stringFunctions;
    }
    
    public ElementReference getStringFunction(String name) {
        ElementReference stringFunction = stringFunctionMap.get(name);
        if (stringFunction == null) {
            stringFunction = getStringFunctions().getImpl().copy().
                addName(name).getImpl().getBehaviorReferent();
            stringFunctionMap.put(name, stringFunction);
        }
        return stringFunction;
    }

    public QualifiedName getUnlimitedNaturalFunctions() {
        if (unlimitedNaturalFunctions == null) {
            unlimitedNaturalFunctions = getPrimitiveBehaviors().getImpl().copy().
                addName("UnlimitedNaturalFunctions");
            unlimitedNaturalFunctions.getImpl().setCurrentScope(getRootScope());
        }
        return unlimitedNaturalFunctions;
    }
    
    public QualifiedName getRealFunctions() {
        if (realFunctions == null) {
            realFunctions = getPrimitiveBehaviors().getImpl().copy().
                addName("RealFunctions");
            realFunctions.getImpl().setCurrentScope(getRootScope());
        }
        return realFunctions;
    }
    
    public ElementReference getRealFunction(String name) {
        ElementReference realFunction = realFunctionMap.get(name);
        if (realFunction == null) {
            realFunction = getRealFunctions().getImpl().copy().
                addName(name).getImpl().getBehaviorReferent();
            realFunctionMap.put(name, realFunction);
        }
        return realFunction;
    }

    public ElementReference getRealFunctionNeg() {
        return getRealFunction("Neg");
    }

    public ElementReference getRealFunctionPlus() {
        return getRealFunction("+");
    }

    public ElementReference getRealFunctionMinus() {
        return getRealFunction("-");
    }

    public ElementReference getRealFunctionToInteger() {
        return getRealFunction("ToInteger");
    }

    public ElementReference getUnlimitedNaturalFunction(String name) {
        ElementReference unlimitedNaturalFunction = 
            unlimitedNaturalFunctionMap.get(name);
        if (unlimitedNaturalFunction == null) {
            unlimitedNaturalFunction = getUnlimitedNaturalFunctions().getImpl().copy().
                addName(name).getImpl().getBehaviorReferent();
            unlimitedNaturalFunctionMap.put(name, unlimitedNaturalFunction);
        }
        return unlimitedNaturalFunction;
    }
    
    public ElementReference getUnlimitedNaturalFunctionToInteger() {
        return getUnlimitedNaturalFunction("ToInteger");
    }
    
    public QualifiedName getBitStringFunctions() {
        if (bitStringFunctions == null) {
            bitStringFunctions = getPrimitiveBehaviors().getImpl().copy().
                addName("BitStringFunctions");
            bitStringFunctions.getImpl().setCurrentScope(getRootScope());
        }
        return bitStringFunctions;
    }
    
    public ElementReference getBitStringFunction(String name) {
        ElementReference bitStringFunction = bitStringFunctionMap.get(name);
        if (bitStringFunction == null) {
            bitStringFunction = getBitStringFunctions().getImpl().copy().
                addName(name).getImpl().getBehaviorReferent();
            bitStringFunctionMap.put(name, bitStringFunction);
        }
        return bitStringFunction;
    }

    public ElementReference getBitStringFunctionComplement() {
        return getBitStringFunction("~");
    }

    public ElementReference getBitStringFunctionToBitString() {
        return getBitStringFunction("ToBitString");
    }

    public ElementReference getBitStringFunctionToInteger() {
        return getBitStringFunction("ToInteger");
    }
    
    protected QualifiedName getStandardProfileName() {
        return new QualifiedName().getImpl().addName("StandardProfile");        
    }
    
    public ElementReference getStandardProfile() {
        if (standardProfile == null) {
            QualifiedName standardProfileName = getStandardProfileName();
            standardProfileName.getImpl().setCurrentScope(getRootScope());
            ElementReference namespace = standardProfileName.getImpl().getNamespaceReferent();
            if (namespace != null && namespace.getImpl().isProfile()) {
                standardProfile = namespace;
            }
        }
        return standardProfile;
    }
    
    public boolean isStandardProfileUsed() {
        return standardProfile != null;
    }

   public ElementReference getCreateStereotype() {
        if (createStereotype == null) {
            ElementReference standardProfile = getStandardProfile();
            if (standardProfile != null) {
                QualifiedName name = new QualifiedName().getImpl().addName("Create");
                name.getImpl().setCurrentScope(standardProfile.getImpl().asNamespace());
                createStereotype = name.getImpl().getStereotypeReferent();
            }
        }
        return createStereotype;
    }
    
    public ElementReference getDestroyStereotype() {
        if (destroyStereotype == null) {
            ElementReference standardProfile = getStandardProfile();
            if (standardProfile != null) {
                QualifiedName name = new QualifiedName().getImpl().addName("Destroy");
                name.getImpl().setCurrentScope(standardProfile.getImpl().asNamespace());
                destroyStereotype = name.getImpl().getStereotypeReferent();
            }
        }
        return destroyStereotype;
    }

    public ElementReference getModelLibraryStereotype() {
        if (modelLibraryStereotype == null) {
            ElementReference standardProfile = getStandardProfile();
            if (standardProfile != null) {
                QualifiedName name = new QualifiedName().getImpl().addName("ModelLibrary");
                name.getImpl().setCurrentScope(standardProfile.getImpl().asNamespace());
                modelLibraryStereotype = name.getImpl().getStereotypeReferent();
            }
        }
        return modelLibraryStereotype;
    }

}
