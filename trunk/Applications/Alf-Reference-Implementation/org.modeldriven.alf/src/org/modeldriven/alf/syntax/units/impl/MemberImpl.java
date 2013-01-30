
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
import org.modeldriven.alf.syntax.common.impl.DocumentedElementImpl;
import org.modeldriven.alf.syntax.common.impl.ElementReferenceImpl;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.expressions.impl.NameBindingImpl;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        this.name = NameBindingImpl.processName(name);
    }
    
    public void setExactName(String name) {
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
        // NOTE: The following avoids an infinite recursion trying to get
        // annotations from the stub of an external subunit.
		return hasAnnotation("external", this.getSelf().getAnnotation());
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
	        return this.resolveUnit(this.getQualifiedName());
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
	    if (!self.getIsStub() || self.getIsExternal()) {
	        return true;
	    } else {
	        UnitDefinition subunit = self.getSubunit();
    	    NamespaceDefinition definition = subunit == null? null: subunit.getDefinition();
    		return definition != null && 
    		    definition.getImpl().getQualifiedName().getImpl().equals(this.getQualifiedName()) && 
    		        self.matchForStub(subunit);
	    }
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
		return !(self.getName().equals(member.getName()) &&
		            self.isSameKindAs(member));
	} // isDistinguishableFrom

	/**
	 * Returns true if this member is of the same kind as the given member.
	 **/
	public abstract Boolean isSameKindAs(Member member);
	
	/**
	 * Returns true if this is imported.
	 */
	public boolean isImported() {
	    return false;
	}
	
	public UnitDefinition resolveUnit(QualifiedName qualifiedName) {
	    return this.getOuterScope().getImpl().resolveUnit(qualifiedName);
	}

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
        Member self = this.getSelf();
        UnitDefinition subunit = self.getSubunit();
        NamespaceDefinition definition = subunit == null? null: subunit.getDefinition();
        return hasAnnotation(name, this.getSelf().getAnnotation()) ||
                definition != null && hasAnnotation(name, definition.getAnnotation());
    }
    
    private static boolean hasAnnotation(String name, Collection<StereotypeAnnotation> annotations) {
        for (StereotypeAnnotation annotation: annotations) {
            if (annotation.getStereotypeName().getImpl().equals(name)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isTemplate() {
        return false;
    }
    
    public boolean isCompletelyBound() {
        return true;
    }

    public QualifiedName getQualifiedName() {
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
        ElementReferenceImpl referent = this.getReferent().getImpl();
        for (Member otherMember: ownedMembers) {
            if (!referent.equals(otherMember.getImpl().getReferent()) &&
                    !self.isDistinguishableFrom(otherMember)) {
                return false;
            }
        }
        return true;
    }

    public ElementReference getNamespaceReference() {
        NamespaceDefinition namespace = this.getSelf().getNamespace();
        return namespace == null? null: namespace.getImpl().getReferent();
    }
    
    public static void removeDuplicates(List<Member> members) {
        for (int i = 0; i < members.size(); i++) {
            Member importedMember = members.get(i);
            String name = importedMember.getName();
            ElementReferenceImpl referent = importedMember.getImpl().
                getReferent().getImpl();            
            for (int j = i + 1; j < members.size();) {
                Member otherMember = members.get(j);                
                ElementReference otherReferent =
                    otherMember.getImpl().getReferent();
                if (name != null && name.equals(otherMember.getName()) && 
                        otherReferent.getImpl().equals(referent)) {
                    members.remove(j);
                } else {
                    j++;
                }
            }
        }
    }
    
    @Override
    public SyntaxElement bind(
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        return this.bind(this.getSelf().getName(), null, false, 
                templateParameters, templateArguments);
    }
    
    /**
     * Create a binding of this member to a given set of template arguments.
     */
    public Member bind(String name,
            NamespaceDefinition namespace,
            boolean addAsMember,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        Member self = this.getSelf();
        Member boundElement = null;
        try {
            boundElement = self.getClass().newInstance();
        } catch (Exception e) {
            System.out.println("Error binding " + name + ": " + e);
            return null;
        }
        boundElement.getImpl().setExactName(name);
        boundElement.setNamespace(namespace);
        if (namespace != null) {
            namespace.addOwnedMember(boundElement);
            if (addAsMember) {
                namespace.addMember(boundElement);
            }
        }
        boundElement.getImpl().bindTo(self, templateParameters, templateArguments);
        return boundElement;
    }
    
    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof Member) {
            Member self = this.getSelf();
            Member member = (Member)base;
            self.setVisibility(member.getVisibility());
            self.setIsStub(false);
            self.setAnnotation(member.getAnnotation());
        }
    }
    
    protected void fixUpAfterBinding() {        
    }

} // MemberImpl
