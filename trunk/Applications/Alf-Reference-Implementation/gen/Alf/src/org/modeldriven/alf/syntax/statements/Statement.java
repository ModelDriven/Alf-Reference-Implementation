
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
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

import org.omg.uml.*;

import java.util.ArrayList;

import org.modeldriven.alf.syntax.statements.impl.StatementImpl;

/**
 * A model of an Alf statement.
 **/

public abstract class Statement extends DocumentedElement {

	private ArrayList<Annotation> annotation = new ArrayList<Annotation>();
	private ArrayList<AssignedSource> assignmentBefore = null; // DERIVED
	private ArrayList<AssignedSource> assignmentAfter = null; // DERIVED
	private Statement enclosingStatement = null; // DERIVED
	private Boolean isIsolated = null; // DERIVED

	public StatementImpl getImpl() {
		return (StatementImpl) this.impl;
	}

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

	public Statement getEnclosingStatement() {
		if (this.enclosingStatement == null) {
			this.enclosingStatement = this.getImpl().deriveEnclosingStatement();
		}
		return this.enclosingStatement;
	}

	public Boolean getIsIsolated() {
		if (this.isIsolated == null) {
			this.isIsolated = this.getImpl().deriveIsIsolated();
		}
		return this.isIsolated;
	}

	/**
	 * All the annotations of a statement must be allowed, as given by the
	 * annotationAllowed operation for the statement.
	 **/
	public boolean statementAnnotationsAllowed() {
		return this.getImpl().statementAnnotationsAllowed();
	}

	/**
	 * No name may be assigned more than once before or after a statement.
	 **/
	public boolean statementUniqueAssignments() {
		return this.getImpl().statementUniqueAssignments();
	}

	/**
	 * A statement is isolated if it has an @isolated annotation.
	 **/
	public boolean statementIsIsolatedDerivation() {
		return this.getImpl().statementIsIsolatedDerivation();
	}

	/**
	 * Returns true if the given annotation is allowed for this kind of
	 * statement. By default, only an @isolated annotation is allowed, with no
	 * arguments. This operation is redefined only in subclasses of Statement
	 * for kinds of statements that allow different annotations than this
	 * default.
	 **/
	public Boolean annotationAllowed(Annotation annotation) {
		return this.getImpl().annotationAllowed(annotation);
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		Boolean isIsolated = this.getIsIsolated();
		if (isIsolated != null) {
			s.append(" /isIsolated:");
			s.append(isIsolated);
		}
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		ArrayList<Annotation> annotation = this.getAnnotation();
		if (annotation != null) {
			if (annotation.size() > 0) {
				System.out.println(prefix + " annotation:");
			}
			for (Annotation _annotation : (ArrayList<Annotation>) annotation
					.clone()) {
				if (_annotation != null) {
					_annotation.print(prefix + "  ");
				} else {
					System.out.println(prefix + "  null");
				}
			}
		}
		ArrayList<AssignedSource> assignmentBefore = this.getAssignmentBefore();
		if (assignmentBefore != null) {
			if (assignmentBefore.size() > 0) {
				System.out.println(prefix + " /assignmentBefore:");
			}
			for (AssignedSource _assignmentBefore : (ArrayList<AssignedSource>) assignmentBefore
					.clone()) {
				System.out.println(prefix + "  " + _assignmentBefore);
			}
		}
		ArrayList<AssignedSource> assignmentAfter = this.getAssignmentAfter();
		if (assignmentAfter != null) {
			if (assignmentAfter.size() > 0) {
				System.out.println(prefix + " /assignmentAfter:");
			}
			for (AssignedSource _assignmentAfter : (ArrayList<AssignedSource>) assignmentAfter
					.clone()) {
				System.out.println(prefix + "  " + _assignmentAfter);
			}
		}
		Statement enclosingStatement = this.getEnclosingStatement();
		if (enclosingStatement != null) {
			System.out.println(prefix + " /enclosingStatement:"
					+ enclosingStatement);
		}
	}
} // Statement
