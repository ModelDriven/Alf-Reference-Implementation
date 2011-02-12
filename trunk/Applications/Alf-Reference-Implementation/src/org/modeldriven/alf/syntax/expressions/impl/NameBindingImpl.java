
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
 * An unqualified name, optionally with a template binding.
 **/

public class NameBindingImpl extends
		org.modeldriven.alf.syntax.common.impl.SyntaxElementImpl {

	public NameBindingImpl(NameBinding self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.expressions.NameBinding getSelf() {
		return (NameBinding) this.self;
	}

} // NameBindingImpl
