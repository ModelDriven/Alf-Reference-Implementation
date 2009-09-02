
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
		definition.setUnit(this);
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

	public String toString() {
		return super.toString() + " name:" + this.getDefinition().getName();
	} // toString

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

	public ArrayList<Member> getAllMembers() {
		return this.getDefinition().getAllMembers();
	} // getAllMembers

	public ArrayList<Member> getImportedMembers() {
		ArrayList<Member> importedMembers = new ArrayList<Member>();

		for (ImportReference importRef : this.getImports()) {
			importedMembers.addAll(importRef.getMembers());
		}

		return importedMembers;
	} // getImportedMembers

	public ArrayList<Member> resolveImports(String name) {
		ArrayList<Member> members = new ArrayList<Member>();

		for (ImportReference importRef : this.getImports()) {
			ArrayList<Member> imports = importRef.resolve(name);
			if (imports.size() == 1 && imports.get(0).isError()) {
				return imports;
			}
			members.addAll(imports);
		}

		return members;
	} // resolveImports

	public ArrayList<Member> resolvePublicImports(String name) {
		ArrayList<Member> members = new ArrayList<Member>();

		for (ImportReference importRef : this.getImports()) {
			if (importRef.isPublic()) {
				ArrayList<Member> imports = importRef.resolve(name);
				if (imports.size() == 1 && imports.get(0).isError()) {
					return imports;
				}
				members.addAll(imports);
			}
		}

		return members;
	} // resolvePublicImports

	public ArrayList<Member> resolve(String name) {
		return this.getDefinition().resolve(name);
	} // resolve

} // UnitDefinition
