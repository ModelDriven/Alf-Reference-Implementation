
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

public abstract class NamespaceDefinition extends Member {

	private ArrayList<Member> members = new ArrayList<Member>();
	private UnitDefinition unit = null;

	public void addMember(Member member) {
		this.members.add(member);
		member.setNamespace(this);

	} // addMember

	public ArrayList<Member> getMembers() {
		return this.members;
	} // getMembers

	public void setUnit(UnitDefinition unit) {
		this.unit = unit;
	} // setUnit

	public UnitDefinition getUnit() {
		return this.unit;
	} // getUnit

	public void print(String prefix) {
		super.print(prefix);

		for (Member member : this.getMembers()) {
			member.printChild(prefix);
		}
	} // print

	public QualifiedName getQualifiedName() {
		UnitDefinition unit = this.getUnit();
		if (unit == null || unit.getNamespace() == null) {
			return super.getQualifiedName();
		} else {
			QualifiedName qualifiedName = unit.getNamespace().copy();
			qualifiedName.addName(this.getName());
			return qualifiedName;
		}
	} // getQualifiedName

	public ArrayList<Member> getAllMembers() {
		ArrayList<Member> members = this.getMembers();

		if (this.getUnit() != null) {
			members.addAll(this.getUnit().getImportedMembers());
		}

		return members;
	} // getAllMembers

	public ArrayList<Member> resolve(String name) {
		ArrayList<Member> members = new ArrayList<Member>();

		for (Member member : this.getMembers()) {
			if (member.getName().equals(name)) {
				members.add(member);
			}
		}

		if (this.getNamespace() != null) {
			ArrayList<Member> outerMembers = this.getNamespace().resolve(name);
			members.addAll(outerMembers);
		}

		if (this.getUnit() != null) {
			ArrayList<Member> imports = this.getUnit().resolveImports(name);
			if (imports.size() == 1 && imports.get(0).isError()) {
				return imports;
			}
			members.addAll(imports);
		}

		return members;
	} // resolve

	public ArrayList<Member> resolve(QualifiedName qualifiedName) {
		return qualifiedName.resolve(this);
	} // resolve

} // NamespaceDefinition
