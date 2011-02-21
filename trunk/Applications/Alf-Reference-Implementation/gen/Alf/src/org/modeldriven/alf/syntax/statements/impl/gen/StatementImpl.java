
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.statements.impl.gen;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.omg.uml.*;

import java.util.ArrayList;

/**
 * A model of an Alf statement.
 **/

public abstract class StatementImpl extends
		org.modeldriven.alf.syntax.common.impl.gen.DocumentedElementImpl {

	public StatementImpl(Statement self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.statements.Statement getSelf() {
		return (Statement) this.self;
	}

	public ArrayList<AssignedSource> deriveAssignmentBefore() {
		return null; // STUB
	}

	public ArrayList<AssignedSource> deriveAssignmentAfter() {
		return null; // STUB
	}

	public Statement deriveEnclosingStatement() {
		return null; // STUB
	}

	public Boolean deriveIsIsolated() {
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

} // StatementImpl
