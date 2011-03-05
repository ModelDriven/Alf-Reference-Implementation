
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

import org.modeldriven.alf.syntax.expressions.impl.SequenceElementsImpl;

/**
 * A specification of the elements of a sequence.
 **/

public abstract class SequenceElements extends SyntaxElement {

	private Integer upper = null; // DERIVED
	private Integer lower = null; // DERIVED

	public SequenceElementsImpl getImpl() {
		return (SequenceElementsImpl) this.impl;
	}

	public Integer getUpper() {
		if (this.upper == null) {
			this.setUpper(this.getImpl().deriveUpper());
		}
		return this.upper;
	}

	public void setUpper(Integer upper) {
		this.upper = upper;
	}

	public Integer getLower() {
		if (this.lower == null) {
			this.setLower(this.getImpl().deriveLower());
		}
		return this.lower;
	}

	public void setLower(Integer lower) {
		this.lower = lower;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		Integer upper = this.getUpper();
		if (upper != null) {
			s.append(" /upper:");
			s.append(upper);
		}
		Integer lower = this.getLower();
		if (lower != null) {
			s.append(" /lower:");
			s.append(lower);
		}
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
	}
} // SequenceElements
