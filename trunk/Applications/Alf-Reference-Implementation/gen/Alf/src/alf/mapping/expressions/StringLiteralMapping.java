
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package alf.mapping.expressions;

import alf.nodes.*;
import alf.syntax.SyntaxNode;
import alf.syntax.behavioral.*;
import alf.syntax.expressions.*;
import alf.syntax.namespaces.*;
import alf.syntax.structural.*;

import java.util.ArrayList;

import fUML.Syntax.Classes.Kernel.*;

public class StringLiteralMapping extends LiteralMapping {

	public ValueSpecification mapValueSpecification() {
		String image = this.getStringLiteral().getImage();

		LiteralString literal = new LiteralString();
		literal.setName("Value(" + image + ")");
		literal.setValue(image.substring(2, image.length()));

		return literal;
	} // mapValueSpecification

	public StringLiteral getStringLiteral() {
		return (StringLiteral) this.getSourceNode();
	} // getStringLiteral

	public String getTypeName() {
		return "String";

	} // getTypeName

} // StringLiteralMapping
