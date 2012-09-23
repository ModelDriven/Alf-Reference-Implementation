package org.modeldriven.uml.alf.fuml;


public class Pin extends ObjectNode implements org.modeldriven.alf.uml.Pin {

	public Pin(fUML.Syntax.Actions.BasicActions.Pin base) {
		super(base);
	}

	public fUML.Syntax.Actions.BasicActions.Pin getBase() {
		return (fUML.Syntax.Actions.BasicActions.Pin) this.base;
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
