
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.statements.gen;

import org.modeldriven.alf.mapping.fuml.common.gen.DocumentedElementMapping;

import org.modeldriven.alf.syntax.statements.Statement;

public abstract class StatementMapping extends DocumentedElementMapping {

	public Statement getStatement() {
		return (Statement) this.getSource();
	}

} // StatementMapping
