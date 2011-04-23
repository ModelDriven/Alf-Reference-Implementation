
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
import java.util.Collection;

/**
 * A model of the common properties of the definition of a member of a namespace
 * in Alf.
 **/

public abstract class MemberImpl extends DocumentedElementImpl {

    private String name = "";
    private String visibility = "";
    private Boolean isStub = false;
    private NamespaceDefinition namespace = null;
    private Collection<StereotypeAnnotation> annotation = new ArrayList<StereotypeAnnotation>();
    private Boolean isFeature = null; // DERIVED
    private Boolean isPrimitive = null; // DERIVED
    private Boolean isExternal = null; // DERIVED
    private UnitDefinition subunit = null; // DERIVED

    public MemberImpl(Member self) {
        super(self);
    }

    public Member getSelf() {
        return (Member) this.self;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVisibility() {
        return this.visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public Boolean getIsStub() {
        return this.isStub;
    }

    public void setIsStub(Boolean isStub) {
        this.isStub = isStub;
    }

    public NamespaceDefinition getNamespace() {
        return this.namespace;
    }

    public void setNamespace(NamespaceDefinition namespace) {
        this.namespace = namespace;
    }

    public Collection<StereotypeAnnotation> getAnnotation() {
        return this.annotation;
    }

    public void setAnnotation(Collection<StereotypeAnnotation> annotation) {
        this.annotation = annotation;
    }

    public void addAnnotation(StereotypeAnnotation annotation) {
        this.annotation.add(annotation);
    }

    public Boolean getIsFeature() {
        if (this.isFeature == null) {
            this.setIsFeature(this.deriveIsFeature());
        }
        return this.isFeature;
    }

    public void setIsFeature(Boolean isFeature) {
        this.isFeature = isFeature;
    }

    public Boolean getIsPrimitive() {
        if (this.isPrimitive == null) {
            this.setIsPrimitive(this.deriveIsPrimitive());
        }
        return this.isPrimitive;
    }

    public void setIsPrimitive(Boolean isPrimitive) {
        this.isPrimitive = isPrimitive;
    }

    public Boolean getIsExternal() {
        if (this.isExternal == null) {
            this.setIsExternal(this.deriveIsExternal());
        }
        return this.isExternal;
    }

    public void setIsExternal(Boolean isExternal) {
        this.isExternal = isExternal;
    }

    public UnitDefinition getSubunit() {
        if (this.subunit == null) {
            this.setSubunit(this.deriveSubunit());
        }
        return this.subunit;
    }

    public void setSubunit(UnitDefinition subunit) {
        this.subunit = subunit;
    }

    protected Boolean deriveIsFeature() {
		return false;
	}

    /**
     * A member is primitive if it has a @primitive annotation.
     **/
    protected Boolean deriveIsPrimitive() {
		return this.hasAnnotation("primitive");
	}

    /**
     * A member is external if it has an @external derivation.
     **/
    protected Boolean deriveIsExternal() {
		return this.hasAnnotation("external");
	}

	/**
	 * If the member is a stub and is not external, then its corresponding
	 * subunit is a unit definition with the same fully qualified name as the
	 * stub.
	 **/
    protected UnitDefinition deriveSubunit() {
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
	    NamespaceDefinition definition = subunit == null? null: subunit.getDefinition();
		return definition != null && 
		    definition.getImpl().getQualifiedName().equals(this.getQualifiedName()) && 
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
            Collection<StereotypeAnnotation> stubAnnotations = self.getAnnotation();
            NamespaceDefinition subunitDefinition = self.getSubunit().getDefinition();
            Collection<StereotypeAnnotation> subunitAnnotations = 
                subunitDefinition == null? null: subunitDefinition.getAnnotation();
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
        Member self = this.getSelf();
        UnitDefinition subunit = self.getSubunit();
        NamespaceDefinition definition = subunit == null? null: subunit.getDefinition();
        InternalElementReference referent = new InternalElementReference();
        referent.setElement(definition == null? self: definition);
        return referent;
    }

    public boolean isDistinguishableFromAll(Collection<Member> ownedMembers) {
        Member self = this.getSelf();
        for (Member otherMember: ownedMembers) {
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
