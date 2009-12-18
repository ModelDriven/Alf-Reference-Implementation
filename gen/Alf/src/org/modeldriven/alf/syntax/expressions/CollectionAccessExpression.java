
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
