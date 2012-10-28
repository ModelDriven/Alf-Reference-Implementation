
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.fuml.mapping.statements.gen;

import org.modeldriven.alf.fuml.mapping.statements.gen.StatementMapping;

import org.modeldriven.alf.syntax.statements.SwitchStatement;

import org.modeldriven.alf.uml.Element;

import java.util.ArrayList;
import java.util.List;

public class SwitchStatementMapping extends StatementMapping {

	public SwitchStatementMapping() {
		this.setErrorMessage("SwitchStatementMapping not yet implemented.");
	}

	public List<Element> getModelElements() {
		// TODO: Auto-generated stub
		return new ArrayList<Element>();
	}

	public SwitchStatement getSwitchStatement() {
		return (SwitchStatement) this.getSource();
	}

} // SwitchStatementMapping
