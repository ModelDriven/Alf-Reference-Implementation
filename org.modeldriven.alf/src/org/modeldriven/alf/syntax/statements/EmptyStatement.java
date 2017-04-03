
/*******************************************************************************
 * Copyright 2011, 2016 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.statements;

import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.syntax.common.*;
import java.util.Collection;
import org.modeldriven.alf.syntax.statements.impl.EmptyStatementImpl;

/**
 * A statement that has no affect when executed.
 **/

public class EmptyStatement extends Statement {

	public EmptyStatement() {
		this.impl = new EmptyStatementImpl(this);
	}

	public EmptyStatement(Parser parser) {
		this();
		this.init(parser);
	}

	public EmptyStatement(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
    public EmptyStatementImpl getImpl() {
		return (EmptyStatementImpl) this.impl;
	}

	/**
	 * The assignments after and empty statement are the same as the assignments
	 * before the statement.
	 **/
	public boolean emptyStatementAssignmentsAfter() {
		return this.getImpl().emptyStatementAssignmentsAfter();
	}

	/**
	 * An empty statement may not have any annotations.
	 **/
	@Override
    public Boolean annotationAllowed(Annotation annotation) {
		return this.getImpl().annotationAllowed(annotation);
	}

	@Override
    public void _deriveAll() {
		super._deriveAll();
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.emptyStatementAssignmentsAfter()) {
			violations.add(new ConstraintViolation(
					"emptyStatementAssignmentsAfter", this));
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
	}
} // EmptyStatement
