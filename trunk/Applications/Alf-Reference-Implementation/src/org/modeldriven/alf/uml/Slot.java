package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.InstanceSpecification;
import org.modeldriven.alf.uml.ValueSpecification;
import org.modeldriven.uml.Element;
import org.modeldriven.uml.StructuralFeature;

public interface Slot extends Element {
	public InstanceSpecification getOwningInstance();

	public StructuralFeature getDefiningFeature();

	public void setDefiningFeature(StructuralFeature definingFeature);

	public List<ValueSpecification> getValue();

	public void addValue(ValueSpecification value);
}
