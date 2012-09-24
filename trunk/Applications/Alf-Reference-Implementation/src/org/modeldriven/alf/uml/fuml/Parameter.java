/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.uml.fuml;


public class Parameter extends TypedElement implements
		org.modeldriven.alf.uml.Parameter {
	public Parameter() {
		this(new fUML.Syntax.Classes.Kernel.Parameter());
	}

	public Parameter(fUML.Syntax.Classes.Kernel.Parameter base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.Parameter getBase() {
		return (fUML.Syntax.Classes.Kernel.Parameter) this.base;
	}

	public String getDirection() {
		String direction = this.getBase().direction.toString();
		return direction == null? null: "return_".equals(direction)? "return": direction;
	}

	public void setDirection(String direction) {
		this.getBase().setDirection(direction == null? null:
				fUML.Syntax.Classes.Kernel.ParameterDirectionKind
						.valueOf("return".equals(direction)? "return_": direction));
	}

	public org.modeldriven.alf.uml.Operation getOperation() {
		return (Operation)this.wrap(this.getBase().operation);
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
		return (ValueSpecification)this.wrap(
				this.getBase().multiplicityElement.upperValue);
	}

	public void setUpperValue(org.modeldriven.alf.uml.ValueSpecification upperValue) {
		this.getBase().setUpperValue(
				((ValueSpecification) upperValue).getBase());
	}

	public org.modeldriven.alf.uml.ValueSpecification getLowerValue() {
		return (ValueSpecification)this.wrap(
				this.getBase().multiplicityElement.lowerValue);
	}

	public void setLowerValue(org.modeldriven.alf.uml.ValueSpecification lowerValue) {
		this.getBase().setLowerValue(
				((ValueSpecification) lowerValue).getBase());
	}

}
