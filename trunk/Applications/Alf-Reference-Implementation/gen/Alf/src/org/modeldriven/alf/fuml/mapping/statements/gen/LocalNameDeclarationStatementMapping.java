
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.fuml.mapping.statements.gen;

import org.modeldriven.alf.fuml.mapping.statements.gen.StatementMapping;

import org.modeldriven.alf.syntax.statements.LocalNameDeclarationStatement;

import org.modeldriven.alf.uml.Element;

import java.util.ArrayList;
import java.util.List;

public class LocalNameDeclarationStatementMapping extends StatementMapping {

	public LocalNameDeclarationStatementMapping() {
		this
				.setErrorMessage("LocalNameDeclarationStatementMapping not yet implemented.");
	}

	public List<Element> getModelElements() {
		// TODO: Auto-generated stub
		return new ArrayList<Element>();
	}

	public LocalNameDeclarationStatement getLocalNameDeclarationStatement() {
		return (LocalNameDeclarationStatement) this.getSource();
	}

} // LocalNameDeclarationStatementMapping
