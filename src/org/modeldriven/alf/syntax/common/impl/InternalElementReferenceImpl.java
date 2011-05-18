
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.common.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.units.*;

import org.omg.uml.Element;

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
    public String toString() {
        return "InternalElementReference " + this.getElement();
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
        return null;
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
        return false;
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
        return false;
    }

    @Override
    public boolean isFeature() {
        SyntaxElement element = this.getSelf().getElement();
        return element != null && element instanceof Member && 
                ((Member)element).getIsFeature();
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
    public boolean isTemplate() {
        return this.isClassifier() && 
                ((ClassifierDefinition)this.getSelf().getElement()).getImpl().isTemplate();
    }

    @Override
    public boolean isEnumerationLiteral() {
        return this.getSelf().getElement() instanceof EnumerationLiteralName;        
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
    public FormalParameter asParameter() {
        if (this.isParameter()) {
            return (FormalParameter)this.getSelf().getElement();
        } else {
            return null;
        }
    }

    @Override
    public NamespaceDefinition asNamespace() {
        if (this.isNamespace()) {
            return (NamespaceDefinition)this.getSelf().getElement();
        } else {
            return null;
        }
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
        SyntaxElement alfSignal = signal.getImpl().getAlf();
        if (alfSignal == null || !(alfSignal instanceof SignalDefinition) 
                || !this.isClass()) {
            return false;
        } else {
            ClassDefinition class_ = (ClassDefinition)this.getAlf();
            for (Member member: class_.getOwnedMember()) {
                if (member instanceof ReceptionDefinition &&
                        ((ReceptionDefinition)member).getSignal().getImpl().getAlf() == alfSignal ||
                    member instanceof SignalReceptionDefinition && member == alfSignal) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public Collection<ElementReference> parents() {
        if (this.isClassifier()) {
            return ((ClassifierDefinition)this.getSelf().getElement()).getSpecializationReferent();
        } else {
            return new HashSet<ElementReference>();
        }
    }

    @Override
    public Collection<ElementReference> allParents() {
        if (this.allParents == null) {
            if (!this.isClassifier()) {
                this.allParents = new HashSet<ElementReference>();
            } else {
                this.allParents = this.allParents(new HashSet<ElementReference>());
            }
        }
        return this.allParents;
    }

    // This will work even if there are (illegal) cyclic generalization relationships.
    private Collection<ElementReference> allParents(Collection<ElementReference> allReadySeen) {
        Collection<ElementReference> parents = this.parents();
        parents.removeAll(allReadySeen);
        Set<ElementReference> allParents = new HashSet<ElementReference>(parents);
        for (ElementReference parent: parents) {
            allReadySeen.add(parent);
            allParents.addAll(
                    parent instanceof InternalElementReference?
                            ((InternalElementReferenceImpl)parent.getImpl()).allParents(allReadySeen):
                            parent.getImpl().allParents());
        }
        return allParents;
    }

    @Override
    public String getName() {
        SyntaxElement element = this.getSelf().getElement();
        return element instanceof Member? ((Member)element).getName(): null;
    }

    @Override
    public String getVisibility() {
        SyntaxElement element = this.getSelf().getElement();
        if (!(element instanceof Member)) {
            return null;
        } else {
            return ((Member)element).getVisibility();
        }
    }

    @Override
    public List<ElementReference> getPublicMembers() {
        List<ElementReference> members = new ArrayList<ElementReference>();
        if (this.isPackage()) {
            for (Member member: ((PackageDefinition)this.getSelf().getElement()).getImpl().getPublicMembers()) {
                members.add(member.getImpl().getReferent());
            }
        }
        return members;
        
    }

    @Override
    public List<ElementReference> getFeatures() {
        List<ElementReference> features = new ArrayList<ElementReference>();
        if (this.isClassifier()) {
            for (Member member: ((ClassifierDefinition)this.getSelf().getElement()).getMember()) {
                if (member.getIsFeature()) {
                    features.add(member.getImpl().getReferent());
                }
            }
        }
        return features;
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
        return this.getAttributes();
    }

    @Override
    public List<FormalParameter> getParameters() {
        if (this.isBehavior() || this.isOperation()) {
            return ((NamespaceDefinition)this.getSelf().getElement()).getImpl().getFormalParameters();
        } else {
            return new ArrayList<FormalParameter>();
        }
    }

    @Override
    public ElementReference getType() {
        if (this.isProperty() || this.isParameter()) {
            return ((TypedElementDefinition)this.getSelf().getElement()).getType();
        } else if (this.isOperation()) {
            return ((OperationDefinition)this.getSelf().getElement()).getImpl().getType();
        } else if (this.isBehavior()) {
            return ((ActivityDefinition)this.getSelf().getElement()).getImpl().getType();
        } else if (this.isEnumerationLiteral()) {
            return ((EnumerationDefinition)
                        ((EnumerationLiteralName)this.getSelf().getElement()).
                            getNamespace()).getImpl().getReferent();
        } else {
            return null;
        }
    }

    @Override
    public ElementReference getAssociation() {
        return !this.isAssociationEnd()? null:
                    ((Member)this.getSelf().getElement()).
                        getNamespace().getImpl().getReferent();
    }
    
    public Integer getLower() {
        int lower = 0;
        if (this.isProperty()) {
            lower = ((PropertyDefinition)this.getSelf().getElement()).getLower();
        } else if (this.isParameter()) {
                lower = ((FormalParameter)this.getSelf().getElement()).getLower();
        } else if (this.isOperation()) {
            lower = ((OperationDefinition)this.getSelf().getElement()).getImpl().getLower();
        } else if (this.isBehavior()) {
            lower = ((ActivityDefinition)this.getSelf().getElement()).getImpl().getLower();
        }
        return lower;
    }

    public Integer getUpper() {
        int upper = 0;
        if (this.isProperty()) {
            upper = ((PropertyDefinition)this.getSelf().getElement()).getUpper();
        } else if (this.isParameter()) {
            upper = ((FormalParameter)this.getSelf().getElement()).getUpper();
        } else if (this.isOperation()) {
            upper = ((OperationDefinition)this.getSelf().getElement()).getImpl().getUpper();
        } else if (this.isBehavior()) {
            upper = ((ActivityDefinition)this.getSelf().getElement()).getImpl().getUpper();
        }
        return upper;
    }

    @Override
    public ElementReference getClassifierBehavior() {
        if (!this.isActiveClass()) {
            return null;
        } else {
            ActivityDefinition classifierBehavior = 
                ((ActiveClassDefinition)this.getSelf().getElement()).
                    getClassifierBehavior();
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
            return ((Member)element).getImpl().getNamespaceReference();
        }
    }

    @Override
    public ElementReference getActiveClass() {
        SyntaxElement element = this.getSelf().getElement();
        if (!(element instanceof ActivityDefinition)) {
            return null;
        } else {
            ElementReference namespace = ((ActivityDefinition)element).getImpl().
                                                        getNamespaceReference();
            if (namespace != null && namespace.getImpl().isActiveClass() &&
                   this.equals(namespace.getImpl().getClassifierBehavior())) {
                return namespace;
            } else {
                return null;
            }
        }
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        } else {
            SyntaxElement element = null;
            if (object instanceof ElementReference) {
                element = ((ElementReference)object).getImpl().getAlf();
            } else if (object instanceof ElementReferenceImpl) {
                element = ((ElementReferenceImpl)object).getAlf();
            } else if (object instanceof SyntaxElement) {
                element = (SyntaxElement)object;
            }
            return element != null && this.getSelf().getElement() == element;
        }
    }

    @Override
    public boolean conformsTo(ElementReference type) {
        return this.isClassifier() && (type == null ||
                this.equals(type) || 
                type.getImpl().isContainedIn(this.allParents()));
    }

} // InternalElementReferenceImpl
