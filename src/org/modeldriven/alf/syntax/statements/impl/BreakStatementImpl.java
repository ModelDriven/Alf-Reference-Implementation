
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
 * A statement that causes the termination of execution of an immediately
 * enclosing block.
 **/

public class BreakStatementImpl extends StatementImpl {

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
	    return this.getLoopStatement();
	}
	
	/*
	 * Derivations
	 */

	/**
	 * The target of a break statement is the innermost switch, while, do or for
	 * statement enclosing the break statement.
	 **/
	public boolean breakStatementTargetDerivation() {
	    // Note: The target attribute is mandatory.
		return this.getSelf().getTarget() != null;
	}
	
	/*
	 * Constraints
	 */

	/**
	 * The target of a break statement may not have a @parallel annotation.
	 **/
	public boolean breakStatementNonparallelTarget() {
	    Statement target = this.getSelf().getTarget();
		return target == null || !target.getImpl().hasAnnotation("parallel");
	}
	
	/*
	 * Helper Methods
	 */

	/**
	 * A break statement may not have any annotations.
	 **/
	public Boolean annotationAllowed(Annotation annotation) {
		return false;
	} // annotationAllowed

} // BreakStatementImpl
