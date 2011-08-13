
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

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
