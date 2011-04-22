
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

    public abstract void setCurrentScope(NamespaceDefinition currentScope);

} // TemplateBindingImpl
