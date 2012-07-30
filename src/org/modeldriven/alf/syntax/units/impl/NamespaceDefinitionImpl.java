
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
import org.modeldriven.alf.syntax.common.impl.ElementReferenceImpl;
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
    private Collection<Member> member = null; // DERIVED
    private Map<String, Collection<Member>> memberMap = null;

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
        if (this.member == null) {
            this.setMember(this.deriveMember());
        }
        return this.member;
	}
	
	public Map<String, Collection<Member>> getMemberMap() {
        if (this.memberMap == null) {
            this.getMember();
        }
	    return this.memberMap;
	}

	public void setMember(Collection<Member> members) {
		this.member = members;
		if (members == null) {
		    this.memberMap = null;
		} else {
		    this.memberMap = new HashMap<String, Collection<Member>>();
		    addAllMembers(members, this.memberMap);
		}
	}
	
	public void addMember(Member member) {
	    this.member.add(member);
	    addMember(member, this.memberMap);
	}
	
	public void addAllMembers(Collection<Member> members) {
	    this.member.addAll(members);
	    addAllMembers(members, this.memberMap);
	}
	
	public void removeMember(Member member) {
	    this.member.remove(member);
	    Collection<Member> members = this.memberMap.get(member.getName());
	    if (members != null) {
	        members.remove(member);
	    }
	}
	
	@Override
	public void setSubunit(UnitDefinition subunit) {
	    super.setSubunit(subunit);
	    if (subunit != null) {
	        NamespaceDefinition definition = subunit.getDefinition();
	        if (definition != null) {
	            NamespaceDefinition self = this.getSelf();
	            self.setMember(null);
	        }
	    }
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
    protected Collection<Member> deriveMember() {
	    NamespaceDefinition self = this.getSelf();
	    
        if (self.getIsStub()) {
	        UnitDefinition subunit = self.getSubunit();
	        if (subunit != null) {
	            NamespaceDefinition definition = subunit.getDefinition();
	            if (definition != null) {
	                return definition.getMember();
	            }
	        }
	    }
        
        List<Member> members = new ArrayList<Member>();
        members.addAll(self.getOwnedMember());
        
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
	    ArrayList<Member> members = new ArrayList<Member>(this.getSelf().getMember());
	    int n = members.size();
	    for (int i = 0; i < n; i++) {
	        Member member = members.get(i);
	        ElementReference referent = member.getImpl().getReferent();
	        for (int j = i+1; j < n; j++) {
	            Member otherMember = members.get(j);
	            if (!member.isDistinguishableFrom(otherMember) &&
	                    !referent.getImpl().equals(otherMember.getImpl().getReferent())) {
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
	
	@Override
	public boolean hasAnnotation(String name) {
	    UnitDefinition unit = this.getSelf().getUnit();
	    Member stub = unit == null? null: unit.getImpl().getStub();
	    return stub == null? super.hasAnnotation(name): 
	        stub.getImpl().hasAnnotation(name);
	}
	
    public Collection<Member> resolveVisible(String name, 
            NamespaceDefinition namespace, boolean classifierOnly) {
        Collection<Member> members = this.resolveInScope(name, classifierOnly);
            
        // Note: If this namespace is the same as or a containing scope of the 
        // given namespace, then all members of this namespace are visible.
        if (namespace != null && !this.getReferent().getImpl().equals(namespace.getImpl().getReferent()) &&
                !this.containsMember(namespace)) {
            boolean allowPackageOnly = this.allowPackageOnly();
            for (Object member: members.toArray()) {
                MemberImpl memberImpl = ((Member)member).getImpl();
                if (!(memberImpl.isPublic() || 
                        allowPackageOnly && memberImpl.isPackageOnly())) {
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
                    (namespace.getImpl().getReferent().getImpl().
                            equals(this.getReferent()) || 
                    this.containsMember(namespace));
        }
    }

    protected boolean allowPackageOnly() {
        return true;
    }
    
    public Collection<Member> resolve(String name) {
        return this.resolve(name, false);
    }

    public Collection<Member> resolve(String name, boolean classifierOnly) {
        List<Member> members = this.resolveInScope(name, classifierOnly);
        
        // Resolve in the containing scope, if there is one.
        NamespaceDefinition outerScope = this.getOuterScope();
        if (outerScope != null) {
            for (Member member: outerScope.getImpl().resolve(name)) {
                if (!classifierOnly || 
                        member.getImpl().getReferent().getImpl().isClassifier()) {
                    if (member != null && member.getImpl().isDistinguishableFromAll(members)) {
                        members.add(member);
                    }
                }
            }
        }
        
        MemberImpl.removeDuplicates(members);

        return members;
    }
    
    public List<Member> resolveInScope(String name, boolean classifierOnly) {
        Collection<Member> namedMembers = this.getMemberMap().get(name);
        ArrayList<Member> members = new ArrayList<Member>();
        
        if (namedMembers != null) {
            for (Member member: namedMembers) {
                if (!classifierOnly || 
                        member.getImpl().getReferent().getImpl().isClassifier()) {
                    members.add(member);
                }
            }
        }
              
        return members;
    }
    
    /**
     * For all visible binary associations, return association ends with the
     * given opposite end type and name.
     */
    public Collection<ElementReference> resolveAssociationEnd
        (ElementReference oppositeEndType, String name) {
        Collection<ElementReference> referents = new ArrayList<ElementReference>();
        Collection<ElementReferenceImpl> associations = 
            new ArrayList<ElementReferenceImpl>();
        for (Member member: this.getSelf().getMember()) {
            ElementReferenceImpl referent = 
                member.getImpl().getReferent().getImpl();
            if (referent.isAssociation()) {
                associations.add(referent);
                List<ElementReference> associationEnds = 
                    referent.getAssociationEnds();
                if (associationEnds.size() == 2) {
                    ElementReference associationEnd1 =
                        associationEnds.get(0);
                    ElementReference associationEnd2 =
                        associationEnds.get(1);
                    if (oppositeEndType.getImpl().
                            equals(associationEnd1.getImpl().getType()) &&
                        name.equals(associationEnd2.getImpl().getName())) {
                        referents.add(associationEnd2);
                    }                               
                    if (oppositeEndType.getImpl().
                            equals(associationEnd2.getImpl().getType()) &&
                        name.equals(associationEnd1.getImpl().getName())) {
                        referents.add(associationEnd1);
                    }                               
                }
            }
        }
        
        NamespaceDefinition outerScope = this.getOuterScope();
        if (outerScope != null) {
            for (ElementReference outerReferent: outerScope.getImpl().
                    resolveAssociationEnd(oppositeEndType, name)) {
                Boolean visible = true;
                name = outerReferent.getImpl().getName();
                for (ElementReference referent: referents) {
                    if (name.equals(referent.getImpl().getName())) {
                        visible = false;
                        break;
                    }
                }
                if (visible) {
                    referents.add(outerReferent);
                }
            }
        }
        
        return referents;
    }

    public boolean hasSubunitFor(UnitDefinition unit) {
        return this.getStubFor(unit) != null;
    }
    
    public Member getStubFor(UnitDefinition unit) {
        NamespaceDefinition definition = unit == null? null: unit.getDefinition();
        if (definition != null) {
            for (Object object: this.getSelf().getOwnedMember().toArray()) {
                Member member = (Member)object;
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
    
    public Collection<Member> getSubunitOwnedMembers() {
        /*
        NamespaceDefinition self = this.getSelf();
        self.getSubunit();
        return self.getOwnedMember();
        */
        NamespaceDefinition self = this.getSelf();
        UnitDefinition subunit = self.getSubunit();
        NamespaceDefinition definition = subunit == null? null: subunit.getDefinition();
        return definition == null? self.getOwnedMember(): definition.getOwnedMember();
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
    
    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof NamespaceDefinition) {
            NamespaceDefinition self = this.getSelf();
            NamespaceDefinition baseNamespace = (NamespaceDefinition)base;
            
            // Note: The unit for the bound namespace, if any, is bound before
            // adding owned members so imported members are available during
            // the resolution of types for operation distinguishibility testing.
            UnitDefinition baseUnit = baseNamespace.getUnit();
            if (baseUnit != null) {
                UnitDefinition unit = (UnitDefinition)baseUnit.getImpl().
                    bind(templateParameters, templateArguments);
                self.setUnit(unit);
                unit.setDefinition(self);
            }
            
            self.setOwnedMember(new ArrayList<Member>());
            for (Member ownedMember: baseNamespace.getOwnedMember()) {
                // Note: If a boundMember is created, it will be added to
                // the given namespace.
                ownedMember.getImpl().bind(ownedMember.getName(), self, false,
                        templateParameters, templateArguments);
            }
        }
    }

} // NamespaceDefinitionImpl
