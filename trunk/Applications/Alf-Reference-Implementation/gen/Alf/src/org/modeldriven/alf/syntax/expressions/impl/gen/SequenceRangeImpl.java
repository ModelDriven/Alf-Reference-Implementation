
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

/**
 * A specification of the elements of a sequence as a range of integers.
 **/

public class SequenceRangeImpl extends
		org.modeldriven.alf.syntax.expressions.impl.gen.SequenceElementsImpl {

	private Expression rangeLower = null;
	private Expression rangeUpper = null;

	public SequenceRangeImpl(SequenceRange self) {
		super(self);
	}

	public SequenceRange getSelf() {
		return (SequenceRange) this.self;
	}

	public Expression getRangeLower() {
		return this.rangeLower;
	}

	public void setRangeLower(Expression rangeLower) {
		this.rangeLower = rangeLower;
	}

	public Expression getRangeUpper() {
		return this.rangeUpper;
	}

	public void setRangeUpper(Expression rangeUpper) {
		this.rangeUpper = rangeUpper;
	}

	/**
	 * The multiplicity lower bound of a sequence range is 0.
	 **/
	public boolean sequenceRangeLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	/**
	 * The multiplicity uper bound of a sequence range is * (since it is not
	 * possible, in general, to statically determine a more constrained upper
	 * bound).
	 **/
	public boolean sequenceRangeUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

} // SequenceRangeImpl
