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
		return wrap(this.getBase().getType());
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
