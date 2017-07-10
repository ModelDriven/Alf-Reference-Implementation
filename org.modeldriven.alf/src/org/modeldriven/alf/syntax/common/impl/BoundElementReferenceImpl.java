/*******************************************************************************
 * Copyright 2017 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.common.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.modeldriven.alf.syntax.common.BoundElementReference;
import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.common.SyntaxElement;
import org.modeldriven.alf.syntax.units.Member;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.impl.BoundNamespaceImpl;
import org.modeldriven.alf.syntax.units.impl.ImportedMemberImpl;
import org.modeldriven.alf.uml.Element;

public class BoundElementReferenceImpl extends ElementReferenceImpl {
    
    private ElementReference referent = null;
    private ElementReference namespace = null;
    private ElementReference templateBinding = null;
    
    public BoundElementReferenceImpl(BoundElementReference self) {
        super(self);
    }

    @Override
    public BoundElementReference getSelf() {
        return (BoundElementReference) this.self;
    }

    @Override
    public String toString(boolean includeDerived) {
        ElementReference referent = this.getReferent();
        ElementReference namespace = this.getNamespace();
        ElementReference templateBinding = this.getTemplateBinding();
        return "BoundElementReference" + 
            " referent:" + (referent==null? null: referent.toString(includeDerived)) +
            " namespace:" + (namespace==null? null: namespace.toString(includeDerived)) +
            " templateBinding:" + (templateBinding==null? null: templateBinding.toString(includeDerived));
    }
    
    @Override
    public ElementReference getReferent() {
        return this.referent;
    }
    
    public void setReferent(ElementReference referent) {
        this.referent = referent;
    }

    @Override
    public ElementReference getNamespace() {
        if (this.namespace == null) {
            this.namespace = this.getSelf().getReferent().getImpl().getNamespace();
            if (this.namespace != null && !this.isTemplateBinding()) {
                if (this.namespace.getImpl().isTemplateBinding()) {
                    this.namespace = this.namespace.getImpl().getTemplate();
                }
                this.namespace = makeBoundReference(this.namespace, null, this.getTemplateBinding());
            }
        }
        return this.namespace;
    }
    
    public void setNamespace(ElementReference namespace) {
        this.namespace = namespace;
    }

    @Override
    public ElementReference getTemplateBinding() {
        return this.templateBinding;
    }
    
    public void setTemplateBinding(ElementReference templateBinding) {
        this.templateBinding = templateBinding;
    }

    @Override
    public SyntaxElement getAlf() {
        ElementReference boundElement = this.getEffectiveBoundElement();
        return boundElement == null? 
                ImportedMemberImpl.makeImportedMember(this.getSelf(), this.isImported()):
                boundElement.getImpl().getAlf();
    }

    @Override
    public Element getUml() {
        ElementReference boundElement = this.getEffectiveBoundElement();
        return boundElement == null? null: boundElement.getImpl().getUml();
    }
    
    @Override
    public boolean isNamespace() {
        return this.getSelf().getReferent().getImpl().isNamespace();
    }

    @Override
    public boolean isPackage() {
        return this.getSelf().getReferent().getImpl().isPackage();
    }

    @Override
    public boolean isProfile() {
        return this.getSelf().getReferent().getImpl().isProfile();
    }

    @Override
    public boolean isClassifier() {
        return this.getSelf().getReferent().getImpl().isClassifier();
    }

    @Override
    public boolean isAbstractClassifier() {
        return this.getSelf().getReferent().getImpl().isAbstractClassifier();
    }

    @Override
    public boolean isAssociation() {
        return this.getSelf().getReferent().getImpl().isAssociation();
    }

    @Override
    public boolean isClass() {
        return this.getSelf().getReferent().getImpl().isClass();
    }

    @Override
    public boolean isClassOnly() {
        return this.getSelf().getReferent().getImpl().isClassOnly();
    }

    @Override
    public boolean isActiveClass() {
        return this.getSelf().getReferent().getImpl().isActiveClass();
    }

    @Override
    public boolean isDataType() {
        return this.getSelf().getReferent().getImpl().isDataType();
    }

    @Override
    public boolean isBehavior() {
        return this.getSelf().getReferent().getImpl().isBehavior();
    }

    @Override
    public boolean isActivity() {
        return this.getSelf().getReferent().getImpl().isActivity();
    }

    @Override
    public boolean isMethod() {
        return this.getSelf().getReferent().getImpl().isMethod();
     }
    
    @Override
    public boolean isEnumeration() {
        return this.getSelf().getReferent().getImpl().isEnumeration();
    }

    @Override
    public boolean isPrimitive() {
        return this.getSelf().getReferent().getImpl().isPrimitive();
    }

    @Override
    public boolean isSignal() {
        return this.getSelf().getReferent().getImpl().isSignal();
    }

    @Override
    public boolean isStereotype() {
        return this.getSelf().getReferent().getImpl().isStereotype();
    }

    @Override
    public boolean isNamedElement() {
        return true;
    }
    
    @Override
    public boolean isPackageableElement() {
        return this.getSelf().getReferent().getImpl().isPackageableElement();
    }
    
    @Override
    public boolean isFeature() {
        return this.getSelf().getReferent().getImpl().isFeature();
    }
    
    @Override
    public boolean isOrdered() {
        return this.getSelf().getReferent().getImpl().isOrdered();
    }

    @Override
    public boolean isUnique() {
        return this.getSelf().getReferent().getImpl().isUnique();
    }

    @Override
    public boolean isOperation() {
        return this.getSelf().getReferent().getImpl().isOperation();
    }

    @Override
    public boolean isConstructor() {
        return this.getSelf().getReferent().getImpl().isConstructor();
    }
    
    @Override
    public boolean isDestructor() {
        return this.getSelf().getReferent().getImpl().isDestructor();
    }
    
    @Override
    public boolean isReception() {
        return this.getSelf().getReferent().getImpl().isReception();
    }

    @Override
    public boolean isEnumerationLiteral() {
        return this.getSelf().getReferent().getImpl().isEnumerationLiteral();
    }

    @Override
    public boolean isTemplate() {
        ElementReference referent = this.isTemplateBinding()?
                this.getSelf().getTemplateBinding():
                this.getSelf().getReferent();
        return referent.getImpl().isTemplate();
    }
    
    @Override
    public boolean isParameteredElement() {
        return false;
    }
    
    @Override
    public boolean isClassifierTemplateParameter() {
        return false;
    }
    
    @Override
    public boolean isCompletelyBound() {
        return this.getSelf().getTemplateBinding().getImpl().isCompletelyBound();
    }

    @Override
    public boolean isTemplateBinding() {
        return this.getSelf().getTemplateBinding().getImpl().getTemplate().getImpl().
                equals(this.getSelf().getReferent());
    }
    
    @Override
    public boolean isProperty() {
        return this.getSelf().getReferent().getImpl().isProperty();
    }
    
    @Override
    public boolean isAssociationEnd() {       
        return this.getSelf().getReferent().getImpl().isAssociationEnd();
    }

    @Override
    public boolean isParameter() {
        return this.getSelf().getReferent().getImpl().isParameter();
    }

    @Override
    public boolean isImported() {
        return this.getSelf().getReferent().getImpl().isImported();
    }

    @Override
    public NamespaceDefinition asNamespace() {
        return this.isTemplateBinding()? this.getSelf().getTemplateBinding().getImpl().asNamespace():
               this.isNamespace()? BoundNamespaceImpl.makeBoundNamespace(this.getSelf()):
               null;
    }

    @Override
    public boolean isInNamespace(NamespaceDefinition namespace) {
        return namespace.getImpl().getReferent().getImpl().equals(this.getNamespace());
    }
    
    @Override
    public boolean hasReceptionFor(ElementReference signal) {
        return this.getSelf().getReferent().getImpl().hasReceptionFor(signal);
    }
    
    @Override
    public Collection<ElementReference> parents() {
        return this.makeBoundReferences(this.getSelf().getReferent().getImpl().parents());
    }

    @Override
    public Collection<ElementReference> allParents() {
        return this.makeBoundReferences(this.getSelf().getReferent().getImpl().allParents());
    }

    @Override
    public String getName() {
        return this.getSelf().getReferent().getImpl().getName();
    }
    
    @Override
    public String getVisibility() {
        return this.getSelf().getReferent().getImpl().getVisibility();
    }

    @Override
    public List<ElementReference> getOwnedMembers() {
        List<ElementReference> members = new ArrayList<ElementReference>();
        for (ElementReference member: this.getSelf().getReferent().getImpl().getOwnedMembers()) {
            if (!member.getImpl().isClassifierTemplateParameter()) {
                members.add(makeBoundReference(member));
            }
        }
        return members;
    }

    @Override
    public List<ElementReference> getMembers() {
        List<ElementReference> members = new ArrayList<ElementReference>();
        for (ElementReference member: this.getSelf().getReferent().getImpl().getMembers()) {
            if (!member.getImpl().isClassifierTemplateParameter()) {
                members.add(makeBoundReference(member));
            }
        }
        return members;
    }

    @Override
    public List<Member> getPublicMembers(Collection<ElementReference> excluded) {
        return this.makeBoundMembers(this.getSelf().getReferent().getImpl().getPublicMembers(excluded));
    }

    @Override
    public List<ElementReference> getAttributes() {
        return this.makeBoundReferences(this.getSelf().getReferent().getImpl().getAttributes());
    }
    
    @Override
    public List<ElementReference> getAssociationEnds() {
        return this.makeBoundReferences(this.getSelf().getReferent().getImpl().getAssociationEnds());
    }
    
    @Override
    public List<Member> getInheritableMembers() {
        return this.makeBoundMembers(this.getSelf().getReferent().getImpl().getInheritableMembers());
    }

    @Override
    public List<ElementReference> getParameters() {
        return this.makeBoundReferences(this.getSelf().getReferent().getImpl().getParameters());
    }
    
    @Override
    public ElementReference getReturnParameter() {
        return this.makeBoundReference(this.getSelf().getReferent().getImpl().getReturnParameter());
    }
    
    @Override
    public List<ElementReference> getMethods() {
        return this.makeBoundReferences(this.getSelf().getReferent().getImpl().getMethods());
    }
    
    @Override
    public ElementReference getSpecification() {
        return this.makeBoundReference(this.getSelf().getReferent().getImpl().getSpecification());
    }
    
    @Override
    public List<ElementReference> getTemplateParameters() {
        return new ArrayList<ElementReference>();
    }
    
    @Override
    public List<ElementReference> getTemplateActuals() {
        return this.isTemplateBinding()? 
            this.getSelf().getTemplateBinding().getImpl().getTemplateActuals():
            new ArrayList<ElementReference>();
    }
    
    @Override
    public List<ElementReference> getParameteredElements() {
        return new ArrayList<ElementReference>();
    }
    
    @Override
    public ElementReference getParameteredElement() {
        return null;
    }
    
    @Override
    public ElementReference getTemplate() {
        return this.isTemplateBinding()? this.getSelf().getReferent(): null;
    }
    
    @Override
    public Collection<ElementReference> getConstrainingClassifiers() {
        return new ArrayList<ElementReference>();
    }

    @Override
    public ElementReference getType() {
        ElementReference type = this.getSelf().getReferent().getImpl().getType();
        ElementReference templateBinding = this.getSelf().getTemplateBinding();
        if ((this.isConstructor() || 
             this.isParameter() && "return".equals(this.getDirection()) && 
                this.getNamespace().getImpl().isConstructor()) && 
                templateBinding.getImpl().getTemplate().getImpl().equals(type)) {
            // This handles the special case of a constructor of a template class
            // that has that class directly as its return type, rather than a
            // template binding for it.
            type = type.getImpl().bind(templateBinding.getImpl().getTemplateActuals());
        } else {
            type = this.makeBoundReference(type);
        }
        return type;
    }

    @Override
    public ElementReference getAssociation() {
        return this.isAssociationEnd()? this.getSelf().getNamespace(): null;
    }
    
    public Integer getLower() {
        return this.getSelf().getReferent().getImpl().getLower();
    }

    public Integer getUpper() {
        return this.getSelf().getReferent().getImpl().getUpper();
    }

    @Override
    public String getDirection() {
        return this.getSelf().getReferent().getImpl().getDirection();

    }
    
    @Override
    public ElementReference getClassifierBehavior() {
        return this.getSelf().getReferent().getImpl().getClassifierBehavior();
    }
    
    @Override
    public Collection<ElementReference> getRedefinedElements() {
        return this.getSelf().getReferent().getImpl().getRedefinedElements();
    }
    
    @Override
    public ElementReference getSignal() {
        return this.makeBoundReference(this.getSelf().getReferent().getImpl().getSignal());
    }
    
    @Override
    public ElementReference getActiveClass() {
        return this.isActivity()? this.getSelf(): null;
    }
    
    @Override
    public ElementReference getContext() {
        return this.makeBoundReference(this.getSelf().getReferent().getImpl().getContext());
    }
    
    @Override
    public Collection<Class<?>> getStereotypeMetaclasses() {
        return this.getSelf().getReferent().getImpl().getStereotypeMetaclasses();
    }
    
    @Override
    public Class<?> getUMLMetaclass() {
        return this.getSelf().getReferent().getImpl().getUMLMetaclass();
     }
    
    @Override
    public ElementReference getEffectiveBoundElement(BoundElementReference boundElement) {
        return null;
    }


    @Override
    public boolean equals(Object object) {
        if (!(object instanceof BoundElementReference || 
                object instanceof BoundElementReferenceImpl)) {
            return false;
        } else {
            BoundElementReferenceImpl other = object instanceof BoundElementReference? 
                    ((BoundElementReference)object).getImpl(): 
                    (BoundElementReferenceImpl)object;
            return this.getReferent().getImpl().equals(other.getReferent()) &&
                   this.getTemplateBinding().getImpl().equals(other.getTemplateBinding());
        }
    }

    @Override
    public boolean conformsTo(ElementReference type) {
        if (type == null || this.equals(type)) {
            return true;
        } else {
            for (ElementReference parent: this.parents()) {
                if (parent.getImpl().conformsTo(type)) {
                    return true;
                }
            }
            return false;
        }
    }
    
    public ElementReference getEffectiveBoundElement() {
        BoundElementReference self = this.getSelf();
        if (self.getImpl().isTemplateBinding()) {
            return self.getTemplateBinding().getImpl().getEffectiveBoundElement();
        } else {
            ElementReference namespace = self.getNamespace();
            if (namespace != null) {
                namespace = namespace.getImpl().getEffectiveBoundElement();
            }
            return namespace == null? null: namespace.getImpl().getEffectiveBoundElement(self);
        }
    }
    
    private ElementReference makeBoundReference(ElementReference element) {
        BoundElementReference self = this.getSelf();
        ElementReference reference = element;
        if (element != null && element.getImpl().isNamedElement()) {
            ElementReference templateBinding = self.getTemplateBinding();
            if (element.getImpl().isTemplateBinding()) {
                List<ElementReference> actuals = 
                        this.makeBoundReferences(element.getImpl().getTemplateActuals());
                reference = element.getImpl().getTemplate().getImpl().bind(actuals);
            } else if (element.getImpl().isParameteredElement()) {
                List<ElementReference> parameters = 
                        templateBinding.getImpl().getTemplate().getImpl().getParameteredElements();
                for (int i = 0; i < parameters.size(); i++) {
                    if (parameters.get(i).getImpl().equals(element)) {
                        reference = templateBinding.getImpl().getTemplateActuals().get(i);
                        break;
                    }
                }
            } else {
                ElementReference elementTemplateBinding = element.getImpl().getTemplateBinding();
                if (elementTemplateBinding == null) {
                    if (self.getReferent().getImpl().equals(element.getImpl().getNamespace())) {
                        reference = makeBoundReference(element, self, templateBinding);
                    } else if (element.getImpl().isContainedIn(
                            templateBinding.getImpl().getTemplate().getImpl().getMembers())) {
                        reference = makeBoundReference(element, null, templateBinding);
                    }
                } else if (!elementTemplateBinding.getImpl().equals(templateBinding)) {
                    List<ElementReference> actuals = 
                            this.makeBoundReferences(elementTemplateBinding.getImpl().getTemplateActuals());
                    reference = makeBoundReference(element.getImpl().getReferent(), null, 
                            elementTemplateBinding.getImpl().getTemplate().getImpl().bind(actuals));
                }
            }
        }
        return reference;
    }
    
    public static BoundElementReference makeBoundReference
        (ElementReference referent, ElementReference namespace, ElementReference templateBinding) {
        BoundElementReference reference = new BoundElementReference();
        reference.setReferent(referent);
        reference.setNamespace(namespace);
        reference.setTemplateBinding(templateBinding);
        return reference;
    }
    
    private List<ElementReference> makeBoundReferences(Collection<ElementReference> elements) {
        List<ElementReference> list = new ArrayList<ElementReference>();
        for (ElementReference element: elements) {
            list.add(this.makeBoundReference(element));
        }
        return list;
    }
    
    private List<Member> makeBoundMembers(Collection<Member> members) {
        List<Member> list = new ArrayList<Member>();
        for (Member member: members) {
            list.add(ImportedMemberImpl.makeImportedMember(
                    this.makeBoundReference(member.getImpl().getReferent()),
                    member.getImpl().isImported()));
        }
        return list;
    }

}
