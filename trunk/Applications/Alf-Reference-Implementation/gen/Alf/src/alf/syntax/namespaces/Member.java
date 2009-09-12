
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package alf.syntax.namespaces;

import alf.nodes.*;
import alf.syntax.SyntaxNode;
import alf.syntax.behavioral.*;
import alf.syntax.expressions.*;
import alf.syntax.namespaces.*;
import alf.syntax.structural.*;

import java.util.ArrayList;

public abstract class Member extends DocumentedNode {

	private String name = "";
	private String visibility = "";
	private boolean isStub = false;
	private NamespaceDefinition namespace = null;

	public void setName(String name) {
		this.name = name;
	} // setName

	public String getName() {
		return this.name;
	} // getName

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	} // setVisibility

	public String getVisibility() {
		return this.visibility;
	} // getVisibility

	public void setIsStub() {
		this.isStub = true;
	} // setIsStub

	public boolean isStub() {
		return this.isStub;
	} // isStub

	public void setNamespace(NamespaceDefinition namespace) {
		this.namespace = namespace;
	} // setNamespace

	public NamespaceDefinition getNamespace() {
		return this.namespace;
	} // getNamespace

	public String toString() {
		return super.toString() + " name:" + this.getName() + " visibility:"
				+ this.getVisibility() + " isStub:" + this.isStub();
	} // toString

	public QualifiedName getQualifiedName() {
		QualifiedName qualifiedName;

		if (this.getNamespace() == null) {
			qualifiedName = new QualifiedName();
		} else {
			qualifiedName = this.getNamespace().getQualifiedName().copy();
		}

		qualifiedName.addName(this.getName());
		return qualifiedName;
	} // getQualifiedName

	public ArrayList<Member> getAllMembers() {
		return new ArrayList<Member>();
	} // getAllMembers

	public ArrayList<Member> getPublicMembers() {
		return this.getAllMembers();
	} // getPublicMembers

	public ArrayList<Member> resolve(String name) {
		ArrayList<Member> error = new ArrayList<Member>();
		error.add(new ErrorMember(this, "Not a namespace"));
		return error;
	} // resolve

	public ArrayList<Member> resolvePublic(String name, boolean allowPackageOnly) {
		return this.resolve(name);
	} // resolvePublic

	public boolean isPublic() {
		String visibility = this.getVisibility();
		return visibility != null && visibility.equals("public");
	} // isPublic

	public boolean isPrivate() {
		String visibility = this.getVisibility();
		return visibility != null && visibility.equals("private");
	} // isPrivate

	public boolean isProtected() {
		String visibility = this.getVisibility();
		return visibility != null && visibility.equals("protected");
	} // isProtected

	public boolean isPackageOnly() {
		String visibility = this.getVisibility();
		return visibility == null || visibility.equals("");
	} // isPackageOnly

	public Member completeStub() {
		Member completion = null;

		if (this.isStub()) {
			completion = this.completeStub(this.getCompletion());
		}

		return completion;
	} // completeStub

	public Member completeStub(Member completion) {
		this.isStub = completion.isError();
		return completion;
	} // completeStub

	public Member getCompletion() {
		QualifiedName qualifiedName = this.getQualifiedName();
		Member completion = qualifiedName.resolveSubunit();

		if (!completion.isError() && !this.isCompletedBy(completion)) {
			completion = new ErrorMember(this, "Invalid subunit for: "
					+ qualifiedName);
		}

		return completion;
	} // getCompletion

	public boolean isCompletedBy(Member member) {
		return false;
	} // isCompletedBy

	public boolean isDistinguishableFrom(Member other,
			NamespaceDefinition namespace) {
		// System.out.println("isDistinguishableFrom: this = " + this +
		// ", other = " + other);

		ArrayList<String> names = namespace.getNamesOfMember(this);
		ArrayList<String> otherNames = namespace.getNamesOfMember(other);

		for (String name : names) {
			// System.out.println("  name = " + name);
			for (String otherName : otherNames) {
				// System.out.println("    otherName = " + otherName);
				if (otherName.equals(name)) {
					return false;
				}
			}
		}

		return true;
	} // isDistinguishableFrom

	public boolean isDistinguishableFromAll(ArrayList<Member> others,
			NamespaceDefinition namespace) {
		// System.out.println("isDistinguishableFromAll: this = " + this +
		// "...");
		for (Member other : others) {
			// System.out.println("  other = " + other + "...");
			if (!this.isDistinguishableFrom(other, namespace)) {
				// System.out.println("Failed!");
				return false;
			}
		}

		return true;
	} // isDistinguishableFromAll

} // Member
