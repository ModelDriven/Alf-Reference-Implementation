
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
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.statements.impl.StatementImpl;

/**
 * A model of an Alf statement.
 **/

public abstract class Statement extends DocumentedElement {

	public StatementImpl getImpl() {
		return (StatementImpl) this.impl;
	}

	public Collection<Annotation> getAnnotation() {
		return this.getImpl().getAnnotation();
	}

	public void setAnnotation(Collection<Annotation> annotation) {
		this.getImpl().setAnnotation(annotation);
	}

	public void addAnnotation(Annotation annotation) {
		this.getImpl().addAnnotation(annotation);
	}

	public Collection<AssignedSource> getAssignmentBefore() {
		return this.getImpl().getAssignmentBefore();
	}

	public void setAssignmentBefore(Collection<AssignedSource> assignmentBefore) {
		this.getImpl().setAssignmentBefore(assignmentBefore);
	}

	public void addAssignmentBefore(AssignedSource assignmentBefore) {
		this.getImpl().addAssignmentBefore(assignmentBefore);
	}

	public Collection<AssignedSource> getAssignmentAfter() {
		return this.getImpl().getAssignmentAfter();
	}

	public void setAssignmentAfter(Collection<AssignedSource> assignmentAfter) {
		this.getImpl().setAssignmentAfter(assignmentAfter);
	}

	public void addAssignmentAfter(AssignedSource assignmentAfter) {
		this.getImpl().addAssignmentAfter(assignmentAfter);
	}

	public Statement getEnclosingStatement() {
		return this.getImpl().getEnclosingStatement();
	}

	public void setEnclosingStatement(Statement enclosingStatement) {
		this.getImpl().setEnclosingStatement(enclosingStatement);
	}

	public Boolean getIsIsolated() {
		return this.getImpl().getIsIsolated();
	}

	public void setIsIsolated(Boolean isIsolated) {
		this.getImpl().setIsIsolated(isIsolated);
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

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.statementAnnotationsAllowed()) {
			violations.add(new ConstraintViolation(
					"statementAnnotationsAllowed", this));
		}
		if (!this.statementUniqueAssignments()) {
			violations.add(new ConstraintViolation(
					"statementUniqueAssignments", this));
		}
		if (!this.statementIsIsolatedDerivation()) {
			violations.add(new ConstraintViolation(
					"statementIsIsolatedDerivation", this));
		}
		for (Annotation _annotation : this.getAnnotation()) {
			_annotation.checkConstraints(violations);
		}
	}

	public String toString() {
		return this.getImpl().toString();
	}

	public String _toString() {
		StringBuffer s = new StringBuffer(super._toString());
		Boolean isIsolated = this.getIsIsolated();
		if (isIsolated != null) {
			s.append(" /isIsolated:");
			s.append(isIsolated);
		}
		return s.toString();
	}

	public void print() {
		this.print("");
	}

	public void print(String prefix) {
		super.print(prefix);
		Collection<Annotation> annotation = this.getAnnotation();
		if (annotation != null) {
			if (annotation.size() > 0) {
				System.out.println(prefix + " annotation:");
			}
			for (Annotation _annotation : annotation) {
				if (_annotation != null) {
					_annotation.print(prefix + "  ");
				} else {
					System.out.println(prefix + "  null");
				}
			}
		}
		Collection<AssignedSource> assignmentBefore = this
				.getAssignmentBefore();
		if (assignmentBefore != null) {
			if (assignmentBefore.size() > 0) {
				System.out.println(prefix + " /assignmentBefore:");
			}
			for (AssignedSource _assignmentBefore : assignmentBefore) {
				System.out.println(prefix + "  " + _assignmentBefore);
			}
		}
		Collection<AssignedSource> assignmentAfter = this.getAssignmentAfter();
		if (assignmentAfter != null) {
			if (assignmentAfter.size() > 0) {
				System.out.println(prefix + " /assignmentAfter:");
			}
			for (AssignedSource _assignmentAfter : assignmentAfter) {
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
