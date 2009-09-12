
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

import alf.parser.AlfParser;
import alf.parser.ParseException;

public class QualifiedName extends SyntaxNode {

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
		if (this.isAbsolute()) {
			namespace = namespace.getRootNamespace();
		}

		if (namespace.isError()) {
			ArrayList<Member> error = new ArrayList<Member>();
			error.add(new ErrorMember(this, ((ErrorNamespace) namespace)
					.getError()));
			return error;
		}

		ArrayList<String> names = this.getNames();
		ArrayList<Member> members = new ArrayList<Member>();
		members.add(namespace);

		Boolean allowPackageOnly = true;
		int n = names.size();

		if (n > 0) {
			String name = names.get(0);
			members = namespace.resolve(name);

			if (members.size() == 0
					&& (namespace instanceof ModelNamespace || namespace instanceof RootNamespace)) {
				QualifiedName qualifiedName = namespace.getQualifiedName();
				qualifiedName.addName(name);
				Member member = qualifiedName.resolveSubunit();
				member.setName(name); // (Ensures an error member is named)
				namespace.addMember(member);
				members.add(member);
			}

			for (int i = 1; i < n; i++) {
				if (members.size() == 1 && members.get(0).isError()) {
					ArrayList<Member> error = new ArrayList<Member>();
					error.add(new ErrorMember(this, (ErrorMember) members
							.get(0)));
					return error;
				}
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
					members = namespace.resolvePublic(names.get(i),
							allowPackageOnly);
				}
			}
		}

		return members;
	} // resolve

	public Member resolveSubunit() {
		System.out.println("Resolving subunit " + this);

		StringBuffer path = new StringBuffer("Root");

		if (!this.isAbsolute()) {
			path.append("/Model");
		}

		ArrayList<String> names = this.getNames();
		for (String name : names) {
			path.append("/" + this.processName(name));
		}

		path.append(".alf");

		AlfParser parser;

		try {
			System.out.println("Parsing " + path + "...");
			parser = new AlfParser(new java.io.FileInputStream(path.toString()));
		} catch (java.io.FileNotFoundException e) {
			System.out.println("Unit not found.");
			return new ErrorMember(this, "Unit not found: " + this);
		}

		try {
			UnitDefinition subunit = parser.UnitDefinition();
			System.out.println("Parsed successfully.");
			NamespaceDefinition subunitDefinition = subunit.getDefinition();

			// System.out.println("Subunit " + subunitDefinition.getName());
			// for (Member member: subunitDefinition.getMembers()) {
			// System.out.println(member.toString(" "));
			// }

			QualifiedName qualifiedName = subunitDefinition.getQualifiedName();

			if (qualifiedName.getNames().size() == 1 && this.isAbsolute()) {
				qualifiedName.setIsAbsolute();
			}

			if (qualifiedName.equals(this)) {
				if (!this.isAbsolute()) {
					subunit.addImplicitImports();
				}
				return subunitDefinition;
			} else {
				return new ErrorMember(this, "Incorrect subunit: " + this);
			}
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			System.out.println("Encountered errors during parse.");
			return new ErrorMember(this, "Cannot parse unit: " + this + "\n"
					+ e.getMessage());
		}
	} // resolveSubunit

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

	public Member getClassifier(NamespaceDefinition context) {
		ArrayList<Member> members = this.resolve(context);

		if (members.size() == 1 && members.get(0).isError()) {
			return new ErrorMember(this, (ErrorMember) (members.get(0)));
		} else {
			for (Object member : members.toArray()) {
				if (!(member instanceof ClassifierDefinition)) {
					members.remove(member);
				}
			}

			if (members.size() == 1) {
				return members.get(0);
			} else if (members.size() == 0) {
				return new ErrorMember(this, "Type not found: " + this);
			} else {
				return new ErrorMember(this, "Ambiguous type reference: "
						+ this);
			}
		}
	} // getClassifier

} // QualifiedName
