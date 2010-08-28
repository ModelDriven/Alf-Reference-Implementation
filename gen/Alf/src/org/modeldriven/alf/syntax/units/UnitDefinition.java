
/*
 * Copyright 2010 Data Access Technologies, Inc. (Model Driven Solutions)
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

import java.util.ArrayList;

/**
 * The definition of a namespace as an Alf unit.
 **/

public class UnitDefinition extends DocumentedElement {

	private QualifiedName namespaceName = null;
	private NamespaceDefinition definition = null;
	private ArrayList<ImportReference> import_ = new ArrayList<ImportReference>();
	private ElementReference namespace = null; // DERIVED
	private boolean isModelLibrary = false; // DERIVED
	private ArrayList<Profile> appliedProfile = new ArrayList<Profile>(); // DERIVED

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

	public ArrayList<ImportReference> getImport() {
		return this.import_;
	}

	public void setImport(ArrayList<ImportReference> import_) {
		this.import_ = import_;
	}

	public void addImport(ImportReference import_) {
		this.import_.add(import_);
	}

	public ElementReference getNamespace() {
		return this.namespace;
	}

	public void setNamespace(ElementReference namespace) {
		this.namespace = namespace;
	}

	public boolean getIsModelLibrary() {
		return this.isModelLibrary;
	}

	public void setIsModelLibrary(boolean isModelLibrary) {
		this.isModelLibrary = isModelLibrary;
	}

	public ArrayList<Profile> getAppliedProfile() {
		return this.appliedProfile;
	}

	public void setAppliedProfile(ArrayList<Profile> appliedProfile) {
		this.appliedProfile = appliedProfile;
	}

	public void addAppliedProfile(Profile appliedProfile) {
		this.appliedProfile.add(appliedProfile);
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		if (this.namespaceName != null) {
			this.namespaceName.print(prefix + " ");
		}
		if (this.definition != null) {
			this.definition.print(prefix + " ");
		}
		for (ImportReference import_ : this.getImport()) {
			if (import_ != null) {
				import_.print(prefix + " ");
			} else {
				System.out.println(prefix + " null");
			}
		}
	}
} // UnitDefinition
