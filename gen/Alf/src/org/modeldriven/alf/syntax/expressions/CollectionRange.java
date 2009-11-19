
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
