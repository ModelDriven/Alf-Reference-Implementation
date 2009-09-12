
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

public class CollectionRange extends CollectionElements {

	private Expression lower = null;
	private Expression upper = null;

	public CollectionRange(Expression lower, Expression upper) {
		this.lower = lower;
		this.upper = upper;
	} // CollectionRange

	public Expression getLower() {
		return this.lower;
	} // getLower

	public Expression getUpper() {
		return this.upper;
	} // getUpper

	public void print(String prefix) {
		super.print(prefix);
		this.getLower().printChild(prefix);
		this.getUpper().printChild(prefix);
	} // print

} // CollectionRange
