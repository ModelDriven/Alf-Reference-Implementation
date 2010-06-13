
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

public class SequenceOperationExpression extends Expression {

	private Tuple tuple = null;
	private Expression sequence = null;
	private QualifiedName behavior = null;

	public SequenceOperationExpression(Expression sequence,
			QualifiedName behavior, Tuple tuple) {
		this.sequence = sequence;
		this.behavior = behavior;
		this.tuple = tuple;
	} // SequenceOperationExpression

	public Expression getSequence() {
		return this.sequence;
	} // getSequence

	public QualifiedName getBehavior() {
		return this.behavior;
	} // getBehavior

	public Tuple getTuple() {
		return this.tuple;
	} // getTuple

	public void print(String prefix) {
		super.print(prefix);
		this.getBehavior().printChild(prefix);
		this.getSequence().printChild(prefix);
		this.getTuple().printChild(prefix);
	} // print

} // SequenceOperationExpression
