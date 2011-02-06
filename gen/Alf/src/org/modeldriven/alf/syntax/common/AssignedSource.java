
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
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

public class AssignedSource implements IAssignedSource {

	private String name = "";
	private ISyntaxElement source = null;
	private Integer upper = 0;
	private Integer lower = 0;
	private IElementReference type = null;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ISyntaxElement getSource() {
		return this.source;
	}

	public void setSource(ISyntaxElement source) {
		this.source = source;
	}

	public Integer getUpper() {
		return this.upper;
	}

	public void setUpper(Integer upper) {
		this.upper = upper;
	}

	public Integer getLower() {
		return this.lower;
	}

	public void setLower(Integer lower) {
		this.lower = lower;
	}

	public IElementReference getType() {
		return this.type;
	}

	public void setType(IElementReference type) {
		this.type = type;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(this.getClass().getSimpleName());
		s.append(" name:");
		s.append(this.getName());
		s.append(" upper:");
		s.append(this.getUpper());
		s.append(" lower:");
		s.append(this.getLower());
		return s.toString();
	}

	public void print(String prefix) {
		System.out.println(prefix + this.toString());
		ISyntaxElement source = this.getSource();
		if (source != null) {
			source.print(prefix + " ");
		}
		IElementReference type = this.getType();
		if (type != null) {
			type.print(prefix + " ");
		}
	}
} // AssignedSource
