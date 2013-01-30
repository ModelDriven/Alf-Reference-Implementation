
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

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
