
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units.impl.gen;

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

/**
 * A typed element definition for a property (attribute or association end).
 **/

public class PropertyDefinitionImpl extends
		org.modeldriven.alf.syntax.units.impl.gen.TypedElementDefinitionImpl {

	private Boolean isComposite = false;
	private Expression initializer = null;
	private Boolean isCollectionConversion = null; // DERIVED
	private Boolean isBitStringConversion = null; // DERIVED

	public PropertyDefinitionImpl(PropertyDefinition self) {
		super(self);
	}

	public PropertyDefinition getSelf() {
		return (PropertyDefinition) this.self;
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
			this.setIsCollectionConversion(this.deriveIsCollectionConversion());
		}
		return this.isCollectionConversion;
	}

	public void setIsCollectionConversion(Boolean isCollectionConversion) {
		this.isCollectionConversion = isCollectionConversion;
	}

	public Boolean getIsBitStringConversion() {
		if (this.isBitStringConversion == null) {
			this.setIsBitStringConversion(this.deriveIsBitStringConversion());
		}
		return this.isBitStringConversion;
	}

	public void setIsBitStringConversion(Boolean isBitStringConversion) {
		this.isBitStringConversion = isBitStringConversion;
	}

	protected Boolean deriveIsCollectionConversion() {
		return null; // STUB
	}

	protected Boolean deriveIsBitStringConversion() {
		return null; // STUB
	}

	/**
	 * If a property definition has an initializer, then the initializer
	 * expression must be assignable to the property definition.
	 **/
	public boolean propertyDefinitionInitializer() {
		return true;
	}

	/**
	 * A property definition requires collection conversion if its initializer
	 * has a collection class as its type and the property definition does not.
	 **/
	public boolean propertyDefinitionIsCollectionConversionDerivation() {
		this.getSelf().getIsCollectionConversion();
		return true;
	}

	/**
	 * A property definition requires BitString conversion if its type is
	 * BitString and the type of its initializer is Integer or a collection
	 * class whose argument type is Integer.
	 **/
	public boolean propertyDefinitionIsBitStringConversion() {
		return true;
	}

	/**
	 * A property definition is a feature.
	 **/
	public boolean propertyDefinitionIsFeatureDerivation() {
		this.getSelf().getIsFeature();
		return true;
	}

	/**
	 * Returns true if the annotation is for a stereotype that has a metaclass
	 * consistent with Property.
	 **/
	public Boolean annotationAllowed(StereotypeAnnotation annotation) {
		return false; // STUB
	} // annotationAllowed

	/**
	 * Return true if the given member is either a PropertyDefinition or an
	 * imported member whose referent is a PropertyDefinition or a Property.
	 **/
	public Boolean isSameKindAs(Member member) {
		return false; // STUB
	} // isSameKindAs

} // PropertyDefinitionImpl
