package org.modeldriven.alf.syntax.units;

import java.util.Collection;

import org.modeldriven.alf.syntax.common.ConstraintViolation;
import org.modeldriven.alf.syntax.common.SyntaxElement;
import org.modeldriven.alf.syntax.units.impl.ModelNamespaceImpl;

public class ModelNamespace extends PackageDefinition {
    
    public ModelNamespace() {
        this.impl = new ModelNamespaceImpl(this);
    }
    
    public ModelNamespaceImpl getImpl() {
        return (ModelNamespaceImpl)this.impl;
    }
    
    @Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
        Collection<Member> ownedMember = this.getOwnedMember();
        if (ownedMember != null) {
            for (Object _ownedMember : ownedMember.toArray()) {
                // The owned members of a model namespace should all be units,
                // so check the constraints for them as units.
                if (_ownedMember instanceof NamespaceDefinition) {
                    UnitDefinition unit = ((NamespaceDefinition)_ownedMember).getUnit();
                    if (unit != null) {
                        _ownedMember = unit;
                    }
                }
                ((SyntaxElement) _ownedMember).checkConstraints(violations);
            }
        }
    }
    
}
