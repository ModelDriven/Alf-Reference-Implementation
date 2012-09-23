package org.modeldriven.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.Element;
import org.modeldriven.uml.fuml.ValueSpecification;

public class MultiplicityElement extends Element implements
		org.modeldriven.alf.uml.MultiplicityElement {
	public MultiplicityElement() {
		this(new fUML.Syntax.Classes.Kernel.MultiplicityElement());
	}

	public MultiplicityElement(
			fUML.Syntax.Classes.Kernel.MultiplicityElement base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.MultiplicityElement getBase() {
		return (fUML.Syntax.Classes.Kernel.MultiplicityElement) this.base;
	}

	public boolean getIsOrdered() {
		return this.getBase().isOrdered;
	}

	public void setIsOrdered(boolean isOrdered) {
		this.getBase().setIsOrdered(isOrdered);
	}

	public boolean getIsUnique() {
		return this.getBase().isUnique;
	}

	public void setIsUnique(boolean isUnique) {
		this.getBase().setIsUnique(isUnique);
	}

	public int getUpper() {
		return this.getBase().upper.naturalValue;
	}

	public int getLower() {
		return this.getBase().lower;
	}

	public org.modeldriven.alf.uml.ValueSpecification getUpperValue() {
		return new ValueSpecification(this.getBase().upperValue);
	}

	public void setUpperValue(org.modeldriven.alf.uml.ValueSpecification upperValue) {
		this.getBase().setUpperValue(
				((ValueSpecification) upperValue).getBase());
	}

	public org.modeldriven.alf.uml.ValueSpecification getLowerValue() {
		return new ValueSpecification(this.getBase().lowerValue);
	}

	public void setLowerValue(org.modeldriven.alf.uml.ValueSpecification lowerValue) {
		this.getBase().setLowerValue(
				((ValueSpecification) lowerValue).getBase());
	}

}
