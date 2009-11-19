
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.SyntaxNode;
import org.modeldriven.alf.syntax.behavioral.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.namespaces.*;
import org.modeldriven.alf.syntax.structural.*;

import java.util.ArrayList;

import fUML.Syntax.Classes.Kernel.Element;

public class ErrorMapping extends MappingNode {

	public ErrorMapping(Node context, String message) {
		this.setError(new ErrorNode(context, message));
	} // ErrorMapping

	public ErrorMapping(Node context, ErrorMapping nestedError) {
		this.setError(new ErrorNode(context, nestedError.getError()));
	} // ErrorMapping

	public ArrayList<Element> getModelElements() {
		return new ArrayList<Element>();
	} // getModelElements

	public Node getContext() {
		return this.getError().getContext();
	} // getContext

	public String getMessage() {
		return this.getError().getMessage();
	} // getMessage

	public String toString() {
		return this.getError().toString();
	} // toString

	public void print(String prefix) {
		this.getError().print(prefix);
	} // print

} // ErrorMapping
