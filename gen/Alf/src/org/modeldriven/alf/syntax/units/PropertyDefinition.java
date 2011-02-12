
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

import org.modeldriven.alf.syntax.units.impl.PropertyDefinitionImpl;

/**
 * A typed element definition for a property (attribute or association end).
 **/

public class PropertyDefinition extends TypedElementDefinition {

	private Boolean isComposite = false;
	private Expression initializer = null;
	private Boolean isCollectionConversion = null; // DERIVED
	private Boolean isBitStringConversion = null; // DERIVED

	public PropertyDefinition() {
		this.impl = new PropertyDefinitionImpl(this);
	}

	public PropertyDefinitionImpl getImpl() {
		return (PropertyDefinitionImpl) this.impl;
	}

	public Boolean getIsComposite() {
		return this.isComposite;
	}

	public void setIsComposite(Boolean isComposite) {
		this.isComposite = isComposite;
	}

	public Expression getInitializer() {
		return this.initializer;
	}

	public void setInitializer(Expression initializer) {
		this.initializer = initializer;
	}

	public Boolean getIsCollectionConversion() {
		if (this.isCollectionConversion == null) {
			this.isCollectionConversion = this.getImpl()
					.deriveIsCollectionConversion();
		}
		return this.isCollectionConversion;
	}

	public Boolean getIsBitStringConversion() {
		if (this.isBitStringConversion == null) {
			this.isBitStringConversion = this.getImpl()
					.deriveIsBitStringConversion();
		}
		return this.isBitStringConversion;
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

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
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

	public void print(String prefix) {
		super.print(prefix);
		Expression initializer = this.getInitializer();
		if (initializer != null) {
			initializer.print(prefix + " ");
		}
	}
} // PropertyDefinition
