
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

import java.util.ArrayList;

/**
 * A specification of the elements of a sequence as a range of integers.
 **/

public class SequenceRange extends SequenceElements implements ISequenceRange {

	private IExpression rangeLower = null;
	private IExpression rangeUpper = null;

	public IExpression getRangeLower() {
		return this.rangeLower;
	}

	public void setRangeLower(IExpression rangeLower) {
		this.rangeLower = rangeLower;
	}

	public IExpression getRangeUpper() {
		return this.rangeUpper;
	}

	public void setRangeUpper(IExpression rangeUpper) {
		this.rangeUpper = rangeUpper;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		IExpression rangeLower = this.getRangeLower();
		if (rangeLower != null) {
			rangeLower.print(prefix + " ");
		}
		IExpression rangeUpper = this.getRangeUpper();
		if (rangeUpper != null) {
			rangeUpper.print(prefix + " ");
		}
	}
} // SequenceRange
