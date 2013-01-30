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

public class Parameter extends TypedElement implements
		org.modeldriven.alf.uml.Parameter {
	public Parameter() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createParameter());
	}

	public Parameter(org.eclipse.uml2.uml.Parameter base) {
		super(base);
	}

	public org.eclipse.uml2.uml.Parameter getBase() {
		return (org.eclipse.uml2.uml.Parameter) this.base;
	}

	public String getDirection() {
		return this.getBase().getDirection().toString();
	}

	public void setDirection(String direction) {
		this.getBase().setDirection(
				org.eclipse.uml2.uml.ParameterDirectionKind.get(direction));
	}

	public org.modeldriven.alf.uml.Operation getOperation() {
		return (org.modeldriven.alf.uml.Operation) wrap(this.getBase()
				.getOperation());
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
