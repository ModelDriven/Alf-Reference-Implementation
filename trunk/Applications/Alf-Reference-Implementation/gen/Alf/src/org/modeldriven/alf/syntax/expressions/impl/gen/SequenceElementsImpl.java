
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

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
import java.util.TreeSet;

/**
 * A specification of the elements of a sequence.
 **/

public abstract class SequenceElementsImpl extends
		org.modeldriven.alf.syntax.common.impl.gen.SyntaxElementImpl {

	private Integer upper = null; // DERIVED
	private Integer lower = null; // DERIVED

	public SequenceElementsImpl(SequenceElements self) {
		super(self);
	}

	public SequenceElements getSelf() {
		return (SequenceElements) this.self;
	}

	public Integer getUpper() {
		if (this.upper == null) {
			this.setUpper(this.deriveUpper());
		}
		return this.upper;
	}

	public void setUpper(Integer upper) {
		this.upper = upper;
	}

	public Integer getLower() {
		if (this.lower == null) {
			this.setLower(this.deriveLower());
		}
		return this.lower;
	}

	public void setLower(Integer lower) {
		this.lower = lower;
	}

	protected Integer deriveUpper() {
		return null; // STUB
	}

	protected Integer deriveLower() {
		return null; // STUB
	}

} // SequenceElementsImpl
