
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

public class ClassExtentExpression extends Expression {

	private QualifiedName type = null;

	public ClassExtentExpression(QualifiedName type) {
		this.type = type;
	} // ClassExtentExpression

	public QualifiedName getType() {
		return this.type;
	} // getType

	public void print(String prefix) {
		super.print(prefix);
		this.getType().printChild(prefix);
	} // print

	public Member getClass(NamespaceDefinition context) {
		QualifiedName type = this.getType();
		ArrayList<Member> members = type.resolve(context);
		Member member;

		if (members.size() == 0) {
			member = new ErrorMember(this, "Cannot resolve type: " + type);
		} else if (members.size() == 1 && members.get(0).isError()) {
			member = new ErrorMember(this, ((ErrorMember) members.get(0))
					.getError());
		} else {
			for (Object m : members.toArray()) {
				if (!(m instanceof ClassDefinition)) {
					members.remove(m);
				}
			}

			if (members.size() == 0) {
				member = new ErrorMember(this, "Must be a class: " + type);
			} else if (members.size() > 1) {
				member = new ErrorMember(this, "Ambiguous type reference: "
						+ type);
			} else {
				member = members.get(0);
			}
		}

		return member;
	} // getClass

} // ClassExtentExpression
