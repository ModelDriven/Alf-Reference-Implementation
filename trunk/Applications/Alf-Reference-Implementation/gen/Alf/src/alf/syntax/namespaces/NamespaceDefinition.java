
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
		NamespaceDefinition namespace = super.getNamespace();

		if (namespace == null && unit != null) {
			QualifiedName namespaceName = unit.getNamespace();
			if (namespaceName == null) {
				namespace = new ModelNamespace();
				namespace.addMember(this);
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
							namespace.replaceStub(this);
						}
					} else if (resolvents.size() > 0) {
						namespace = new ErrorNamespace(unit,
								"Not a namespace: " + namespaceName);
					} else {
						namespace = new ErrorNamespace(unit,
								"Ambiguous namespace: " + namespaceName);
					}
				}
			}
		}

		return namespace;
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

	public ArrayList<Member> getAllMembers() {
		Member completion = this.completeStub();

		if (completion != null && completion.isError()) {
			return completion.getAllMembers();
		} else {
			ArrayList<Member> members = this.getMembers();

			if (this.getUnit() != null) {
				members.addAll(this.getUnit().getImportedMembers());
			}

			return members;
		}
	} // getAllMembers

	public ArrayList<Member> getPublicMembers() {
		Member completion = this.completeStub();

		if (completion != null && completion.isError()) {
			return completion.getAllMembers();
		} else {
			ArrayList<Member> members = this.getMembers();

			for (Object member : members.toArray()) {
				if (!((Member) member).isPublic()) {
					members.remove(member);
				}
			}

			if (this.getUnit() != null) {
				members.addAll(this.getUnit().getImportedPublicMembers());
			}

			return members;
		}
	} // getPublicMembers

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

			NamespaceDefinition namespace = this.getNamespace();

			if (namespace != null) {
				ArrayList<Member> outerMembers = namespace.resolve(name);
				if (outerMembers.size() == 1 && outerMembers.get(0).isError()) {
					return outerMembers;
				}
				members.addAll(outerMembers);
			}

			UnitDefinition unit = this.getUnit();
			if (unit != null) {
				ArrayList<Member> imports = unit.resolveImports(name);
				if (imports.size() == 1 && imports.get(0).isError()) {
					return imports;
				}
				members.addAll(imports);
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

	public void replaceStub(Member completion) {
		ArrayList<Member> members = this.getMembers();

		for (Member member : members) {
			if (member.getName().equals(completion.getName())
					&& member.isStub()) {
				members.remove(member);
				member.setNamespace(null);
				this.addMember(completion);
				return;
			}
		}
	} // replaceStub

	public Member completeStub() {
		Member completion = super.completeStub();

		if (completion != null && !completion.isError()
				&& completion instanceof NamespaceDefinition) {
			for (Member member : ((NamespaceDefinition) completion)
					.getMembers()) {
				this.addMember(member);
			}
			this.setUnit(((NamespaceDefinition) completion).getUnit());
		}

		return completion;
	} // completeStub

	public NamespaceDefinition getRootNamespace() {
		return this.getNamespace().getRootNamespace();
	} // getRootNamespace

} // NamespaceDefinition
