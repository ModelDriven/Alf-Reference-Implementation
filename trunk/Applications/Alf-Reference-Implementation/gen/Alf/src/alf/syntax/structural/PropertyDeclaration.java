
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

public class PropertyDeclaration extends TypedElementDeclaration {

	private boolean isComposite = false;

	public PropertyDeclaration(String name) {
		this.setName(name);
	} // PropertyDeclaration

	public void setIsComposite() {
		this.isComposite = true;
	} // setIsComposite

	public boolean isComposite() {
		return this.isComposite;
	} // isComposite

	public String toString() {
		return super.toString() + " isComposite:" + this.isComposite();
	} // toString

} // PropertyDeclaration
