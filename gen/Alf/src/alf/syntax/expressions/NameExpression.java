
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

public class NameExpression extends Expression {

	private QualifiedName name = null;

	public NameExpression(QualifiedName name) {
		this.name = name;
	} // NameExpression

	public QualifiedName getName() {
		return this.name;
	} // getName

	public void print(String prefix) {
		super.print(prefix);
		this.getName().printChild(prefix);
	} // print

} // NameExpression
