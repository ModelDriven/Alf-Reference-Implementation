
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * The definition of a class, whose members may be properties, operations,
 * signals or signal receptions.
 **/

public class ClassDefinitionImpl extends ClassifierDefinitionImpl {

	public ClassDefinitionImpl(ClassDefinition self) {
		super(self);
	}

	public ClassDefinition getSelf() {
		return (ClassDefinition) this.self;
	}
	
	/*
	 * Constraints
	 */

	/**
	 * The specialization referents of a class definition must all be classes. A
	 * class definition may not have any referents that are active classes
	 * unless this is an active class definition.
	 **/
	public boolean classDefinitionSpecializationReferent() {
        for (ElementReference referent: this.getSelf().getSpecializationReferent()) {
            if (!referent.getImpl().isClass() || 
                    referent.getImpl().isActiveClass() && !this.isActive()) {
                return false;
            }
        }
        return true;
	}
	
    /*
	 * Helper Methods
	 */

	/**
	 * In addition to the annotations allowed for classifiers in general, a
	 * class definition allows an annotation for any stereotype whose metaclass
	 * is consistent with Class.
	 **/
	public Boolean annotationAllowed(StereotypeAnnotation annotation) {
	    // TODO: Allow stereotypes consistent with classes.
		return super.annotationAllowed(annotation);
	} // annotationAllowed

	/**
	 * Returns true if the given unit definition matches this class definition
	 * considered as a classifier definition and the subunit is for a class
	 * definition.
	 **/
	public Boolean matchForStub(UnitDefinition unit) {
		return unit.getDefinition() instanceof ClassDefinition &&
		    super.matchForStub(unit);
	} // matchForStub

	/**
	 * Return true if the given member is either a ClassDefinition or an
	 * imported member whose referent is a ClassDefinition or a Class.
	 **/
	@Override
	public Boolean isSameKindAs(Member member) {
	    return member.getImpl().getReferent().getImpl().isClass();
	} // isSameKindAs

	@Override
	// Removes redefined members from inheritableMembers.
    protected ArrayList<Member> inherit(ArrayList<Member> inheritableMembers) {
	    Collection<Member> ownedMembers = this.getSelf().getOwnedMember();
	    int i = 0;
	    while (i < inheritableMembers.size()) {
	        Member inheritableMember = inheritableMembers.get(i);
	        for (Member ownedMember: ownedMembers) {
	            // Note: Alf allows redefinition only for operations.
	            if (ownedMember instanceof OperationDefinition &&
	                    (!ownedMember.isDistinguishableFrom(inheritableMember) ||
	                     ((OperationDefinition)ownedMember).getRedefinedOperations().
	                        contains(inheritableMember.getImpl().getReferent()))) {
	                inheritableMembers.remove(i);
	                i--;
	                break;
	            }
	        }
	        i++;
	    }
        return inheritableMembers;
    }

    public boolean isActive() {
        return false;
    }

} // ClassDefinitionImpl
