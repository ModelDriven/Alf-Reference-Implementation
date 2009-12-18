
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

public abstract class NamespaceDefinition extends Member {

	private ArrayList<Member> ownedMembers = new ArrayList<Member>();
	private UnitDefinition unit = null;

	public NamespaceDefinition getNamespace() {
		UnitDefinition unit = this.getUnit();
		NamespaceDefinition namespace = super.getNamespace();

		if (namespace == null && unit != null) {
			QualifiedName namespaceName = unit.getNamespace();
			if (namespaceName == null) {
				namespace = new ModelNamespace();
				namespace.addOwnedMember(this);
			} else {
				ArrayList<Member> resolvents = namespaceName
						.resolve(new ModelNamespace());
				if (resolvents.size() == 1 && resolvents.get(0).isError()) {
					namespace = new ErrorNamespace(unit,
							(ErrorMember) resolvents.get(0));
				} else {
					for (Object m : resolvents.toArray()) {
						if (!(m instanceof NamespaceDefinition)) {
							resolvents.remove(m);
						}
					}
					if (resolvents.size() == 1) {
						namespace = (NamespaceDefinition) resolvents.get(0);
						Member completion = namespace.completeStub();
						if (completion != null && completion.isError()) {
							namespace = new ErrorNamespace(unit,
									(ErrorMember) completion);
						} else {
							completion = namespace.replaceStub(this);
							if (completion.isError()) {
								namespace = new ErrorNamespace(unit,
										(ErrorMember) completion);
							}
						}
					} else if (resolvents.size() > 0) {
						namespace = new ErrorNamespace(unit,
								"Not a namespace: " + namespaceName);
					} else {
						namespace = new ErrorNamespace(unit,
								"Ambiguous namespace: " + namespaceName);
					}
					this.setNamespace(namespace);
				}
			}
		}

		return namespace;
	} // getNamespace

	public void addOwnedMember(Member member) {
		this.ownedMembers.add(member);
		member.setNamespace(this);

	} // addOwnedMember

	public ArrayList<Member> getOwnedMembers() {
		return this.ownedMembers;
	} // getOwnedMembers

	public ArrayList<Member> getMembers() {
		return this.getOwnedMembers();
	} // getMembers

	public ArrayList<Member> getAllMembers() {
		Member completion = this.completeStub();

		if (completion != null && completion.isError()) {
			return completion.getAllMembers();
		} else {
			ArrayList<Member> allMembers = (ArrayList<Member>) this
					.getMembers().clone();

			if (this.getUnit() != null) {
				allMembers.addAll(this.getUnit().getImportedMembers());
			}

			return allMembers;
		}
	} // getAllMembers

	public ArrayList<Member> getPublicMembers() {
		Member completion = this.completeStub();

		if (completion != null && completion.isError()) {
			return completion.getAllMembers();
		} else {
			ArrayList<Member> publicMembers = new ArrayList<Member>();

			for (Member member : this.getMembers()) {
				if (((Member) member).isPublic()) {
					publicMembers.add(member);
				}
			}

			if (this.getUnit() != null) {
				publicMembers.addAll(this.getUnit().getImportedPublicMembers());
			}

			return publicMembers;
		}
	} // getPublicMembers

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

		if (unit == null || super.getNamespace() != null) {
			return super.getQualifiedName();
		} else {
			QualifiedName qualifiedName = unit.getNamespace();

			if (qualifiedName == null) {
				qualifiedName = new QualifiedName(); // Model scope
			} else {
				qualifiedName = qualifiedName.copy();
			}

			qualifiedName.addName(this.getName());
			return qualifiedName;
		}

	} // getQualifiedName

	public ArrayList<Member> resolve(String name) {
		ArrayList<Member> members = new ArrayList<Member>();

		Member completion = this.completeStub();

		if (completion != null && completion.isError()) {
			members.add(completion);
		} else {
			for (Member member : this.getMembers()) {
				if (member.getName().equals(name)) {
					members.add(member);
				}
			}

			UnitDefinition unit = this.getUnit();
			if (unit != null) {
				ArrayList<Member> imports = unit.resolveImports(name);
				if (imports.size() == 1 && imports.get(0).isError()) {
					return imports;
				}
				members.addAll(imports);
			}

			NamespaceDefinition namespace = this.getNamespace();

			if (namespace != null) {
				ArrayList<Member> outerMembers = namespace.resolve(name);
				if (outerMembers.size() == 1 && outerMembers.get(0).isError()) {
					return outerMembers;
				}
				for (Member outerMember : outerMembers) {
					if (outerMember.isDistinguishableFromAll(members, this)) {
						members.add(outerMember);
					}
				}
			}

		}

		return members;
	} // resolve

	public ArrayList<Member> resolvePublic(String name, boolean allowPackageOnly) {
		// System.out.println("resolvePublic: " + this.getQualifiedName() +
		// "...");

		ArrayList<Member> publicMembers = new ArrayList<Member>();

		Member completion = this.completeStub();

		if (completion != null && completion.isError()) {
			publicMembers.add(completion);
		} else {
			for (Member member : this.getMembers()) {
				if (member.getName().equals(name)
						&& (member.isPublic() || allowPackageOnly
								&& member.isPackageOnly())) {
					publicMembers.add(member);
				}
			}

			UnitDefinition unit = this.getUnit();
			if (unit != null) {
				ArrayList<Member> imports = unit.resolvePublicImports(name);
				if (imports.size() == 1 && imports.get(0).isError()) {
					return imports;
				}
				publicMembers.addAll(imports);
			}
		}

		return publicMembers;
	} // resolvePublic

	public ArrayList<Member> resolve(QualifiedName qualifiedName) {
		return qualifiedName.resolve(this);
	} // resolve

	public Member replaceStub(Member completion) {
		ArrayList<Member> members = this.getMembers();

		for (Member member : members) {
			if (member.getName().equals(completion.getName())
					&& member.isStub() && member.isCompletedBy(completion)) {
				return member.completeStub(completion);
			}
		}

		return new ErrorMember(completion, "Invalid subunit: "
				+ completion.getQualifiedName());
	} // replaceStub

	public Member completeStub(Member completion) {
		completion = super.completeStub(completion);

		if (!completion.isError() && completion instanceof NamespaceDefinition) {
			for (Member member : ((NamespaceDefinition) completion)
					.getOwnedMembers()) {
				this.addOwnedMember(member);
			}

			UnitDefinition unit = ((NamespaceDefinition) completion).getUnit();
			if (unit != null) {
				unit.setDefinition(this);
			}
		}

		return completion;
	} // completeStub

	public NamespaceDefinition getModelNamespace() {
		return this.getNamespace().getModelNamespace();
	} // getModelNamespace

	public ArrayList<String> getNamesOfMember(Member member) {
		ArrayList<String> names = new ArrayList<String>();

		if (this.getOwnedMembers().contains(member)) {
			names.add(member.getName());
		} else if (this.getUnit() != null) {
			names = this.getUnit().getImportedNamesOfMember(member);
		}

		return names;
	} // getNamesOfMember

} // NamespaceDefinition
