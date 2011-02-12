
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

import org.modeldriven.alf.syntax.expressions.impl.ExpressionImpl;

/**
 * A model of the common properties derived for any Alf expression.
 * 
 * NOTE: The derivations for all properties of Expression except
 * AssignmentsAfter are specific to its various subclasses.
 **/

public abstract class Expression extends SyntaxElement {

	private ArrayList<AssignedSource> assignmentBefore = null; // DERIVED
	private ArrayList<AssignedSource> assignmentAfter = null; // DERIVED
	private Integer upper = null; // DERIVED
	private Integer lower = null; // DERIVED
	private ElementReference type = null; // DERIVED

	public ExpressionImpl getImpl() {
		return (ExpressionImpl) this.impl;
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

	public Integer getUpper() {
		if (this.upper == null) {
			this.upper = this.getImpl().deriveUpper();
		}
		return this.upper;
	}

	public Integer getLower() {
		if (this.lower == null) {
			this.lower = this.getImpl().deriveLower();
		}
		return this.lower;
	}

	public ElementReference getType() {
		if (this.type == null) {
			this.type = this.getImpl().deriveType();
		}
		return this.type;
	}

	/**
	 * The assignments after an expression are given by the result of the
	 * updateAssignments helper operation.
	 **/
	public boolean expressionAssignmentAfterDerivation() {
		return this.getImpl().expressionAssignmentAfterDerivation();
	}

	/**
	 * No name may be assigned more than once before or after an expression.
	 **/
	public boolean expressionUniqueAssignments() {
		return this.getImpl().expressionUniqueAssignments();
	}

	/**
	 * Returns the assignments from before this expression updated for any
	 * assignments made in the expression. By default, this is the same set as
	 * the assignments before the expression. This operation is redefined only
	 * in subclasses of Expression for kinds of expressions that make
	 * assignments.
	 **/
	public ArrayList<AssignedSource> updateAssignments() {
		return this.getImpl().updateAssignments();
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		Integer upper = this.getUpper();
		if (upper != null) {
			s.append(" /upper:");
			s.append(upper);
		}
		Integer lower = this.getLower();
		if (lower != null) {
			s.append(" /lower:");
			s.append(lower);
		}
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
		ElementReference type = this.getType();
		if (type != null) {
			System.out.println(prefix + " /" + type);
		}
	}
} // Expression
