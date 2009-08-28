
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

public class FormalParameter extends TypedElementDeclaration {

	private String direction = "";
	private String documentation = "";

	public FormalParameter(String name, String direction, String documentation) {
		this.setName(name);
		this.direction = direction;
		this.documentation = documentation;
	} // FormalParameter

	public String getDirection() {
		return this.direction;
	} // getDirection

	public String getDocumentation() {
		return this.documentation;
	} // getDocumentation

	public String toString() {
		return super.toString() + " direction:" + this.getDirection()
				+ " documentation: " + this.getDocumentation();
	} // toString

	public void print(String prefix) {
		// TODO
	} // print

} // FormalParameter
