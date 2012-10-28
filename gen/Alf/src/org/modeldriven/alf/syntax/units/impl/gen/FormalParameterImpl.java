
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.units.impl.gen;

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
import java.util.TreeSet;

/**
 * A typed element definition for the formal parameter of an activity or
 * operation.
 **/

public class FormalParameterImpl extends
		org.modeldriven.alf.syntax.units.impl.gen.TypedElementDefinitionImpl {

	private String direction = "";

	public FormalParameterImpl(FormalParameter self) {
		super(self);
	}

	public FormalParameter getSelf() {
		return (FormalParameter) this.self;
	}

	public String getDirection() {
		return this.direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	/**
	 * Returns true if the annotation is for a stereotype that has a metaclass
	 * consistent with Parameter.
	 **/
	public Boolean annotationAllowed(StereotypeAnnotation annotation) {
		return false; // STUB
	} // annotationAllowed

	/**
	 * Return true if the given member is a FormalParameter.
	 **/
	public Boolean isSameKindAs(Member member) {
		return false; // STUB
	} // isSameKindAs

} // FormalParameterImpl
