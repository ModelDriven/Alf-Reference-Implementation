
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package alf.syntax.namespaces;

import alf.syntax.behavioral.*;
import alf.syntax.expressions.*;
import alf.syntax.namespaces.*;
import alf.syntax.nodes.*;
import alf.syntax.structural.*;

import java.util.ArrayList;

public class ErrorNamespace extends NamespaceDefinition {

	private ErrorNode error = null;

	public ErrorNamespace(Node context, String message) {
		this.error = new ErrorNode(context, message);
	} // ErrorNamespace

	public ErrorNamespace(Node context, ErrorMember nestedError) {
		this.error = new ErrorNode(context, nestedError.getError());
	} // ErrorNamespace

	public ErrorNode getError() {
		return this.error;
	} // getError

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

	public boolean isError() {
		return true;
	} // isError

	public ArrayList<Member> getAllMembers() {
		ArrayList<Member> error = new ArrayList<Member>();
		error.add(this);
		return error;
	} // getAllMembers

	public ArrayList<Member> getPublicMembers() {
		return this.getAllMembers();
	} // getPublicMembers

} // ErrorNamespace
