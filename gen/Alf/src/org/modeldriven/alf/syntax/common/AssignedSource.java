
/*
 * Copyright 2010 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.common;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

/**
 * An assignment of a source element that gives the value of a local name, along
 * with a record of the defined type (if any) and multiplicity of the local
 * name.
 **/

public class AssignedSource {

	private String name = "";
	private SyntaxElement source = null;
	private int upper = 0;
	private int lower = 0;
	private ElementReference type = null;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SyntaxElement getSource() {
		return this.source;
	}

	public void setSource(SyntaxElement source) {
		this.source = source;
	}

	public int getUpper() {
		return this.upper;
	}

	public void setUpper(int upper) {
		this.upper = upper;
	}

	public int getLower() {
		return this.lower;
	}

	public void setLower(int lower) {
		this.lower = lower;
	}

	public ElementReference getType() {
		return this.type;
	}

	public void setType(ElementReference type) {
		this.type = type;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(this.getClass().getSimpleName());
		s.append(" name:");
		s.append(this.name);
		s.append(" upper:");
		s.append(this.upper);
		s.append(" lower:");
		s.append(this.lower);
		return s.toString();
	}

	public void print(String prefix) {
		System.out.println(prefix + this.toString());
		if (this.source != null) {
			this.source.print(prefix + " ");
		}
		if (this.type != null) {
			this.type.print(prefix + " ");
		}
	}
} // AssignedSource
