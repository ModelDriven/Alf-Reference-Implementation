
/*
 * Copyright 2010 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

/**
 * An unqualified name, optionally with a template binding.
 **/

public class NameBinding extends SyntaxElement {

	private TemplateBinding binding = null;
	private String name = "";

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

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" name:");
		s.append(this.name);
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		if (this.binding != null) {
			this.binding.print(prefix + " ");
		}
	}
} // NameBinding
