/*******************************************************************************
 * Copyright 2012-2017 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.common.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.AssignableElement;
import org.modeldriven.alf.syntax.units.*;
import org.modeldriven.alf.syntax.units.impl.ActivityDefinitionImpl;
import org.modeldriven.alf.syntax.units.impl.OperationDefinitionImpl;
import org.modeldriven.alf.uml.Element;

/**
 * A direct reference to a UML model element.
 **/

public class InternalElementReferenceImpl extends ElementReferenceImpl {

    private SyntaxElement element = null;
    
    private Collection<ElementReference> allParents = null;

	public InternalElementReferenceImpl(InternalElementReference self) {
		super(self);
	}

	@Override
	public InternalElementReference getSelf() {
		return (InternalElementReference) this.self;
	}

    @Override
    public String toString(boolean includeDerived) {
        SyntaxElement element = this.getElement();
        return "InternalElementReference " + 
            (element==null? null: element.toString(includeDerived));
    }

    public SyntaxElement getElement() {
        return this.element;
    }

    public void setElement(SyntaxElement element) {
        this.element = element;
    }

    @Override
    public SyntaxElement getAlf() {
        return this.getSelf().getElement();
    }

    @Override
    public Element getUml() {
        SyntaxElement element = this.getSelf().getElement();
        return element == null? null: element.getImpl().getUml();
    }
    
    @Override
    public boolean isNamespace() {
        return this.getSelf().getElement() instanceof NamespaceDefinition;
    }

    @Override
    public boolean isPackage() {
        return this.getSelf().getElement() instanceof PackageDefinition;
    }

    @Override
    public boolean isProfile() {
        SyntaxElement element = this.getSelf().getElement();
        return element instanceof Member &&
                ((Member)element).getImpl().isProfile();
    }

    @Override
    public boolean isClassifier() {
        return this.getSelf().getElement() instanceof ClassifierDefinition;
    }

    @Override
    public boolean isAbstractClassifier() {
        return this.isClassifier() &&
                ((ClassifierDefinition)this.getSelf().getElement()).getIsAbstract();
    }

    @Override
    public boolean isAssociation() {
        return this.getSelf().getElement() instanceof AssociationDefinition;
    }

    @Override
    public boolean isClass() {
        return this.getSelf().getElement() instanceof ClassDefinition;
    }

    @Override
    public boolean isClassOnly() {
        return this.getSelf().getElement() instanceof ClassDefinition;
    }

    @Override
    public boolean isActiveClass() {
        return this.getSelf().getElement() instanceof ActiveClassDefinition;
    }

    @Override
    public boolean isDataType() {
        return this.getSelf().getElement() instanceof DataTypeDefinition;
    }

    @Override
    public boolean isBehavior() {
        return this.isActivity();
    }

    @Override
    public boolean isActivity() {
        return this.getSelf().getElement() instanceof ActivityDefinition;
    }

    @Override
    public boolean isEnumeration() {
        return this.getSelf().getElement() instanceof EnumerationDefinition;
    }

    @Override
    public boolean isPrimitive() {
        return this.isDataType() && 
               ((DataTypeDefinition)this.getSelf().getElement()).getIsPrimitive();
    }

    @Override
    public boolean isSignal() {
        return this.getSelf().getElement() instanceof SignalDefinition;
    }

    @Override
    public boolean isStereotype() {
        SyntaxElement element = this.getSelf().getElement();
        return element instanceof Member &&
                ((Member)element).getImpl().isStereotype();
    }

    @Override
    public boolean isNamedElement() {
        return this.getSelf().getElement() instanceof Member;
    }
    
   @Override
    public boolean isPackageableElement() {
        SyntaxElement element = this.getSelf().getElement();
        return element instanceof Member &&
                !((Member)element).getIsFeature();
    }
    
    @Override
    public boolean isFeature() {
        SyntaxElement element = this.getSelf().getElement();
        return element instanceof Member && 
                ((Member)element).getIsFeature();
    }
    
