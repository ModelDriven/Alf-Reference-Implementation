
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

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

	/**
	 * Both expression in a sequence range must have a multiplicity upper bound
	 * of 1.
	 **/
	public boolean sequenceRangeExpressionMultiplicity() {
		return true;
	}

	/**
	 * A local name may be defined or reassigned in at most one of the
	 * expressions of a sequence range.
	 **/
	public boolean sequenceRangeAssignments() {
		return true;
	}

} // SequenceRangeImpl
