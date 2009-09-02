
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

public abstract class Member extends DocumentedElement {

	private String name = "";
	private String visibility = "";
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

	public void setNamespace(NamespaceDefinition namespace) {
		this.namespace = namespace;
	} // setNamespace

	public NamespaceDefinition getNamespace() {
		return this.namespace;
	} // getNamespace

	public String toString() {
		return super.toString() + " name:" + this.getName() + " visibility:"
				+ this.getVisibility();
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

	public ArrayList<Member> resolve(String name) {
		ArrayList<Member> error = new ArrayList<Member>();
		error.add(new ErrorMember(this, "Not a namespace"));
		return error;
	} // resolve

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

} // Member
