
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.SyntaxNode;
import org.modeldriven.alf.syntax.behavioral.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.namespaces.*;
import org.modeldriven.alf.syntax.structural.*;

import java.util.ArrayList;

public class BehaviorInvocationExpression extends InvocationExpression {

	private QualifiedName target = null;

	public BehaviorInvocationExpression(QualifiedName target, Tuple tuple) {
		super(tuple);
		this.target = target;
	} // BehaviorInvocationExpression

	public QualifiedName getTarget() {
		return this.target;
	} // getTarget

	public void printTarget(String prefix) {
		this.getTarget().printChild(prefix);
	} // printTarget

	public Member getBehavior(NamespaceDefinition context) {
		QualifiedName target = this.getTarget();
		ArrayList<Member> members = target.resolve(context);
		Member member;

		if (members.size() == 0) {
			member = new ErrorMember(this, "Cannot resolve behavior: " + target);
		} else if (members.size() == 1 && members.get(0).isError()) {
			member = new ErrorMember(this, ((ErrorMember) members.get(0))
					.getError());
		} else {
			for (Object m : members.toArray()) {
				if (!(m instanceof ActivityDefinition)) {
					members.remove(m);
				}
			}

			if (members.size() == 0) {
				member = new ErrorMember(this, "Must be a behavior: " + target);
			} else if (members.size() > 1) {
				member = new ErrorMember(this, "Ambiguous behavior reference: "
						+ target);
			} else {
				member = members.get(0);
			}
		}

		return member;
	} // getBehavior

} // BehaviorInvocationExpression
