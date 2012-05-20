
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.units.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.common.impl.DocumentedElementImpl;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;
import org.modeldriven.alf.uml.Profile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The definition of a namespace as an Alf unit.
 **/

public class UnitDefinitionImpl extends DocumentedElementImpl {
    
	private QualifiedName namespaceName = null;
	private NamespaceDefinition definition = null;
	private Collection<ImportReference> import_ = new ArrayList<ImportReference>();
	private ElementReference namespace = null; // DERIVED
	private Boolean isModelLibrary = null; // DERIVED
	private Collection<Profile> appliedProfile = null; // DERIVED

    private boolean hasImplicitImports = false;

	public UnitDefinitionImpl(UnitDefinition self) {
		super(self);
	}

	@Override
	public UnitDefinition getSelf() {
		return (UnitDefinition) this.self;
	}
	
	@Override
	public String toString(boolean includeDerived) {
	    return super.toString(includeDerived) + " definition:(" + this.getSelf().getDefinition() + ")";
	}

	public QualifiedName getNamespaceName() {
		return this.namespaceName;
	}

	public void setNamespaceName(QualifiedName namespaceName) {
		this.namespaceName = namespaceName;
        this.namespaceName.getImpl().setCurrentScope(RootNamespace.getRootScope());
	}

	public NamespaceDefinition getDefinition() {
		return this.definition;
	}

	public void setDefinition(NamespaceDefinition definition) {
		this.definition = definition;
	}

	public Collection<ImportReference> getImport() {
		return this.import_;
	}

	public void setImport(Collection<ImportReference> import_) {
		this.import_ = import_;
	}

	public void addImport(ImportReference import_) {
		this.import_.add(import_);
	}

	public ElementReference getNamespace() {
		if (this.namespace == null) {
			this.setNamespace(this.deriveNamespace());
		}
		return this.namespace;
	}

	public void setNamespace(ElementReference namespace) {
		this.namespace = namespace;
	}

	public Boolean getIsModelLibrary() {
		if (this.isModelLibrary == null) {
			this.setIsModelLibrary(this.deriveIsModelLibrary());
		}
		return this.isModelLibrary;
	}

	public void setIsModelLibrary(Boolean isModelLibrary) {
		this.isModelLibrary = isModelLibrary;
	}

	public Collection<Profile> getAppliedProfile() {
		if (this.appliedProfile == null) {
			this.setAppliedProfile(this.deriveAppliedProfile());
		}
		return this.appliedProfile;
	}

	public void setAppliedProfile(Collection<Profile> appliedProfile) {
		this.appliedProfile = appliedProfile;
	}

	public void addAppliedProfile(Profile appliedProfile) {
		this.appliedProfile.add(appliedProfile);
	}

    public boolean hasImplicitImports() {
        return hasImplicitImports;
    }

    public void setHasImplicitImports(boolean hasImplicitImports) {
        this.hasImplicitImports = hasImplicitImports;
    }

    /**
     * If a unit definition has a declared namespace name, then the containing
     * namespace for the unit is the referent for that name.
     **/
	protected ElementReference deriveNamespace() {
	    UnitDefinition self = this.getSelf();
	    ElementReference referent = null;
	    QualifiedName namespaceName = self.getNamespaceName();
	    if (namespaceName != null) {
	        referent = namespaceName.getImpl().getNamespaceReferent();
	    }
		return referent;
	}

    /**
     * A unit definition is for a model library if its associated namespace
     * definition has a stereotype annotation for the UML standard stereotype
     * ModelLibrary.
     **/
	protected Boolean deriveIsModelLibrary() {
	    // TODO: Require reference to standard UML ModelLibary stereotype.
	    NamespaceDefinition definition = this.getSelf().getDefinition();
		return definition != null && definition.getImpl().hasAnnotation("ModelLibrary");
	}

    /**
     * The profiles applied to a unit definition include any profiles applied to
     * the containing namespace of the unit definition. If the unit definition
     * is for a package, then the applied profiles for the unit definition also
     * include the applied profiles for its associated package definition.
     **/
	protected Collection<Profile> deriveAppliedProfile() {
	    // TODO: Implement profile application.
		return new ArrayList<Profile>(); // STUB
	}
	
	/*
	 * Derivations
	 */

	public boolean unitDefinitionNamespaceDerivation() {
		this.getSelf().getNamespace();
		return true;
	}

	public boolean unitDefinitionIsModelLibraryDerivation() {
		this.getSelf().getIsModelLibrary();
		return true;
	}

    public boolean unitDefinitionAppliedProfileDerivation() {
        this.getSelf().getAppliedProfile();
        return true;
    }

