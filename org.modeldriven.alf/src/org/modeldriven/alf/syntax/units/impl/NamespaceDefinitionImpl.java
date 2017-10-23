/*******************************************************************************
 * Copyright 2011-2017 Data Access Technologies, Inc. (Model Driven Solutions)
 * 
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. 
 *******************************************************************************/

package org.modeldriven.alf.syntax.units.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.common.impl.ElementReferenceImpl;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.Block;
import org.modeldriven.alf.syntax.units.*;
import org.modeldriven.alf.uml.Element;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A model of the common properties of the definition of a namespace in Alf.
 **/

public abstract class NamespaceDefinitionImpl extends MemberImpl {

	private List<Member> ownedMember = new ArrayList<Member>();
	private UnitDefinition unit = null;
    protected Collection<Member> member = null; // DERIVED
    protected Map<String, Collection<Member>> memberMap = null;
    
    protected Element umlElement = null;

	public NamespaceDefinitionImpl(NamespaceDefinition self) {
		super(self);
	}
	
	@Override
	public NamespaceDefinition getSelf() {
		return (NamespaceDefinition) this.self;
	}

	public List<Member> getOwnedMember() {
		return this.ownedMember;
	}

	public void setOwnedMember(List<Member> ownedMember) {
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
        return this.deriveMember(new ArrayList<ElementReference>());
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
	public boolean namespaceDefinitionMemberDistinguishability() {
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
	    return annotation.getStereotypeName().getImpl().equals("external") ||
	           super.annotationAllowed(annotation);
	}
	
	/*
	 *  Helper methods
	 */
	
	public void setUml(Element umlElement) {
	    this.umlElement = umlElement;
	}
	
	@Override
	public Element getUml() {
	    return this.umlElement;
	}
	
	public Collection<Member> getMember(Collection<ElementReference> excluded) {
	    if (this.member != null) {
	        return this.member;
	    } else if (excluded == null || excluded.isEmpty()) {
	        return this.getSelf().getMember();
	    } else {
	        return this.deriveMember(excluded);
	    }
	}
	
	// Derive the members of this namespace, but exclude the further importing
	// of any of the excluded elements, in order to prevent infinite looping
	// due to circular references.
    protected Collection<Member> deriveMember(Collection<ElementReference> excluded) {
        NamespaceDefinition self = this.getSelf();

        if (self.getIsStub()) {
            UnitDefinition subunit = self.getSubunit();
            if (subunit != null) {
                NamespaceDefinition definition = subunit.getDefinition();
                if (definition != null) {
                    return definition.getImpl().getMember(excluded);
                }
            }
        }
        
        List<Member> members = new ArrayList<Member>();
        members.addAll(self.getOwnedMember());
        
        UnitDefinition unit = self.getUnit();       
        if (unit != null) {
            members.addAll(unit.getImpl().getImportedMembers(excluded));
        }
        
        return members;
    }
    
    public boolean noExcludedImports() {
        UnitDefinition unit = this.getSelf().getUnit();
        return unit == null || unit.getImpl().noExcludedImports();
    }

	@Override 
	public Collection<StereotypeAnnotation> getAllAnnotations() {
	    Collection<StereotypeAnnotation> annotations = super.getAllAnnotations();
	    UnitDefinition unit = this.getSelf().getUnit();
	    Member stub = unit == null? null: unit.getImpl().getStub();
	    if (stub != null) {
	        annotations.addAll(stub.getImpl().getAnnotation());
	    }
	    return annotations;
	}

    public Collection<Member> resolveVisible(String name, 
            NamespaceDefinition namespace, boolean classifierOnly) {
        Collection<Member> members = this.resolveInScope(name, classifierOnly);
            
        // Note: If this namespace is the same as or a containing scope of the 
        // given namespace, or if the given namespace is null, then all members 
        // of this namespace are visible. If
        ElementReference namespaceReferent = 
                namespace == null? null: namespace.getImpl().getReferent();
        if (namespaceReferent != null && 
                !this.getReferent().getImpl().equals(namespaceReferent) &&
                !this.containsMember(namespaceReferent)) {
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
    
    public ElementReference resolveStereotype(QualifiedName name) {
        // First check if it is a standard stereotype.
        ElementReference stereotype = ModelNamespaceImpl.resolveStandardStereotype(name);
        if (stereotype == null) {
            QualifiedName qualification = name.getQualification();
            String stereotypeName = name.getUnqualifiedName().getName();
            if (qualification == null) {
                // If the stereotype name is unqualified, there must be exactly one
                // applied profile that contains a stereotype of that name.
                for (ElementReference profile: this.getAllAppliedProfiles()) {
                    ElementReference profileStereotype = findStereotype(stereotypeName, profile);
                    if (profileStereotype != null) {
                        if (stereotype != null) {
                            stereotype = null;
                            break;
                        }
                        stereotype = profileStereotype;
                    }
                }
            } else {
                // If the stereotype name is qualified, then there must be an
                // applied profile that is named by the qualification of
                // the stereotype and that contains a stereotype of that name.
                // NOTE: This means that if the stereotype is not directly owned by
                // the profile, then the names of any subpackages in which it is
                // nested are NOT to be included in the qualification when
                // naming the stereotype.
                qualification.getImpl().setCurrentScope(this.getOuterScope());
                ElementReference profile = qualification.getImpl().getProfileReferent();
                if (profile != null && profile.getImpl().isContainedIn(this.getAllAppliedProfiles())) {
                            stereotype = findStereotype(stereotypeName, profile);
                }
            }
        }
        if (stereotype != null) {
            // NOTE: This avoids a qualifiedNameQualifiedResolution violation for name.
            name.setReferent(Collections.singleton(stereotype));
        }
        return stereotype;
    }
    
    // This method is used to find stereotypes that are not only directly owned by
    // a profile, but also those that are indirectly nested in packages within the
    // profile (but not within nested profiles).
    private static ElementReference findStereotype(String name, ElementReference package_) {
        Collection<ElementReference> packagedElements = package_.getImpl().getOwnedMembers();
        for (ElementReference element: packagedElements) {
            if (element.getImpl().isStereotype() && name.equals(element.getImpl().getName())) {
                return element;
            }
        }
        for (ElementReference element: packagedElements) {
            if (element.getImpl().isPackage() && !element.getImpl().isProfile()) {
                ElementReference stereotype = findStereotype(name, element);
                if (stereotype != null) {
                    return stereotype;
                }
            }
        }
        return null;
    }
    
    // NOTE: This is overridden in PackageDefinitionImpl, since profiles can only be 
    // applied to packages.
    public Collection<ElementReference> getAllAppliedProfiles() {
        NamespaceDefinition outerScope = this.getOuterScope();
        return outerScope == null? new ArrayList<ElementReference>(): 
            outerScope.getImpl().getAllAppliedProfiles();
    }
    
    private boolean containsMember(ElementReference member) {
        if (member == null) {
            return false;
        } else {
            ElementReference namespace = member.getImpl().getNamespace();
            if (namespace == null) {
                ElementReference context = member.getImpl().getContext();
                if (!member.getImpl().equals(context)) {
                    namespace = context;
                }
            }
            return namespace != null && 
                    (namespace.getImpl().equals(this.getReferent()) || 
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
            for (Member member: outerScope.getImpl().resolveAsOuterScope(name, classifierOnly)) {
                if (member != null && member.getImpl().isDistinguishableFromAll(members)) {
                    members.add(member);
                }
            }
        }
        
        MemberImpl.removeDuplicates(members);

        return members;
    }
    
    public List<Member> resolveInScope(
            String name, boolean classifierOnly) {
        Collection<Member> namedMembers = null;
        if (this.noExcludedImports()) {
            namedMembers = this.getMemberMap().get(name);
        } else {
            namedMembers = new ArrayList<Member>();
            for (Member member: this.deriveMember()) {
                if (name.equals(member.getName())) {
                    namedMembers.add(member);
                }
            }
        }
        
        List<Member> selectedMembers = new ArrayList<Member>();
        
        if (namedMembers != null) {
            for (Member member: namedMembers) {
                if (!classifierOnly || 
                        member.getImpl().getReferent().getImpl().isClassifier()) {
                    selectedMembers.add(member);
                }
            }
        }
              
        return selectedMembers;
    }
    
    public Collection<Member> resolveAsOuterScope(String name, boolean classifierOnly) {
        return this.resolve(name, classifierOnly);
    }
    
    /**
     * For all visible binary associations, return association ends that conform 
     * to the given opposite end type and have the given name.
     */
    public Collection<ElementReference> resolveAssociationEnd
        (ElementReference oppositeEndType, String name) {
        Collection<ElementReference> referents = new ArrayList<ElementReference>();
        for (Member member: this.getSelf().getMember()) {
            ElementReferenceImpl referent = 
                member.getImpl().getReferent().getImpl();
            if (referent.isAssociation()) {
                List<ElementReference> associationEnds = 
                    referent.getAssociationEnds();
                if (associationEnds.size() == 2) {
                    ElementReference associationEnd1 =
                        associationEnds.get(0);
                    ElementReference associationEnd2 =
                        associationEnds.get(1);
                    if (oppositeEndType.getImpl().
                            conformsTo(associationEnd1.getImpl().getType()) &&
                        name.equals(associationEnd2.getImpl().getName())) {
                        referents.add(associationEnd2);
                    }                               
                    if (oppositeEndType.getImpl().
                            conformsTo(associationEnd2.getImpl().getType()) &&
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
    
    public boolean isModelLibrary() {
        // NOTE: A profile definition is automatically treated as a model library.
        return this.isProfile() || 
               this.isStereotyped(RootNamespace.getRootScope().getModelLibraryStereotype());
    }

    public boolean hasSubunitFor(UnitDefinition unit) {
        return this.getStubFor(unit) != null;
    }
    
    public Member getStubFor(UnitDefinition unit) {
        NamespaceDefinition definition = unit == null? null: unit.getDefinition();
        if (definition != null) {
            for (Object object: this.getSelf().getOwnedMember().toArray()) {
                Member member = (Member)object;
                if (this.matchNameForStub(member, definition) && 
                        member.getIsStub() && member.matchForStub(unit)) {
                    return member;
                }
            }
        }
        return null;
    }
    
    protected Boolean matchNameForStub(Member stub, NamespaceDefinition definition) {
        String stubName = stub.getName();
        return stubName != null && stubName.equals(definition.getName());
    }
    
    
    /**
     * Return the stub member the subunit for which has this namespace
     * definition as its definition, if any. Note that, for an activity
     * definition, the "stub" may be an external operation or activity. 
     */
    public Member getStub() {
        UnitDefinition unit = this.getSelf().getUnit();
        if (unit != null) {
            NamespaceDefinition namespace = this.getOuterScope();
            if (namespace != null) {
                return namespace.getImpl().getStubFor(unit);
            }
        }
        return null;
    }
    
    public ElementReference getStubReference() {
        Member stub = this.getStub();
        return stub == null? null: stub.getImpl().getReferent();
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

    public List<ElementReference> getParameters() {
        List<ElementReference> parameters = new ArrayList<ElementReference>();
        for (FormalParameter formalParameter: this.getFormalParameters()) {
            parameters.add(formalParameter.getImpl().getReferent());
        }
        return parameters;
    }

    public ElementReference getReturnParameter() {
        Collection<ElementReference> parameters = this.getParameters();
        for (ElementReference parameter: parameters) {
            if (parameter.getImpl().getDirection().equals("return")) {
                return parameter;
            }
        }
        return null;
    }
    
    /**
     * Overridden for activity and operation definitions, which have effective bodies.
     */
    public Block getEffectiveBody() {
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
            
            for (Object ownedMember: baseNamespace.getOwnedMember().toArray()) {
                // Note: If a boundMember is created, it will be added to
                // the given namespace.
                ((Member)ownedMember).getImpl().bind(((Member)ownedMember).getName(), 
                        self, false,
                        templateParameters, templateArguments);
            }
            
        }
    }
    
} // NamespaceDefinitionImpl
