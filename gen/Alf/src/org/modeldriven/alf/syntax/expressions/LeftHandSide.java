
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

import org.modeldriven.alf.syntax.expressions.impl.LeftHandSideImpl;

/**
 * The left-hand side of an assignment expression.
 * 
 * NOTE: The derivations for the derived properties of LeftHandSide are specific
 * to its various subclasses.
 **/

public abstract class LeftHandSide extends SyntaxElement {

	private ArrayList<AssignedSource> assignmentBefore = null; // DERIVED
	private ArrayList<AssignedSource> assignmentAfter = null; // DERIVED
	private Expression index = null;

	public LeftHandSideImpl getImpl() {
		return (LeftHandSideImpl) this.impl;
	}

	public ArrayList<AssignedSource> getAssignmentBefore() {
		if (this.assignmentBefore == null) {
			this.assignmentBefore = this.getImpl().deriveAssignmentBefore();
		}
		return this.assignmentBefore;
	}

	public ArrayList<AssignedSource> getAssignmentAfter() {
		if (this.assignmentAfter == null) {
			this.assignmentAfter = this.getImpl().deriveAssignmentAfter();
		}
		return this.assignmentAfter;
	}

	public Expression getIndex() {
		return this.index;
	}

	public void setIndex(Expression index) {
		this.index = index;
	}

	/**
	 * If a left-hand side has an index, then the index expression must have a
	 * multiplicity upper bound no greater than 1.
	 **/
	public boolean leftHandSideIndexExpression() {
		return this.getImpl().leftHandSideIndexExpression();
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		ArrayList<AssignedSource> assignmentBefore = this.getAssignmentBefore();
		if (assignmentBefore != null) {
			for (AssignedSource item : this.getAssignmentBefore()) {
				System.out.println(prefix + " /" + item);
			}
		}
		ArrayList<AssignedSource> assignmentAfter = this.getAssignmentAfter();
		if (assignmentAfter != null) {
			for (AssignedSource item : this.getAssignmentAfter()) {
				System.out.println(prefix + " /" + item);
			}
		}
		Expression index = this.getIndex();
		if (index != null) {
			index.print(prefix + " ");
		}
	}
} // LeftHandSide
