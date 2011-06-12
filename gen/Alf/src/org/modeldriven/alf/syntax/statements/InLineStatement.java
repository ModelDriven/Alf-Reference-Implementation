
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

import org.omg.uml.Element;
import org.omg.uml.Profile;
import org.omg.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.statements.impl.InLineStatementImpl;

/**
 * A statement that executes code in a language other than Alf.
 **/

public class InLineStatement extends Statement {

	public InLineStatement() {
		this.impl = new InLineStatementImpl(this);
	}

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

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.inLineStatementAssignmentsAfter()) {
			violations.add(new ConstraintViolation(
					"inLineStatementAssignmentsAfter", this));
		}
	}

	public String toString() {
		return "(" + this.hashCode() + ")" + this.getImpl().toString();
	}

	public String _toString() {
		StringBuffer s = new StringBuffer(super._toString());
		s.append(" language:");
		s.append(this.getLanguage());
		s.append(" code:");
		s.append(this.getCode());
		return s.toString();
	}

	public void print() {
		this.print("");
	}

	public void print(String prefix) {
		super.print(prefix);
	}
} // InLineStatement
