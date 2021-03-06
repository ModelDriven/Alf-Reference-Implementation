/*******************************************************************************
 * Copyright 2011, 2018 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.units;

import org.modeldriven.alf.parser.ParsedElement;
import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import java.util.Collection;
import org.modeldriven.alf.syntax.units.impl.PropertyDefinitionImpl;

/**
 * A typed element definition for a property (attribute or association end).
 **/

public class PropertyDefinition extends TypedElementDefinition {

	public PropertyDefinition() {
		this.impl = new PropertyDefinitionImpl(this);
	}

	public PropertyDefinition(Parser parser) {
		this();
		this.init(parser);
	}

	public PropertyDefinition(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
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
	 * expression must be assignable to the property definition. There are no
	 * assignments before an initializer expression.
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
	 * class whose sequence type is Integer.
	 **/
	public boolean propertyDefinitionIsBitStringConversionDerivation() {
		return this.getImpl()
				.propertyDefinitionIsBitStringConversionDerivation();
	}

	/**
	 * A property definition is a feature.
	 **/
	public boolean propertyDefinitionIsFeatureDerivation() {
		return this.getImpl().propertyDefinitionIsFeatureDerivation();
	}
	
    /**
     * If the initializer of a property definition is an instance creation
     * expression with no constructor, and the type of the property definition
     * is a class or (structured) data type, then the referent of the expression
     * is the type of the property definition. If the initializer of a property
     * definition is a sequence construction expression with no type name, but
     * with non-empty elements, then the type of the expression is the type of
     * the property definition and the expression has multiplicity if and only
     * if the multiplicity upper bound of the property definition is greater
     * than 1.
     */
	public boolean propertyDefinitionInitializerType() {
	    return this.getImpl().propertyDefinitionInitializerType();
	}

	/**
	 * Returns true if the annotation is for a stereotype that has a metaclass
	 * consistent with Property.
	 **/
	@Override
    public Boolean annotationAllowed(StereotypeAnnotation annotation) {
		return this.getImpl().annotationAllowed(annotation);
	}

	/**
	 * Return true if the given member is either a PropertyDefinition or an
	 * imported member whose referent is a PropertyDefinition or a Property.
	 **/
	@Override
    public Boolean isSameKindAs(Member member) {
		return this.getImpl().isSameKindAs(member);
	}

    @Override
    public void _addExternalReferences(Collection<ExternalElementReference> references) {
        super._addExternalReferences(references);
        addExternalReferencesFor(references, this.getInitializer());
    }

	@Override
    public void _deriveAll() {
		this.getIsCollectionConversion();
		this.getIsBitStringConversion();
		super._deriveAll();
		Expression initializer = this.getInitializer();
		if (initializer != null) {
			initializer.deriveAll();
		}
	}

	@Override
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
		if (!this.propertyDefinitionIsBitStringConversionDerivation()) {
			violations.add(new ConstraintViolation(
					"propertyDefinitionIsBitStringConversionDerivation", this));
		}
		if (!this.propertyDefinitionIsFeatureDerivation()) {
			violations.add(new ConstraintViolation(
					"propertyDefinitionIsFeatureDerivation", this));
		}
        if (!this.propertyDefinitionInitializerType()) {
            violations.add(new ConstraintViolation(
                    "propertyDefinitionInitializerType", this));
        }
		Expression initializer = this.getInitializer();
		if (initializer != null) {
			initializer.checkConstraints(violations);
		}
	}

	@Override
    public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		s.append(" isComposite:");
		s.append(this.getIsComposite());
		if (includeDerived) {
			s.append(" /isCollectionConversion:");
			s.append(this.getIsCollectionConversion());
		}
		if (includeDerived) {
			s.append(" /isBitStringConversion:");
			s.append(this.getIsBitStringConversion());
		}
		return s.toString();
	}

	@Override
    public void print() {
		this.print("", false);
	}

	@Override
    public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	@Override
    public void print(String prefix, boolean includeDerived) {
		super.print(prefix, includeDerived);
		Expression initializer = this.getInitializer();
		if (initializer != null) {
			System.out.println(prefix + " initializer:");
			initializer.print(prefix + "  ", includeDerived);
		}
	}
} // PropertyDefinition
