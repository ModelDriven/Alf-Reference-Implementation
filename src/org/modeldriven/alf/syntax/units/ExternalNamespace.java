/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.syntax.units;

import org.modeldriven.alf.syntax.units.impl.ExternalNamespaceImpl;
import org.modeldriven.alf.syntax.units.impl.ImportedMemberImpl;
import org.modeldriven.alf.uml.NamedElement;
import org.modeldriven.alf.uml.Namespace;

import java.util.ArrayList;

public class ExternalNamespace extends NamespaceDefinition {
    
    private Namespace umlNamespace = null;
    
    public ExternalNamespace(Namespace namespace) {
        this.impl = new ExternalNamespaceImpl(this);
        this.umlNamespace = namespace;
    }
    
    public ExternalNamespaceImpl getImpl() {
        return (ExternalNamespaceImpl)this.impl;
    }
    
    public Namespace getUmlNamespace() {
        return this.umlNamespace;
    }
    
    @Override
    public NamespaceDefinition getNamespace() {
        Namespace namespace = this.getUmlNamespace().getNamespace();
        return namespace == null? null: new ExternalNamespace(namespace);
    }
    
    @Override
    public ArrayList<Member> getOwnedMember() {
        Namespace namespace = this.getUmlNamespace();
        ArrayList<Member> members = new ArrayList<Member>();
        for (NamedElement element: namespace.getOwnedMember()) {
            members.add(ImportedMemberImpl.makeImportedMember(element.getName(), element));
        }
        return members;
    }
    

    @Override
    public ArrayList<Member> getMember() {
        Namespace namespace = this.getUmlNamespace();
        ArrayList<Member> members = new ArrayList<Member>();
        for (NamedElement element: namespace.getMember()) {
            for (String name: namespace.getNamesOfMember(element)) {
                members.add(ImportedMemberImpl.makeImportedMember(name, element));
            }
        }
        return members;
    }

}
