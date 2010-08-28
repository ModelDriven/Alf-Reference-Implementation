
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
 * An expression used to reduce a sequence of values effectively by inserting a
 * binary operation between the values.
 **/

public class SequenceReductionExpression extends Expression {

	private ElementReference referent = null; // DERIVED
	private boolean isOrdered = false;
	private ExtentOrExpression primary = null;
	private QualifiedName behaviorName = null;

	public ElementReference getReferent() {
		return this.referent;
	}

	public void setReferent(ElementReference referent) {
		this.referent = referent;
	}

	public boolean getIsOrdered() {
		return this.isOrdered;
	}

	public void setIsOrdered(boolean isOrdered) {
		this.isOrdered = isOrdered;
	}

	public ExtentOrExpression getPrimary() {
		return this.primary;
	}

	public void setPrimary(ExtentOrExpression primary) {
		this.primary = primary;
	}

	public QualifiedName getBehaviorName() {
		return this.behaviorName;
	}

	public void setBehaviorName(QualifiedName behaviorName) {
		this.behaviorName = behaviorName;
	}

	public ArrayList<AssignedSource> updateAssignments() {
		/*
		 * The assignments after a sequence reduction expression are the same as
		 * after its primary expression.
		 */
		return new ArrayList<AssignedSource>(); // STUB
	} // updateAssignments

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" isOrdered:");
		s.append(this.isOrdered);
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		if (this.primary != null) {
			this.primary.print(prefix + " ");
		}
		if (this.behaviorName != null) {
			this.behaviorName.print(prefix + " ");
		}
	}
} // SequenceReductionExpression
