
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.common.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.units.ExternalNamespace;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.omg.uml.Activity;
import org.omg.uml.Association;
import org.omg.uml.BehavioredClassifier;
import org.omg.uml.Class;
import org.omg.uml.Classifier;
import org.omg.uml.DataType;
import org.omg.uml.Element;
import org.omg.uml.Enumeration;
import org.omg.uml.Feature;
import org.omg.uml.Namespace;
import org.omg.uml.Operation;
import org.omg.uml.Package;
import org.omg.uml.Profile;
import org.omg.uml.Property;
import org.omg.uml.Reception;
import org.omg.uml.Signal;
import org.omg.uml.Stereotype;

/**
 * A direct reference to a UML model element.
 **/

public class ExternalElementReferenceImpl extends ElementReferenceImpl {

	public ExternalElementReferenceImpl(ExternalElementReference self) {
		super(self);
	}

	@Override
	public ExternalElementReference getSelf() {
		return (ExternalElementReference) this.self;
	}

    @Override
    public SyntaxElement getAlf() {
        return null;
    }

    @Override
    public Element getUml() {
        return this.getSelf().getElement();
    }

    @Override
    public boolean isNamespace() {
        return this.getSelf().getElement() instanceof Namespace;
    }

    @Override
    public boolean isPackage() {
        return this.getSelf().getElement() instanceof Package;
    }

    @Override
    public boolean isProfile() {
        return this.getSelf().getElement() instanceof Profile;
    }

    @Override
    public boolean isClassifier() {
        return this.getSelf().getElement() instanceof Classifier;
    }

    @Override
    public boolean isAssociation() {
        return this.getSelf().getElement() instanceof Association;
    }

    @Override
    public boolean isClass() {
        return this.getSelf().getElement().getClass() == Class.class;
    }

    @Override
    public boolean isActiveClass() {
        return this.isClass() && ((Class)this.getSelf().getElement()).getIsActive();
    }

    @Override
    public boolean isActiveBehavior() {
        if (!this.isActivity()) {
            return false;
        } else {
            Activity element = (Activity)this.getSelf().getElement();
            BehavioredClassifier context = element.getContext();
            return element.getIsActive() || 
                    context != null && context.getClassifierBehavior() == element;
        }
    }

    @Override
    public boolean isDataType() {
        return this.getSelf().getElement() instanceof DataType;
    }

    @Override
    public boolean isActivity() {
        return this.getSelf().getElement() instanceof Activity;
    }

    @Override
    public boolean isEnumeration() {
        return this.getSelf().getElement() instanceof Enumeration;
    }

    @Override
    public boolean isSignal() {
        return this.getSelf().getElement() instanceof Signal;
    }

    @Override
    public boolean isStereotype() {
        return this.getSelf().getElement() instanceof Stereotype;
    }

    @Override
    public boolean isFeature() {
        return this.getSelf().getElement() instanceof Feature;
    }

    @Override
    public boolean isOperation() {
        return this.getSelf().getElement() instanceof Operation;
    }

    @Override
    public boolean isConstructor() {
        // TODO: Check for external constructors.
        return false;
    }
    
    @Override
    public boolean isDestructor() {
        // TODO: Check for external destructors.
        return false;
    }
    
    @Override
    public boolean isReception() {
        return this.getSelf().getElement() instanceof Reception;
    }

    @Override
    public boolean isTemplate() {
        return this.isClassifier() && 
                ((Classifier)this.getSelf().getElement()).isTemplate();
    }

    @Override
    public boolean isAbstractClassifier() {
        return this.isClassifier() &&
                ((Classifier)this.getSelf().getElement()).isAbstract();
    }

    @Override
    public boolean isProperty() {
        return this.getSelf().getElement() instanceof Property;
    }
    
    @Override
    public NamespaceDefinition asNamespace() {
        if (this.isNamespace()) {
            return new ExternalNamespace((Namespace)this.getSelf().getElement());
        } else {
            return null;
        }
    }

    @Override
    public boolean equals(Object object) {
        Element element = null;
        if (object == null) {
            return false;
        } else {
            if (object instanceof ElementReference) {
                element = ((ElementReference)object).getImpl().getUml();
            } else if (object instanceof ElementReferenceImpl) {
                element = ((ElementReferenceImpl)object).getUml();
            } else if (object instanceof Element) {
                element = (Element)object;
            }
            return element != null && this.getSelf().getElement() == element;
        }
    }

} // ExternalElementReferenceImpl
