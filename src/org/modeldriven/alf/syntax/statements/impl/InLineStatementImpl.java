
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

import java.util.ArrayList;

/**
 * A statement that executes code in a language other than Alf.
 **/

public class InLineStatementImpl extends
		org.modeldriven.alf.syntax.statements.impl.StatementImpl {

	public InLineStatementImpl(InLineStatement self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.statements.InLineStatement getSelf() {
		return (InLineStatement) this.self;
	}

	/**
	 * The assignments after an in-line statement are the same as the
	 * assignments before the statement.
	 **/
	public boolean inLineStatementAssignmentsAfter() {
		return true;
	}

} // InLineStatementImpl
