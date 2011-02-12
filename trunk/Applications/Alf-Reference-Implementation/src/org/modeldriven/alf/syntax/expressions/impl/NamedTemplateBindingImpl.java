
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

/**
 * A template binding in which the arguments are matched to formal template
 * parameters by name.
 **/

public class NamedTemplateBindingImpl extends
		org.modeldriven.alf.syntax.expressions.impl.TemplateBindingImpl {

	public NamedTemplateBindingImpl(NamedTemplateBinding self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.expressions.NamedTemplateBinding getSelf() {
		return (NamedTemplateBinding) this.self;
	}

} // NamedTemplateBindingImpl
