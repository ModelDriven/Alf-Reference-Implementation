package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class Parameter extends TypedElement implements
		org.modeldriven.alf.uml.Parameter {
	public Parameter() {
		this(UMLFactory.eINSTANCE.createParameter());
	}

	public Parameter(fUML.Syntax.Classes.Kernel.Parameter base) {
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
				fUML.Syntax.Classes.Kernel.ParameterDirectionKind
						.valueOf(direction));
	}

	public org.modeldriven.alf.uml.Operation getOperation() {
		return new Operation(this.getBase().getOperation());
	}

	public boolean getIsOrdered() {
		return this.getBase().getIsOrdered();
	}

	public void setIsOrdered(boolean isOrdered) {
		this.getBase().setIsOrdered(isOrdered);
	}

	public boolean getIsUnique() {
		return this.getBase().getIsUnique();
	}

	public void setIsUnique(boolean isUnique) {
		this.getBase().setIsUnique(isUnique);
	}

	public int getUpper() {
		return this.getBase().getUpper();
	}

	public int getLower() {
		return this.getBase().getLower();
	}

	public org.modeldriven.alf.uml.ValueSpecification getUpperValue() {
		return new ValueSpecification(this.getBase().getUpperValue());
	}

	public void setUpperValue(
			org.modeldriven.alf.uml.ValueSpecification upperValue) {
		this.getBase().setUpperValue(
				upperValue == null ? null : ((ValueSpecification) upperValue)
						.getBase());
	}

	public org.modeldriven.alf.uml.ValueSpecification getLowerValue() {
		return new ValueSpecification(this.getBase().getLowerValue());
	}

	public void setLowerValue(
			org.modeldriven.alf.uml.ValueSpecification lowerValue) {
		this.getBase().setLowerValue(
				lowerValue == null ? null : ((ValueSpecification) lowerValue)
						.getBase());
	}

}
