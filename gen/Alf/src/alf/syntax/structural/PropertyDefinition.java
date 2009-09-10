
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

public class PropertyDefinition extends TypedElementDefinition {

	private boolean isComposite = false;
	private Expression initializer = null;

	public PropertyDefinition(TypedElementDeclaration declaration) {
		super(declaration);
	} // PropertyDefinition

	public void setIsComposite() {
		this.isComposite = true;
	} // setIsComposite

	public boolean isComposite() {
		return this.isComposite;
	} // isComposite

	public void setInitializer(Expression initializer) {
		this.initializer = initializer;
	} // setInitializer

	public Expression getInitializer() {
		return this.initializer;
	} // getInitializer

	public String toString() {
		return super.toString() + " isComposite:" + this.isComposite();
	} // toString

	public void print(String prefix) {
		super.print(prefix);

		Expression initializer = this.getInitializer();
		if (initializer != null) {
			initializer.printChild(prefix);
		}
	} // print

	public boolean isDistinguishableFrom(Member other,
			NamespaceDefinition namespace) {
		return !(other instanceof PropertyDefinition)
				|| super.isDistinguishableFrom(other, namespace);
	} // isDistinguishableFrom

} // PropertyDefinition
