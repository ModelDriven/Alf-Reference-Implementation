
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.expressions;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

import fUML.Syntax.Classes.Kernel.*;

public class StringLiteralMapping extends LiteralMapping {

	public ValueSpecification mapValueSpecification() {
		String image = this.getStringLiteral().getImage();

		LiteralString literal = new LiteralString();
		literal.setName("Value(" + image + ")");
		literal.setValue(image.substring(1, image.length() - 1));

		return literal;
	} // mapValueSpecification

	public StringLiteral getStringLiteral() {
		return (StringLiteral) this.getSourceNode();
	} // getStringLiteral

	public String getTypeName() {
		return "String";

	} // getTypeName

} // StringLiteralMapping
