/*******************************************************************************
 * Copyright 2017, 2018 Data Access Technologies, Inc. (Model Driven Solutions)
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
import org.modeldriven.alf.syntax.common.impl.ElementReferenceImpl;
import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.units.BoundClassifier;
import org.modeldriven.alf.syntax.units.Member;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.RootNamespace;
import org.modeldriven.alf.uml.Element;

public class BoundClassifierImpl extends ClassifierDefinitionImpl {
    
    private final static List<BoundClassifier> boundClassifiers = 
            new ArrayList<BoundClassifier>();
    
    private ElementReference template = null;
    private List<ElementReference> actual = new ArrayList<ElementReference>();
    private ElementReference effectiveBoundElement = null;
    private boolean existingBoundElementNotChecked = true; 
        
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
    
    public ElementReference getEffectiveBoundElement() {
        if (this.effectiveBoundElement == null && this.existingBoundElementNotChecked) {
            this.setEffectiveBoundElement(this.getExistingBoundElement());
            this.existingBoundElementNotChecked = false;
        }
        return this.effectiveBoundElement;           
    }
    
    public void setEffectiveBoundElement(ElementReference effectiveBoundElement) {
        this.effectiveBoundElement = effectiveBoundElement;
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
    
    protected ElementReference getExistingBoundElement() {
        BoundClassifier self = this.getSelf();
        return RootNamespace.getRootScope().getExistingBoundElement(
                self.getTemplate(), self.getActual());        
    }
    
    protected ElementReference deriveReferent() {
        ElementReference template = this.getSelf().getTemplate();        
        return BoundElementReferenceImpl.makeBoundReference(
                template, template.getImpl().getNamespace(), super.getReferent());
    }
    
    @Override
    public List<Member> getOwnedMember() {
        List<Member> ownedMembers = new ArrayList<Member>();
        for (ElementReference member: this.getSelf().getReferent().getImpl().getOwnedMembers()) {
            if (!member.getImpl().isClassifierTemplateParameter()) {
                ownedMembers.add(ImportedMemberImpl.makeImportedMember(member, false));
            }
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
        for (ElementReference member: this.getSelf().getReferent().getImpl().getMembers()) {
            if (!member.getImpl().isClassifierTemplateParameter()) {
                members.add(ImportedMemberImpl.makeImportedMember(
                        member, member.getImpl().isImported()));
            }
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
    public Element getUml() {
        ElementReference boundElement = this.getEffectiveBoundElement();
        return boundElement == null? super.getUml(): boundElement.getImpl().getUml();
    }

    @Override
    public Boolean isSameKindAs(Member member) {
        return false;
    }
    
    @Override
    public List<Member> resolveInScope(String name, boolean classifierOnly) {
        List<Member> members = new ArrayList<Member>();
        for (ElementReference member: this.getReferent().getImpl().resolveInScope(name, classifierOnly)) {
            members.add(ImportedMemberImpl.makeImportedMember(member, member.getImpl().isImported()));
        }
        return members;
    }
    
    /**
     * A completely bound classifier is one that has no unbound template
     * parameters and all of whose bound template parameters are bound to
     * arguments that are themselves completely bound.
     */
    @Override
    public boolean isCompletelyBound() {
        for (ElementReference actual: this.getSelf().getActual()) {
            if (actual != null && !actual.getImpl().isCompletelyBound()) {
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
    
    @Override
    public ElementReference getContext() {
        return this.getReferent().getImpl().getContext();
    }
    
    public static BoundClassifier makeBoundClassifier(
            ElementReference template, List<ElementReference> actuals) {
        BoundClassifier classifier = new BoundClassifier();
        classifier.getImpl().setExactName(
                RootNamespace.getRootScope().makeBoundElementName(template, actuals));
        classifier.setTemplate(template);
        classifier.setActual(actuals);
        if (!template.getImpl().isCollectionFunction()) {
            ElementReference existingElement = 
                    RootNamespace.getRootScope().getExistingBoundElement(template, actuals);
            if (existingElement != null) {
                classifier.setEffectiveBoundElement(existingElement);
            } else {
                addBoundClassifier(classifier);
            }
        }
        return classifier;
    }

    public static void clearBoundClassifiers() {
        boundClassifiers.clear();
    }
    
    public static void saveBoundClassifiers(List<BoundClassifier> boundClassifierList) {
        boundClassifierList.addAll(boundClassifiers);
    }
    
    public static void restoreBoundClassifiers(List<BoundClassifier> savedBoundClassifierList) {
        clearBoundClassifiers();
        boundClassifiers.addAll(savedBoundClassifierList);
    }
    
    public static void addBoundClassifier(BoundClassifier boundClassifier) {
        boundClassifiers.add(boundClassifier);
    }
    
    public static void makeBoundElements() {
        ElementReferenceImpl.clearTemplateBindings();
        
        // NOTE: It is possible that new bound classifiers are created during
        // the generation of the effective bound elements of previous bound
        // classifiers.
        for (int i = 0; i < boundClassifiers.size(); i++) {
            BoundClassifier boundClassifier = boundClassifiers.get(i);
            if (boundClassifier.getEffectiveBoundElement() == null) {
                ElementReference template = boundClassifier.getTemplate();
                boundClassifier.setEffectiveBoundElement(
                        RootNamespace.getRootScope().getEffectiveBoundElement(
                            template, 
                            template.getImpl().getTemplateParameters(), 
                            boundClassifier.getActual()));
            }
        }
    }
    
}
