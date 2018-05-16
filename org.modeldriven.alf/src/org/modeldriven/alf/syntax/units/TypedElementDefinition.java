
/*******************************************************************************
 * Copyright 2011, 2017 Data Access Technologies, Inc. (Model Driven Solutions)
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
import org.modeldriven.alf.syntax.units.impl.TypedElementDefinitionImpl;

/**
 * The common properties of the definitions of typed elements.
 **/

public abstract class TypedElementDefinition extends Member {

	public TypedElementDefinition() {
	}

	public TypedElementDefinition(Parser parser) {
		this();
		this.init(parser);
	}

	public TypedElementDefinition(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
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

    @Override
    public void _addExternalReferences(Collection<ExternalElementReference> references) {
        super._addExternalReferences(references);
        addExternalReferencesFor(references, this.getTypeName());
    }

	@Override
    public void _deriveAll() {
		this.getType();
		this.getLower();
		this.getUpper();
		super._deriveAll();
		QualifiedName typeName = this.getTypeName();
		if (typeName != null) {
			typeName.deriveAll();
		}
	}

	@Override
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

	@Override
    public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		s.append(" lowerBound:");
		s.append(this.getLowerBound());
		s.append(" upperBound:");
		s.append(this.getUpperBound());
		s.append(" isOrdered:");
		s.append(this.getIsOrdered());
		s.append(" isNonunique:");
		s.append(this.getIsNonunique());
		if (includeDerived) {
			s.append(" /lower:");
			s.append(this.getLower());
		}
		if (includeDerived) {
			s.append(" /upper:");
			s.append(this.getUpper());
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
		QualifiedName typeName = this.getTypeName();
		if (typeName != null) {
			System.out.println(prefix + " typeName:");
			typeName.print(prefix + "  ", includeDerived);
		}
		if (includeDerived) {
			ElementReference type = this.getType();
			if (type != null) {
				System.out.println(prefix + " /type:"
						+ type.toString(includeDerived));
			}
		}
	}
} // TypedElementDefinition
