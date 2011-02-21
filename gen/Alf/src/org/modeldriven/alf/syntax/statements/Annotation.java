
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.statements;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.omg.uml.*;

import java.util.ArrayList;

import org.modeldriven.alf.syntax.statements.impl.AnnotationImpl;

/**
 * An identified modification to the behavior of an annotated statement.
 **/

public class Annotation extends SyntaxElement {

	private String identifier = "";
	private ArrayList<String> argument = new ArrayList<String>();

	public Annotation() {
		this.impl = new AnnotationImpl(this);
	}

	public AnnotationImpl getImpl() {
		return (AnnotationImpl) this.impl;
	}

	public String getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public ArrayList<String> getArgument() {
		return this.argument;
	}

	public void setArgument(ArrayList<String> argument) {
		this.argument = argument;
	}

	public void addArgument(String argument) {
		this.argument.add(argument);
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" identifier:");
		s.append(this.getIdentifier());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		ArrayList<String> argument = this.getArgument();
		if (argument != null) {
			if (argument.size() > 0) {
				System.out.println(prefix + " argument:");
			}
			for (String item : this.getArgument()) {
				System.out.println(prefix + "  " + item);
			}
		}
	}
} // Annotation
