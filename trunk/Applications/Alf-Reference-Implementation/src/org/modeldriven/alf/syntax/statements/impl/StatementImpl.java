
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0
 * (http://www.opensource.org/licenses/afl-3.0.php)
 *
 */

package org.modeldriven.alf.syntax.statements.impl;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.omg.uml.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A model of an Alf statement.
 **/

public abstract class StatementImpl extends
		org.modeldriven.alf.syntax.common.impl.DocumentedElementImpl {

	private Collection<Annotation> annotation = new ArrayList<Annotation>();
	private Collection<AssignedSource> assignmentBefore = null; // DERIVED
	private Collection<AssignedSource> assignmentAfter = null; // DERIVED
	private Statement enclosingStatement = null; // DERIVED
	private Boolean isIsolated = null; // DERIVED

	public StatementImpl(Statement self) {
		super(self);
	}

	public Statement getSelf() {
		return (Statement) this.self;
	}

	public Collection<Annotation> getAnnotation() {
		return this.annotation;
	}

	public void setAnnotation(Collection<Annotation> annotation) {
		this.annotation = annotation;
	}

	public void addAnnotation(Annotation annotation) {
		this.annotation.add(annotation);
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

	public Statement getEnclosingStatement() {
		if (this.enclosingStatement == null) {
			this.setEnclosingStatement(this.deriveEnclosingStatement());
		}
		return this.enclosingStatement;
	}

	public void setEnclosingStatement(Statement enclosingStatement) {
		this.enclosingStatement = enclosingStatement;
	}

	public Boolean getIsIsolated() {
		if (this.isIsolated == null) {
			this.setIsIsolated(this.deriveIsIsolated());
		}
		return this.isIsolated;
	}

	public void setIsIsolated(Boolean isIsolated) {
		this.isIsolated = isIsolated;
	}

	protected Collection<AssignedSource> deriveAssignmentBefore() {
		return new ArrayList<AssignedSource>(); // STUB
	}

	protected Collection<AssignedSource> deriveAssignmentAfter() {
		return null; // STUB
	}

	protected Statement deriveEnclosingStatement() {
		return null; // STUB
	}

	protected Boolean deriveIsIsolated() {
		return null; // STUB
	}

	/**
	 * All the annotations of a statement must be allowed, as given by the
	 * annotationAllowed operation for the statement.
	 **/
	public boolean statementAnnotationsAllowed() {
		return true;
	}

	/**
	 * No name may be assigned more than once before or after a statement.
	 **/
	public boolean statementUniqueAssignments() {
		return true;
	}

	/**
	 * A statement is isolated if it has an @isolated annotation.
	 **/
	public boolean statementIsIsolatedDerivation() {
		this.getSelf().getIsIsolated();
		return true;
	}

	/**
	 * Returns true if the given annotation is allowed for this kind of
	 * statement. By default, only an @isolated annotation is allowed, with no
	 * arguments. This operation is redefined only in subclasses of Statement
	 * for kinds of statements that allow different annotations than this
	 * default.
	 **/
	public Boolean annotationAllowed(Annotation annotation) {
		return false; // STUB
	} // annotationAllowed

    public void setCurrentScope(NamespaceDefinition currentScope) {
        // TODO Should be abstract.
        return;
    }

} // StatementImpl
