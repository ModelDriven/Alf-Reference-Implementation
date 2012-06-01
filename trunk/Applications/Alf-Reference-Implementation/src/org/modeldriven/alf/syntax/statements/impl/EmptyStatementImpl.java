
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

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
