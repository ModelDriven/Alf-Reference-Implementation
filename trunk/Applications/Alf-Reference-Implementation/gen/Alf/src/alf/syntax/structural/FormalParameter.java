
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

public class FormalParameter extends TypedElementDefinition {

	private String direction = "";

	public FormalParameter(TypedElementDeclaration declaration) {
		super(declaration);
	} // FormalParameter

	public void setDirection(String direction) {
		this.direction = direction;
	} // setDirection

	public String getDirection() {
		return this.direction;
	} // getDirection

	public String toString() {
		return super.toString() + " direction:" + this.getDirection();
	} // toString

	public boolean equals(FormalParameter other) {
		// System.out.println("Checking: ");
		// this.print("  ");
		// other.print("  ");

		return this.getDirection().equals(other.getDirection())
				&& this.getName().equals(other.getName())
				&& this.getDeclaration().equals(other.getDeclaration(),
						this.getNamespace().getNamespace());
	} // equals

	public boolean isDistinguishableFrom(Member other,
			NamespaceDefinition namespace) {
		return !(other instanceof FormalParameter)
				|| super.isDistinguishableFrom(other, namespace);
	} // isDistinguishableFrom

} // FormalParameter
