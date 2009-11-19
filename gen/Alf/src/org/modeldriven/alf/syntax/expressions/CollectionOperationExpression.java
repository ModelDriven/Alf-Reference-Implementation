
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.SyntaxNode;
import org.modeldriven.alf.syntax.behavioral.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.namespaces.*;
import org.modeldriven.alf.syntax.structural.*;

import java.util.ArrayList;

public class CollectionOperationExpression extends Expression {

	private Tuple tuple = null;
	private Expression collection = null;
	private String operation = "";

	public CollectionOperationExpression(Expression collection,
			String operation, Tuple tuple) {
		this.collection = collection;
		this.operation = operation;
		this.tuple = tuple;
	} // CollectionOperationExpression

	public Expression getCollection() {
		return this.collection;
	} // getCollection

	public String getOperation() {
		return this.operation;
	} // getOperation

	public Tuple getTuple() {
		return this.tuple;
	} // getTuple

	public String toString() {
		return super.toString() + " operation:" + getOperation();
	} // toString

	public void print(String prefix) {
		super.print(prefix);
		this.getCollection().printChild(prefix);
		this.getTuple().printChild(prefix);
	} // print

} // CollectionOperationExpression
