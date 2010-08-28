
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
 * A pairing of a parameter name and an argument expression in a tuple.
 **/

public class NamedExpression extends SyntaxElement {

	private String name = "";
	private Expression expression = null;
	private Expression index = null;
	private boolean isCollectionConversion = false; // DERIVED
	private boolean isBitStringConverstion = false; // DERIVED

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Expression getExpression() {
		return this.expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	public Expression getIndex() {
		return this.index;
	}

	public void setIndex(Expression index) {
		this.index = index;
	}

	public boolean getIsCollectionConversion() {
		return this.isCollectionConversion;
	}

	public void setIsCollectionConversion(boolean isCollectionConversion) {
		this.isCollectionConversion = isCollectionConversion;
	}

	public boolean getIsBitStringConverstion() {
		return this.isBitStringConverstion;
	}

	public void setIsBitStringConverstion(boolean isBitStringConverstion) {
		this.isBitStringConverstion = isBitStringConverstion;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" name:");
		s.append(this.name);
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		if (this.expression != null) {
			this.expression.print(prefix + " ");
		}
		if (this.index != null) {
			this.index.print(prefix + " ");
		}
	}
} // NamedExpression
