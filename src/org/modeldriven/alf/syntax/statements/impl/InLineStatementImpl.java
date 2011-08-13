
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0
 * (http://www.opensource.org/licenses/afl-3.0.php)
 *
 */

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
