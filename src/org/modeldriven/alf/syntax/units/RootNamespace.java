package org.modeldriven.alf.syntax.units;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.impl.RootNamespaceImpl;

public class RootNamespace extends NamespaceDefinition {
    
    private RootNamespace() {
        this.impl = new RootNamespaceImpl(this);
    }
    
    public RootNamespaceImpl getImpl() {
        return (RootNamespaceImpl)this.impl;
    }
    
    private static RootNamespace rootNamespace = new RootNamespace();
    private static QualifiedName alfStandardLibrary = null;
    private static QualifiedName primitiveTypes = null;
    private static QualifiedName primitiveBehaviors = null;
    private static QualifiedName basicInputOutput = null;
    
    private static ElementReference booleanType = null;
    private static ElementReference integerType = null;
    private static ElementReference stringType = null;
    private static ElementReference unlimitedNaturalType = null;
    private static ElementReference bitStringType = null;
    private static ElementReference naturalType = null;

    public static RootNamespace getRootScope() {
        return rootNamespace;
    }
    
    public static NamespaceDefinition getModelScope(UnitDefinition unit) {
        return getRootScope();
    }
    
    public static UnitDefinition resolveUnit(QualifiedName qualifiedName) {
        return getRootScope().getImpl().resolveUnit(qualifiedName);
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

}
