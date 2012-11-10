
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.common.impl.gen;

import org.modeldriven.alf.parser.Parser;
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
 * A direct reference to a UML model element.
 **/

public class InternalElementReferenceImpl extends
		org.modeldriven.alf.syntax.common.impl.gen.ElementReferenceImpl {

	private SyntaxElement element = null;

	public InternalElementReferenceImpl(InternalElementReference self) {
		super(self);
	}

	public InternalElementReference getSelf() {
		return (InternalElementReference) this.self;
	}

	public SyntaxElement getElement() {
		return this.element;
	}

	public void setElement(SyntaxElement element) {
		this.element = element;
	}

} // InternalElementReferenceImpl
