package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class Pin extends ObjectNode implements org.modeldriven.alf.uml.Pin {

	public Pin(org.eclipse.uml2.uml.Pin base) {
		super(base);
	}

	public org.eclipse.uml2.uml.Pin getBase() {
		return (org.eclipse.uml2.uml.Pin) this.base;
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
