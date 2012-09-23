package org.modeldriven.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.Feature;
import org.modeldriven.uml.fuml.Type;
import org.modeldriven.uml.fuml.ValueSpecification;

public class StructuralFeature extends Feature implements
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
		return new Type(this.getBase().typedElement.type);
	}

	public void setType(org.modeldriven.alf.uml.Type type) {
		this.getBase().setType(((Type) type).getBase());
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
