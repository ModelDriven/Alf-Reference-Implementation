
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.statements.impl.gen;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.omg.uml.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * An identified modification to the behavior of an annotated statement.
 **/

public class AnnotationImpl extends
		org.modeldriven.alf.syntax.common.impl.gen.SyntaxElementImpl {

	private String identifier = "";
	private Collection<String> argument = new ArrayList<String>();

	public AnnotationImpl(Annotation self) {
		super(self);
	}

	public Annotation getSelf() {
		return (Annotation) this.self;
	}

	public String getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public Collection<String> getArgument() {
		return this.argument;
	}

	public void setArgument(Collection<String> argument) {
		this.argument = argument;
	}

	public void addArgument(String argument) {
		this.argument.add(argument);
	}

} // AnnotationImpl
