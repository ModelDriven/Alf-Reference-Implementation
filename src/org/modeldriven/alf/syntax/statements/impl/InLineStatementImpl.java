
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.statements.impl;

import java.util.List;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.common.SyntaxElement;
import org.modeldriven.alf.syntax.statements.*;


/**
 * A statement that executes code in a language other than Alf.
 **/

public class InLineStatementImpl extends StatementImpl {

	private String language = "";
	private String code = "";

	public InLineStatementImpl(InLineStatement self) {
		super(self);
	}

	@Override
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
	
	/*
	 * Constraints
	 */

	/**
	 * The assignments after an in-line statement are the same as the
	 * assignments before the statement.
	 **/
	public boolean inLineStatementAssignmentsAfter() {
	    // Note: This is handled by the inherited deriveAssignmentAfter.
		return true;
	}

    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof InLineStatement) {
           InLineStatement self = this.getSelf();
           InLineStatement baseStatement = (InLineStatement)base;
           self.setLanguage(baseStatement.getLanguage());
           self.setCode(baseStatement.getCode());
        }
    }
    
} // InLineStatementImpl
