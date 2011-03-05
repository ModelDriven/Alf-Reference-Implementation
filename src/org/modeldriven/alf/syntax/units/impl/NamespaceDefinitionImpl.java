
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A model of the common properties of the definition of a namespace in Alf.
 **/

public abstract class NamespaceDefinitionImpl extends MemberImpl {

	public NamespaceDefinitionImpl(NamespaceDefinition self) {
		super(self);
	}
	
	@Override
	public NamespaceDefinition getSelf() {
		return (NamespaceDefinition) this.self;
	}

    /**
     * The members of a namespace definition include references to all owned
     * members. Also, if the namespace definition has a unit with imports, then
     * the members include imported members with referents to all imported
     * elements. The imported elements and their visibility are determined as
     * given in the UML Superstructure. The name of an imported member is the
     * name of the imported element or its alias, if one has been given for it.
     * Elements that would be indistinguishable from each other or from an owned
     * member (as determined by the Member::isDistinguishableFrom operation) are
     * not imported.
     **/
	@SuppressWarnings("unchecked")
    public ArrayList<Member> deriveMember() {
	    NamespaceDefinition self = this.getSelf();

        if (self.getIsStub()) {
	        UnitDefinition subunit = self.getSubunit();
	        if (subunit != null) {
	            subunit.getImpl().addImplicitImports();
	            NamespaceDefinition definition = subunit.getDefinition();
	            if (definition != null) {
	                self = definition;
	            }
	        }
	    }
        
	    ArrayList<Member> members = (ArrayList<Member>)self.getOwnedMember().clone();
	    
        UnitDefinition unit = self.getUnit();	    
	    if (unit != null) {
	      members.addAll(unit.getImpl().getImportedMembers());
	    }
	    
		return members;
	}

	/*
	 * Derivations
	 */
	
	public boolean namespaceDefinitionMemberDerivation() {
		this.getSelf().getMember();
		return true;
	}
	
	/*
	 * Constraints
	 */

	/**
	 * The members of a namespace must be distinguishable as determined by the
	 * Member::isDistinguishableFrom operation.
	 **/
	public boolean namespaceDefinitionMemberDistinguishaibility() {
	    ArrayList<Member> members = this.getSelf().getMember();
	    int n = members.size();
	    for (int i = 0; i < n; i++) {
	        Member member = members.get(i);
	        ElementReference referent = member.getImpl().getReferent();
	        for (int j = i+1; j < n; j++) {
	            Member otherMember = members.get(j);
	            if (!member.isDistinguishableFrom(otherMember) &&
	                    !referent.equals(otherMember.getImpl().getReferent())) {
	                return false;
	            }
	        }
	    }
		return true;
	}

	/**
	 * Returns true if the annotation is @external.
	 **/
	@Override
	public Boolean annotationAllowed(StereotypeAnnotation annotation) {
	    return annotation.getStereotypeName().getImpl().equals("external");
	}
	
	/*
	 *  Helper methods
	 */
	
    public List<Member> resolveVisible(String name, NamespaceDefinition namespace) {
        // If this namespace is a containing scope of the given namespace,
        // then all members of this namespace are visible.
        boolean containingScope = this.containsMember(namespace);
        
        ArrayList<Member> members = new ArrayList<Member>();
        boolean allowPackageOnly = this.allowPackageOnly();
        for (Member member: this.getSelf().getMember()) {
            if (member.getName().equals(name) && 
                    (containingScope || member.getImpl().isPublic() || 
                            allowPackageOnly && member.getImpl().isPackageOnly())) {
                members.add(member);
            }
        }
        
        return members;
    }
    
    private boolean containsMember(Member member) {
        if (member == null) {
            return false;
        } else {
            NamespaceDefinition namespace = member.getNamespace();
            return namespace != null && 
                    (namespace.getImpl().getReferent() == this.getReferent() || 
                    this.containsMember(namespace));
        }
    }

    protected boolean allowPackageOnly() {
        return true;
    }

    public List<Member> resolve(String name) {
        ArrayList<Member> members = new ArrayList<Member>();
        NamespaceDefinition self = this.getSelf();
        for (Member member: self.getMember()) {
            if (member.getName().equals(name)) {
                members.add(member);
             }
        }
        
        // Resolve in the containing scope, if there is one.
        NamespaceDefinition namespace = this.getOuterScope();
        if (namespace != null) {
            for (Member member: namespace.getImpl().resolve(name)) {
                if (member != null && member.getImpl().isDistinguishableFromAll(members)) {
                    members.add(member);
                }
            }
        }

        return members;
    }

    public boolean hasSubunitFor(UnitDefinition unit) {
        for (Member member: this.getSelf().getOwnedMember()) {
            if (member.getIsStub() && member.matchForStub(unit)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    protected QualifiedName getNamespaceName() {
        UnitDefinition unit = this.getSelf().getUnit();
        QualifiedName qualifiedName = null;
        if (unit == null) {
            qualifiedName = super.getNamespaceName();
        } else {
            QualifiedName namespaceName = unit.getNamespaceName();
            if (namespaceName == null) {
                qualifiedName = new QualifiedName();
            } else {
                qualifiedName = namespaceName.getImpl().copy().getSelf();
            }
            qualifiedName.getImpl().setCurrentScope(RootNamespace.getRootScope());
        }
        return qualifiedName;
    }
    
    @Override
    public NamespaceDefinition getOuterScope() {
        UnitDefinition unit = this.getSelf().getUnit();
        if (unit == null) {
            return super.getOuterScope();
        } else {
            ElementReference namespace = unit.getNamespace();
            if (namespace == null) {
                return RootNamespace.getModelScope(unit);
            } else {
                return namespace.getImpl().asNamespace();
            }
        }
    }
    
    @Override
    public ElementReference getNamespaceReference() {
        UnitDefinition unit = this.getSelf().getUnit();
        if (unit == null) {
            return super.getNamespaceReference();
        } else {
            return unit.getNamespace();
        }
    }
    
} // NamespaceDefinitionImpl
