
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions.impl.gen;

import org.modeldriven.alf.parser.AlfParser;
import org.modeldriven.alf.parser.Token;

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
 * An unqualified name, optionally with a template binding.
 **/

public class NameBindingImpl extends
		org.modeldriven.alf.syntax.common.impl.gen.SyntaxElementImpl {

	private TemplateBinding binding = null;
	private String name = "";

	public NameBindingImpl(NameBinding self) {
		super(self);
	}

	public NameBinding getSelf() {
		return (NameBinding) this.self;
	}

	public TemplateBinding getBinding() {
		return this.binding;
	}

	public void setBinding(TemplateBinding binding) {
		this.binding = binding;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

} // NameBindingImpl
