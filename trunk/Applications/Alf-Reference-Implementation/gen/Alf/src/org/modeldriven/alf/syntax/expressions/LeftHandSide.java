
/*
 * Copyright 2010 Data Access Technologies, Inc. (Model Driven Solutions)
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

/**
 * The left-hand side of an assignment expression.
 * 
 * NOTE: The derivations for the derived properties of LeftHandSide are specific
 * to its various subclasses.
 **/

public abstract class LeftHandSide extends SyntaxElement {

	private ArrayList<AssignedSource> assignmentBefore = new ArrayList<AssignedSource>(); // DERIVED
	private ArrayList<AssignedSource> assignmentAfter = new ArrayList<AssignedSource>(); // DERIVED
	private Expression index = null;

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

	public Expression getIndex() {
		return this.index;
	}

	public void setIndex(Expression index) {
		this.index = index;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		if (this.index != null) {
			this.index.print(prefix + " ");
		}
	}
} // LeftHandSide
