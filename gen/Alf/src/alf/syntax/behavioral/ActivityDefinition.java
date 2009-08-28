
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package alf.syntax.behavioral;

import alf.syntax.behavioral.*;
import alf.syntax.expressions.*;
import alf.syntax.namespaces.*;
import alf.syntax.nodes.*;
import alf.syntax.structural.*;

import java.util.ArrayList;

public class ActivityDefinition extends ClassifierDefinition {

	private Block body = null;

	public ActivityDefinition(ActivityDeclaration declaration, Block body) {
		this.setDeclaration(declaration);
		this.body = body;
	} // ActivityDefinition

	public Block getBody() {
		return this.body;
	} // getBody

	public void print(String prefix) {
		super.print(prefix);
		this.body.printChild(prefix);
	} // print

} // ActivityDefinition
