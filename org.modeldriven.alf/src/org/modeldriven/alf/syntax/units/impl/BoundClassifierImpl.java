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

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.common.impl.BoundElementReferenceImpl;
import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.units.BoundClassifier;
import org.modeldriven.alf.syntax.units.ClassifierTemplateParameter;
import org.modeldriven.alf.syntax.units.Member;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.RootNamespace;

public class BoundClassifierImpl extends ClassifierDefinitionImpl {
    
    private ElementReference template = null;
    private List<ElementReference> actual = new ArrayList<ElementReference>();
    
    private ElementReference referent = null; // DERIVED

    public BoundClassifierImpl(BoundClassifier self) {
        super(self);
    }

    @Override
    public BoundClassifier getSelf() {
        return (BoundClassifier) this.self;
    }
    
    public ElementReference getTemplate() {
        return this.template;
    }
    
    public void setTemplate(ElementReference template) {
        this.template = template;
    }
    
    public List<ElementReference> getActual() {
        return this.actual;
    }
    
    public void setActual(List<ElementReference> actual) {
        this.actual = actual;
    }
    
    public void addActual(ElementReference actual) {
        this.actual.add(actual);
    }
    
    @Override
    public ElementReference getReferent() {
        if (this.referent == null) {
            this.setReferent(this.deriveReferent());
        }
        return this.referent;
    }
    
    public void setReferent(ElementReference referent) {
        this.referent = referent;
    }
    
    protected ElementReference deriveReferent() {
        ElementReference template = this.getSelf().getTemplate();        
        return BoundElementReferenceImpl.makeBoundReference(
                template, template.getImpl().getNamespace(), super.getReferent());
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
            members.add(ImportedMemberImpl.makeImportedMember(
                    member, member.getImpl().isImported()));
        }
        return members;        
    }

    @Override
    public NamespaceDefinition getNamespace() {
        return this.getSelf().getTemplate().getImpl().getNamespace().getImpl().asNamespace();
    }
    
    @Override
    public QualifiedName getNamespaceName() {
        return this.getNamespace().getImpl().getQualifiedName();
    }
    
    @Override
    public List<ElementReference> getTemplateParameters() {
        return this.getSelf().getTemplate().getImpl().getTemplateParameters();
    }
    
    @Override
    public List<ElementReference> getTemplateActuals() {
        return this.getSelf().getActual();
    }

    @Override
    public Boolean isSameKindAs(Member member) {
        return false;
    }
    
    /**
     * A completely bound classifier is one that has no unbound template
     * parameters and all of whose bound template parameters are bound to
     * arguments that are themselves completely bound.
     */
    @Override
    public boolean isCompletelyBound() {
        for (ElementReference actual: this.getSelf().getActual()) {
            if (!actual.getImpl().isCompletelyBound()) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof BoundClassifier || 
              object instanceof BoundClassifierImpl)) {
            return false;
        } else {
            BoundClassifierImpl other = object instanceof BoundClassifier? 
                    ((BoundClassifier)object).getImpl(): 
                    (BoundClassifierImpl)object;
            return this.getTemplate().getImpl().equals(other.getTemplate()) && 
                   this.getName().equals(other.getName());
        }
    }
    
    public static BoundClassifier makeBoundClassifier(
            ElementReference template, List<ElementReference> actuals) {
        BoundClassifier classifier = new BoundClassifier();
        classifier.getImpl().setExactName(
                RootNamespace.getRootScope().makeBoundElementName(template, actuals));
        classifier.setTemplate(template);
        classifier.setActual(actuals);
        return classifier;
    }

}
