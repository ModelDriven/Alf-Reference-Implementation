
/*
 * Copyright 2010 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.statements;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

/**
 * The definition of a loop variable in a for statement.
 **/

public class LoopVariableDefinition extends SyntaxElement {

	private String variable = "";
	private Expression expression1 = null;
	private Expression expression2 = null;
	private QualifiedName typeName = null;
	private boolean typeIsInferred = true;
	private boolean isCollectionConversion = false; // DERIVED
	private ElementReference type = null; // DERIVED
	private boolean isFirst = false; // DERIVED
	private ArrayList<AssignedSource> assignmentBefore = new ArrayList<AssignedSource>(); // DERIVED
	private ArrayList<AssignedSource> assignmentAfter = new ArrayList<AssignedSource>(); // DERIVED

	public String getVariable() {
		return this.variable;
	}

	public void setVariable(String variable) {
		this.variable = variable;
	}

	public Expression getExpression1() {
		return this.expression1;
	}

	public void setExpression1(Expression expression1) {
		this.expression1 = expression1;
	}

	public Expression getExpression2() {
		return this.expression2;
	}

	public void setExpression2(Expression expression2) {
		this.expression2 = expression2;
	}

	public QualifiedName getTypeName() {
		return this.typeName;
	}

	public void setTypeName(QualifiedName typeName) {
		this.typeName = typeName;
	}

	public boolean getTypeIsInferred() {
		return this.typeIsInferred;
	}

	public void setTypeIsInferred(boolean typeIsInferred) {
		this.typeIsInferred = typeIsInferred;
	}

	public boolean getIsCollectionConversion() {
		return this.isCollectionConversion;
	}

	public void setIsCollectionConversion(boolean isCollectionConversion) {
		this.isCollectionConversion = isCollectionConversion;
	}

	public ElementReference getType() {
		return this.type;
	}

	public void setType(ElementReference type) {
		this.type = type;
	}

	public boolean getIsFirst() {
		return this.isFirst;
	}

	public void setIsFirst(boolean isFirst) {
		this.isFirst = isFirst;
	}

	public ArrayList<AssignedSource> getAssignmentBefore() {
		return this.assignmentBefore;
	}

	public void setAssignmentBefore(ArrayList<AssignedSource> assignmentBefore) {
		this.assignmentBefore = assignmentBefore;
	}

	public void addAssignmentBefore(AssignedSource assignmentBefore) {
		this.assignmentBefore.add(assignmentBefore);
	}

	public ArrayList<AssignedSource> getAssignmentAfter() {
		return this.assignmentAfter;
	}

	public void setAssignmentAfter(ArrayList<AssignedSource> assignmentAfter) {
		this.assignmentAfter = assignmentAfter;
	}

	public void addAssignmentAfter(AssignedSource assignmentAfter) {
		this.assignmentAfter.add(assignmentAfter);
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" variable:");
		s.append(this.variable);
		s.append(" typeIsInferred:");
		s.append(this.typeIsInferred);
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		if (this.expression1 != null) {
			this.expression1.print(prefix + " ");
		}
		if (this.expression2 != null) {
			this.expression2.print(prefix + " ");
		}
		if (this.typeName != null) {
			this.typeName.print(prefix + " ");
		}
	}
} // LoopVariableDefinition
