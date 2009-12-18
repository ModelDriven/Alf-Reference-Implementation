
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
