
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

public class ClassificationExpression extends UnaryExpression {

	private QualifiedName type = null;

	public ClassificationExpression(Expression operand, String operator,
			QualifiedName type) {
		super(operator, operand);
		this.type = type;
	} // ClassificationExpression

	public QualifiedName getType() {
		return this.type;
	} // getType

	public void print(String prefix) {
		super.print(prefix);
		this.getType().printChild(prefix);
	} // print

} // ClassificationExpression
