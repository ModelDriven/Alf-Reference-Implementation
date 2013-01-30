
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions;

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

import org.modeldriven.alf.syntax.expressions.impl.SequenceRangeImpl;

/**
 * A specification of the elements of a sequence as a range of integers.
 **/

public class SequenceRange extends SequenceElements {

	public SequenceRange() {
		this.impl = new SequenceRangeImpl(this);
	}

	public SequenceRange(Parser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public SequenceRange(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
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

	/**
	 * Both expression in a sequence range must have a multiplicity upper bound
	 * of 1.
	 **/
	public boolean sequenceRangeExpressionMultiplicity() {
		return this.getImpl().sequenceRangeExpressionMultiplicity();
	}

	/**
	 * A local name may be defined or reassigned in at most one of the
	 * expressions of a sequence range.
	 **/
	public boolean sequenceRangeAssignments() {
		return this.getImpl().sequenceRangeAssignments();
	}

	public void _deriveAll() {
		super._deriveAll();
		Expression rangeLower = this.getRangeLower();
		if (rangeLower != null) {
			rangeLower.deriveAll();
		}
		Expression rangeUpper = this.getRangeUpper();
		if (rangeUpper != null) {
			rangeUpper.deriveAll();
		}
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
		if (!this.sequenceRangeExpressionMultiplicity()) {
			violations.add(new ConstraintViolation(
					"sequenceRangeExpressionMultiplicity", this));
		}
		if (!this.sequenceRangeAssignments()) {
			violations.add(new ConstraintViolation("sequenceRangeAssignments",
					this));
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

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		return s.toString();
	}

	public void print() {
		this.print("", false);
	}

	public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	public void print(String prefix, boolean includeDerived) {
		super.print(prefix, includeDerived);
		Expression rangeLower = this.getRangeLower();
		if (rangeLower != null) {
			System.out.println(prefix + " rangeLower:");
			rangeLower.print(prefix + "  ", includeDerived);
		}
		Expression rangeUpper = this.getRangeUpper();
		if (rangeUpper != null) {
			System.out.println(prefix + " rangeUpper:");
			rangeUpper.print(prefix + "  ", includeDerived);
		}
	}
} // SequenceRange
