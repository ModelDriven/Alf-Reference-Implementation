
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package alf.syntax.namespaces;

import alf.syntax.behavioral.*;
import alf.syntax.expressions.*;
import alf.syntax.namespaces.*;
import alf.syntax.nodes.*;
import alf.syntax.structural.*;

import java.util.ArrayList;

public class UnitDefinition extends DocumentedElement {

	private QualifiedName namespace = null;
	private NamespaceDefinition definition = null;
	private ArrayList<ImportReference> imports = new ArrayList<ImportReference>();

	public void setNamespace(QualifiedName namespace) {
		this.namespace = namespace;
	} // setNamespace

	public QualifiedName getNamespace() {
		return this.namespace;
	} // getNamespace

	public void setDefinition(NamespaceDefinition definition) {
		this.definition = definition;
	} // setDefinition

	public NamespaceDefinition getDefinition() {
		return this.definition;
	} // getDefinition

	public void addImport(ImportReference importRef) {
		this.imports.add(importRef);
	} // addImport

	public ArrayList<ImportReference> getImports() {
		return this.imports;
	} // getImports

	public void print(String prefix) {
		super.print(prefix);

		if (this.getNamespace() != null) {
			this.getNamespace().printChild(prefix);
		}

		for (ImportReference importRef : this.getImports()) {
			importRef.printChild(prefix);
		}

		this.getDefinition().printChild(prefix);
	} // print

} // UnitDefinition
