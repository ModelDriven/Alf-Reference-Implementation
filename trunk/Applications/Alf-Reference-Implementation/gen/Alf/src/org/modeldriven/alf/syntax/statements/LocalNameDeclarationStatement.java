
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.statements;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

public class LocalNameDeclarationStatement extends Statement {

	private String name = "";
	private Expression expression = null;
	private QualifiedName typeName = null;
	private boolean hasMultiplicity = false;

	public void setName(String name) {
		this.name = name;
	} // setName

	public String getName() {
		return this.name;
	} // getName

	public void setTypeName(QualifiedName typeName) {
		this.typeName = typeName;
	} // setTypeName

	public QualifiedName getTypeName() {
		return this.typeName;
	} // getTypeName

	public void setHasMultiplicity() {
		this.hasMultiplicity = true;
	} // setHasMultiplicity

	public boolean hasMultiplicity() {
		return this.hasMultiplicity;
	} // hasMultiplicity

	public void setExpression(Expression expression) {
		this.expression = expression;
	} // setExpression

	public Expression getExpression() {
		return this.expression;
	} // getExpression

	public String toString() {
		return super.toString() + " name:" + this.getName()
				+ " hasMultiplicity:" + this.hasMultiplicity();
	} // toString

	public void print(String prefix) {
		super.print(prefix);
		this.getTypeName().printChild(prefix);
		this.getExpression().printChild(prefix);
	} // print

} // LocalNameDeclarationStatement
