
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package alf.syntax.expressions;

import alf.nodes.*;
import alf.syntax.SyntaxNode;
import alf.syntax.behavioral.*;
import alf.syntax.expressions.*;
import alf.syntax.namespaces.*;
import alf.syntax.structural.*;

import java.util.ArrayList;

public class NameExpression extends Expression {

	private QualifiedName name = null;

	public NameExpression(QualifiedName name) {
		this.name = name;
	} // NameExpression

	public QualifiedName getName() {
		return this.name;
	} // getName

	public void print(String prefix) {
		super.print(prefix);
		this.getName().printChild(prefix);
	} // print

	public Member getParameter(NamespaceDefinition context) {
		QualifiedName name = this.getName();

		ArrayList<Member> members = name.resolve(context);
		Member member;

		if (members.size() == 1 && members.get(0).isError()) {
			member = new ErrorMember(this, ((ErrorMember) members.get(0))
					.getError());
		} else {
			for (Object m : members.toArray()) {
				if (!(m instanceof FormalParameter)) {
					members.remove(m);
				}
			}

			if (members.size() == 0) {
				if (name.getNames().size() == 1) {
					member = null;
				} else {
					member = new ErrorMember(this, "Must be a parameter: "
							+ name);
				}
			} else if (members.size() > 1) {
				member = new ErrorMember(this,
						"Ambiguous parameter reference: " + name);
			} else {
				member = members.get(0);
			}
		}

		return member;
	} // getParameter

} // NameExpression
