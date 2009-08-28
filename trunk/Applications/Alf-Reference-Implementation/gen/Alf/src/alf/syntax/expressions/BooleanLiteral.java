
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package alf.syntax.expressions;

import alf.syntax.behavioral.*;
import alf.syntax.expressions.*;
import alf.syntax.namespaces.*;
import alf.syntax.nodes.*;
import alf.syntax.structural.*;

import java.util.ArrayList;

public class BooleanLiteral extends Expression {

	private String image = "";

	public BooleanLiteral(String image) {
		this.image = image;
	} // BooleanLiteral

	public String getImage() {
		return this.image;
	} // getImage

	public String toString() {
		return super.toString() + " image:" + this.getImage();
	} // toString

} // BooleanLiteral
