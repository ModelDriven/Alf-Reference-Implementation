package org.modeldriven.alf.uml;

import java.util.List;

public interface Slot extends Element {
	public InstanceSpecification getOwningInstance();

	public StructuralFeature getDefiningFeature();

	public void setDefiningFeature(StructuralFeature definingFeature);

	public List<ValueSpecification> getValue();

	public void addValue(ValueSpecification value);
}
