
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0
 * (http://www.opensource.org/licenses/afl-3.0.php)
 *
 */

package org.modeldriven.alf.syntax.statements.impl;

import org.modeldriven.alf.syntax.statements.*;

/**
 * A statement that has no affect when executed.
 **/

public class EmptyStatementImpl extends StatementImpl {

	public EmptyStatementImpl(EmptyStatement self) {
		super(self);
	}

	@Override
	public EmptyStatement getSelf() {
		return (EmptyStatement) this.self;
	}

	/**
	 * The assignments after and empty statement are the same as the assignments
	 * before the statement.
	 **/
	public boolean emptyStatementAssignmentsAfter() {
	    // Note: This is handled by the inherited deriveAssignmentAfter.
		return true;
	}

	/**
	 * An empty statement may not have any annotations.
	 **/
	public Boolean annotationAllowed(Annotation annotation) {
		return false;
	} // annotationAllowed

} // EmptyStatementImpl
