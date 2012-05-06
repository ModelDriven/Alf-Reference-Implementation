
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.common.impl.gen;

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
 * An assignment of a source element that gives the value of a local name, along
 * with a record of the defined type (if any) and multiplicity of the local
 * name.
 **/

public class AssignedSourceImpl {

	private String name = "";
	private SyntaxElement source = null;
	private Integer upper = 0;
	private Integer lower = 0;
	private ElementReference type = null;

	protected AssignedSource self;

	public AssignedSourceImpl(AssignedSource self) {
		this.self = self;
	}

	public String toString(boolean includeDerived) {
		return this.getSelf()._toString(includeDerived);
	}

	public void deriveAll() {
		this.getSelf()._deriveAll();
	}

	public AssignedSource getSelf() {
		return (AssignedSource) this.self;
	}

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

	public ElementReference getType() {
		return this.type;
	}

	public void setType(ElementReference type) {
		this.type = type;
	}

} // AssignedSourceImpl
