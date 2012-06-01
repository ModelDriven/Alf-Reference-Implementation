
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl;

import java.util.List;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.common.impl.SyntaxElementImpl;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

/**
 * A list of type names used to provide arguments for the parameters of a
 * template.
 **/

public abstract class TemplateBindingImpl extends SyntaxElementImpl {

	public TemplateBindingImpl(TemplateBinding self) {
		super(self);
	}

	@Override
	public TemplateBinding getSelf() {
		return (TemplateBinding) this.self;
	}
	
	/*
	 * Helper Methods
	 */
	
	public abstract List<ElementReference> getArgumentReferents(
	        List<ElementReference> templateParameters, 
	        NamespaceDefinition currentScope);

    public abstract void setCurrentScope(NamespaceDefinition currentScope);

    public TemplateBinding update(
            List<ElementReference> templateParameters,
            List<ElementReference> templateArguments) {
        return (TemplateBinding)this.bind(templateParameters, templateArguments);
    }

} // TemplateBindingImpl
