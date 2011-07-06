
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.statements.gen;

import org.modeldriven.alf.mapping.fuml.statements.gen.StatementMapping;

import org.modeldriven.alf.syntax.statements.DoStatement;

import fUML.Syntax.Classes.Kernel.Element;

import java.util.ArrayList;
import java.util.List;

public class DoStatementMapping extends StatementMapping {

	public DoStatementMapping() {
		this.setErrorMessage("DoStatementMapping not yet implemented.");
	}

	public List<Element> getModelElements() {
		// TODO: Auto-generated stub
		return new ArrayList<Element>();
	}

	public DoStatement getDoStatement() {
		return (DoStatement) this.getSource();
	}

} // DoStatementMapping
