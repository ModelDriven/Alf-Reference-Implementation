
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.omg.uml.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.units.impl.UnitDefinitionImpl;

/**
 * The definition of a namespace as an Alf unit.
 **/

public class UnitDefinition extends DocumentedElement {

	public UnitDefinition() {
		this.impl = new UnitDefinitionImpl(this);
	}

	public UnitDefinitionImpl getImpl() {
		return (UnitDefinitionImpl) this.impl;
	}

	public QualifiedName getNamespaceName() {
		return this.getImpl().getNamespaceName();
	}

	public void setNamespaceName(QualifiedName namespaceName) {
		this.getImpl().setNamespaceName(namespaceName);
	}

	public NamespaceDefinition getDefinition() {
		return this.getImpl().getDefinition();
	}

	public void setDefinition(NamespaceDefinition definition) {
		this.getImpl().setDefinition(definition);
	}

	public Collection<ImportReference> getImport() {
		return this.getImpl().getImport();
	}

	public void setImport(Collection<ImportReference> import_) {
		this.getImpl().setImport(import_);
	}

	public void addImport(ImportReference import_) {
		this.getImpl().addImport(import_);
	}

	public ElementReference getNamespace() {
		return this.getImpl().getNamespace();
	}

	public void setNamespace(ElementReference namespace) {
		this.getImpl().setNamespace(namespace);
	}

	public Boolean getIsModelLibrary() {
		return this.getImpl().getIsModelLibrary();
	}

	public void setIsModelLibrary(Boolean isModelLibrary) {
		this.getImpl().setIsModelLibrary(isModelLibrary);
	}

	public Collection<Profile> getAppliedProfile() {
		return this.getImpl().getAppliedProfile();
	}

	public void setAppliedProfile(Collection<Profile> appliedProfile) {
		this.getImpl().setAppliedProfile(appliedProfile);
	}

	public void addAppliedProfile(Profile appliedProfile) {
		this.getImpl().addAppliedProfile(appliedProfile);
	}

	/**
	 * If a unit definition has a declared namespace name, then the containing
	 * namespace for the unit is the referent for that name.
	 **/
	public boolean unitDefinitionNamespaceDerivation() {
		return this.getImpl().unitDefinitionNamespaceDerivation();
	}

	/**
	 * The declared namespace name for a unit definition, if any, must resolve
	 * to a UML namespace of an Alf unit definition. If it is an Alf unit
	 * definition, then it must have a stub for this unit definition.
	 **/
	public boolean unitDefinitionNamespace() {
		return this.getImpl().unitDefinitionNamespace();
	}

	/**
	 * A unit definition is for a model library if its associated namespace
	 * definition has a stereotype annotation for the UML standard stereotype
	 * ModelLibrary.
	 **/
	public boolean unitDefinitionIsModelLibraryDerivation() {
		return this.getImpl().unitDefinitionIsModelLibraryDerivation();
	}

	/**
	 * Unless the unit definition is a model library, it has private package
	 * import references for all the sub-packages of the Alf::Library package.
	 **/
	public boolean unitDefinitionImplicitImports() {
		return this.getImpl().unitDefinitionImplicitImports();
	}

	/**
	 * The profiles applied to a unit definition include any profiles applied to
	 * the containing namespace of the unit definition. If the unit definition
	 * is for a package, then the applied profiles for the unit definition also
	 * include the applied profiles for its associated package definition.
	 **/
	public boolean unitDefinitionAppliedProfileDerivation() {
		return this.getImpl().unitDefinitionAppliedProfileDerivation();
	}

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.unitDefinitionNamespaceDerivation()) {
			violations.add(new ConstraintViolation(
					"unitDefinitionNamespaceDerivation", this));
		}
		if (!this.unitDefinitionNamespace()) {
			violations.add(new ConstraintViolation("unitDefinitionNamespace",
					this));
		}
		if (!this.unitDefinitionIsModelLibraryDerivation()) {
			violations.add(new ConstraintViolation(
					"unitDefinitionIsModelLibraryDerivation", this));
		}
		if (!this.unitDefinitionImplicitImports()) {
			violations.add(new ConstraintViolation(
					"unitDefinitionImplicitImports", this));
		}
		if (!this.unitDefinitionAppliedProfileDerivation()) {
			violations.add(new ConstraintViolation(
					"unitDefinitionAppliedProfileDerivation", this));
		}
		QualifiedName namespaceName = this.getNamespaceName();
		if (namespaceName != null) {
			namespaceName.checkConstraints(violations);
		}
		NamespaceDefinition definition = this.getDefinition();
		if (definition != null) {
			definition.checkConstraints(violations);
		}
		for (ImportReference _import_ : this.getImport()) {
			_import_.checkConstraints(violations);
		}
	}

	public String toString() {
		return this.getImpl().toString();
	}

	public String _toString() {
		StringBuffer s = new StringBuffer(super._toString());
		Boolean isModelLibrary = this.getIsModelLibrary();
		if (isModelLibrary != null) {
			s.append(" /isModelLibrary:");
			s.append(isModelLibrary);
		}
		return s.toString();
	}

	public void print() {
		this.print("");
	}

	public void print(String prefix) {
		super.print(prefix);
		QualifiedName namespaceName = this.getNamespaceName();
		if (namespaceName != null) {
			System.out.println(prefix + " namespaceName:");
			namespaceName.print(prefix + "  ");
		}
		NamespaceDefinition definition = this.getDefinition();
		if (definition != null) {
			System.out.println(prefix + " definition:");
			definition.print(prefix + "  ");
		}
		Collection<ImportReference> import_ = this.getImport();
		if (import_ != null) {
			if (import_.size() > 0) {
				System.out.println(prefix + " import:");
			}
			for (ImportReference _import_ : import_) {
				if (_import_ != null) {
					_import_.print(prefix + "  ");
				} else {
					System.out.println(prefix + "  null");
				}
			}
		}
		ElementReference namespace = this.getNamespace();
		if (namespace != null) {
			System.out.println(prefix + " /namespace:" + namespace);
		}
		Collection<Profile> appliedProfile = this.getAppliedProfile();
		if (appliedProfile != null) {
			if (appliedProfile.size() > 0) {
				System.out.println(prefix + " /appliedProfile:");
			}
			for (Profile _appliedProfile : appliedProfile) {
				System.out.println(prefix + "  " + _appliedProfile);
			}
		}
	}
} // UnitDefinition
