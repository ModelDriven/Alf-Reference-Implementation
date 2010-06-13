
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

public class NameBinding extends SyntaxNode {

	private String name = "";
	private TemplateBinding binding = null;

	public void setName(String name) {
		this.name = name;
	} // setName

	public String getName() {
		return this.name;
	} // getName

	public void setBinding(TemplateBinding binding) {
		this.binding = binding;
	} // setBinding

	public TemplateBinding getBinding() {
		return this.binding;
	} // getBinding

	public String toString() {
		String s = this.getName();
		TemplateBinding b = this.getBinding();

		if (b != null) {
			s = s + b;
		}

		return s;
	} // toString

} // NameBinding
