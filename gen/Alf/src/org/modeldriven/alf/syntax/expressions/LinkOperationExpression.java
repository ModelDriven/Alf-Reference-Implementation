
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

public class LinkOperationExpression extends InvocationExpression {

	private String operation = "";
	private QualifiedName association = null;

	public LinkOperationExpression(QualifiedName association, String operation,
			Tuple tuple) {
		super(tuple);
		this.association = association;
		this.operation = operation;
	} // LinkOperationExpression

	public QualifiedName getAssociation() {
		return this.association;
	} // getAssociation

	public String getOperation() {
		return this.operation;
	} // getOperation

	public String toString() {
		return super.toString() + " operation:" + this.getOperation();
	} // toString

	public void printTarget(String prefix) {
		this.getAssociation().printChild(prefix);

	} // printTarget

} // LinkOperationExpression
