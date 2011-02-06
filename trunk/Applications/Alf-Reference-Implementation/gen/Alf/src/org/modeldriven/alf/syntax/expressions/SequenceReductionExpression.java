
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
 * An expression used to reduce a sequence of values effectively by inserting a
 * binary operation between the values.
 **/

public class SequenceReductionExpression extends Expression implements
		ISequenceReductionExpression {

	private Boolean isOrdered = false;
	private IExtentOrExpression primary = null;
	private IQualifiedName behaviorName = null;

	public Boolean getIsOrdered() {
		return this.isOrdered;
	}

	public void setIsOrdered(Boolean isOrdered) {
		this.isOrdered = isOrdered;
	}

	public IExtentOrExpression getPrimary() {
		return this.primary;
	}

	public void setPrimary(IExtentOrExpression primary) {
		this.primary = primary;
	}

	public IQualifiedName getBehaviorName() {
		return this.behaviorName;
	}

	public void setBehaviorName(IQualifiedName behaviorName) {
		this.behaviorName = behaviorName;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" isOrdered:");
		s.append(this.getIsOrdered());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		IExtentOrExpression primary = this.getPrimary();
		if (primary != null) {
			primary.print(prefix + " ");
		}
		IQualifiedName behaviorName = this.getBehaviorName();
		if (behaviorName != null) {
			behaviorName.print(prefix + " ");
		}
	}
} // SequenceReductionExpression
