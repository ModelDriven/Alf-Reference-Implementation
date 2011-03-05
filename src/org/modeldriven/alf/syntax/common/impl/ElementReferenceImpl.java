
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.common.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.impl.QualifiedNameImpl;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.RootNamespace;
import org.omg.uml.Element;

/**
 * A reference to a model element, either directly or via its Alf abstract
 * syntax representation. (NOTE: The definitions of all the helper operations of
 * ElementReference are specific to its subclasses.)
 **/

public abstract class ElementReferenceImpl {

	protected ElementReference self;

	public ElementReferenceImpl(ElementReference self) {
		this.self = self;
	}

	public org.modeldriven.alf.syntax.common.ElementReference getSelf() {
		return (ElementReference) this.self;
	}
	
	/*
	 * Helper Methods
	 */
	
	public abstract SyntaxElement getAlf();
	public abstract Element getUml();
	
	public abstract boolean isNamespace();
	public abstract boolean isPackage();
    public abstract boolean isProfile();

	public abstract boolean isClassifier();
    public abstract boolean isAbstractClassifier();
    public abstract boolean isAssociation();
	public abstract boolean isClass();  // But not any subtype of Class
    public abstract boolean isActiveClass();
	public abstract boolean isDataType();
	public abstract boolean isActivity();
    public abstract boolean isEnumeration();
    public abstract boolean isSignal();
    public abstract boolean isStereotype();
    public abstract boolean isTemplate();

    public abstract boolean isFeature();
    public abstract boolean isOperation();
    public abstract boolean isConstructor();
    public abstract boolean isDestructor();
    public abstract boolean isReception();
    public abstract boolean isProperty();

    public abstract NamespaceDefinition asNamespace();

    public abstract boolean hasReceptionFor(ElementReference signal);
    
    /**
     * Return the active class corresponding to an activity, if any.
     * This is either the activity itself, if it is active, or the class that
     * has the activity as a classifier behavior.
     */
    public abstract ElementReference getActiveClass();
    
    public boolean isActiveBehavior() {
        return this.getActiveClass() != null;
    }

	@Override
	public abstract boolean equals(Object object);

    public boolean isCollectionClass() {
        // TODO Write condition for being a collection class.
        return false;
    }

    public boolean isIntegerCollection() {
        // TODO Write condition for being an integer collection.
        return false;
    }
    
    static final QualifiedNameImpl bitString = 
        RootNamespace.getPrimitiveTypes().getImpl().copy().addName("BitString").getImpl();
    
    public boolean isBitString() {
        ElementReference referent = bitString.getClassifierReferent();
        return referent != null && this.equals(referent);
    }

    public boolean isAssignableTo(ElementReference type) {
        //TODO Write condition for assignability.
        return false;
    }

} // ElementReferenceImpl
