
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
 * A model of the common properties derived for any Alf expression.
 * 
 * NOTE: The derivations for all properties of Expression except
 * AssignmentsAfter are specific to its various subclasses.
 **/

public abstract class Expression extends SyntaxElement {

	private ArrayList<AssignedSource> assignmentBefore = new ArrayList<AssignedSource>(); // DERIVED
	private ArrayList<AssignedSource> assignmentAfter = new ArrayList<AssignedSource>(); // DERIVED
	private int upper = 0; // DERIVED
	private int lower = 0; // DERIVED
	private ElementReference type = null; // DERIVED

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

	public int getUpper() {
		return this.upper;
	}

	public void setUpper(int upper) {
		this.upper = upper;
	}

	public int getLower() {
		return this.lower;
	}

	public void setLower(int lower) {
		this.lower = lower;
	}

	public ElementReference getType() {
		return this.type;
	}

	public void setType(ElementReference type) {
		this.type = type;
	}

	public ArrayList<AssignedSource> updateAssignments() {
		/*
		 * Returns the assignments from before this expression updated for any
		 * assignments made in the expression. By default, this is the same set
		 * as the assignments before the expression. This operation is redefined
		 * only in subclasses of Expression for kinds of expressions that make
		 * assignments.
		 */
		return new ArrayList<AssignedSource>(); // STUB
	} // updateAssignments

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
	}
} // Expression
