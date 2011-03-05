
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

import org.omg.uml.*;

import java.util.ArrayList;

import org.modeldriven.alf.syntax.units.impl.TypedElementDefinitionImpl;

/**
 * The common properties of the definitions of typed elements.
 **/

public abstract class TypedElementDefinition extends Member {

	private String lowerBound = "";
	private String upperBound = "1";
	private Boolean isOrdered = false;
	private Boolean isNonunique = false;
	private QualifiedName typeName = null;
	private ElementReference type = null; // DERIVED
	private Integer lower = null; // DERIVED
	private Integer upper = null; // DERIVED

	public TypedElementDefinitionImpl getImpl() {
		return (TypedElementDefinitionImpl) this.impl;
	}

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

	public QualifiedName getTypeName() {
		return this.typeName;
	}

	public void setTypeName(QualifiedName typeName) {
		this.typeName = typeName;
	}

	public ElementReference getType() {
		if (this.type == null) {
			this.setType(this.getImpl().deriveType());
		}
		return this.type;
	}

	public void setType(ElementReference type) {
		this.type = type;
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

	public Integer getUpper() {
		if (this.upper == null) {
			this.setUpper(this.getImpl().deriveUpper());
		}
		return this.upper;
	}

	public void setUpper(Integer upper) {
		this.upper = upper;
	}

	/**
	 * If the lower bound string image of a typed element definition is not
	 * empty, then the integer lower bound is the integer value of the lower
	 * bound string. Otherwise the lower bound is equal to the upper bound,
	 * unless the upper bound is unbounded, in which case the lower bound is 0.
	 **/
	public boolean typedElementDefinitionLowerDerivation() {
		return this.getImpl().typedElementDefinitionLowerDerivation();
	}

	/**
	 * The unlimited natural upper bound value is the unlimited natural value of
	 * the uper bound string (with "*" representing the unbounded value).
	 **/
	public boolean typedElementDefinitionUpperDerivation() {
		return this.getImpl().typedElementDefinitionUpperDerivation();
	}

	/**
	 * The type of a typed element definition is the single classifier referent
	 * of the type name.
	 **/
	public boolean typedElementDefinitionTypeDerivation() {
		return this.getImpl().typedElementDefinitionTypeDerivation();
	}

	/**
	 * The type name of a typed element definition must have a single classifier
	 * referent. This referent may not be a template.
	 **/
	public boolean typedElementDefinitionTypeName() {
		return this.getImpl().typedElementDefinitionTypeName();
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
		Integer lower = this.getLower();
		if (lower != null) {
			s.append(" /lower:");
			s.append(lower);
		}
		Integer upper = this.getUpper();
		if (upper != null) {
			s.append(" /upper:");
			s.append(upper);
		}
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		QualifiedName typeName = this.getTypeName();
		if (typeName != null) {
			System.out.println(prefix + " typeName:");
			typeName.print(prefix + "  ");
		}
		ElementReference type = this.getType();
		if (type != null) {
			System.out.println(prefix + " /type:" + type);
		}
	}
} // TypedElementDefinition
