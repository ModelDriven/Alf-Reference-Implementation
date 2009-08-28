
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

public class ClassExtentExpression extends Expression {

	private QualifiedName type = null;

	public ClassExtentExpression(QualifiedName type) {
		this.type = type;
	} // ClassExtentExpression

	public QualifiedName getType() {
		return this.type;
	} // getType

	public void print(String prefix) {
		super.print(prefix);
		this.getType().printChild(prefix);
	} // print

} // ClassExtentExpression
