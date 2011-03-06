
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.common.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.units.*;

import org.omg.uml.Element;

/**
 * A direct reference to a UML model element.
 **/

public class InternalElementReferenceImpl extends ElementReferenceImpl {

    private SyntaxElement element = null;

	public InternalElementReferenceImpl(InternalElementReference self) {
		super(self);
	}

	@Override
	public InternalElementReference getSelf() {
		return (InternalElementReference) this.self;
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
    public boolean isAssociation() {
        return this.getSelf().getElement() instanceof AssociationDefinition;
    }

    @Override
    public boolean isClass() {
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
    public boolean isActivity() {
        return this.getSelf().getElement() instanceof ActivityDefinition;
    }

    @Override
    public boolean isEnumeration() {
        return this.getSelf().getElement() instanceof EnumerationDefinition;
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
    public boolean isAbstractClassifier() {
        return this.isClassifier() &&
                ((ClassifierDefinition)this.getSelf().getElement()).getIsAbstract();
    }

    @Override
    public boolean isProperty() {
        return this.getSelf().getElement() instanceof PropertyDefinition;
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
    public ElementReference getActiveClass() {
        SyntaxElement element = this.getSelf().getElement();
        if (!(element instanceof ActivityDefinition)) {
            return null;
        } else {
            NamespaceDefinition namespace = ((ActivityDefinition)element).getNamespace();
            if (namespace != null && namespace instanceof ActiveClassDefinition &&
                   ((ActiveClassDefinition)namespace).getClassifierBehavior() == element) {
                InternalElementReference reference = new InternalElementReference();
                reference.setElement(namespace);
                return reference;
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

} // InternalElementReferenceImpl