    @Override
    public boolean isOrdered() {
        SyntaxElement element = this.getSelf().getElement();
        return element instanceof TypedElementDefinition &&
            ((TypedElementDefinition)element).getIsOrdered();
    }

    @Override
    public boolean isUnique() {
        SyntaxElement element = this.getSelf().getElement();
        return element instanceof TypedElementDefinition &&
            !((TypedElementDefinition)element).getIsNonunique();
    }

    @Override
    public boolean isOperation() {
        return this.getSelf().getElement() instanceof OperationDefinition;
    }

    @Override
    public boolean isConstructor() {
        return this.isOperation() && ((OperationDefinition)this.getSelf().getElement()).getIsConstructor();
    }
    
    @Override
    public boolean isReception() {
        SyntaxElement element = this.getSelf().getElement();
        return  element instanceof ReceptionDefinition ||
                element instanceof SignalReceptionDefinition;
    }

    @Override
    public boolean isDestructor() {
        return this.isOperation() && ((OperationDefinition)this.getSelf().getElement()).getIsDestructor();
    }
    
    @Override
    public boolean isEnumerationLiteral() {
        return this.getSelf().getElement() instanceof EnumerationLiteralName;        
    }

    @Override
    public boolean isTemplate() {
        return this.isClassifier() && 
                ((ClassifierDefinition)this.getSelf().getElement()).getImpl().isTemplate();
    }
    
    @Override
    public boolean isClassifierTemplateParameter() {
        return this.getSelf().getElement() instanceof ClassifierTemplateParameter;
    }
    
    @Override
    public boolean isParameteredElement() {
        return this.isClassifierTemplateParameter();
    }
    
    @Override
    public boolean isTemplateBinding() {
        return this.getSelf().getElement() instanceof BoundClassifier;
    }
    
    @Override
    public boolean isCompletelyBound() {
        return this.isClassifier() &&
                ((ClassifierDefinition)this.getSelf().getElement()).getImpl().
                    isCompletelyBound();
    }

    @Override
    public boolean isProperty() {
        return this.getSelf().getElement() instanceof PropertyDefinition;
    }
    
    @Override
    public boolean isAssociationEnd() {       
        return this.isProperty() && 
                ((PropertyDefinition)this.getSelf().getElement()).getNamespace() 
                    instanceof AssociationDefinition;
    }

    @Override
    public boolean isParameter() {
        return this.getSelf().getElement() instanceof FormalParameter;
    }

    @Override
    public boolean isImported() {
        SyntaxElement element = this.getSelf().getElement();
        return element instanceof Member && ((Member)element).getImpl().isImported();
    }

    @Override
    public NamespaceDefinition asNamespace() {
        return !this.isNamespace()? null: 
            (NamespaceDefinition)this.getSelf().getElement();
    }

    @Override
    public boolean isInNamespace(NamespaceDefinition namespace) {
        SyntaxElement element = this.getSelf().getElement();
        if (!(element instanceof Member)) {
            return false;
        } else {
            NamespaceDefinition elementNamespace = ((Member)element).getNamespace();
            return elementNamespace == namespace ||
                        elementNamespace instanceof ExternalNamespace &&
                        namespace instanceof ExternalNamespace &&
                        ((ExternalNamespace)elementNamespace).getUmlNamespace() == 
                            ((ExternalNamespace)namespace).getUmlNamespace();
        }
    }
    
