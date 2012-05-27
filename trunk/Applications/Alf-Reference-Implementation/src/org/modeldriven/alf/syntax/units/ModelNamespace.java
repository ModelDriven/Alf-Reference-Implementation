package org.modeldriven.alf.syntax.units;

import java.util.Collection;
import java.util.List;

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
    public void _deriveAll() {
        List<Member> ownedMembers = (List<Member>)this.getOwnedMember();
        int i = ownedMembers.size();
        super._deriveAll();
        // NOTE: This allows for the possibility that new units may be added to
        // model scope as a result of derivations.
        for (; i < ownedMembers.size(); i++) {
            ownedMembers.get(i).deriveAll();
        }
    }
    
    @Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
        List<Member> ownedMembers = (List<Member>)this.getOwnedMember();
        if (ownedMembers != null) {
            // NOTE: Using an index for loop allows for the possibility that
            // new units may be added to model scope as a result of constraint
            // checking.
            for (int i = 0; i < ownedMembers.size(); i++) {
                SyntaxElement _ownedMember = ownedMembers.get(i);
                // The owned members of a model namespace should all be units,
                // so check the constraints for them as units.
                if (_ownedMember instanceof NamespaceDefinition) {
                    UnitDefinition unit = ((NamespaceDefinition)_ownedMember).getUnit();
                    if (unit != null) {
                        _ownedMember = unit;
                    }
                }
                _ownedMember.checkConstraints(violations);
            }
        }
    }
    
}
