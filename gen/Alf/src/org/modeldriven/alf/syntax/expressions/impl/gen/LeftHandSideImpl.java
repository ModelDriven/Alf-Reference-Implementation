
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions.impl.gen;

import org.modeldriven.alf.parser.AlfParser;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.Profile;
import org.modeldriven.alf.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The left-hand side of an assignment expression.
 * 
 * NOTE: The derivations for the derived properties of LeftHandSide are specific
 * to its various subclasses.
 **/

public abstract class LeftHandSideImpl extends
		org.modeldriven.alf.syntax.common.impl.gen.SyntaxElementImpl {

	private Collection<AssignedSource> assignmentBefore = null; // DERIVED
	private Collection<AssignedSource> assignmentAfter = null; // DERIVED
	private Expression index = null;

	public LeftHandSideImpl(LeftHandSide self) {
		super(self);
	}

	public LeftHandSide getSelf() {
		return (LeftHandSide) this.self;
	}

	public Collection<AssignedSource> getAssignmentBefore() {
		if (this.assignmentBefore == null) {
			this.setAssignmentBefore(this.deriveAssignmentBefore());
		}
		return this.assignmentBefore;
	}

	public void setAssignmentBefore(Collection<AssignedSource> assignmentBefore) {
		this.assignmentBefore = assignmentBefore;
	}

	public void addAssignmentBefore(AssignedSource assignmentBefore) {
		this.assignmentBefore.add(assignmentBefore);
	}

	public Collection<AssignedSource> getAssignmentAfter() {
		if (this.assignmentAfter == null) {
			this.setAssignmentAfter(this.deriveAssignmentAfter());
		}
		return this.assignmentAfter;
	}

	public void setAssignmentAfter(Collection<AssignedSource> assignmentAfter) {
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

	protected Collection<AssignedSource> deriveAssignmentBefore() {
		return null; // STUB
	}

	protected Collection<AssignedSource> deriveAssignmentAfter() {
		return null; // STUB
	}

	/**
	 * If a left-hand side has an index, then the index expression must have a
	 * multiplicity upper bound no greater than 1.
	 **/
	public boolean leftHandSideIndexExpression() {
		return true;
	}

} // LeftHandSideImpl
