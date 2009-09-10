
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

public class ErrorMember extends Member {

	private ErrorNode error = null;

	public ErrorMember(Node context, String message) {
		this.error = new ErrorNode(context, message);
	} // ErrorMember

	public ErrorMember(Node context, ErrorNode nestedError) {
		this.error = new ErrorNode(context, nestedError);
	} // ErrorMember

	public ErrorMember(Node context, String message, ErrorNode nestedError) {
		this.error = new ErrorNode(context, message, nestedError);
	} // ErrorMember

	public ErrorMember(Node context, ErrorMember nestedError) {
		this(context, null, nestedError);
	} // ErrorMember

	public ErrorMember(Node context, String message, ErrorMember nestedError) {
		this(context, message, nestedError.getError());
	} // ErrorMember

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

	public boolean isDistinguishableFrom(Member other,
			NamespaceDefinition namespace) {
		return true;
	} // isDistinguishableFrom

} // ErrorMember
