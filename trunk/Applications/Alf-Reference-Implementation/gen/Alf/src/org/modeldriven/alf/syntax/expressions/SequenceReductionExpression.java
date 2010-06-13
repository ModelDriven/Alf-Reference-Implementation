
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

public class SequenceReductionExpression extends Expression {

	private boolean isOrdered = false;
	private QualifiedName behavior = null;
	private Expression sequence = null;

	public SequenceReductionExpression(Expression sequence,
			QualifiedName behavior, boolean isOrdered) {
		this.sequence = sequence;
		this.isOrdered = isOrdered;
		this.behavior = behavior;
	} // SequenceReductionExpression

	public QualifiedName getBehavior() {
		return this.behavior;
	} // getBehavior

	public Expression getSequence() {
		return this.sequence;
	} // getSequence

	public boolean isOrdered() {
		return this.isOrdered;
	} // isOrdered

	public String toString() {
		return super.toString() + " isOrdered:" + this.isOrdered();
	} // toString

	public void print(String prefix) {
		super.print(prefix);
		this.getBehavior().printChild(prefix);
		this.getSequence().printChild(prefix);
	} // print

} // SequenceReductionExpression
