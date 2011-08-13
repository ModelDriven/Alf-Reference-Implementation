
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
import org.modeldriven.alf.uml.Element;

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

	public ElementReference getSelf() {
		return (ElementReference) this.self;
	}
	
	@Override
	public String toString() {
	    return this.toString(false);
	}
	
	public abstract String toString(boolean includeDerived);
	
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
    public abstract boolean isEnumerationLiteral();
    
    public abstract boolean isTemplate();
    public abstract boolean isClassifierTemplateParameter();
    public abstract boolean isCompletelyBound();

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
    public abstract List<Member> getPublicMembers();
    public abstract List<ElementReference> getFeatures();
    public abstract List<ElementReference> getAttributes();
    public abstract List<ElementReference> getAssociationEnds();
    public abstract List<Member> getInheritableMembers();
    public abstract List<FormalParameter> getParameters();
    public abstract FormalParameter getReturnParameter();
    public abstract List<ElementReference> getTemplateParameters();
    public abstract ElementReference getParameteredElement();
    public abstract ElementReference getTemplate();
    public abstract Collection<ElementReference> getConstrainingClassifiers();
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
        // Note: The test here is that the referenced element is or has a parent
        // that is an instantiation of a collection class template and that it
        // has an unambiguous toSequence operation.
        
        ElementReferenceImpl collectionClasses = 
            RootNamespace.getCollectionClassesPackage().getImpl();
        ElementReference template = this.getTemplate();
        boolean found = template != null && 
            collectionClasses.equals(template.getImpl().getNamespace());
        if (!found) {
            for (ElementReference parent: this.allParents()) {
                template = parent.getImpl().getTemplate();
                if (template != null && collectionClasses.
                        equals(template.getImpl().getNamespace())) {
                    found = true;
                    break;
                }
            }
        }
        
        return found && this.getToSequenceOperation() != null;
    }

    public boolean isIntegerCollection() {
        ElementReference collectionArgument = this.getCollectionArgument();
        return collectionArgument != null && 
                    collectionArgument.getImpl().isInteger();
    }
    
    public ElementReference getCollectionArgument() {
        ElementReference toSequenceOperation = this.getToSequenceOperation();
        return toSequenceOperation == null? null: 
            toSequenceOperation.getImpl().getType();
    }
    
    public ElementReference getToSequenceOperation() {
        NamespaceDefinition namespace = this.asNamespace();
        ElementReference toSequenceOperation = null;
        if (namespace != null) {
            for (Member member: namespace.getImpl().resolve("toSequence")) {
                ElementReference referent = member.getImpl().getReferent();
                if (referent.getImpl().isOperation() && 
                        referent.getImpl().getParameters().size() == 1 &&
                        referent.getImpl().getReturnParameter() != null) {
                    if (toSequenceOperation != null) {
                        return null;
                    }
                    toSequenceOperation = referent;
                }
            }
        }
        return toSequenceOperation;
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
