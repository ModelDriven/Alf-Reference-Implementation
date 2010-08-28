
/*
 * Copyright 2010 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

/**
 * The common properties of the definitions of typed elements.
 **/

public abstract class TypedElementDefinition extends Member {

	private String lowerBound = "";
	private String upperBound = "1";
	private boolean isOrdered = false;
	private boolean isNonunique = false;
	private QualifiedName typeName = null;
	private ElementReference type = null; // DERIVED
	private int lower = 0; // DERIVED
	private int upper = 0; // DERIVED

	public String getLowerBound() {
		return this.lowerBound;
	}

	public void setLowerBound(String lowerBound) {
		this.lowerBound = lowerBound;
	}

	public String getUpperBound() {
		return this.upperBound;
	}

	public void setUpperBound(String upperBound) {
		this.upperBound = upperBound;
	}

	public boolean getIsOrdered() {
		return this.isOrdered;
	}

	public void setIsOrdered(boolean isOrdered) {
		this.isOrdered = isOrdered;
	}

	public boolean getIsNonunique() {
		return this.isNonunique;
	}

	public void setIsNonunique(boolean isNonunique) {
		this.isNonunique = isNonunique;
	}

	public QualifiedName getTypeName() {
		return this.typeName;
	}

	public void setTypeName(QualifiedName typeName) {
		this.typeName = typeName;
	}

	public ElementReference getType() {
		return this.type;
	}

	public void setType(ElementReference type) {
		this.type = type;
	}

	public int getLower() {
		return this.lower;
	}

	public void setLower(int lower) {
		this.lower = lower;
	}

	public int getUpper() {
		return this.upper;
	}

	public void setUpper(int upper) {
		this.upper = upper;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" lowerBound:");
		s.append(this.lowerBound);
		s.append(" upperBound:");
		s.append(this.upperBound);
		s.append(" isOrdered:");
		s.append(this.isOrdered);
		s.append(" isNonunique:");
		s.append(this.isNonunique);
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		if (this.typeName != null) {
			this.typeName.print(prefix + " ");
		}
	}
} // TypedElementDefinition
