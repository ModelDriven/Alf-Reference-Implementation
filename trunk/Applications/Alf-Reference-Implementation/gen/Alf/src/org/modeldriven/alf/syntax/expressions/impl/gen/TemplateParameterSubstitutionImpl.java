
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

import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.Profile;
import org.modeldriven.alf.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A specification of the substitution of an argument type name for a template
 * parameter.
 **/

public class TemplateParameterSubstitutionImpl extends
		org.modeldriven.alf.syntax.common.impl.gen.SyntaxElementImpl {

	private String parameterName = "";
	private QualifiedName argumentName = null;

	public TemplateParameterSubstitutionImpl(TemplateParameterSubstitution self) {
		super(self);
	}

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

} // TemplateParameterSubstitutionImpl
