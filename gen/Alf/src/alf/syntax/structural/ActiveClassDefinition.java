
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

	public ActiveClassDefinition(ActiveClassDeclaration declaration) {
		super(declaration);
	} // ActiveClassDefinition

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

} // ActiveClassDefinition
