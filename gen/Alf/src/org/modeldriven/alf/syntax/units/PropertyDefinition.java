
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
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.units.impl.PropertyDefinitionImpl;

/**
 * A typed element definition for a property (attribute or association end).
 **/

public class PropertyDefinition extends TypedElementDefinition {

	public PropertyDefinition() {
		this.impl = new PropertyDefinitionImpl(this);
	}

	public PropertyDefinitionImpl getImpl() {
		return (PropertyDefinitionImpl) this.impl;
	}

	public Boolean getIsComposite() {
		return this.getImpl().getIsComposite();
	}

	public void setIsComposite(Boolean isComposite) {
		this.getImpl().setIsComposite(isComposite);
	}

	public Expression getInitializer() {
		return this.getImpl().getInitializer();
	}

	public void setInitializer(Expression initializer) {
		this.getImpl().setInitializer(initializer);
	}

	public Boolean getIsCollectionConversion() {
		return this.getImpl().getIsCollectionConversion();
	}

	public void setIsCollectionConversion(Boolean isCollectionConversion) {
		this.getImpl().setIsCollectionConversion(isCollectionConversion);
	}

	public Boolean getIsBitStringConversion() {
		return this.getImpl().getIsBitStringConversion();
	}

	public void setIsBitStringConversion(Boolean isBitStringConversion) {
		this.getImpl().setIsBitStringConversion(isBitStringConversion);
	}

	/**
	 * If a property definition has an initializer, then the initializer
	 * expression must be assignable to the property definition.
	 **/
	public boolean propertyDefinitionInitializer() {
		return this.getImpl().propertyDefinitionInitializer();
	}

	/**
	 * A property definition requires collection conversion if its initializer
	 * has a collection class as its type and the property definition does not.
	 **/
	public boolean propertyDefinitionIsCollectionConversionDerivation() {
		return this.getImpl()
				.propertyDefinitionIsCollectionConversionDerivation();
	}

	/**
	 * A property definition requires BitString conversion if its type is
	 * BitString and the type of its initializer is Integer or a collection
	 * class whose argument type is Integer.
	 **/
	public boolean propertyDefinitionIsBitStringConversion() {
		return this.getImpl().propertyDefinitionIsBitStringConversion();
	}

	/**
	 * A property definition is a feature.
	 **/
	public boolean propertyDefinitionIsFeatureDerivation() {
		return this.getImpl().propertyDefinitionIsFeatureDerivation();
	}

	/**
	 * Returns true if the annotation is for a stereotype that has a metaclass
	 * consistent with Property.
	 **/
	public Boolean annotationAllowed(StereotypeAnnotation annotation) {
		return this.getImpl().annotationAllowed(annotation);
	}

	/**
	 * Return true if the given member is either a PropertyDefinition or an
	 * imported member whose referent is a PropertyDefinition or a Property.
	 **/
	public Boolean isSameKindAs(Member member) {
		return this.getImpl().isSameKindAs(member);
	}

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.propertyDefinitionInitializer()) {
			violations.add(new ConstraintViolation(
					"propertyDefinitionInitializer", this));
		}
		if (!this.propertyDefinitionIsCollectionConversionDerivation()) {
			violations
					.add(new ConstraintViolation(
							"propertyDefinitionIsCollectionConversionDerivation",
							this));
		}
		if (!this.propertyDefinitionIsBitStringConversion()) {
			violations.add(new ConstraintViolation(
					"propertyDefinitionIsBitStringConversion", this));
		}
		if (!this.propertyDefinitionIsFeatureDerivation()) {
			violations.add(new ConstraintViolation(
					"propertyDefinitionIsFeatureDerivation", this));
		}
		Expression initializer = this.getInitializer();
		if (initializer != null) {
			initializer.checkConstraints(violations);
		}
	}

	public String toString() {
		return this.getImpl().toString();
	}

	public String _toString() {
		StringBuffer s = new StringBuffer(super._toString());
		s.append(" isComposite:");
		s.append(this.getIsComposite());
		Boolean isCollectionConversion = this.getIsCollectionConversion();
		if (isCollectionConversion != null) {
			s.append(" /isCollectionConversion:");
			s.append(isCollectionConversion);
		}
		Boolean isBitStringConversion = this.getIsBitStringConversion();
		if (isBitStringConversion != null) {
			s.append(" /isBitStringConversion:");
			s.append(isBitStringConversion);
		}
		return s.toString();
	}

	public void print() {
		this.print("");
	}

	public void print(String prefix) {
		super.print(prefix);
		Expression initializer = this.getInitializer();
		if (initializer != null) {
			System.out.println(prefix + " initializer:");
			initializer.print(prefix + "  ");
		}
	}
} // PropertyDefinition
