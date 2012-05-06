
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl;

import java.util.List;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.common.SyntaxElement;
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

    public TemplateParameterSubstitution update(
            List<ElementReference> templateParameters,
            List<ElementReference> templateArguments) {
        TemplateParameterSubstitution self = this.getSelf();
        TemplateParameterSubstitution substitution = new TemplateParameterSubstitution();
        substitution.setParameterName(self.getParameterName());
        QualifiedName argumentName = self.getArgumentName();
        if (argumentName != null) {
            substitution.setArgumentName(argumentName.getImpl().
                    updateForBinding(templateParameters, templateArguments));
        }
        return substitution;
    }

    public void setCurrentScope(NamespaceDefinition currentScope) {
        TemplateParameterSubstitution self = this.getSelf();
        QualifiedName argumentName = self.getArgumentName();
        if (argumentName != null) {
            argumentName.getImpl().setCurrentScope(currentScope);
        }
    }

    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof TemplateParameterSubstitution) {
            TemplateParameterSubstitution self = this.getSelf();
            TemplateParameterSubstitution baseSubstitution = 
                (TemplateParameterSubstitution)base;
            QualifiedName argumentName = baseSubstitution.getArgumentName();
            self.setParameterName(baseSubstitution.getParameterName());
            if (argumentName != null) {
                self.setArgumentName(argumentName.getImpl().
                        updateBindings(templateParameters, templateArguments));
            }
        }
    }
    
} // TemplateParameterSubstitutionImpl
