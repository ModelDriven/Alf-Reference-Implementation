
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
			if (this.isAbsolute()) {
				namespace = this.getRootNamespace();
			} else {
				namespace = this.getModelContext();
			}
		}

		ArrayList<String> names = this.getNames();
		ArrayList<Member> members = namespace.resolve(names.get(0));

		Boolean allowPackageOnly = true;
		int n = names.size();

		for (int i = 1; i < n; i++) {
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

		if (members.size() == 1 && members.get(0).isError()) {
			members.add(new ErrorMember(this, (ErrorMember) members.remove(0)));
		}

		return members;
	} // resolve

	public NamespaceDefinition getRootNamespace() {
		return new PackageDefinition("");
	} // getRootNamespace

	public NamespaceDefinition getModelContext() {
		return this.getRootNamespace();
	} // getModelContext

	public QualifiedName copy() {
		QualifiedName copy = new QualifiedName();

		for (String name : this.getNames()) {
			copy.addName(name);
		}

		return copy;
	} // copy

} // QualifiedName
