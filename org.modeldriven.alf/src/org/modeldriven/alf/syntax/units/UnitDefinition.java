/*******************************************************************************
 * Copyright 2011, 2017 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.units;

import org.modeldriven.alf.parser.ParsedElement;
import org.modeldriven.alf.parser.Parser;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.uml.Profile;
import java.util.Collection;
import org.modeldriven.alf.syntax.units.impl.UnitDefinitionImpl;

/**
 * The definition of a namespace as an Alf unit.
 **/

public class UnitDefinition extends DocumentedElement {

	public UnitDefinition() {
		this.impl = new UnitDefinitionImpl(this);
	}

	public UnitDefinition(Parser parser) {
		this();
		this.init(parser);
	}

	public UnitDefinition(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
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

    @Override
    public void _addExternalReferences(Collection<ExternalElementReference> references) {
        super._addExternalReferences(references);
        addExternalReferencesFor(references, this.getNamespaceName());
        addExternalReferencesFor(references, this.getDefinition());
        addExternalReferencesFor(references, this.getImport());
    }

	@Override
    public void _deriveAll() {
		this.getNamespace();
		this.getIsModelLibrary();
		this.getAppliedProfile();
		super._deriveAll();
		QualifiedName namespaceName = this.getNamespaceName();
		if (namespaceName != null) {
			namespaceName.deriveAll();
		}
		NamespaceDefinition definition = this.getDefinition();
		if (definition != null) {
			definition.deriveAll();
		}
		Collection<ImportReference> import_ = this.getImport();
		if (import_ != null) {
			for (Object _import_ : import_.toArray()) {
				((ImportReference) _import_).deriveAll();
			}
		}
	}

	@Override
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
		Collection<ImportReference> import_ = this.getImport();
		if (import_ != null) {
			for (Object _import_ : import_.toArray()) {
				((ImportReference) _import_).checkConstraints(violations);
			}
		}
	}

	@Override
    public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		if (includeDerived) {
			s.append(" /isModelLibrary:");
			s.append(this.getIsModelLibrary());
		}
		return s.toString();
	}

	@Override
    public void print() {
		this.print("", false);
	}

	@Override
    public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	@Override
    public void print(String prefix, boolean includeDerived) {
		super.print(prefix, includeDerived);
		QualifiedName namespaceName = this.getNamespaceName();
		if (namespaceName != null) {
			System.out.println(prefix + " namespaceName:");
			namespaceName.print(prefix + "  ", includeDerived);
		}
		NamespaceDefinition definition = this.getDefinition();
		if (definition != null) {
			System.out.println(prefix + " definition:");
			definition.print(prefix + "  ", includeDerived);
		}
		Collection<ImportReference> import_ = this.getImport();
		if (import_ != null && import_.size() > 0) {
			System.out.println(prefix + " import:");
			for (Object _object : import_.toArray()) {
				ImportReference _import_ = (ImportReference) _object;
				if (_import_ != null) {
					_import_.print(prefix + "  ", includeDerived);
				} else {
					System.out.println(prefix + "  null");
				}
			}
		}
		if (includeDerived) {
			ElementReference namespace = this.getNamespace();
			if (namespace != null) {
				System.out.println(prefix + " /namespace:"
						+ namespace.toString(includeDerived));
			}
		}
		if (includeDerived) {
			Collection<Profile> appliedProfile = this.getAppliedProfile();
			if (appliedProfile != null && appliedProfile.size() > 0) {
				System.out.println(prefix + " /appliedProfile:");
				for (Object _object : appliedProfile.toArray()) {
					Profile _appliedProfile = (Profile) _object;
					System.out.println(prefix + "  "
							+ _appliedProfile.toString(includeDerived));
				}
			}
		}
	}
} // UnitDefinition
