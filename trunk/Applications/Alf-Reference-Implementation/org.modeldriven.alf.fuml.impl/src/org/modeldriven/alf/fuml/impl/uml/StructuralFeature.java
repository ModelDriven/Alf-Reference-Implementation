/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fuml.impl.uml;


public abstract class StructuralFeature extends Feature implements
		org.modeldriven.alf.uml.StructuralFeature {

	public StructuralFeature(fUML.Syntax.Classes.Kernel.StructuralFeature base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.StructuralFeature getBase() {
		return (fUML.Syntax.Classes.Kernel.StructuralFeature) this.base;
	}

	public boolean getIsReadOnly() {
		return this.getBase().isReadOnly;
	}

	public void setIsReadOnly(boolean isReadOnly) {
		this.getBase().setIsReadOnly(isReadOnly);
	}

	public org.modeldriven.alf.uml.Type getType() {
		return (Type)wrap(this.getBase().typedElement.type);
	}

	public void setType(org.modeldriven.alf.uml.Type type) {
		this.getBase().setType(type==null? null: ((Type) type).getBase());
	}

	public boolean getIsOrdered() {
		return this.getBase().multiplicityElement.isOrdered;
	}

	public void setIsOrdered(boolean isOrdered) {
		this.getBase().setIsOrdered(isOrdered);
	}

	public boolean getIsUnique() {
		return this.getBase().multiplicityElement.isUnique;
	}

	public void setIsUnique(boolean isUnique) {
		this.getBase().setIsUnique(isUnique);
	}

	public int getUpper() {
		return this.getBase().multiplicityElement.upper.naturalValue;
	}

    public void setUpper(int upper) {
        this.getBase().setUpper(upper);
    }

    public int getLower() {
        return this.getBase().multiplicityElement.lower;
    }

    public void setLower(int lower) {
        this.getBase().setLower(lower);
    }

	public org.modeldriven.alf.uml.ValueSpecification getUpperValue() {
		return (ValueSpecification)wrap(
				this.getBase().multiplicityElement.upperValue);
	}

	public void setUpperValue(org.modeldriven.alf.uml.ValueSpecification upperValue) {
		this.getBase().setUpperValue(
				((ValueSpecification) upperValue).getBase());
	}

	public org.modeldriven.alf.uml.ValueSpecification getLowerValue() {
		return (ValueSpecification)wrap(
				this.getBase().multiplicityElement.lowerValue);
	}

	public void setLowerValue(org.modeldriven.alf.uml.ValueSpecification lowerValue) {
		this.getBase().setLowerValue(
				((ValueSpecification) lowerValue).getBase());
	}

}
