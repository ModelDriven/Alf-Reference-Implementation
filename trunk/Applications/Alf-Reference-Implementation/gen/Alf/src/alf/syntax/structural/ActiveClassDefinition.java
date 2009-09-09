
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package alf.syntax.structural;

import alf.syntax.behavioral.*;
import alf.syntax.expressions.*;
import alf.syntax.namespaces.*;
import alf.syntax.nodes.*;
import alf.syntax.structural.*;

import java.util.ArrayList;

public class ActiveClassDefinition extends ClassDefinition {

	private String behaviorName = "";
	private Block behaviorBlock = null;

	public void setBehaviorName(String behaviorName) {
		this.behaviorName = behaviorName;
	} // setBehaviorName

	public String getBehaviorName() {
		return this.behaviorName;
	} // getBehaviorName

	public void setBehaviorBlock(Block behavior) {
		this.behaviorBlock = behavior;
	} // setBehaviorBlock

	public Block getBehaviorBlock() {
		return this.behaviorBlock;
	} // getBehaviorBlock

	public String toString() {
		return super.toString() + " behaviorName:" + this.getBehaviorName();
	} // toString

	public void print(String prefix) {
		super.print(prefix);

		Block behavior = this.getBehaviorBlock();
		if (behavior != null) {
			behavior.printChild(prefix);
		}
	} // print

	public Member completeStub() {
		Member completion;

		String behaviorName = this.getBehaviorName();

		if (behaviorName != null && !behaviorName.equals("")
				&& this.getBehaviorBlock() == null) {
			QualifiedName qualifiedName = this.getQualifiedName();
			qualifiedName.addName(behaviorName);

			completion = qualifiedName.resolveSubunit();

			if (completion.isError()) {
				completion = new ErrorMember(this, "Cannot resolve: "
						+ qualifiedName, (ErrorMember) completion);
			} else if (!(completion instanceof ActivityDefinition)
					|| ((ActivityDefinition) completion).getMembers().size() > 0) {
				completion = new ErrorMember(this,
						"Invalid classifier behavior: " + qualifiedName,
						(ErrorMember) completion);
			} else {
				this.setBehaviorBlock(((ActivityDefinition) completion)
						.getBody());
			}
		} else {
			completion = super.completeStub();
		}

		return completion;
	} // completeStub

	public Member completeStub(Member completion) {
		completion = super.completeStub(completion);

		if (!completion.isError()) {
			Member furtherCompletion = ((ActiveClassDefinition) completion)
					.completeStub();
			if (furtherCompletion != null && completion.isError()) {
				completion = furtherCompletion;
			} else {
				behaviorName = ((ActiveClassDefinition) completion)
						.getBehaviorName();
				if (behaviorName != null) {
					this.setBehaviorName(behaviorName);
				}
				Block behaviorBlock = ((ActiveClassDefinition) completion)
						.getBehaviorBlock();
				if (behaviorBlock != null) {
					this.setBehaviorBlock(behaviorBlock);
				}
			}
		}

		return completion;
	} // completeStub

	public boolean isCompletedBy(Member member) {
		return member instanceof ActiveClassDefinition
				&& super.isCompletedBy(member);
	} // isCompletedBy

} // ActiveClassDefinition
