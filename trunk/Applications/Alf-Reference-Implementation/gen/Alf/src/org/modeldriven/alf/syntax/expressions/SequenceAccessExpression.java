
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

public class SequenceAccessExpression extends Expression {

	private Expression sequence = null;
	private Expression index = null;

	public SequenceAccessExpression(Expression sequence, Expression index) {
		this.sequence = sequence;
		this.index = index;
	} // SequenceAccessExpression

	public Expression getSequence() {
		return this.sequence;
	} // getSequence

	public Expression getIndex() {
		return this.index;
	} // getIndex

	public void print(String prefix) {
		super.print(prefix);
		this.getSequence().printChild(prefix);
		this.getIndex().printChild(prefix);
	} // print

} // SequenceAccessExpression
