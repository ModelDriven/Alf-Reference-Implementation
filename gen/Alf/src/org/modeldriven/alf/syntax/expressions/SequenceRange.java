
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

import org.omg.uml.Element;
import org.omg.uml.Profile;
import org.omg.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.expressions.impl.SequenceRangeImpl;

/**
 * A specification of the elements of a sequence as a range of integers.
 **/

public class SequenceRange extends SequenceElements {

	public SequenceRange() {
		this.impl = new SequenceRangeImpl(this);
	}

	public SequenceRangeImpl getImpl() {
		return (SequenceRangeImpl) this.impl;
	}

	public Expression getRangeLower() {
		return this.getImpl().getRangeLower();
	}

	public void setRangeLower(Expression rangeLower) {
		this.getImpl().setRangeLower(rangeLower);
	}

	public Expression getRangeUpper() {
		return this.getImpl().getRangeUpper();
	}

	public void setRangeUpper(Expression rangeUpper) {
		this.getImpl().setRangeUpper(rangeUpper);
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

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.sequenceRangeLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"sequenceRangeLowerDerivation", this));
		}
		if (!this.sequenceRangeUpperDerivation()) {
			violations.add(new ConstraintViolation(
					"sequenceRangeUpperDerivation", this));
		}
		Expression rangeLower = this.getRangeLower();
		if (rangeLower != null) {
			rangeLower.checkConstraints(violations);
		}
		Expression rangeUpper = this.getRangeUpper();
		if (rangeUpper != null) {
			rangeUpper.checkConstraints(violations);
		}
	}

	public String toString() {
		return this.getImpl().toString();
	}

	public String _toString() {
		StringBuffer s = new StringBuffer(super._toString());
		return s.toString();
	}

	public void print() {
		this.print("");
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
