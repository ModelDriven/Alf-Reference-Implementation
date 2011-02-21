
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions.impl.gen;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.omg.uml.*;

import java.util.ArrayList;

/**
 * A specification of the substitution of an argument type name for a template
 * parameter.
 **/

public class TemplateParameterSubstitutionImpl extends
		org.modeldriven.alf.syntax.common.impl.gen.SyntaxElementImpl {

	public TemplateParameterSubstitutionImpl(TemplateParameterSubstitution self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.expressions.TemplateParameterSubstitution getSelf() {
		return (TemplateParameterSubstitution) this.self;
	}

} // TemplateParameterSubstitutionImpl
