
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

public class TaggedValue extends SyntaxNode {

	private String name = "";
	private String value = "";

	public TaggedValue(String name, String value) {
		this.name = name;
		this.value = value;
	} // TaggedValue

	public String getName() {
		return this.name;
	} // getName

	public String getValue() {
		return this.value;
	} // getValue

	public String toString() {
		return name + "=>" + value;
	} // toString

} // TaggedValue
