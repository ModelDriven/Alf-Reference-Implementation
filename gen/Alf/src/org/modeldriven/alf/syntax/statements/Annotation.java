
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.statements;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

public class Annotation extends SyntaxNode {

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
