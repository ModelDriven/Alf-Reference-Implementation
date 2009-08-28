
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

	public void addMember(Member member) {
		this.members.add(member);
	} // addMember

	public ArrayList<Member> getMembers() {
		return this.members;
	} // getMembers

	public void print(String prefix) {
		super.print(prefix);

		for (Member member : this.getMembers()) {
			member.printChild(prefix);
		}
	} // print

} // NamespaceDefinition
