
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

public class Annotation extends Node {

	private String identifier = "";
	private NameList arguments = null;

	public Annotation(String identifier, NameList arguments) {
		this.identifier = identifier;
		this.arguments = arguments;
	} // Annotation

	public String getIdentifier() {
		return this.identifier;
	} // getIdentifier

	public NameList getArguments() {
		return this.arguments;
	} // getArguments

	public String toString() {
		return super.toString() + " identifier:" + this.getIdentifier();
	} // toString

	public void print(String prefix) {
		super.print(prefix);

		NameList names = this.getArguments();
		if (names != null) {
			names.printChild(prefix);
		}
	} // print

} // Annotation
