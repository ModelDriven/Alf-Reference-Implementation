
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
import org.modeldriven.alf.syntax.expressions.impl.QualifiedNameImpl;
import org.modeldriven.alf.syntax.units.*;
import org.omg.uml.Profile;

import java.util.ArrayList;
import java.util.List;

/**
 * The definition of a namespace as an Alf unit.
 **/

public class UnitDefinitionImpl extends
		org.modeldriven.alf.syntax.common.impl.DocumentedElementImpl {
    
    private boolean hasImplicitImports = false;

	public UnitDefinitionImpl(UnitDefinition self) {
		super(self);
	}

	@Override
	public org.modeldriven.alf.syntax.units.UnitDefinition getSelf() {
		return (UnitDefinition) this.self;
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
	public ElementReference deriveNamespace() {
	    UnitDefinition self = this.getSelf();
	    ElementReference referent = null;
	    QualifiedName namespaceName = self.getNamespaceName();
	    if (namespaceName != null) {
            namespaceName.getImpl().setCurrentScope(ModelNamespace.getModelScope());
	        referent = namespaceName.getImpl().getNamespaceReferent();
	    }
		return referent;
	}

    /**
     * A unit definition is for a model library if its associated namespace
     * definition has a stereotype annotation for the UML standard stereotype
     * ModelLibrary.
     **/
	public Boolean deriveIsModelLibrary() {
	    // TODO: Require reference to standard UML ModelLibary stereotype.
		return this.getSelf().getDefinition().getImpl().hasAnnotation("ModelLibrary");
	}

    /**
     * The profiles applied to a unit definition include any profiles applied to
     * the containing namespace of the unit definition. If the unit definition
     * is for a package, then the applied profiles for the unit definition also
     * include the applied profiles for its associated package definition.
     **/
	public ArrayList<Profile> deriveAppliedProfile() {
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
        SyntaxElement element = self.getNamespace().getImpl().getAlf();
        return element != null && element instanceof NamespaceDefinition &&
            ((NamespaceDefinition)element).getImpl().hasSubunitFor(self);
    }

	/**
	 * Unless the unit definition is a model library, it has private package
	 * import references for all the sub-packages of the Alf::Library package.
	 **/
	public boolean unitDefinitionImplicitImports() {
		return this.getSelf().getIsModelLibrary() || this.hasImplicitImports();
	}
    
    /*
     * Helper Methods
     */

    @SuppressWarnings("unchecked")
    public List<Member> getImportedMembers() {
        UnitDefinition self = this.getSelf();
        ArrayList<Member> importedMembers = new ArrayList<Member>();
        for (ImportReference importReference: self.getImport()) {
            importedMembers.addAll(importReference.getImpl().getImportedMembers());
        }
        
        // Remove conflicts
        ArrayList<Member> ownedMembers = self.getDefinition().getOwnedMember();
        ArrayList<Member> otherMembers = (ArrayList<Member>)importedMembers.clone();
        int i = 0;
        while (otherMembers.size() > 0) {
          Member member = otherMembers.remove(0);
          if (member.getImpl().isDistinguishableFromAll(otherMembers) &&
            member.getImpl().isDistinguishableFromAll(ownedMembers)) {
            i++;
          } else {
            importedMembers.remove(i);
          }
        }

        return importedMembers;
    }
    
    public boolean isInModelLibrary() {
        UnitDefinition self = this.getSelf();
        if (self.getIsModelLibrary()) {
            return true;
        } else {
            ElementReference namespaceReference = self.getNamespace();
            if (namespaceReference == null) {
                return false;
            } else {
                NamespaceDefinition namespace = 
                    (NamespaceDefinition)namespaceReference.getImpl().getAlf();
                return namespace != null && namespace.getUnit().getImpl().isInModelLibrary();
            }
        }
    }
    
    public void addImplicitImports() {
        if (!this.hasImplicitImports() && !this.isInModelLibrary()) {
            UnitDefinition self = this.getSelf();
            QualifiedNameImpl fumlLibrary = new QualifiedName().getImpl();
            fumlLibrary.addName("FoundationalModelLibrary");
            fumlLibrary.setCurrentScope(ModelNamespace.getModelScope());
    
            PackageImportReference primitiveBehaviorsImport = new PackageImportReference();
            primitiveBehaviorsImport.setReferentName(fumlLibrary.copy().addName("PrimitiveBehaviors"));
            primitiveBehaviorsImport.setVisibility("private");
            primitiveBehaviorsImport.setUnit(self);
            self.addImport(primitiveBehaviorsImport);
    
            PackageImportReference commonImport = new PackageImportReference();
            commonImport.setReferentName(fumlLibrary.copy().addName("Common"));
            commonImport.setVisibility("private");
            commonImport.setUnit(self);
            self.addImport(commonImport);
    
            PackageImportReference basicInputOutputImport = new PackageImportReference();
            basicInputOutputImport.setReferentName(fumlLibrary.copy().addName("BasicInputOutput"));
            basicInputOutputImport.setVisibility("private");
            basicInputOutputImport.setUnit(self);
            self.addImport(basicInputOutputImport);
    
            PackageImportReference primitiveTypesImport = new PackageImportReference();
            QualifiedName primitiveTypes = new QualifiedName().getImpl().
                addName("UML").getImpl().
                addName("AuxiliaryConstructs").getImpl().
                addName("PrimitiveTypes");
            primitiveTypes.getImpl().setCurrentScope(ModelNamespace.getModelScope());
            primitiveTypesImport.setReferentName(primitiveTypes);
            primitiveTypesImport.setVisibility("private");
            primitiveTypesImport.setUnit(self);
            self.addImport(primitiveTypesImport);
            
            this.setHasImplicitImports(true);
        }
    }

} // UnitDefinitionImpl
