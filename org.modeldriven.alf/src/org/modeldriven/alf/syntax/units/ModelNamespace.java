/*******************************************************************************
 * Copyright 2011-2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.syntax.units;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modeldriven.alf.syntax.common.ConstraintViolation;
import org.modeldriven.alf.syntax.common.SyntaxElement;
import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.units.impl.ModelNamespaceImpl;
import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.Stereotype;
import org.modeldriven.alf.uml.StereotypeApplication;

public class ModelNamespace extends PackageDefinition {
    
    private Map<Element, Collection<Stereotype>> stereotypeApplications = 
            new HashMap<Element, Collection<Stereotype>>();
    
    public ModelNamespace() {
    }
    
    public ModelNamespaceImpl getImpl() {
        if (this.impl == null) {
            this.impl = new ModelNamespaceImpl(this);
        }
        return (ModelNamespaceImpl)this.impl;
    }
    
    public void setImpl(ModelNamespaceImpl impl) {
        this.impl = impl;
    }
    
    public NamespaceDefinition getModelNamespace(UnitDefinition unit) {
        return this.getImpl().getModelNamespace(unit);
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
    
    public UnitDefinition resolveUnit(QualifiedName qualifiedName) {
        return this.getImpl().resolveUnit(qualifiedName);
    }
    
    public void addStereotypeApplication(Element element, Stereotype stereotype) {
        Collection<Stereotype> stereotypes = this.stereotypeApplications.get(element);
        if (stereotypes == null) {
            stereotypes = new ArrayList<Stereotype>();
            this.stereotypeApplications.put(element, stereotypes);
        }
        stereotypes.add(stereotype);
    }
    
    public void addStereotypeApplications(Collection<StereotypeApplication> stereotypeApplications) {
        for (StereotypeApplication stereotypeApplication: stereotypeApplications) {
            this.addStereotypeApplication(
                    stereotypeApplication.getElement(), 
                    stereotypeApplication.getStereotype());
        }
    }
    
    public boolean hasStereotypeApplication(Element element, Stereotype stereotype) {
        Collection<Stereotype> stereotypes = this.stereotypeApplications.get(element);
        return stereotypes != null && stereotypes.contains(stereotype);
    }
    
    public boolean isStereotypeApplied(Element element, Stereotype stereotype) {
        return this.hasStereotypeApplication(element, stereotype) || 
                element.isStereotypeApplied(stereotype);
    }
    
    public void applyStereotypes() {
        for (Element element: this.stereotypeApplications.keySet()) {
            for (Stereotype stereotype: this.stereotypeApplications.get(element)) {
                element.applyStereotype(stereotype);
            }
        }
    }
    
}
