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
		return wrap(this.getBase().getOperation());
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

	public int getLower() {
		return this.getBase().getLower();
	}

	public org.modeldriven.alf.uml.ValueSpecification getUpperValue() {
		return wrap(this.getBase().getUpperValue());
	}

	public void setUpperValue(
			org.modeldriven.alf.uml.ValueSpecification upperValue) {
		this.getBase().setUpperValue(
				upperValue == null ? null : ((ValueSpecification) upperValue)
						.getBase());
	}

	public org.modeldriven.alf.uml.ValueSpecification getLowerValue() {
		return wrap(this.getBase().getLowerValue());
	}

	public void setLowerValue(
			org.modeldriven.alf.uml.ValueSpecification lowerValue) {
		this.getBase().setLowerValue(
				lowerValue == null ? null : ((ValueSpecification) lowerValue)
						.getBase());
	}

}
