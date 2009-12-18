
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

public class UnitDefinition extends DocumentedNode {

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

	public void setAnnotations(StereotypeAnnotationList annotations) {
		this.getDefinition().setAnnotations(annotations);
	} // setAnnotations

	public StereotypeAnnotationList getAnnotations() {
		return this.getDefinition().getAnnotations();
	} // getAnnotations

	public void addImport(ImportReference importRef) {
		this.imports.add(importRef);
		importRef.setUnit(this);
	} // addImport

	public ArrayList<ImportReference> getImports() {
		return this.imports;
	} // getImports

	public void addImplicitImports() {
		// System.out.println("addImplicitImports: " +
		// this.getDefinition().getQualifiedName());
		QualifiedName fumlLibrary = new QualifiedName();
		// fumlLibrary.setIsAbsolute();
		fumlLibrary.addName("FoundationalModelLibrary");

		QualifiedName primitiveBehaviors = fumlLibrary.copy();
		primitiveBehaviors.addName("PrimitiveBehaviors");
		ImportReference primitiveBehaviorsImport = new ImportReference(
				primitiveBehaviors);
		primitiveBehaviorsImport.setVisibility("private");
		primitiveBehaviorsImport.setIsPackageImport();
		this.addImport(primitiveBehaviorsImport);

		QualifiedName common = fumlLibrary.copy();
		common.addName("Common");
		ImportReference commonImport = new ImportReference(common);
		commonImport.setVisibility("private");
		commonImport.setIsPackageImport();
		this.addImport(commonImport);

		QualifiedName basicInputOutput = fumlLibrary.copy();
		basicInputOutput.addName("BasicInputOutput");
		ImportReference basicInputOutputImport = new ImportReference(
				basicInputOutput);
		basicInputOutputImport.setVisibility("private");
		basicInputOutputImport.setIsPackageImport();
		this.addImport(basicInputOutputImport);

		QualifiedName primitiveTypes = new QualifiedName();
		// primitiveTypes.setIsAbsolute();
		primitiveTypes.addName("UML");
		primitiveTypes.addName("AuxiliaryConstructs");
		primitiveTypes.addName("PrimitiveTypes");
		ImportReference primitiveTypesImport = new ImportReference(
				primitiveTypes);
		primitiveTypesImport.setVisibility("private");
		primitiveTypesImport.setIsPackageImport();
		this.addImport(primitiveTypesImport);
	} // addImplicitImports

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

		return this.removeConflicts(importedMembers);
	} // getImportedMembers

	public ArrayList<Member> getImportedPublicMembers() {
		ArrayList<Member> importedMembers = new ArrayList<Member>();

		for (ImportReference importRef : this.getImports()) {
			if (importRef.isPublic()) {
				importedMembers.addAll(importRef.getMembers());
			}
		}

		return this.removeConflicts(importedMembers);
	} // getImportedPublicMembers

	public ArrayList<Member> removeConflicts(ArrayList<Member> members) {
		NamespaceDefinition namespace = this.getDefinition();
		ArrayList<Member> ownedMembers = namespace.getMembers();
		ArrayList<Member> otherMembers = (ArrayList<Member>) members.clone();
		int i = 0;
		while (otherMembers.size() > 0) {
			Member member = otherMembers.remove(0);
			if (member.isDistinguishableFromAll(otherMembers, namespace)
					&& member.isDistinguishableFromAll(ownedMembers, namespace)) {
				i++;
			} else {
				members.remove(i);
			}
		}

		return members;
	} // removeConflicts

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

	public NamespaceDefinition getModelNamespace() {
		return this.getDefinition().getModelNamespace();
	} // getModelNamespace

	public ArrayList<String> getImportedNamesOfMember(Member member) {
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<ImportReference> imports = this.getImports();

		for (ImportReference importRef : imports) {
			if (!importRef.isPackageImport()
					&& importRef.getReferent() == member) {
				names.add(importRef.getName());
			}
		}

		if (names.size() == 0) {
			for (ImportReference importRef : imports) {
				if (importRef.isPackageImport()) {
					NamespaceDefinition referent = (NamespaceDefinition) importRef
							.getReferent();
					if (referent.getPublicMembers().contains(member)) {
						names.addAll(referent.getNamesOfMember(member));
					}
				}
			}
		}

		return names;
	} // getImportedNamesOfMember

} // UnitDefinition
