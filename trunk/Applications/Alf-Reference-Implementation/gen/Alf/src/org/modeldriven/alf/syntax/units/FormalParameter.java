
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

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
