
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.statements.impl.gen;

import org.modeldriven.alf.parser.AlfParser;
import org.modeldriven.alf.parser.Token;

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
 * A statement that has no affect when executed.
 **/

public class EmptyStatementImpl extends
		org.modeldriven.alf.syntax.statements.impl.gen.StatementImpl {

	public EmptyStatementImpl(EmptyStatement self) {
		super(self);
	}

	public EmptyStatement getSelf() {
		return (EmptyStatement) this.self;
	}

	/**
	 * The assignments after and empty statement are the same as the assignments
	 * before the statement.
	 **/
	public boolean emptyStatementAssignmentsAfter() {
		return true;
	}

	/**
	 * An empty statement may not have any annotations.
	 **/
	public Boolean annotationAllowed(Annotation annotation) {
		return false; // STUB
	} // annotationAllowed

} // EmptyStatementImpl
