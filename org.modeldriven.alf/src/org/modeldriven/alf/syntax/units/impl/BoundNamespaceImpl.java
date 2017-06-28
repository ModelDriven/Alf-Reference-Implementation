/*******************************************************************************
 * Copyright 2017 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.units.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.common.BoundElementReference;
import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.units.BoundNamespace;
import org.modeldriven.alf.syntax.units.Member;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;

public class BoundNamespaceImpl extends NamespaceDefinitionImpl {
    
    private BoundElementReference referent = null;

    public BoundNamespaceImpl(BoundNamespace self) {
        super(self);
    }

    @Override
    public BoundNamespace getSelf() {
        return (BoundNamespace) this.self;
    }
    
    @Override
    public BoundElementReference getReferent() {
        return this.referent;
    }
    
    public void setReferent(BoundElementReference referent) {
        this.referent = referent;
    }
    
    @Override
    public List<Member> getOwnedMember() {
        List<Member> ownedMembers = new ArrayList<Member>();
        for (ElementReference member: 
            this.getSelf().getReferent().getImpl().getOwnedMembers()) {
            ownedMembers.add(ImportedMemberImpl.makeImportedMember(member, false));
        }
        return ownedMembers;
    }
    
    @Override
    public void addOwnedMember(Member ownedMember) {        
    }
    
    @Override
    public void addMember(Member member) {
    }
    
    @Override 
    protected Collection<Member> deriveMember() {
        Collection<Member> members = new ArrayList<Member>();
        for (ElementReference member: 
            this.getSelf().getReferent().getImpl().getMembers()) {
            members.add(ImportedMemberImpl.makeImportedMember(member, 
                    member.getImpl().isImported()));
        }
        return members;        
    }

    @Override
    public NamespaceDefinition getNamespace() {
        return this.getSelf().getReferent().getImpl().getNamespace().getImpl().asNamespace();
    }
    
    @Override
    public QualifiedName getNamespaceName() {
        return this.getNamespace().getImpl().getQualifiedName();
    }

    @Override
    public Boolean isSameKindAs(Member member) {
        return false;
    }
    
    @Override
    public boolean isCompletelyBound() {
        return true;
    }
    
    public static BoundNamespace makeBoundNamespace(BoundElementReference referent) {
        BoundNamespace namespace = new BoundNamespace();
        namespace.setReferent(referent);
        return namespace;
    }

}
