/*******************************************************************************
 * Copyright 2011, 2018 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.statements;

import org.modeldriven.alf.parser.ParsedElement;
import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.syntax.common.*;
import java.util.Collection;
import org.modeldriven.alf.syntax.statements.impl.BreakStatementImpl;

/**
 * A statement that causes the termination of execution of an immediately
 * enclosing block.
 **/

public class BreakStatement extends Statement {

	public BreakStatement() {
		this.impl = new BreakStatementImpl(this);
	}

	public BreakStatement(Parser parser) {
		this();
		this.init(parser);
	}

	public BreakStatement(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
    public BreakStatementImpl getImpl() {
		return (BreakStatementImpl) this.impl;
	}

	public Statement getTarget() {
		return this.getImpl().getTarget();
	}

	public void setTarget(Statement target) {
		this.getImpl().setTarget(target);
	}

	/**
	 * The target of a break statement is the innermost switch, while, do or for
	 * statement enclosing the break statement.
	 **/
	public boolean breakStatementTargetDerivation() {
		return this.getImpl().breakStatementTargetDerivation();
	}

	/**
	 * The target of a break statement may not have a @parallel annotation.
	 **/
	public boolean breakStatementNonparallelTarget() {
		return this.getImpl().breakStatementNonparallelTarget();
	}

	/**
	 * A break statement may not have any annotations.
	 **/
	@Override
    public Boolean annotationAllowed(Annotation annotation) {
		return this.getImpl().annotationAllowed(annotation);
	}

	@Override
    public void _deriveAll() {
		this.getTarget();
		super._deriveAll();
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.breakStatementTargetDerivation()) {
			violations.add(new ConstraintViolation(
					"breakStatementTargetDerivation", this));
		}
		if (!this.breakStatementNonparallelTarget()) {
			violations.add(new ConstraintViolation(
					"breakStatementNonparallelTarget", this));
		}
	}

	@Override
    public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		return s.toString();
	}

	@Override
    public void print() {
		this.print("", false);
	}

	@Override
    public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	@Override
    public void print(String prefix, boolean includeDerived) {
		super.print(prefix, includeDerived);
		if (includeDerived) {
			Statement target = this.getTarget();
			if (target != null) {
				System.out.println(prefix + " /target:"
						+ target.toString(includeDerived));
			}
		}
	}
} // BreakStatement
