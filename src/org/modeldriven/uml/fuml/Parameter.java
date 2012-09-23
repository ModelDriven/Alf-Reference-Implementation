package org.modeldriven.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.Operation;
import org.modeldriven.uml.fuml.TypedElement;
import org.modeldriven.uml.fuml.ValueSpecification;

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
		return this.getBase().direction.toString();
	}

	public void setDirection(String direction) {
		this.getBase().setDirection(
				fUML.Syntax.Classes.Kernel.ParameterDirectionKind
						.valueOf(direction));
	}

	public org.modeldriven.alf.uml.Operation getOperation() {
		return new Operation(this.getBase().operation);
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

	public int getLower() {
		return this.getBase().multiplicityElement.lower;
	}

	public org.modeldriven.alf.uml.ValueSpecification getUpperValue() {
		return new ValueSpecification(
				this.getBase().multiplicityElement.upperValue);
	}

	public void setUpperValue(org.modeldriven.alf.uml.ValueSpecification upperValue) {
		this.getBase().setUpperValue(
				((ValueSpecification) upperValue).getBase());
	}

	public org.modeldriven.alf.uml.ValueSpecification getLowerValue() {
		return new ValueSpecification(
				this.getBase().multiplicityElement.lowerValue);
	}

	public void setLowerValue(org.modeldriven.alf.uml.ValueSpecification lowerValue) {
		this.getBase().setLowerValue(
				((ValueSpecification) lowerValue).getBase());
	}

}
