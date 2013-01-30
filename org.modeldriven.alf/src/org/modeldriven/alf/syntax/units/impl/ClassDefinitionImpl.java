
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.units.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.statements.Block;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The definition of a class, whose members may be properties, operations,
 * signals or signal receptions.
 **/

public class ClassDefinitionImpl extends ClassifierDefinitionImpl {
    
    private boolean needsDefaultConstructor = true;
    private boolean needsDefaultDestructor = true;

	public ClassDefinitionImpl(ClassDefinition self) {
		super(self);
	}

	@Override
	public ClassDefinition getSelf() {
		return (ClassDefinition) this.self;
	}
	
	@Override
	public List<Member> getOwnedMember() {
	    ClassDefinition self = this.getSelf();
	    if (!self.getIsStub()) {
            if (this.needsDefaultConstructor()) {
                self.addOwnedMember(this.createDefaultConstructor());
            }
            if (this.needsDefaultDestructor()) {
                self.addOwnedMember(this.createDefaultDestructor());
            }
	    }
	    return super.getOwnedMember();
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
	    ClassDefinition self = this.getSelf();
	    
        for (ElementReference referent: self.getSpecializationReferent()) {
            if (!referent.getImpl().isClassOnly() || 
                    referent.getImpl().isActiveClass() && !this.isActive()) {
                return false;
            }
        }
        
        return true;
	}
	
    /**
     * If a class definition is not abstract, then no member operations (owned
     * or inherited) of the class definition may be abstract.
     **/
    public boolean classDefinitionAbstractMember() {
        ClassDefinition self = this.getSelf();
        if (!self.getIsAbstract()) {
            for (Member member: self.getMember()) {
                if (member instanceof OperationDefinition && 
                        !member.getImpl().isImported() &&
                        ((OperationDefinition)member).getIsAbstract()) {
                    return false;
                }
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
	@Override
	public Boolean annotationAllowed(StereotypeAnnotation annotation) {
	    // TODO: Allow stereotypes consistent with classes.
		return super.annotationAllowed(annotation);
	} // annotationAllowed

	/**
	 * Returns true if the given unit definition matches this class definition
	 * considered as a classifier definition and the subunit is for a class
	 * definition.
	 **/
	@Override
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
    protected List<Member> inherit(List<Member> inheritableMembers) {
	    Collection<Member> ownedMembers = 
	        new ArrayList<Member>(this.getSubunitOwnedMembers());
	    // this.addDefaultMembers(ownedMembers);
	    
	    int i = 0;
	    while (i < inheritableMembers.size()) {
	        Member inheritableMember = inheritableMembers.get(i);
	        for (Member ownedMember: ownedMembers) {
	            // Note: Alf allows redefinition only for operations.
	            if (ownedMember instanceof OperationDefinition) {
	                if (inheritableMember.getImpl().getReferent().getImpl().
                            isContainedIn(((OperationDefinition)ownedMember).
                                    getRedefinedOperation())) {
	                    inheritableMembers.remove(i);
	                    i--;
	                    break;
	                } else if (!ownedMember.isDistinguishableFrom(inheritableMember)) {
	                    inheritableMembers.remove(i);
	                    ((OperationDefinition)ownedMember).addRedefinedOperation
	                        (inheritableMember.getImpl().getReferent());
    	                i--;
    	                break;
	                }
	            }
	        }
	        i++;
	    }
        return inheritableMembers;
    }

    public boolean isActive() {
        return false;
    }
    
    public boolean needsDefaultConstructor() {
        if (this.needsDefaultConstructor) {
            for (Member ownedMember: super.getOwnedMember()) {
                if (ownedMember instanceof OperationDefinition &&
                        ((OperationDefinition)ownedMember).getIsConstructor()) {
                    this.needsDefaultConstructor = false;
                    break;
                }
            }
        }
        return this.needsDefaultConstructor;
    }
    
    /**
     * Get the default constructor or an equivalent explicit constructor (with
     * the same name as the class and no arguments).
     */
    public OperationDefinition getDefaultConstructor() {
        OperationDefinition constructor = null;
        for (Member member: this.resolveInScope(this.getSelf().getName(), false)) {
            if (member instanceof OperationDefinition && 
                    ((OperationDefinition)member).getImpl().getFormalParameters().isEmpty()) {
                constructor = (OperationDefinition)member;
                break;
            }
        }
        return constructor;
    }

    private OperationDefinition createDefaultConstructor() {
        ClassDefinition self = this.getSelf();
        
        OperationDefinition operation = new OperationDefinition();
        operation.setName(self.getName());
        operation.setNamespace(self);
        operation.setBody(new Block());
        
        StereotypeAnnotation annotation = new StereotypeAnnotation();
        annotation.setStereotypeName(new QualifiedName().getImpl().addName("Create"));
        operation.addAnnotation(annotation);
        
        return operation;
    }

    public boolean needsDefaultDestructor() {
        if (this.needsDefaultDestructor) {
            for (Member ownedMember: super.getOwnedMember()) {
                if (ownedMember instanceof OperationDefinition &&
                        ((OperationDefinition)ownedMember).getIsDestructor()) {
                    this.needsDefaultDestructor = false;
                    break;
                }
            }
        }
        return this.needsDefaultDestructor;
    }
    
    private OperationDefinition createDefaultDestructor() {
        ClassDefinition self = this.getSelf();
        
        OperationDefinition operation = new OperationDefinition();
        operation.setName("destroy");
        operation.setNamespace(self);
        operation.setBody(new Block());
        
        StereotypeAnnotation annotation = new StereotypeAnnotation();
        annotation.setStereotypeName(new QualifiedName().getImpl().addName("Destroy"));
        operation.addAnnotation(annotation);
        
        return operation;
    }
    
} // ClassDefinitionImpl
