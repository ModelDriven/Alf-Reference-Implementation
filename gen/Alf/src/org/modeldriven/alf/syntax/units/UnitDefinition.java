
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

import java.util.ArrayList;

/**
 * The definition of a namespace as an Alf unit.
 **/

public class UnitDefinition extends DocumentedElement implements
		IUnitDefinition {

	private IQualifiedName namespaceName = null;
	private INamespaceDefinition definition = null;
	private ArrayList<IImportReference> import_ = new ArrayList<IImportReference>();

	public IQualifiedName getNamespaceName() {
		return this.namespaceName;
	}

	public void setNamespaceName(IQualifiedName namespaceName) {
		this.namespaceName = namespaceName;
	}

	public INamespaceDefinition getDefinition() {
		return this.definition;
	}

	public void setDefinition(INamespaceDefinition definition) {
		this.definition = definition;
	}

	public ArrayList<IImportReference> getImport() {
		return this.import_;
	}

	public void setImport(ArrayList<IImportReference> import_) {
		this.import_ = import_;
	}

	public void addImport(IImportReference import_) {
		this.import_.add(import_);
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		IQualifiedName namespaceName = this.getNamespaceName();
		if (namespaceName != null) {
			namespaceName.print(prefix + " ");
		}
		INamespaceDefinition definition = this.getDefinition();
		if (definition != null) {
			definition.print(prefix + " ");
		}
		ArrayList<IImportReference> import_ = this.getImport();
		if (import_ != null) {
			for (IImportReference item : this.getImport()) {
				if (item != null) {
					item.print(prefix + " ");
				} else {
					System.out.println(prefix + " null");
				}
			}
		}
	}
} // UnitDefinition
