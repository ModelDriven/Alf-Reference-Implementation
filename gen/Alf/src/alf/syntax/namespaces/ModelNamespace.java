
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

public class ModelNamespace extends NamespaceDefinition {

	public ModelNamespace() {
		this(new RootNamespace());
	} // ModelNamespace

	public ModelNamespace(RootNamespace root) {
		this.setName("Model");
		root.addMember(this);
	} // ModelNamespace

	public ArrayList<Member> resolve(String name) {
		ArrayList<Member> members = new ArrayList<Member>();

		for (Member member : this.getMembers()) {
			if (member.getName().equals(name)) {
				members.add(member);
			}
		}

		return members;
	} // resolve

	public ArrayList<Member> resolvePublic(String name, boolean allowPackageOnly) {
		return this.resolve(name);
	} // resolvePublic

	public NamespaceDefinition getModelNamespace() {
		return this;
	} // getModelNamespace

	public QualifiedName getQualifiedName() {
		return new QualifiedName();
	} // getQualifiedName

} // ModelNamespace
