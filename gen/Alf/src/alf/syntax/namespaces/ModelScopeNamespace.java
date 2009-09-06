
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

public class ModelScopeNamespace extends NamespaceDefinition {

	public ArrayList<Member> resolve(String name) {
		QualifiedName qualifiedName = new QualifiedName();
		qualifiedName.addName(name);

		ArrayList<Member> members = new ArrayList<Member>();
		members.add(qualifiedName.resolveSubunit());

		return members;
	} // resolve

	public ArrayList<Member> resolvePublic(String name, boolean allowPackageOnly) {
		Member member = this.resolve(name).get(0);
		ArrayList<Member> publicMembers = new ArrayList<Member>();

		if (member.isError()
				|| (member.getName().equals(name) && (member.isPublic() || allowPackageOnly
						&& member.isPackageOnly()))) {
			publicMembers.add(member);
		}

		return publicMembers;
	} // resolvePublic

} // ModelScopeNamespace
