
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

	public NamespaceDefinition getNamespace() {
		UnitDefinition unit = this.getUnit();

		if (super.getNamespace() == null && unit != null
				&& unit.getNamespace() != null) {
			QualifiedName namespace = unit.getNamespace();
			ArrayList<Member> resolvents = namespace.resolve(null);
			if (resolvents.size() == 1 && resolvents.get(0).isError()) {
				this.setNamespace(new ErrorNamespace(this,
						(ErrorMember) resolvents.get(0)));
			} else {
				for (Object m : resolvents.toArray()) {
					if (!(m instanceof NamespaceDefinition)) {
						resolvents.remove(m);
					}
				}
				if (resolvents.size() == 1) {
					this.setNamespace((NamespaceDefinition) resolvents.get(0));
				} else if (resolvents.size() > 0) {
					this.setNamespace(new ErrorNamespace(unit,
							"Not a namespace: " + namespace));
				} else {
					this.setNamespace(new ErrorNamespace(unit,
							"Ambiguous namespace: " + namespace));
				}
			}
		}

		return super.getNamespace();
	} // getNamespace

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

	public ArrayList<Member> resolvePublic(String name, boolean allowPackageOnly) {
		ArrayList<Member> publicMembers = new ArrayList<Member>();

		for (Member member : this.getMembers()) {
			if (member.isPublic() || allowPackageOnly && member.isPackageOnly()) {
				publicMembers.add(member);
			}
		}

		UnitDefinition unit = this.getUnit();
		if (unit != null) {
			publicMembers.addAll(unit.resolvePublicImports(name));
		}

		return publicMembers;
	} // resolvePublic

	public ArrayList<Member> resolve(QualifiedName qualifiedName) {
		return qualifiedName.resolve(this);
	} // resolve

} // NamespaceDefinition
