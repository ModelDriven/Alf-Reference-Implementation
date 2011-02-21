
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.omg.uml.*;

import java.util.ArrayList;

import org.modeldriven.alf.syntax.expressions.impl.SequenceRangeImpl;

/**
 * A specification of the elements of a sequence as a range of integers.
 **/

public class SequenceRange extends SequenceElements {

	private Expression rangeLower = null;
	private Expression rangeUpper = null;

	public SequenceRange() {
		this.impl = new SequenceRangeImpl(this);
	}

	public SequenceRangeImpl getImpl() {
		return (SequenceRangeImpl) this.impl;
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
		return this.getImpl().sequenceRangeLowerDerivation();
	}

	/**
	 * The multiplicity uper bound of a sequence range is * (since it is not
	 * possible, in general, to statically determine a more constrained upper
	 * bound).
	 **/
	public boolean sequenceRangeUpperDerivation() {
		return this.getImpl().sequenceRangeUpperDerivation();
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		Expression rangeLower = this.getRangeLower();
		if (rangeLower != null) {
			System.out.println(prefix + " rangeLower:");
			rangeLower.print(prefix + "  ");
		}
		Expression rangeUpper = this.getRangeUpper();
		if (rangeUpper != null) {
			System.out.println(prefix + " rangeUpper:");
			rangeUpper.print(prefix + "  ");
		}
	}
} // SequenceRange
