
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

public class RootNamespace extends NamespaceDefinition {

	public ArrayList<Member> resolve(String name) {
		ArrayList<Member> members = super.resolve(name);

		if (members.size() == 0) {
			Member member;
			if (name.equals("Model")) {
				member = new ModelNamespace(this);
			} else {
				QualifiedName qualifiedName = this.getQualifiedName();
				qualifiedName.addName(name);

				member = qualifiedName.resolveSubunit();
				member.setName(name); // (Ensures an error member is named)
				this.addMember(member);
			}
			members.add(member);
		}

		return members;
	} // resolve

	public ArrayList<Member> resolvePublic(String name, boolean allowPackageOnly) {
		return this.resolve(name);
	} // resolvePublic

	public NamespaceDefinition getRootNamespace() {
		return this;
	} // getRootNamespace

	public QualifiedName getQualifiedName() {
		QualifiedName qualifiedName = new QualifiedName();
		qualifiedName.setIsAbsolute();
		return qualifiedName;
	} // getQualifiedName

} // RootNamespace
