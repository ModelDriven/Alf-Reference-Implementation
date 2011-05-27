
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.common.impl;

import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.units.FormalParameter;
import org.modeldriven.alf.syntax.units.Member;
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
	public abstract boolean isClass();  
	public abstract boolean isClassOnly(); // But not any subtype of Class
    public abstract boolean isActiveClass();
	public abstract boolean isDataType();
	public abstract boolean isBehavior();
	public abstract boolean isActivity();
    public abstract boolean isEnumeration();
    public abstract boolean isPrimitive();
    public abstract boolean isSignal();
    public abstract boolean isStereotype();
    public abstract boolean isTemplate();
    public abstract boolean isEnumerationLiteral();

    public abstract boolean isFeature();
    public abstract boolean isOperation();
    public abstract boolean isConstructor();
    public abstract boolean isDestructor();
    public abstract boolean isReception();
    public abstract boolean isProperty();
    public abstract boolean isAssociationEnd();
    public abstract boolean isParameter();

    public abstract FormalParameter asParameter();
    public abstract NamespaceDefinition asNamespace();
    public abstract boolean isInNamespace(NamespaceDefinition namespace);

    public abstract boolean hasReceptionFor(ElementReference signal);
    
    public abstract Collection<ElementReference> parents();
    public abstract Collection<ElementReference> allParents();

    public abstract String getName();
    public abstract String getVisibility();
    public abstract List<ElementReference> getPublicMembers();
    public abstract List<ElementReference> getFeatures();
    public abstract List<ElementReference> getAttributes();
    public abstract List<ElementReference> getAssociationEnds();
    public abstract List<Member> getInheritableMembers();
    public abstract List<FormalParameter> getParameters();
    public abstract FormalParameter getReturnParameter();
    public abstract ElementReference getType();
    public abstract ElementReference getAssociation();
    public abstract Integer getLower();
    public abstract Integer getUpper();
    public abstract ElementReference getClassifierBehavior();
    public abstract ElementReference getNamespace();
    
    /**
     * Return the active class corresponding to an activity, if any.
     * This is either the activity itself, if it is active, or the class that
     * has the activity as a classifier behavior.
     */
    public abstract ElementReference getActiveClass();
    
    public boolean isActiveBehavior() {
        return this.getActiveClass() != null;
    }

    public boolean isCollectionClass() {
        // TODO Write condition for being a collection class.
        return false;
    }

    public boolean isIntegerCollection() {
        if (!this.isCollectionClass()) {
            return false;
        } else {
            ElementReference collectionArgument = this.getCollectionArgument();
            return collectionArgument != null && 
                        collectionArgument.getImpl().isInteger();
        }
    }
    
    public ElementReference getCollectionArgument() {
        // TODO Implement getting the argument of a collection class instantiation.
        return null;
    }

    public boolean isInteger() {
        return this.conformsTo(RootNamespace.getIntegerType());
    }

    public boolean isBoolean() {
        return this.conformsTo(RootNamespace.getBooleanType());
    }

    public boolean isString() {
        return this.conformsTo(RootNamespace.getStringType());
    }

    public boolean isUnlimitedNatural() {
        return this.conformsTo(RootNamespace.getUnlimitedNaturalType());
    }

    public boolean isBitString() {
        return this.conformsTo(RootNamespace.getBitStringType());
    }

    public boolean isNatural() {
        return this.conformsTo(RootNamespace.getNaturalType());
    }

    public boolean isNumeric() {
        return this.isInteger() || 
               this.isUnlimitedNatural() || 
               this.isNatural();
    }

    public abstract boolean conformsTo(ElementReference type);
    
    public boolean isContainedIn(Collection<ElementReference> references) {
        for (ElementReference reference: references) {
            if (this.equals(reference)) {
                return true;
            }
        }
        return false;
    }

} // ElementReferenceImpl
