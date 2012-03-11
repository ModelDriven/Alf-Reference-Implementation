
/*
 * Copyright 2011-2012 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.statements;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.statements.StatementMapping;

import org.modeldriven.alf.syntax.statements.InLineStatement;

import fUML.Syntax.Classes.Kernel.Element;

import java.util.ArrayList;
import java.util.Collection;

public class InLineStatementMapping extends StatementMapping {

	@Override
	public Collection<Element> getModelElements() throws MappingError {
		this.throwError("In-line statements are not implemented.");
		return new ArrayList<Element>();
	}

	public InLineStatement getInLineStatement() {
		return (InLineStatement) this.getSource();
	}

} // InLineStatementMapping
