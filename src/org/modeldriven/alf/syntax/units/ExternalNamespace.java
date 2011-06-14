package org.modeldriven.alf.syntax.units;

import org.modeldriven.alf.syntax.units.impl.ExternalNamespaceImpl;
import org.modeldriven.alf.syntax.units.impl.ImportedMemberImpl;
import org.omg.uml.NamedElement;
import org.omg.uml.Namespace;

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
