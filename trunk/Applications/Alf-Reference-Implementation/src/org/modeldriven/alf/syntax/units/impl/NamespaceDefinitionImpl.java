
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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A model of the common properties of the definition of a namespace in Alf.
 **/

public abstract class NamespaceDefinitionImpl extends MemberImpl {

	private Collection<Member> ownedMember = new ArrayList<Member>();
	private UnitDefinition unit = null;
	private Map<String, Collection<Member>> member = null; // DERIVED

	public NamespaceDefinitionImpl(NamespaceDefinition self) {
		super(self);
	}
	
	@Override
	public NamespaceDefinition getSelf() {
		return (NamespaceDefinition) this.self;
	}

	public Collection<Member> getOwnedMember() {
		return this.ownedMember;
	}

	public void setOwnedMember(Collection<Member> ownedMember) {
		this.ownedMember = ownedMember;
	}

	public void addOwnedMember(Member ownedMember) {
		this.ownedMember.add(ownedMember);
	}

	public UnitDefinition getUnit() {
		return this.unit;
	}

	public void setUnit(UnitDefinition unit) {
		this.unit = unit;
	}

	public Collection<Member> getMember() {
		Collection<Member> allMembers = new ArrayList<Member>();
		for (Collection<Member> members: this.getMemberMap().values()) {
		    allMembers.addAll(members);
		}
		return allMembers;
	}
	
	public Map<String, Collection<Member>> getMemberMap() {
        if (this.member == null) {
            this.member = this.deriveMember();
        }
	    return this.member;
	}

	public void setMember(Collection<Member> members) {
		addAllMembers(members, this.member);
	}
	
	public void setMemberMap(Map<String, Collection<Member>> members) {
	    this.member = members;
	}

	public void addMember(Member member) {
	    addMember(member, this.member);
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
    protected Map<String, Collection<Member>> deriveMember() {
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
        
        Map<String, Collection<Member>> members = new HashMap<String, Collection<Member>>();
        addAllMembers(self.getOwnedMember(), members);
        
        UnitDefinition unit = self.getUnit();	    
	    if (unit != null) {
	      addAllMembers(unit.getImpl().getImportedMembers(), members);
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
	    ArrayList<Member> members = new ArrayList<Member>(this.getSelf().getMember());
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
	
    public Collection<Member> resolveVisible(String name, NamespaceDefinition namespace) {
        Collection<Member> members = this.resolveInScope(name);
            
        // Note: If this namespace is the same as or a containing scope of the 
        // given namespace, then all members of this namespace are visible.
        if (this.getSelf() != namespace && !this.containsMember(namespace)) {
            boolean allowPackageOnly = this.allowPackageOnly();
            for (Member member: members) {
                if (!(member.getImpl().isPublic() || 
                        allowPackageOnly && member.getImpl().isPackageOnly())) {
                    members.remove(member);
                }
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

    public Collection<Member> resolve(String name) {
        Collection<Member> members = this.resolveInScope(name);
        
        // Resolve in the containing scope, if there is one.
        NamespaceDefinition outerScope = this.getOuterScope();
        if (outerScope != null) {
            for (Member member: outerScope.getImpl().resolve(name)) {
                if (member != null && member.getImpl().isDistinguishableFromAll(members)) {
                    members.add(member);
                }
            }
        }

        return members;
    }
    
    private Collection<Member> resolveInScope(String name) {
        Collection<Member> members = this.getMemberMap().get(name);
        return members == null? new ArrayList<Member>(): 
                                new ArrayList<Member>(members);
    }

    public boolean hasSubunitFor(UnitDefinition unit) {
        return this.getStubFor(unit) != null;
    }
    
    public Member getStubFor(UnitDefinition unit) {
        NamespaceDefinition definition = unit == null? null: unit.getDefinition();
        if (definition != null) {
            for (Member member: this.getSelf().getOwnedMember()) {
                String name = member.getName();
                if (name != null && name.equals(definition.getName()) && 
                        member.getIsStub() && member.matchForStub(unit)) {
                    return member;
                }
            }
        }
        return null;
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
        NamespaceDefinition outerScope = super.getOuterScope();
        UnitDefinition unit = this.getSelf().getUnit();
        if (outerScope == null && unit != null) {
            ElementReference namespace = unit.getNamespace();
            if (namespace == null) {
                outerScope = RootNamespace.getModelScope(unit);
            } else {
                outerScope = namespace.getImpl().asNamespace();
            }
        }
        return outerScope;
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
    
    public List<FormalParameter> getFormalParameters() {
        List<FormalParameter> parameters = new ArrayList<FormalParameter>();
        for (Member member: this.getSelf().getOwnedMember()) {
            if (member instanceof FormalParameter) {
                parameters.add((FormalParameter)member);
            }
        }
        return parameters;
    }

    public FormalParameter getReturnParameter() {
        Collection<FormalParameter> parameters = this.getFormalParameters();
        for (FormalParameter parameter: parameters) {
            if (parameter.getDirection().equals("return")) {
                return parameter;
            }
        }
        return null;
    }

    protected static void addAllMembers(Collection<Member> members,
            Map<String, Collection<Member>> map) {
        for (Member member: members) {
            addMember(member, map);          
        }       
    }
    
    protected static void addMember(Member member, Map<String, Collection<Member>> map) {
        String name = member.getName();
        if (name == null) {
            name = "";
        }
        Collection<Member> members = map.get(name);
        if (members == null) {
            members = new ArrayList<Member>();
            map.put(name, members);
        }
        members.add(member);
    }

} // NamespaceDefinitionImpl
