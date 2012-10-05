package org.modeldriven.alf.syntax.units.impl;

import org.modeldriven.alf.syntax.units.ModelNamespace;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.UnitDefinition;

public class ModelNamespaceImpl extends PackageDefinitionImpl {
    
    public ModelNamespaceImpl(ModelNamespace self) {
        super(self);
    }
    
    public ModelNamespace getSelf() {
        return (ModelNamespace)this.self;
    }
    
    public NamespaceDefinition getModelNamespace(UnitDefinition unit) {
        NamespaceDefinition definition = unit.getDefinition();
        NamespaceDefinition modelScope = definition.getNamespace();
        if (modelScope == null) {
            // NOTE: The model scope for a unit must include the unit itself,
            // so that it can refer to itself recursively.
            modelScope = this.getSelf();
            modelScope.getMember(); // To ensure computation of derived attributes.
            modelScope.addOwnedMember(definition);
            modelScope.addMember(definition);
            definition.setNamespace(modelScope);
        }
        return modelScope;
    }

}
