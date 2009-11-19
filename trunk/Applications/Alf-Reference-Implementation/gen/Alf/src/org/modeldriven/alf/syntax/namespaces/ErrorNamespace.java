
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.namespaces;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.SyntaxNode;
import org.modeldriven.alf.syntax.behavioral.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.namespaces.*;
import org.modeldriven.alf.syntax.structural.*;

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

	public ArrayList<Member> resolve(String name) {
		return this.getAllMembers();
	} // resolve

	public ArrayList<Member> resolvePublic(String name, boolean allowPackageOnly) {
		return this.resolve(name);
	} // resolvePublic

	public NamespaceDefinition getModelNamespace() {
		return this;
	} // getModelNamespace

	public boolean isDistinguishableFrom(Member other,
			NamespaceDefinition namespace) {
		return true;
	} // isDistinguishableFrom

} // ErrorNamespace
