/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
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
    
    private static RootNamespace rootNamespace = new RootNamespace();
    
    private static QualifiedName alfStandardLibrary = null;
    private static QualifiedName primitiveTypes = null;
    private static QualifiedName primitiveBehaviors = null;
    private static QualifiedName booleanFunctions = null;
    private static QualifiedName integerFunctions = null;
    private static QualifiedName stringFunctions = null;
    private static QualifiedName unlimitedNaturalFunctions = null;
    private static QualifiedName bitStringFunctions = null;
    private static QualifiedName basicInputOutput = null;
    private static QualifiedName sequenceFunctions = null;
    private static QualifiedName collectionFunctions = null;
    private static QualifiedName collectionClasses = null;
    
    private static ElementReference booleanType = null;
    private static ElementReference integerType = null;
    private static ElementReference stringType = null;
    private static ElementReference unlimitedNaturalType = null;
    private static ElementReference bitStringType = null;
    private static ElementReference naturalType = null;
    
    private static Map<String, ElementReference> booleanFunctionMap = 
        new HashMap<String, ElementReference>();    
    private static Map<String, ElementReference> integerFunctionMap = 
        new HashMap<String, ElementReference>();    
    private static Map<String, ElementReference> stringFunctionMap = 
        new HashMap<String, ElementReference>();    
    private static Map<String, ElementReference> unlimitedNaturalFunctionMap = 
        new HashMap<String, ElementReference>();    
    private static Map<String, ElementReference> bitStringFunctionMap = 
        new HashMap<String, ElementReference>();
    
    private static ElementReference sequenceFunctionIncluding = null;
    private static ElementReference sequenceFunctionIsEmpty = null;
    private static ElementReference sequenceFunctionNotEmpty = null;
    private static ElementReference sequenceFunctionCount = null;
    private static ElementReference sequenceFunctionExcludeAt = null;
    private static ElementReference sequenceFunctionReplacingAt = null;
    
    private static ElementReference collectionFunctionAdd = null;
    private static ElementReference collectionClassesPackage = null;
    
    private static QualifiedName listFunctions = null;
    
    private static ElementReference listFunctionGet = null;
    private static ElementReference listFunctionSize = null;
      
    public static RootNamespace getRootScope() {
        return rootNamespace;
    }
    
    public static void setRootImpl(ModelNamespaceImpl impl) {
        rootNamespace.setImpl(impl);
    }
    
    public static NamespaceDefinition getModelScope(UnitDefinition unit) {
        return getRootScope().getModelNamespace(unit);
    }
    
    public static UnitDefinition resolve(QualifiedName qualifiedName) {
        return getRootScope().resolveUnit(qualifiedName);
    }
    
    public static QualifiedName getAlfStandardLibrary() {
        if (alfStandardLibrary == null) {
            alfStandardLibrary = new QualifiedName();
            alfStandardLibrary.getImpl().addName("Alf").getImpl().addName("Library");
            alfStandardLibrary.getImpl().setCurrentScope(getRootScope());
        }
        return alfStandardLibrary;
    }
    
    public static QualifiedName getPrimitiveTypes() {
        if (primitiveTypes == null) {
            primitiveTypes = getAlfStandardLibrary().getImpl().copy().addName("PrimitiveTypes");
            primitiveTypes.getImpl().setCurrentScope(getRootScope());
        }
        return primitiveTypes;
    }

    public static QualifiedName getPrimitiveBehaviors() {
        if (primitiveBehaviors == null) {
            primitiveBehaviors = getAlfStandardLibrary().getImpl().copy().addName("PrimitiveBehaviors");
            primitiveBehaviors.getImpl().setCurrentScope(getRootScope());
        }
        return primitiveBehaviors;
    }

    public static QualifiedName getBasicInputOutput() {
        if (basicInputOutput == null) {
            basicInputOutput = getAlfStandardLibrary().getImpl().copy().addName("BasicInputOutput");
            basicInputOutput.getImpl().setCurrentScope(getRootScope());
        }
        return basicInputOutput;
    }
    
    public static QualifiedName getSequenceFunctions() {
        if (sequenceFunctions == null) {
            sequenceFunctions = getPrimitiveBehaviors().getImpl().copy().addName("SequenceFunctions");
            sequenceFunctions.getImpl().setCurrentScope(getRootScope());
        }
        return sequenceFunctions;
    }
    
     public static QualifiedName getCollectionFunctions() {
        if (collectionFunctions == null) {
            collectionFunctions = getAlfStandardLibrary().getImpl().copy().addName("CollectionFunctions");
            collectionFunctions.getImpl().setCurrentScope(getRootScope());
        }
        return collectionFunctions;
    }
    
    public static QualifiedName getCollectionClasses() {
        if (collectionClasses == null) {
            collectionClasses = getAlfStandardLibrary().getImpl().copy().addName("CollectionClasses");
            collectionClasses.getImpl().setCurrentScope(getRootScope());
        }
        return collectionClasses;
    }
    
   public static ElementReference getBooleanType() {
        if (booleanType == null) {
            booleanType = getPrimitiveTypes().getImpl().copy().
                            addName("Boolean").getImpl().getClassifierReferent();
        }
        return booleanType;
    }

    public static ElementReference getIntegerType() {
        if (integerType == null) {
            integerType = getPrimitiveTypes().getImpl().copy().
                            addName("Integer").getImpl().getClassifierReferent();
        }
        return integerType;
    }

    public static ElementReference getStringType() {
        if (stringType == null) {
            stringType = getPrimitiveTypes().getImpl().copy().
                            addName("String").getImpl().getClassifierReferent();
        }
        return stringType;
    }

    public static ElementReference getUnlimitedNaturalType() {
        if (unlimitedNaturalType == null) {
            unlimitedNaturalType = getPrimitiveTypes().getImpl().copy().
                            addName("UnlimitedNatural").getImpl().getClassifierReferent();
        }
        return unlimitedNaturalType;
    }

    public static ElementReference getBitStringType() {
        if (bitStringType  == null) {
            bitStringType = getPrimitiveTypes().getImpl().copy().
                            addName("BitString").getImpl().getClassifierReferent();
        }
        return bitStringType;
    }

    public static ElementReference getNaturalType() {
        if (naturalType == null) {
            naturalType = getPrimitiveTypes().getImpl().copy().
                            addName("Natural").getImpl().getClassifierReferent();
        }
        return naturalType;
    }

    public static ElementReference getSequenceFunctionIncluding() {
        if (sequenceFunctionIncluding == null) {
            sequenceFunctionIncluding = getSequenceFunctions().getImpl().copy().
                            addName("Including").getImpl().getBehaviorReferent();
        }
        return sequenceFunctionIncluding;
    }

    public static ElementReference getSequenceFunctionIsEmpty() {
        if (sequenceFunctionIsEmpty == null) {
            sequenceFunctionIsEmpty = getSequenceFunctions().getImpl().copy().
                            addName("IsEmpty").getImpl().getBehaviorReferent();
        }
        return sequenceFunctionIsEmpty;
    }

    public static ElementReference getSequenceFunctionNotEmpty() {
        if (sequenceFunctionNotEmpty == null) {
            sequenceFunctionNotEmpty = getSequenceFunctions().getImpl().copy().
                            addName("NotEmpty").getImpl().getBehaviorReferent();
        }
        return sequenceFunctionNotEmpty;
    }

    public static ElementReference getSequenceFunctionCount() {
        if (sequenceFunctionCount == null) {
            sequenceFunctionCount = getSequenceFunctions().getImpl().copy().
                            addName("Count").getImpl().getBehaviorReferent();
        }
        return sequenceFunctionCount;
    }

    public static ElementReference getSequenceFunctionExcludeAt() {
        if (sequenceFunctionExcludeAt == null) {
            sequenceFunctionExcludeAt = getSequenceFunctions().getImpl().copy().
                            addName("ExcludeAt").getImpl().getBehaviorReferent();
        }
        return sequenceFunctionExcludeAt;
    }

    public static ElementReference getSequenceFunctionReplacingAt() {
        if (sequenceFunctionReplacingAt == null) {
            sequenceFunctionReplacingAt = getSequenceFunctions().getImpl().copy().
                            addName("ReplacingAt").getImpl().getBehaviorReferent();
        }
        return sequenceFunctionReplacingAt;
    }

    public static ElementReference getCollectionFunctionAdd() {
        if (collectionFunctionAdd == null) {
            collectionFunctionAdd = getCollectionFunctions().getImpl().copy().
                            addName("add").getImpl().getBehaviorReferent();
        }
        return collectionFunctionAdd;
    }

    public static ElementReference getCollectionClassesPackage() {
        if (collectionClassesPackage == null) {
            collectionClassesPackage = getCollectionClasses().getImpl().
                getNamespaceReferent();
        }
        return collectionClassesPackage;
    }
    
    public static QualifiedName getListFunctions() {
        if (listFunctions == null) {
            listFunctions = new QualifiedName();
            listFunctions.getImpl().addName("FoundationalModelLibrary").getImpl().
                addName("PrimitiveBehaviors").getImpl().addName("ListFunctions");
            listFunctions.getImpl().setCurrentScope(getRootScope());
        }
        return listFunctions;
    }
    
    public static ElementReference getListFunctionGet() {
        if (listFunctionGet == null) {
            listFunctionGet = getListFunctions().getImpl().copy().
                            addName("ListGet").getImpl().getBehaviorReferent();
        }
        return listFunctionGet;
    }

    public static ElementReference getListFunctionSize() {
        if (listFunctionSize == null) {
            listFunctionSize = getListFunctions().getImpl().copy().
                            addName("ListSize").getImpl().getBehaviorReferent();
        }
        return listFunctionSize;
    }
    
    public static QualifiedName getBooleanFunctions() {
        if (booleanFunctions == null) {
            booleanFunctions = getPrimitiveBehaviors().getImpl().copy().
                addName("BooleanFunctions");
            booleanFunctions.getImpl().setCurrentScope(getRootScope());
        }
        return booleanFunctions;
    }
    
    public static ElementReference getBooleanFunction(String name) {
        ElementReference booleanFunction = booleanFunctionMap.get(name);
        if (booleanFunction == null) {
            booleanFunction = getBooleanFunctions().getImpl().copy().
                addName(name).getImpl().getBehaviorReferent();
            booleanFunctionMap.put(name, booleanFunction);
        }
        return booleanFunction;
    }

    public static ElementReference getBooleanFunctionNot() {
        return getBooleanFunction("!");
    }
    
    public static ElementReference getBooleanFunctionOr() {
        return getBooleanFunction("|");
    }
    
    public static QualifiedName getIntegerFunctions() {
        if (integerFunctions == null) {
            integerFunctions = getPrimitiveBehaviors().getImpl().copy().
                addName("IntegerFunctions");
            integerFunctions.getImpl().setCurrentScope(getRootScope());
        }
        return integerFunctions;
    }
    
    public static ElementReference getIntegerFunction(String name) {
        ElementReference integerFunction = integerFunctionMap.get(name);
        if (integerFunction == null) {
            integerFunction = getIntegerFunctions().getImpl().copy().
                addName(name).getImpl().getBehaviorReferent();
            integerFunctionMap.put(name, integerFunction);
        }
        return integerFunction;
    }

    public static ElementReference getIntegerFunctionNeg() {
        return getIntegerFunction("Neg");
    }

    public static ElementReference getIntegerFunctionPlus() {
        return getIntegerFunction("+");
    }

    public static ElementReference getIntegerFunctionMinus() {
        return getIntegerFunction("-");
    }

    public static ElementReference getIntegerFunctionLessThanOrEqual() {
        return getIntegerFunction("<=");
    }

    public static ElementReference getIntegerFunctionToUnlimitedNatural() {
        return getIntegerFunction("ToUnlimitedNatural");
    }

    public static QualifiedName getStringFunctions() {
        if (stringFunctions == null) {
            stringFunctions = getPrimitiveBehaviors().getImpl().copy().
                addName("StringFunctions");
            stringFunctions.getImpl().setCurrentScope(getRootScope());
        }
        return stringFunctions;
    }
    
    public static ElementReference getStringFunction(String name) {
        ElementReference stringFunction = stringFunctionMap.get(name);
        if (stringFunction == null) {
            stringFunction = getStringFunctions().getImpl().copy().
                addName(name).getImpl().getBehaviorReferent();
            stringFunctionMap.put(name, stringFunction);
        }
        return stringFunction;
    }

    public static QualifiedName getUnlimitedNaturalFunctions() {
        if (unlimitedNaturalFunctions == null) {
            unlimitedNaturalFunctions = getPrimitiveBehaviors().getImpl().copy().
                addName("UnlimitedNaturalFunctions");
            unlimitedNaturalFunctions.getImpl().setCurrentScope(getRootScope());
        }
        return unlimitedNaturalFunctions;
    }
    
    public static ElementReference getUnlimitedNaturalFunction(String name) {
        ElementReference unlimitedNaturalFunction = 
            unlimitedNaturalFunctionMap.get(name);
        if (unlimitedNaturalFunction == null) {
            unlimitedNaturalFunction = getUnlimitedNaturalFunctions().getImpl().copy().
                addName(name).getImpl().getBehaviorReferent();
            unlimitedNaturalFunctionMap.put(name, unlimitedNaturalFunction);
        }
        return unlimitedNaturalFunction;
    }
    
    public static ElementReference getUnlimitedNaturalFunctionToInteger() {
        return getUnlimitedNaturalFunction("ToInteger");
    }
    
    public static QualifiedName getBitStringFunctions() {
        if (bitStringFunctions == null) {
            bitStringFunctions = getPrimitiveBehaviors().getImpl().copy().
                addName("BitStringFunctions");
            bitStringFunctions.getImpl().setCurrentScope(getRootScope());
        }
        return bitStringFunctions;
    }
    
    public static ElementReference getBitStringFunction(String name) {
        ElementReference bitStringFunction = bitStringFunctionMap.get(name);
        if (bitStringFunction == null) {
            bitStringFunction = getBitStringFunctions().getImpl().copy().
                addName(name).getImpl().getBehaviorReferent();
            bitStringFunctionMap.put(name, bitStringFunction);
        }
        return bitStringFunction;
    }

    public static ElementReference getBitStringFunctionComplement() {
        return getBitStringFunction("~");
    }

    public static ElementReference getBitStringFunctionToBitString() {
        return getBitStringFunction("ToBitString");
    }

    public static ElementReference getBitStringFunctionToInteger() {
        return getBitStringFunction("ToInteger");
    }

}
