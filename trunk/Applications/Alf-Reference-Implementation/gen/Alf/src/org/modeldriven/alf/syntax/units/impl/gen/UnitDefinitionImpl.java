
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units.impl.gen;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.omg.uml.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The definition of a namespace as an Alf unit.
 **/

public class UnitDefinitionImpl extends
		org.modeldriven.alf.syntax.common.impl.gen.DocumentedElementImpl {

	private QualifiedName namespaceName = null;
	private NamespaceDefinition definition = null;
	private Collection<ImportReference> import_ = new ArrayList<ImportReference>();
	private ElementReference namespace = null; // DERIVED
	private Boolean isModelLibrary = null; // DERIVED
	private Collection<Profile> appliedProfile = null; // DERIVED

	public UnitDefinitionImpl(UnitDefinition self) {
		super(self);
	}

	public UnitDefinition getSelf() {
		return (UnitDefinition) this.self;
	}

	public QualifiedName getNamespaceName() {
		return this.namespaceName;
	}

	public void setNamespaceName(QualifiedName namespaceName) {
		this.namespaceName = namespaceName;
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

	protected ElementReference deriveNamespace() {
		return null; // STUB
	}

	protected Boolean deriveIsModelLibrary() {
		return null; // STUB
	}

	protected Collection<Profile> deriveAppliedProfile() {
		return null; // STUB
	}

	/**
	 * If a unit definition has a declared namespace name, then the containing
	 * namespace for the unit is the referent for that name.
	 **/
	public boolean unitDefinitionNamespaceDerivation() {
		this.getSelf().getNamespace();
		return true;
	}

	/**
	 * The declared namespace name for a unit definition, if any, must resolve
	 * to a UML namespace of an Alf unit definition. If it is an Alf unit
	 * definition, then it must have a stub for this unit definition.
	 **/
	public boolean unitDefinitionNamespace() {
		return true;
	}

	/**
	 * A unit definition is for a model library if its associated namespace
	 * definition has a stereotype annotation for the UML standard stereotype
	 * ModelLibrary.
	 **/
	public boolean unitDefinitionIsModelLibraryDerivation() {
		this.getSelf().getIsModelLibrary();
		return true;
	}

	/**
	 * Unless the unit definition is a model library, it has private package
	 * import references for all the sub-packages of the Alf::Library package.
	 **/
	public boolean unitDefinitionImplicitImports() {
		return true;
	}

	/**
	 * The profiles applied to a unit definition include any profiles applied to
	 * the containing namespace of the unit definition. If the unit definition
	 * is for a package, then the applied profiles for the unit definition also
	 * include the applied profiles for its associated package definition.
	 **/
	public boolean unitDefinitionAppliedProfileDerivation() {
		this.getSelf().getAppliedProfile();
		return true;
	}

} // UnitDefinitionImpl
