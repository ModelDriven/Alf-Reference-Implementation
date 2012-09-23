package org.modeldriven.alf.uml;

import java.util.List;

public interface InstanceSpecification extends NamedElement {
	public List<Classifier> getClassifier();

	public void addClassifier(Classifier classifier);

	public List<Slot> getSlot();

	public void addSlot(Slot slot);
}
