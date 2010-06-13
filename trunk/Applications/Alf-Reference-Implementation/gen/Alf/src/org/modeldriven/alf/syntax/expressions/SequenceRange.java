
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

public class SequenceRange extends SequenceElements {

	private Expression lower = null;
	private Expression upper = null;

	public SequenceRange(Expression lower, Expression upper) {
		this.lower = lower;
		this.upper = upper;
	} // SequenceRange

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

} // SequenceRange
