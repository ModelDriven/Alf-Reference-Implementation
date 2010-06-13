
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.nodes;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

public class ErrorNode extends Node {

	private String message = "";
	private Node context = null;
	public ErrorNode nestedError = null;

	public ErrorNode(Node context, String message) {
		this.context = context;
		this.message = message;
	} // ErrorNode

	public ErrorNode(Node context, ErrorNode nestedError) {
		this(context, null, nestedError);
	} // ErrorNode

	public ErrorNode(Node context, String message, ErrorNode nestedError) {
		this.context = context;

		if (message == null && nestedError != null) {
			message = nestedError.getMessage();
		}

		this.message = message;
		this.nestedError = nestedError;
	} // ErrorNode

	public Node getContext() {
		return this.context;
	} // getContext

	public String getMessage() {
		return this.message;
	} // getMessage

	public ErrorNode getNestedError() {
		return this.nestedError;
	} // getNestedError

	public String toString() {
		return super.toString() + " message:" + this.getMessage() + " context:"
				+ this.getContext();
	} // toString

	public void print(String prefix) {
		super.print(prefix);

		if (this.getNestedError() != null) {
			this.getNestedError().printChild(prefix);
		}
	} // print

	public boolean isError() {
		return true;
	} // isError

} // ErrorNode
