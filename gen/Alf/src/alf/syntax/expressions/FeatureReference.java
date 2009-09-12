
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

public class FeatureReference extends Expression {

	private Expression expression = null;
	private String name = "";

	public FeatureReference(Expression expression, String name) {
		this.expression = expression;
		this.name = name;
	} // FeatureReference

	public Expression getExpression() {
		return this.expression;
	} // getExpression

	public String getName() {
		return this.name;
	} // getName

	public String toString() {
		return super.toString() + " name:" + this.getName();
	} // toString

	public void print(String prefix) {
		super.print(prefix);
		this.getExpression().printChild(prefix);
	} // print

} // FeatureReference
