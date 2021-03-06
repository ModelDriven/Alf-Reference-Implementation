
/*******************************************************************************
 * Copyright 2011, 2013 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.statements;

import org.modeldriven.alf.fuml.mapping.statements.StatementMapping;

import org.modeldriven.alf.syntax.statements.EmptyStatement;

public class EmptyStatementMapping extends StatementMapping {

    /**
     * An empty statement maps to an empty structured activity node.
     */
    
    public EmptyStatement getEmptyStatement() {
		return (EmptyStatement) this.getSource();
	}

} // EmptyStatementMapping
