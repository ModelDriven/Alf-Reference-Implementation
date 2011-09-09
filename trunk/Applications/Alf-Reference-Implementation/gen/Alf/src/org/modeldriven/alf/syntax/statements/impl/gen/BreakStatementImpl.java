
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.statements.impl.gen;

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
 * A statement that causes the termination of execution of an immediately
 * enclosing block.
 **/

public class BreakStatementImpl extends
		org.modeldriven.alf.syntax.statements.impl.gen.StatementImpl {

	private Statement target = null; // DERIVED

	public BreakStatementImpl(BreakStatement self) {
		super(self);
	}

	public BreakStatement getSelf() {
		return (BreakStatement) this.self;
	}

	public Statement getTarget() {
		if (this.target == null) {
			this.setTarget(this.deriveTarget());
		}
		return this.target;
	}

	public void setTarget(Statement target) {
		this.target = target;
	}

	protected Statement deriveTarget() {
		return null; // STUB
	}

	/**
	 * The target of a break statement is the innermost switch, while, do or for
	 * statement enclosing the break statement.
	 **/
	public boolean breakStatementTargetDerivation() {
		this.getSelf().getTarget();
		return true;
	}

	/**
	 * The target of a break statement may not have a @parallel annotation.
	 **/
	public boolean breakStatementNonparallelTarget() {
		return true;
	}

	/**
	 * A break statement may not have any annotations.
	 **/
	public Boolean annotationAllowed(Annotation annotation) {
		return false; // STUB
	} // annotationAllowed

} // BreakStatementImpl
