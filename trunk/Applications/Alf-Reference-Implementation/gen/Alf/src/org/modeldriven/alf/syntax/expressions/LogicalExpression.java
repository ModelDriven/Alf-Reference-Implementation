
/*
 * Copyright 2010 Data Access Technologies, Inc. (Model Driven Solutions)
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
 * A binary expression with a logical operator.
 **/

public class LogicalExpression extends BinaryExpression {

	private boolean isBitWise = false; // DERIVED
	private boolean isBitStringConversion1 = false; // DERIVED
	private boolean isBitStringConversion2 = false; // DERIVED

	public boolean getIsBitWise() {
		return this.isBitWise;
	}

	public void setIsBitWise(boolean isBitWise) {
		this.isBitWise = isBitWise;
	}

	public boolean getIsBitStringConversion1() {
		return this.isBitStringConversion1;
	}

	public void setIsBitStringConversion1(boolean isBitStringConversion1) {
		this.isBitStringConversion1 = isBitStringConversion1;
	}

	public boolean getIsBitStringConversion2() {
		return this.isBitStringConversion2;
	}

	public void setIsBitStringConversion2(boolean isBitStringConversion2) {
		this.isBitStringConversion2 = isBitStringConversion2;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
	}
} // LogicalExpression
