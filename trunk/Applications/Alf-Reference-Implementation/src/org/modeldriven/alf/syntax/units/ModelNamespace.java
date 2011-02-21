package org.modeldriven.alf.syntax.units;

import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.impl.ModelNamespaceImpl;

public class ModelNamespace extends NamespaceDefinition {
    
    private ModelNamespace() {
        this.impl = new ModelNamespaceImpl(this);
    }
    
    public ModelNamespaceImpl getImpl() {
        return (ModelNamespaceImpl)this.impl;
    }
    
    private static ModelNamespace modelScope = new ModelNamespace();
    
    public static ModelNamespace getModelScope() {
        return modelScope;
    }
    
    public static UnitDefinition resolveUnit(QualifiedName qualifiedName) {
        return getModelScope().getImpl().resolveUnit(qualifiedName);
    }

}
