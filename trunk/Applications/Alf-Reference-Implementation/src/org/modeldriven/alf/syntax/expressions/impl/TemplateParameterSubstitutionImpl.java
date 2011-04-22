
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.impl.SyntaxElementImpl;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

/**
 * A specification of the substitution of an argument type name for a template
 * parameter.
 **/

public class TemplateParameterSubstitutionImpl extends SyntaxElementImpl {

	private String parameterName = "";
	private QualifiedName argumentName = null;

	public TemplateParameterSubstitutionImpl(TemplateParameterSubstitution self) {
		super(self);
	}

	@Override
	public TemplateParameterSubstitution getSelf() {
		return (TemplateParameterSubstitution) this.self;
	}

	public String getParameterName() {
		return this.parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public QualifiedName getArgumentName() {
		return this.argumentName;
	}

	public void setArgumentName(QualifiedName argumentName) {
		this.argumentName = argumentName;
	}
	
	/*
	 * Helper Methods
	 */

    public void setCurrentScope(NamespaceDefinition currentScope) {
        TemplateParameterSubstitution self = this.getSelf();
        QualifiedName argumentName = self.getArgumentName();
        if (argumentName != null) {
            argumentName.getImpl().setCurrentScope(currentScope);
        }
    }

} // TemplateParameterSubstitutionImpl
