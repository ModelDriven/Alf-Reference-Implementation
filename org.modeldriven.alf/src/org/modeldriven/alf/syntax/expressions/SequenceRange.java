
/*******************************************************************************
 * Copyright 2011, 2018 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions;

import java.util.Collection;

import org.modeldriven.alf.parser.ParsedElement;
import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.syntax.common.ConstraintViolation;
import org.modeldriven.alf.syntax.common.ExternalElementReference;
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
		this.init(parser);
	}

	public SequenceRange(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
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

    @Override
    public void _addExternalReferences(Collection<ExternalElementReference> references) {
        super._addExternalReferences(references);
        addExternalReferencesFor(references, this.getRangeLower());
        addExternalReferencesFor(references, this.getRangeUpper());
    }

	@Override
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

	@Override
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

	@Override
    public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		return s.toString();
	}

	@Override
    public void print() {
		this.print("", false);
	}

	@Override
    public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	@Override
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