    /**
     * The declared namespace name for a unit definition, if any, must resolve
     * to a UML namespace or an Alf unit definition. If it is an Alf unit
     * definition, then it must have a stub for this unit definition.
     **/
    public boolean unitDefinitionNamespace() {
        UnitDefinition self = this.getSelf();
        ElementReference namespace = self.getNamespace();
        if (namespace == null) {
            return true;
        } else if (!namespace.getImpl().isNamespace()) {
            return false;
        } else {
            NamespaceDefinition alfNamespace = (NamespaceDefinition)namespace.getImpl().getAlf();
            return alfNamespace == null || alfNamespace.getImpl().hasSubunitFor(self);
        }
    }

	/**
	 * Unless the unit definition is a model library, it has private package
	 * import references for all the sub-packages of the Alf::Library package.
	 **/
	public boolean unitDefinitionImplicitImports() {
        // Note: Is is only necessary to add implicit imports to model units,
        // since subunits will have access to these imports from their
        // containing scope.
	    UnitDefinition self = this.getSelf();
		return this.hasImplicitImports() || self.getNamespaceName() != null ||
                    self.getIsModelLibrary();
	}
    
    /*
     * Helper Methods
     */

    public List<Member> getImportedMembers() {
        UnitDefinition self = this.getSelf();
        
        ArrayList<Member> importedMembers = new ArrayList<Member>();
        for (ImportReference importReference: self.getImport()) {
            importedMembers.addAll(importReference.getImpl().getImportedMembers());
        }
        MemberImpl.removeDuplicates(importedMembers);
        
        // Remove conflicts
        NamespaceDefinition definition = self.getDefinition();
        if (definition != null) {
            Collection<Member> ownedMembers = definition.getOwnedMember();
            ArrayList<Member> otherMembers = new ArrayList<Member>(importedMembers);
            otherMembers = new ArrayList<Member>(importedMembers);
            int i = 0;
            for (Member member: otherMembers) {
              if (member.getImpl().isDistinguishableFromAll(otherMembers) &&
                member.getImpl().isDistinguishableFromAll(ownedMembers)) {
                i++;
              } else {
                importedMembers.remove(i);
              }
            }
        }
        
        return importedMembers;
    }
    
    public Member getStub() {
        UnitDefinition self = this.getSelf();
        ElementReference namespaceReference = self.getNamespace();
        NamespaceDefinition namespace = namespaceReference == null? null: 
            namespaceReference.getImpl().asNamespace();
        return namespace == null? null: namespace.getImpl().getStubFor(self);
    }
    
    /**
     * Resolves the ownership of this unit as by its stub or, if the unit is not
     * a subunit, by the model scope namespace.
     * 
     * @return True if the unit was resolved as a subunit, false otherwise.
     */
    public Boolean resolveStub() {
        UnitDefinition self = this.getSelf();
        Member stub = self.getImpl().getStub();
        if (stub != null) {
            stub.setSubunit(self);
            return true;
        } else {
            RootNamespace.getModelScope(self).addOwnedMember(self.getDefinition());
            return false;
        }
    }
    
    public void addImplicitImports() {
        UnitDefinition self = this.getSelf();
        if (!this.hasImplicitImports() && self.getNamespaceName() == null &&
                !self.getIsModelLibrary()) {
            this.addImplicitImport(RootNamespace.getPrimitiveTypes());
            this.addImplicitImport(RootNamespace.getPrimitiveBehaviors());
            this.addImplicitImport(RootNamespace.getBasicInputOutput());
            this.addImplicitImport(RootNamespace.getCollectionFunctions());
            this.addImplicitImport(RootNamespace.getCollectionClasses());   
            this.setHasImplicitImports(true);
        }
    }
    
    public void addImplicitImport(QualifiedName packageName) {
        UnitDefinition self = this.getSelf();
        for (ImportReference importReference: self.getImport()) {
            if (importReference instanceof PackageImportReference &&
                    importReference.getReferentName().getImpl().equals(packageName)) {
                return;
            }
        }      
        PackageImportReference importReference = new PackageImportReference();
        importReference.setReferentName(packageName);
        importReference.setVisibility("private");
        importReference.setUnit(self);
        self.addImport(importReference);
    }
    
    @Override
    public void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof UnitDefinition) {
            UnitDefinition self = this.getSelf();
            UnitDefinition baseUnit = (UnitDefinition)base;
            
            self.setNamespaceName(baseUnit.getNamespaceName());
            self.setImport(baseUnit.getImport());
            self.setNamespace(baseUnit.getNamespace());
            self.setIsModelLibrary(baseUnit.getIsModelLibrary());
            
            this.setHasImplicitImports(baseUnit.getImpl().hasImplicitImports());
            
            // Note: The bound unit definition namespace is set in the binding 
            // of the definition.
        }
    }

} // UnitDefinitionImpl
