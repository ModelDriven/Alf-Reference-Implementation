
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

public abstract class BehaviorDefinition extends NamespaceDefinition {

	private Block body = null;

	public void setBody(Block body) {
		this.body = body;
	} // setBody

	public Block getBody() {
		return this.body;
	} // getBody

	public Member completeStub(Member completion) {
		this.getMembers().clear();
		completion = super.completeStub(completion);

		if (!completion.isError()) {
			this.setBody(((ActivityDefinition) completion).getBody());
		}

		return completion;
	} // completeStub

	public boolean isCompletedBy(Member member) {
		/*
		 * System.out.println("isCompletedBy: "); System.out.println("  this = "
		 * + this); System.out.println("  member = " + member);
		 */

		if (!(member instanceof ActivityDefinition)) {
			return false;
		} else {
			ActivityDefinition activity = (ActivityDefinition) member;

			ArrayList<Member> parameters = this.getMembers();
			ArrayList<Member> otherParameters = activity.getMembers();

			int n = parameters.size();
			if (n != otherParameters.size()) {
				return false;
			} else {
				for (int i = 0; i < n; i++) {
					if (!((FormalParameter) parameters.get(i))
							.equals((FormalParameter) otherParameters.get(i))) {
						// System.out.println("Failed!");
						return false;
					}
				}

				return true;
			}
		}
	} // isCompletedBy

} // BehaviorDefinition
