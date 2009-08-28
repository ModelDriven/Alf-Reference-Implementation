
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

public abstract class Member extends DocumentedElement {

	private String name = "";
	private String visibility = "";

	public void setName(String name) {
		this.name = name;
	} // setName

	public String getName() {
		return this.name;
	} // getName

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	} // setVisibility

	public String getVisibility() {
		return this.visibility;
	} // getVisibility

	public String toString() {
		return super.toString() + " name:" + this.getName() + " visibility:"
				+ this.getVisibility();
	} // toString

} // Member
