/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.syntax.units.impl;

import java.util.List;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.common.ExternalElementReference;
import org.modeldriven.alf.syntax.units.ExternalNamespace;
import org.modeldriven.alf.syntax.units.Member;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.uml.Namespace;
import org.modeldriven.alf.uml.Package;


public class ExternalNamespaceImpl extends NamespaceDefinitionImpl {

    public ExternalNamespaceImpl(ExternalNamespace self) {
        super(self);
    }
    
    @Override
    public ExternalNamespace getSelf() {
        return (ExternalNamespace) this.self;
    }
    
    @Override
    public Boolean isSameKindAs(Member member) {
        return false;
    }
    
    @Override
    public ElementReference getReferent() {
        Namespace namespace = this.getSelf().getUmlNamespace();
        ExternalElementReference reference = new ExternalElementReference();
        reference.setElement(namespace);
        return reference;
    }

    @Override
    protected boolean allowPackageOnly() {
        return !(this.getSelf().getUmlNamespace() instanceof Package);
    }
    
    @Override
    public ElementReference getNamespaceReference() {
        Namespace namespace = this.getSelf().getUmlNamespace().getNamespace();
        if (namespace == null) {
            return null;
        } else {       
            ExternalElementReference reference = new ExternalElementReference();
            reference.setElement(namespace);
            return reference;
        }
    }
    
    // TODO: Allowing binding of non-Alf templates.
    @Override
    public Member bind(String name,
            NamespaceDefinition namespace,
            boolean isOwnedMember,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        return null;
    }
}
