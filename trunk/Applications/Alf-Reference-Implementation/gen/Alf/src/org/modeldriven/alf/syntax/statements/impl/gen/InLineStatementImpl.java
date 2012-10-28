
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
import java.util.TreeSet;

/**
 * A statement that executes code in a language other than Alf.
 **/

public class InLineStatementImpl extends
		org.modeldriven.alf.syntax.statements.impl.gen.StatementImpl {

	private String language = "";
	private String code = "";

	public InLineStatementImpl(InLineStatement self) {
		super(self);
	}

	public InLineStatement getSelf() {
		return (InLineStatement) this.self;
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
		return true;
	}

} // InLineStatementImpl
