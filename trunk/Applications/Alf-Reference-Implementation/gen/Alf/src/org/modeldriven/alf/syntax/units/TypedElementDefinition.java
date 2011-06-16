
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

import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.Profile;
import org.modeldriven.alf.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.units.impl.TypedElementDefinitionImpl;

/**
 * The common properties of the definitions of typed elements.
 **/

public abstract class TypedElementDefinition extends Member {

	public TypedElementDefinitionImpl getImpl() {
		return (TypedElementDefinitionImpl) this.impl;
	}

	public String getLowerBound() {
		return this.getImpl().getLowerBound();
	}

	public void setLowerBound(String lowerBound) {
		this.getImpl().setLowerBound(lowerBound);
	}

	public String getUpperBound() {
		return this.getImpl().getUpperBound();
	}

	public void setUpperBound(String upperBound) {
		this.getImpl().setUpperBound(upperBound);
	}

	public Boolean getIsOrdered() {
		return this.getImpl().getIsOrdered();
	}

	public void setIsOrdered(Boolean isOrdered) {
		this.getImpl().setIsOrdered(isOrdered);
	}

	public Boolean getIsNonunique() {
		return this.getImpl().getIsNonunique();
	}

	public void setIsNonunique(Boolean isNonunique) {
		this.getImpl().setIsNonunique(isNonunique);
	}

	public QualifiedName getTypeName() {
		return this.getImpl().getTypeName();
	}

	public void setTypeName(QualifiedName typeName) {
		this.getImpl().setTypeName(typeName);
	}

	public ElementReference getType() {
		return this.getImpl().getType();
	}

	public void setType(ElementReference type) {
		this.getImpl().setType(type);
	}

	public Integer getLower() {
		return this.getImpl().getLower();
	}

	public void setLower(Integer lower) {
		this.getImpl().setLower(lower);
	}

	public Integer getUpper() {
		return this.getImpl().getUpper();
	}

	public void setUpper(Integer upper) {
		this.getImpl().setUpper(upper);
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

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.typedElementDefinitionLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"typedElementDefinitionLowerDerivation", this));
		}
		if (!this.typedElementDefinitionUpperDerivation()) {
			violations.add(new ConstraintViolation(
					"typedElementDefinitionUpperDerivation", this));
		}
		if (!this.typedElementDefinitionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"typedElementDefinitionTypeDerivation", this));
		}
		if (!this.typedElementDefinitionTypeName()) {
			violations.add(new ConstraintViolation(
					"typedElementDefinitionTypeName", this));
		}
		QualifiedName typeName = this.getTypeName();
		if (typeName != null) {
			typeName.checkConstraints(violations);
		}
	}

	public String toString() {
		return "(" + this.hashCode() + ")" + this.getImpl().toString();
	}

	public String _toString() {
		StringBuffer s = new StringBuffer(super._toString());
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

	public void print() {
		this.print("");
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
