
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.structural;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.SyntaxNode;
import org.modeldriven.alf.syntax.behavioral.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.namespaces.*;
import org.modeldriven.alf.syntax.structural.*;

import java.util.ArrayList;

public class ModelNamespace extends PackageDefinition {

	public ModelNamespace() {
		this.setName("Model");

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

	public boolean isCompletedBy(Member member) {
		return false;
	} // isCompletedBy

	public boolean isDistinguishableFrom(Member other,
			NamespaceDefinition namespace) {
		return true;
	} // isDistinguishableFrom

} // ModelNamespace
