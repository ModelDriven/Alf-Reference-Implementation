
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

public class ActivityDeclaration extends ClassifierDeclaration {

	private ParameterSetDeclaration parameterSet = null;

	public ActivityDeclaration(String name, ParameterSetDeclaration parameterSet) {
		this.setName(name);
		this.parameterSet = parameterSet;
	} // ActivityDeclaration

	public ParameterSetDeclaration getParameterSet() {
		return this.parameterSet;
	} // getParameterSet

	public void print(String prefix) {
		super.print(prefix);
		this.getParameterSet().printChild(prefix);
	} // print

} // ActivityDeclaration
