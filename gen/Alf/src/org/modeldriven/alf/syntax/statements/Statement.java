
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
 * A model of an Alf statement.
 **/

public abstract class Statement extends DocumentedElement {

	private ArrayList<Annotation> annotation = new ArrayList<Annotation>();
	private ArrayList<AssignedSource> assignmentBefore = new ArrayList<AssignedSource>(); // DERIVED
	private ArrayList<AssignedSource> assignmentAfter = new ArrayList<AssignedSource>(); // DERIVED
	private Statement enclosingStatement = null; // DERIVED
	private boolean isIsolated = false; // DERIVED

	public ArrayList<Annotation> getAnnotation() {
		return this.annotation;
	}

	public void setAnnotation(ArrayList<Annotation> annotation) {
		this.annotation = annotation;
	}

	public void addAnnotation(Annotation annotation) {
		this.annotation.add(annotation);
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

	public Statement getEnclosingStatement() {
		return this.enclosingStatement;
	}

	public void setEnclosingStatement(Statement enclosingStatement) {
		this.enclosingStatement = enclosingStatement;
	}

	public boolean getIsIsolated() {
		return this.isIsolated;
	}

	public void setIsIsolated(boolean isIsolated) {
		this.isIsolated = isIsolated;
	}

	public boolean annotationAllowed(Annotation annotation) {
		/*
		 * Returns true if the given annotation is allowed for this kind of
		 * statement. By default, only an @isolated annotation is allowed, with
		 * no arguments. This operation is redefined only in subclasses of
		 * Statement for kinds of statements that allow different annotations
		 * than this default.
		 */
		return false; // STUB
	} // annotationAllowed

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		for (Annotation annotation : this.getAnnotation()) {
			if (annotation != null) {
				annotation.print(prefix + " ");
			} else {
				System.out.println(prefix + " null");
			}
		}
	}
} // Statement
