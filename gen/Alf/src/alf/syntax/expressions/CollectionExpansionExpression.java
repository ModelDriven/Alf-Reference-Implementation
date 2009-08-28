
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

public class CollectionExpansionExpression extends Expression {

	private Tuple tuple = null;
	private Expression collection = null;
	private String operation = "";
	private String variable = "";

	public CollectionExpansionExpression(Expression collection,
			String operation, String variable, Tuple tuple) {
		this.collection = collection;
		this.operation = operation;
		this.variable = variable;
		this.tuple = tuple;
	} // CollectionExpansionExpression

	public Expression getCollection() {
		return this.collection;
	} // getCollection

	public String getOperation() {
		return this.operation;
	} // getOperation

	public String getVariable() {
		return this.variable;
	} // getVariable

	public Tuple getTuple() {
		return this.tuple;
	} // getTuple

	public String toString() {
		return super.toString() + " operation:" + this.getOperation()
				+ " variable:" + this.getVariable();
	} // toString

	public void print(String prefix) {
		super.print(prefix);
		this.getCollection().printChild(prefix);
		this.getTuple().printChild(prefix);

	} // print

} // CollectionExpansionExpression
