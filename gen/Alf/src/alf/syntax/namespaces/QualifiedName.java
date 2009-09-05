
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

import alf.parser.AlfParser;
import alf.parser.ParseException;

public class QualifiedName extends Node {

	private boolean isAbsolute = false;
	private ArrayList<String> names = new ArrayList<String>();

	public void setIsAbsolute() {
		this.isAbsolute = true;
	} // setIsAbsolute

	public boolean isAbsolute() {
		return this.isAbsolute;
	} // isAbsolute

	public void addName(String name) {
		this.names.add(name);
	} // addName

	public ArrayList<String> getNames() {
		return this.names;
	} // getNames

	public String toString() {
		StringBuffer s = new StringBuffer();

		if (this.isAbsolute()) {
			s.append("::");
		}

		ArrayList<String> nameList = this.getNames();

		for (int i = 0; i < nameList.size(); i++) {
			s.append(nameList.get(i));
			if (i < nameList.size() - 1) {
				s.append("::");
			}
		}

		return s.toString();
	} // toString

	public void print(String prefix) {
		System.out.println(prefix + super.toString() + " " + this.toString());
	} // print

	public ArrayList<Member> resolve(NamespaceDefinition namespace) {
		if (namespace == null) {
			Member root;
			if (this.isAbsolute()) {
				root = this.getRootNamespace();
			} else {
				root = this.getModelContext();
			}

			if (root.isError()) {
				ArrayList<Member> error = new ArrayList<Member>();
				error.add(new ErrorMember(this, (ErrorMember) root));
				return error;
			}

			namespace = (NamespaceDefinition) root;
		}

		ArrayList<String> names = this.getNames();
		ArrayList<Member> members = new ArrayList<Member>();

		Boolean allowPackageOnly = true;
		int n = names.size();

		for (int i = 0; i < n; i++) {
			members = namespace.resolvePublic(names.get(i), allowPackageOnly);
			if (members.size() == 1 && members.get(0).isError()) {
				ArrayList<Member> error = new ArrayList<Member>();
				error.add(new ErrorMember(this, (ErrorMember) members.get(0)));
				return error;
			}
			if (i < n - 1) {
				for (Object m : members.toArray()) {
					if (!(m instanceof NamespaceDefinition)) {
						members.remove(m);
					}
				}
				if (members.size() == 0) {
					ArrayList<Member> error = new ArrayList<Member>();
					error.add(new ErrorMember(this, "Cannot find namespace: "
							+ names.get(i)));
					return error;
				} else if (members.size() > 1) {
					ArrayList<Member> error = new ArrayList<Member>();
					error.add(new ErrorMember(this, "Ambiguous namespace: "
							+ names.get(i)));
					return error;
				} else {
					namespace = (NamespaceDefinition) members.get(0);
					allowPackageOnly = !(namespace instanceof PackageDefinition);
				}
			}
		}

		return members;
	} // resolve

	public Member resolveSubunit() {
		StringBuffer path = new StringBuffer("Root");

		ArrayList<String> names = this.getNames();

		for (String name : names) {
			path.append("/" + name);
		}

		AlfParser parser;

		try {
			System.out.println("Parsing " + path + ".alf...");
			parser = new AlfParser(new java.io.FileInputStream(path + ".alf"));
		} catch (java.io.FileNotFoundException e) {
			return new ErrorMember(this, "Subunit not found: " + this);
		}

		try {
			UnitDefinition subunit = parser.UnitDefinition();
			NamespaceDefinition subunitDefinition = subunit.getDefinition();
			if (this.isAbsolute() && names.size() == 0
					|| subunitDefinition.getQualifiedName().equals(this)) {
				return subunitDefinition;
			} else {
				return new ErrorMember(this, "Incorrect subunit: " + this);
			}
		} catch (ParseException e) {
			return new ErrorMember(this, "Cannot parse subunit: " + this + "\n"
					+ e.getMessage());
		}
	} // resolveSubunit

	public Member getRootNamespace() {
		QualifiedName root = new QualifiedName();
		root.setIsAbsolute();
		Member member = root.resolveSubunit();

		if (member.isError()) {
			return member;
		} else if (member instanceof NamespaceDefinition) {
			member.setName("");
			return (NamespaceDefinition) member;
		} else {
			return new ErrorMember(this, "Invalid root namespace");
		}
	} // getRootNamespace

	public Member getModelContext() {
		return this.getRootNamespace();
	} // getModelContext

	public QualifiedName copy() {
		QualifiedName copy = new QualifiedName();

		if (this.isAbsolute()) {
			copy.setIsAbsolute();
		}

		for (String name : this.getNames()) {
			copy.addName(name);
		}

		return copy;
	} // copy

	public boolean equals(QualifiedName other) {
		ArrayList<String> names = this.getNames();
		ArrayList<String> otherNames = other.getNames();

		int n = names.size();

		if (n != otherNames.size()) {
			return false;
		} else {
			for (int i = 0; i < n; i++) {
				if (!names.get(i).equals(otherNames.get(i))) {
					return false;
				}
			}
			return true;
		}
	} // equals

	public boolean isEquivalentTo(QualifiedName other,
			NamespaceDefinition context) {
		ArrayList<Member> resolvents = this.resolve(context);
		ArrayList<Member> otherResolvents = other.resolve(context);

		if (resolvents.size() != 1 || otherResolvents.size() != 1) {
			return false;
		} else {
			Member resolvent = resolvents.get(0);
			Member otherResolvent = otherResolvents.get(0);

			return !resolvent.isError()
					&& !otherResolvent.isError()
					&& resolvent.getQualifiedName().equals(
							otherResolvent.getQualifiedName());
		}
	} // isEquivalentTo

} // QualifiedName
