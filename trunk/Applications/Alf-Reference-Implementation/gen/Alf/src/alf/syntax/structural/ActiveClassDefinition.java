
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package alf.syntax.structural;

import alf.nodes.*;
import alf.syntax.SyntaxNode;
import alf.syntax.behavioral.*;
import alf.syntax.expressions.*;
import alf.syntax.namespaces.*;
import alf.syntax.structural.*;

import java.util.ArrayList;

public class ActiveClassDefinition extends ClassDefinition {

	private ActivityDefinition classifierBehavior = null;

	public void setClassifierBehavior(ActivityDefinition activity) {
		this.addOwnedMember(activity);
		this.classifierBehavior = activity;
	} // setClassifierBehavior

	public ActivityDefinition getClassifierBehavior() {
		return this.classifierBehavior;
	} // getClassifierBehavior

	public String toString() {
		return super.toString() + " classifierBehavior:"
				+ this.getClassifierBehavior();
	} // toString

	public Member completeStub(Member completion) {
		completion = super.completeStub(completion);

		if (!completion.isError()) {
			// super.completeStub will already have added the classifier
			// behavior as a member.
			this.classifierBehavior = ((ActiveClassDefinition) completion)
					.getClassifierBehavior();
		}

		return completion;
	} // completeStub

	public boolean isCompletedBy(Member member) {
		return member instanceof ActiveClassDefinition
				&& super.isCompletedBy(member);

	} // isCompletedBy

	public boolean canSpecialize(Member member) {
		return member instanceof ClassDefinition;
	} // canSpecialize

} // ActiveClassDefinition
