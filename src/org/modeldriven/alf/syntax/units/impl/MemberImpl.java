
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.common.impl.DocumentedElementImpl;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.expressions.impl.QualifiedNameImpl;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A model of the common properties of the definition of a member of a namespace
 * in Alf.
 **/

public abstract class MemberImpl extends DocumentedElementImpl {

	public MemberImpl(Member self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.units.Member getSelf() {
		return (Member) this.self;
	}

	public Boolean deriveIsFeature() {
		return false;
	}

    /**
     * A member is primitive if it has a @primitive annotation.
     **/
	public Boolean deriveIsPrimitive() {
		return this.hasAnnotation("primitive");
	}

    /**
     * A member is external if it has an @external derivation.
     **/
	public Boolean deriveIsExternal() {
		return this.hasAnnotation("external");
	}

	/**
	 * If the member is a stub and is not external, then its corresponding
	 * subunit is a unit definition with the same fully qualified name as the
	 * stub.
	 **/
	public UnitDefinition deriveSubunit() {
	    Member self = this.getSelf();
	    if (!self.getIsStub() || self.getIsExternal()) {
	        return null;
	    } else {
	        return RootNamespace.resolveUnit(this.getQualifiedName());
	    }	    
	}

	/*
	 * Derivations
	 */

    public boolean memberIsPrimitiveDerivation() {
		this.getSelf().getIsPrimitive();
		return true;
	}

	public boolean memberIsExternalDerivation() {
		this.getSelf().getIsExternal();
		return true;
	}

	public boolean memberSubunitDerivation() {
		this.getSelf().getSubunit();
		return true;
	}

    /*
     * Constraints
     */

	/**
	 * If a member is external then it must be a stub.
	 **/
	public boolean memberExternal() {
        Member self = this.getSelf();
		return !self.getIsExternal() || self.getIsStub();
	}

	/**
	 * If a member is a stub and is not external, then there must be a single
	 * subunit with the same qualified name as the stub that matches the stub,
	 * as determined by the matchForStub operation.
	 **/
	public boolean memberStub() {
	    Member self = this.getSelf();
	    UnitDefinition subunit = self.getSubunit();
		return subunit != null && 
		    subunit.getDefinition().getImpl().getQualifiedName().equals(this.getQualifiedName()) && 
		        self.matchForStub(subunit);
	}

	/**
	 * If a member is a stub, then the it must not have any stereotype
	 * annotations that are the same as its subunit. Two stereotype annotations
	 * are the same if they are for the same stereotype.
	 **/
	public boolean memberStubStereotypes() {
        Member self = this.getSelf();
        if (self.getIsStub()) {
            ArrayList<StereotypeAnnotation> stubAnnotations = self.getAnnotation();
            ArrayList<StereotypeAnnotation> subunitAnnotations = 
                self.getSubunit().getDefinition().getAnnotation();
            if (stubAnnotations != null && stubAnnotations.size() > 0 &&
                    subunitAnnotations != null && subunitAnnotations.size() > 0) {
                for (StereotypeAnnotation stubAnnotation: stubAnnotations) {
                    for (StereotypeAnnotation subunitAnnotation: subunitAnnotations) {
                        if (stubAnnotation.getStereotype() == subunitAnnotation.getStereotype()) {
                            return false;
                        }
                    }
                }
            }
        }
		return true;
	}

	/**
	 * If a member is primitive, then it may not be a stub and it may not have
	 * any owned members that are template parameters.
	 **/
	public boolean memberPrimitive() {
	    Member self = this.getSelf();
	    return !(self.getIsPrimitive() && (self.getIsStub() || this.isTemplate()));
	}
	
    /**
     * All stereotype annotations for a member must be allowed, as determined
     * using the stereotypeAllowed operation.
     **/
    public boolean memberAnnotations() {
        Member self = this.getSelf();
        for (StereotypeAnnotation annotation: self.getAnnotation()) {
            if (!self.annotationAllowed(annotation)) {
                return false;
            }
        }
        return true;
    }
    
    /*
     * Helper methods
     */
    
	/**
	 * Returns true of the given stereotype annotation is allowed for this kind
	 * of element.
	 **/
	public abstract Boolean annotationAllowed(StereotypeAnnotation annotation);

	/**
	 * Returns true if the given unit definition is a legal match for this
	 * member as a stub. By default, always returns false.
	 **/
	public Boolean matchForStub(UnitDefinition unit) {
		return false;
	} // matchForStub

	/**
	 * Returns true if this member is distinguishable from the given member. Two
	 * members are distinguishable if their names are different or the they are
	 * of different kinds (as determined by the isSameKindAs operation).
	 **/
	public Boolean isDistinguishableFrom(Member member) {
	    Member self = this.getSelf();
		return !(QualifiedNameImpl.processName(self.getName()).
		    equals(QualifiedNameImpl.processName(member.getName())) &&
		    self.isSameKindAs(member));
	} // isDistinguishableFrom

	/**
	 * Returns true if this member is of the same kind as the given member.
	 **/
	public abstract Boolean isSameKindAs(Member member);

    public boolean hasNoVisibility() {
        String visibility = this.getSelf().getVisibility();
        return visibility == null || visibility.length() == 0;
    }

    public boolean isPublic() {
        String visibility = this.getSelf().getVisibility();
        return visibility != null && visibility.equals("public");
    }
    
    public boolean isPackageOnly() {
        // Default visibility for packaged members is package only.
        return this.hasNoVisibility();
    }

    public boolean isPrivate() {
        String visibility = this.getSelf().getVisibility();
        return visibility != null && visibility.equals("private");
    }
    
    public boolean hasAnnotation(String name) {
        for (StereotypeAnnotation annotation: this.getSelf().getAnnotation()) {
            if (annotation.getStereotypeName().getImpl().equals(name)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isTemplate() {
        return false;
    }

    protected QualifiedName getQualifiedName() {
        return this.getNamespaceName().getImpl().addName(this.getSelf().getName());
    }
    
    protected QualifiedName getNamespaceName() {
        Member self = this.getSelf();
        QualifiedName qualifiedName = null;
        NamespaceDefinition namespace = self.getNamespace();
        if (namespace == null) {
            qualifiedName = new QualifiedName();
            qualifiedName.getImpl().setCurrentScope(RootNamespace.getRootScope());
        } else {
            qualifiedName = namespace.getImpl().getQualifiedName();
        }
        return qualifiedName;
    }
    
    public NamespaceDefinition getOuterScope() {
        return this.getSelf().getNamespace();
    }

    public ElementReference getReferent() {
        InternalElementReference referent = new InternalElementReference();
        Member self = this.getSelf();
        UnitDefinition subunit = self.getSubunit();
        if (subunit == null) {
            referent.setElement(self);
        } else {
            referent.setElement(subunit.getDefinition());
        }
        return referent;
    }

    public boolean isDistinguishableFromAll(List<Member> otherMembers) {
        Member self = this.getSelf();
        for (Member otherMember: otherMembers) {
            if (!self.isDistinguishableFrom(otherMember)) {
                return false;
            }
        }
        return true;
    }

    public ElementReference getNamespaceReference() {
        NamespaceDefinition namespace = this.getSelf().getNamespace();
        if (namespace == null) {
            return null;
        } else {
            InternalElementReference reference = new InternalElementReference();
            reference.setElement(namespace);
            return reference;
        }
    }
    
} // MemberImpl
