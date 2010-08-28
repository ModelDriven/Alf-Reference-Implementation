
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
 * A binary expression with a relational operator.
 **/

public class RelationalExpression extends BinaryExpression {

	private boolean isUnlimitedNatural = false; // DERIVED

	public boolean getIsUnlimitedNatural() {
		return this.isUnlimitedNatural;
	}

	public void setIsUnlimitedNatural(boolean isUnlimitedNatural) {
		this.isUnlimitedNatural = isUnlimitedNatural;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
	}
} // RelationalExpression
