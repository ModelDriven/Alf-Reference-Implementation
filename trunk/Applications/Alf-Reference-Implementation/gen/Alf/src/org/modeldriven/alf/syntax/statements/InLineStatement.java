
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.statements;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

import org.modeldriven.alf.syntax.statements.impl.InLineStatementImpl;

/**
 * A statement that executes code in a language other than Alf.
 **/

public class InLineStatement extends Statement {

	private String language = "";
	private String code = "";

	public InLineStatement() {
		this.impl = new InLineStatementImpl(this);
	}

	public InLineStatementImpl getImpl() {
		return (InLineStatementImpl) this.impl;
	}

	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * The assignments after an in-line statement are the same as the
	 * assignments before the statement.
	 **/
	public boolean inLineStatementAssignmentsAfter() {
		return this.getImpl().inLineStatementAssignmentsAfter();
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" language:");
		s.append(this.getLanguage());
		s.append(" code:");
		s.append(this.getCode());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
	}
} // InLineStatement
