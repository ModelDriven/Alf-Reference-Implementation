
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

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
