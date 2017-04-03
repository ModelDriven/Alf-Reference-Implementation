
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
import org.modeldriven.alf.syntax.statements.impl.InLineStatementImpl;

/**
 * A statement that executes code in a language other than Alf.
 **/

public class InLineStatement extends Statement {

	public InLineStatement() {
		this.impl = new InLineStatementImpl(this);
	}

	public InLineStatement(Parser parser) {
		this();
		this.init(parser);
	}

	public InLineStatement(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
    public InLineStatementImpl getImpl() {
		return (InLineStatementImpl) this.impl;
	}

	public String getLanguage() {
		return this.getImpl().getLanguage();
	}

	public void setLanguage(String language) {
		this.getImpl().setLanguage(language);
	}

	public String getCode() {
		return this.getImpl().getCode();
	}

	public void setCode(String code) {
		this.getImpl().setCode(code);
	}

	/**
	 * The assignments after an in-line statement are the same as the
	 * assignments before the statement.
	 **/
	public boolean inLineStatementAssignmentsAfter() {
		return this.getImpl().inLineStatementAssignmentsAfter();
	}

	@Override
    public void _deriveAll() {
		super._deriveAll();
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.inLineStatementAssignmentsAfter()) {
			violations.add(new ConstraintViolation(
					"inLineStatementAssignmentsAfter", this));
		}
	}

	@Override
    public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		s.append(" language:");
		s.append(this.getLanguage());
		s.append(" code:");
		s.append(this.getCode());
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
} // InLineStatement
