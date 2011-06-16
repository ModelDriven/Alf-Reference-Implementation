
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
 * A template binding in which the arguments are matched to formal template
 * parameters by name.
 **/

public class NamedTemplateBindingImpl extends
		org.modeldriven.alf.syntax.expressions.impl.gen.TemplateBindingImpl {

	private Collection<TemplateParameterSubstitution> substitution = new ArrayList<TemplateParameterSubstitution>();

	public NamedTemplateBindingImpl(NamedTemplateBinding self) {
		super(self);
	}

	public NamedTemplateBinding getSelf() {
		return (NamedTemplateBinding) this.self;
	}

	public Collection<TemplateParameterSubstitution> getSubstitution() {
		return this.substitution;
	}

	public void setSubstitution(
			Collection<TemplateParameterSubstitution> substitution) {
		this.substitution = substitution;
	}

	public void addSubstitution(TemplateParameterSubstitution substitution) {
		this.substitution.add(substitution);
	}

} // NamedTemplateBindingImpl
