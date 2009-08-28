
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

public class CollectionAccessExpression extends Expression {

	private Expression collection = null;
	private Expression index = null;

	public CollectionAccessExpression(Expression collection, Expression index) {
		this.collection = collection;
		this.index = index;
	} // CollectionAccessExpression

	public Expression getCollection() {
		return this.collection;
	} // getCollection

	public Expression getIndex() {
		return this.index;
	} // getIndex

	public void print(String prefix) {
		super.print(prefix);
		this.getCollection().printChild(prefix);
		this.getIndex().printChild(prefix);
	} // print

} // CollectionAccessExpression
