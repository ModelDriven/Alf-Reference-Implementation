/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class StructuralFeature extends Feature implements
		org.modeldriven.alf.uml.StructuralFeature {

	public StructuralFeature(org.eclipse.uml2.uml.StructuralFeature base) {
		super(base);
	}

	public org.eclipse.uml2.uml.StructuralFeature getBase() {
		return (org.eclipse.uml2.uml.StructuralFeature) this.base;
	}

	public boolean getIsReadOnly() {
		return this.getBase().isReadOnly();
	}

	public void setIsReadOnly(boolean isReadOnly) {
		this.getBase().setIsReadOnly(isReadOnly);
	}

	public org.modeldriven.alf.uml.Type getType() {
		return (org.modeldriven.alf.uml.Type) wrap(this.getBase().getType());
	}

	public void setType(org.modeldriven.alf.uml.Type type) {
		this.getBase().setType(type == null ? null : ((Type) type).getBase());
	}

	public boolean getIsOrdered() {
		return this.getBase().isOrdered();
	}

	public void setIsOrdered(boolean isOrdered) {
		this.getBase().setIsOrdered(isOrdered);
	}

	public boolean getIsUnique() {
		return this.getBase().isUnique();
	}

	public void setIsUnique(boolean isUnique) {
		this.getBase().setIsUnique(isUnique);
	}

	public int getUpper() {
		return this.getBase().getUpper();
	}

    @Override
    public void setUpper(int upper) {
        this.getBase().setUpper(upper);
    }

	public int getLower() {
		return this.getBase().getLower();
	}

    @Override
    public void setLower(int lower) {
        this.getBase().setLower(lower);
    }

	public org.modeldriven.alf.uml.ValueSpecification getUpperValue() {
		return (org.modeldriven.alf.uml.ValueSpecification) wrap(this.getBase()
				.getUpperValue());
	}

	public void setUpperValue(
			org.modeldriven.alf.uml.ValueSpecification upperValue) {
		this.getBase().setUpperValue(
				upperValue == null ? null : ((ValueSpecification) upperValue)
						.getBase());
	}

	public org.modeldriven.alf.uml.ValueSpecification getLowerValue() {
		return (org.modeldriven.alf.uml.ValueSpecification) wrap(this.getBase()
				.getLowerValue());
	}

	public void setLowerValue(
			org.modeldriven.alf.uml.ValueSpecification lowerValue) {
		this.getBase().setLowerValue(
				lowerValue == null ? null : ((ValueSpecification) lowerValue)
						.getBase());
	}

}
