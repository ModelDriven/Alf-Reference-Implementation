
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
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

public abstract class TypedElementDefinition extends Member implements
		ITypedElementDefinition {

	private String lowerBound = "";
	private String upperBound = "1";
	private Boolean isOrdered = false;
	private Boolean isNonunique = false;
	private IQualifiedName typeName = null;

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

	public Boolean getIsOrdered() {
		return this.isOrdered;
	}

	public void setIsOrdered(Boolean isOrdered) {
		this.isOrdered = isOrdered;
	}

	public Boolean getIsNonunique() {
		return this.isNonunique;
	}

	public void setIsNonunique(Boolean isNonunique) {
		this.isNonunique = isNonunique;
	}

	public IQualifiedName getTypeName() {
		return this.typeName;
	}

	public void setTypeName(IQualifiedName typeName) {
		this.typeName = typeName;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" lowerBound:");
		s.append(this.getLowerBound());
		s.append(" upperBound:");
		s.append(this.getUpperBound());
		s.append(" isOrdered:");
		s.append(this.getIsOrdered());
		s.append(" isNonunique:");
		s.append(this.getIsNonunique());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		IQualifiedName typeName = this.getTypeName();
		if (typeName != null) {
			typeName.print(prefix + " ");
		}
	}
} // TypedElementDefinition