    @Override
    public boolean hasReceptionFor(ElementReference signal) {
        if (this.isClass()) {
           for (ElementReference member: this.getMembers()) {
                if (member.getImpl().isReception() && 
                        signal.getImpl().equals(member.getImpl().getSignal())) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public Collection<ElementReference> parents() {
        return !this.isClassifier()? new HashSet<ElementReference>():
            ((ClassifierDefinition)this.getSelf().getElement()).getSpecializationReferent();
    }

    @Override
    public Collection<ElementReference> allParents() {
        if (this.allParents == null) {
            this.allParents = !this.isClassifier()?
                    new HashSet<ElementReference>():
                    this.allParents(new HashSet<ElementReference>());
        }
        return this.allParents;
    }

    // This will work even if there are (illegal) cyclic generalization relationships.
    private Collection<ElementReference> allParents(Collection<ElementReference> alreadySeen) {
        Collection<ElementReference> parents = 
                new HashSet<ElementReference>(this.parents());
        parents.removeAll(alreadySeen);
        Set<ElementReference> allParents = new HashSet<ElementReference>(parents);
        for (ElementReference parent: parents) {
            alreadySeen.add(parent);
            allParents.addAll(
                    parent instanceof InternalElementReference?
                            ((InternalElementReferenceImpl)parent.getImpl()).allParents(alreadySeen):
                            parent.getImpl().allParents());
        }
        return allParents;
    }

    @Override
    public String getName() {
        if (this.isAny()) {
            return "any";
        } else {
            SyntaxElement element = this.getSelf().getElement();
            return element instanceof Member? ((Member)element).getName(): null;
        }
    }
    
    @Override
    public String getVisibility() {
        SyntaxElement element = this.getSelf().getElement();
        return !(element instanceof Member)? null: ((Member)element).getVisibility();
    }

    @Override
    public List<ElementReference> getOwnedMembers() {
        List<ElementReference> members = new ArrayList<ElementReference>();
        if (this.isNamespace()) {
            for (Member member: ((NamespaceDefinition)this.getSelf().getElement()).getOwnedMember()) {
                members.add(member.getImpl().getReferent());
            }
        }
        return members;
    }

    @Override
    public List<ElementReference> getMembers() {
        List<ElementReference> members = new ArrayList<ElementReference>();
        if (this.isNamespace()) {
            for (Member member: ((NamespaceDefinition)this.getSelf().getElement()).getMember()) {
                members.add(member.getImpl().getReferent());
            }
        }
        return members;
    }

    @Override
    public List<Member> getPublicMembers(Collection<ElementReference> excluded) {
        return !this.isPackage()? new ArrayList<Member>():
            ((PackageDefinition)this.getSelf().getElement()).getImpl().getPublicMembers(excluded);
    }

    @Override
    public List<ElementReference> getAttributes() {
        List<ElementReference> attributes = new ArrayList<ElementReference>();
        if (this.isClassifier()) {
            for (Member member: ((ClassifierDefinition)this.getSelf().getElement()).getMember()) {
                if (member instanceof PropertyDefinition) {
                    attributes.add(member.getImpl().getReferent());
                }
            }
        }
        return attributes;
    }
    
    @Override
    public List<ElementReference> getAssociationEnds() {
        return !this.isAssociation()? new ArrayList<ElementReference>(): 
                    this.getAttributes();
    }
    
    @Override
    public List<Member> getInheritableMembers() {
        return !this.isClassifier()? new ArrayList<Member>():
                ((ClassifierDefinition)this.getSelf().getElement()).getImpl().
                    getInheritableMembers();
    }

    @Override
    public List<ElementReference> getParameters() {
        return this.isBehavior() || this.isOperation()?
                ((NamespaceDefinition)this.getSelf().getElement()).getImpl().getParameters():
                new ArrayList<ElementReference>();
    }
    
    @Override
    public ElementReference getReturnParameter() {
        return this.isBehavior() || this.isOperation()?
                ((NamespaceDefinition)this.getSelf().getElement()).getImpl().getReturnParameter():
                null;
    }
    
    @Override
    public List<ElementReference> getMethods() {
        List<ElementReference> methods = new ArrayList<ElementReference>();
        if (this.isOperation()) {
            OperationDefinition definition = 
                    (OperationDefinition)this.getSelf().getElement();
            if (!definition.getIsAbstract()) {
                UnitDefinition subunit = definition.getSubunit();
                methods.add(subunit == null? definition.getImpl().getReferent(): 
                    subunit.getDefinition().getImpl().getReferent());
            }
        }
        return methods;
    }
    
    @Override
    public ElementReference getSpecification() {
        if (this.isOperation()) {
            return this.getSelf();
        } else if (this.isBehavior()) {
            Member stub = ((NamespaceDefinition)this.getSelf().getElement()).getImpl().getStub();
            return stub == null? null: stub.getImpl().getReferent();
        } else {
            return null;
        }
    }
    
    @Override
    public List<ElementReference> getTemplateParameters() {
        return !this.isClassifier()? new ArrayList<ElementReference>(): 
            ((ClassifierDefinition)this.getSelf().getElement()).getImpl().getTemplateParameters();
    }
    
    @Override
    public List<ElementReference> getTemplateActuals() {
        return !this.isClassifier()? new ArrayList<ElementReference>():
            ((ClassifierDefinition)this.getSelf().getElement()).getImpl().
                getTemplateActuals();
    }
    
    @Override
    public List<ElementReference> getParameteredElements() {
        return this.getTemplateParameters();
    }
    
    @Override
    public ElementReference getParameteredElement() {
        return this.isClassifierTemplateParameter()? this.getSelf(): null;
    }
    
    @Override
    public ElementReference getTemplate() {
        SyntaxElement element = this.getSelf().getElement();
        return !(element instanceof BoundClassifier)? null:
            ((BoundClassifier)element).getTemplate();
    }
    
    @Override
    public Collection<ElementReference> getConstrainingClassifiers() {
        return this.isClassifierTemplateParameter()? 
                this.parents():
                new ArrayList<ElementReference>();
    }
    
    @Override
    public ElementReference getTemplateBinding() {
        InternalElementReference self = this.getSelf();
        return self.getElement() instanceof BoundClassifier? self: null;
    }

    @Override
    public ElementReference getType() {
        return this.isProperty() || this.isParameter()? 
                    ((TypedElementDefinition)this.getSelf().getElement()).getType():
               this.isOperation()?
                    ((OperationDefinition)this.getSelf().getElement()).getImpl().getType():
               this.isBehavior()?
                    ((ActivityDefinition)this.getSelf().getElement()).getImpl().getType():
               this.isEnumerationLiteral()?
                    ((EnumerationLiteralName)this.getSelf().getElement()).
                        getNamespace().getImpl().getReferent():
               null;
    }

    @Override
    public ElementReference getAssociation() {
        return !this.isAssociationEnd()? null:
                    ((Member)this.getSelf().getElement()).
                        getNamespace().getImpl().getReferent();
    }
    
    public Integer getLower() {
        SyntaxElementImpl element = this.getSelf().getElement().getImpl();
        return element instanceof AssignableElement? ((AssignableElement)element).getLower():
               this.isOperation()? ((OperationDefinitionImpl)element).getLower():
               this.isBehavior()? ((ActivityDefinitionImpl)element).getLower():
               0;
    }

    public Integer getUpper() {
        SyntaxElementImpl element = this.getSelf().getElement().getImpl();
        return element instanceof AssignableElement? ((AssignableElement)element).getUpper():
            this.isOperation()? ((OperationDefinitionImpl)element).getUpper():
            this.isBehavior()? ((ActivityDefinitionImpl)element).getUpper():
            0;
    }

    @Override
    public String getDirection() {
        return !this.isParameter()? null: ((FormalParameter)this.getElement()).getDirection();
    }
    
    @Override
    public ElementReference getClassifierBehavior() {
        if (!this.isActiveClass()) {
            return null;
        } else {
            ActivityDefinition classifierBehavior = 
                ((ActiveClassDefinition)this.getSelf().getElement()).getClassifierBehavior();
            return classifierBehavior == null? null: 
                        classifierBehavior.getImpl().getReferent();
        }
    }
    
    @Override
    public ElementReference getNamespace() {
        SyntaxElement element = this.getSelf().getElement();
        if (!(element instanceof Member)) {
            return null;
        } else {
            NamespaceDefinition outerScope = ((Member)element).getImpl().getOuterScope();
            return outerScope == null? null: outerScope.getImpl().getReferent();
        }
    }

    @Override
    public Collection<ElementReference> getRedefinedElements() {
        return this.isOperation()?
                ((OperationDefinition)this.getSelf().getElement()).getImpl().
                    getRedefinedOperation():
                new ArrayList<ElementReference>();
    }
    
    @Override
    public ElementReference getSignal() {
        SyntaxElement element = this.getSelf().getElement();
        return element instanceof ReceptionDefinition?
                ((ReceptionDefinition)element).getSignal():
               element instanceof SignalReceptionDefinition?
                ((SignalReceptionDefinition)element).getImpl().getReferent():
               null;
    }
    
    @Override
    public ElementReference getActiveClass() {
        SyntaxElement element = this.getSelf().getElement();
        if (!(element instanceof ActivityDefinition)) {
            return null;
        } else {
            ElementReference namespace = 
                   ((ActivityDefinition)element).getImpl().getNamespaceReference();
            if (namespace != null &&
                   this.equals(namespace.getImpl().getClassifierBehavior())) {
                return namespace;
            } else {
                return null;
            }
        }
    }
    
    @Override
    public ElementReference getContext() {
        SyntaxElement element = this.getSelf().getElement();
        return !(element instanceof Member)? null:
            ((Member)element).getImpl().getContext();
    }
    
    @Override
    public Collection<Class<?>> getStereotypeMetaclasses() {
        return !this.isStereotype()? new ArrayList<Class<?>>():
            ((ClassDefinition)this.getSelf().getElement()).getImpl().getStereotypeMetaclasses();
    }
    
    @Override
    public Class<?> getUMLMetaclass() {
        SyntaxElement element = this.getSelf().getElement();
        return !(element instanceof Member)? null:
            ((Member)element).getImpl().getUMLMetaclass();
     }
    
    @Override
    public List<ElementReference> resolveInScope(String name, boolean classifierOnly) {
        List<ElementReference> members = new ArrayList<ElementReference>();
        for (Member member: ((NamespaceDefinition)this.getElement()).getImpl().resolveInScope(name, classifierOnly)) {
            members.add(member.getImpl().getReferent());
        }
        return members;
    }
    
    @Override
    public ElementReference getEffectiveBoundElement() {
        return !this.isTemplateBinding()? null: 
            ((BoundClassifier)this.getSelf().getElement()).getEffectiveBoundElement();
    }
    
    @Override
    public ElementReference getEffectiveBoundElement(BoundElementReference boundElement) {
        ElementReference referent = boundElement.getReferent();
        for (ElementReference member: this.getOwnedMembers()) {
            Member alfMember = (Member)member.getImpl().getAlf();
            if (alfMember != null) {
                Member base = (Member)alfMember.getImpl().getBase();
                if (base != null && referent.getImpl().equals(base.getImpl().getReferent())) {
                    return member;
                }
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        } else if (object == this || object == this.getSelf()) {
            return true;
        } else {
            SyntaxElement element = 
                    object instanceof ElementReference? 
                            ((ElementReference)object).getImpl().getAlf():
                    object instanceof ElementReferenceImpl? 
                            ((ElementReferenceImpl)object).getAlf():
                    object instanceof SyntaxElement? 
                            (SyntaxElement)object:
                    null;
            return element != null && element.equals(this.getSelf().getElement());
        }
    }

    @Override
    public boolean conformsTo(ElementReference type) {
        return this.isClassifier() && type != null &&
               (type.getImpl().isAny() ||
                this.equals(type) || 
                type.getImpl().isContainedIn(this.allParents()));
    }

    @Override
    public int hashCode() {
        SyntaxElement element = this.getSelf().getElement();
        return element == null? this.hashCode(): element.hashCode();
    }

} // InternalElementReferenceImpl
