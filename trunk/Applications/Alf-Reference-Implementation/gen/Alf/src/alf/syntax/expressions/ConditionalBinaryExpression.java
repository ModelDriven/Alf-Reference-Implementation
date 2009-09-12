
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package alf.syntax.expressions;

import alf.nodes.*;
import alf.syntax.SyntaxNode;
import alf.syntax.behavioral.*;
import alf.syntax.expressions.*;
import alf.syntax.namespaces.*;
import alf.syntax.structural.*;

import java.util.ArrayList;

public class ConditionalBinaryExpression extends BinaryExpression {

	public ConditionalBinaryExpression(Expression operand1, String operator,
			Expression operand2) {
		super(operand1, operator, operand2);
	} // ConditionalBinaryExpression

} // ConditionalBinaryExpression
